package pers.wayease.duolaimall.pay.service.impl;

import com.alibaba.fastjson.JSON;
import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pers.wayease.duolaimall.common.constant.RedisConstant;
import pers.wayease.duolaimall.common.constant.ResultCodeEnum;
import pers.wayease.duolaimall.common.exception.BaseException;
import pers.wayease.duolaimall.common.result.Result;
import pers.wayease.duolaimall.order.pojo.constant.OrderStatusEnum;
import pers.wayease.duolaimall.order.pojo.dto.OrderInfoDto;
import pers.wayease.duolaimall.pay.client.OrderServiceClient;
import pers.wayease.duolaimall.pay.configuration.AlipayConfiguration;
import pers.wayease.duolaimall.pay.constant.PaymentStatusEnum;
import pers.wayease.duolaimall.pay.constant.PaymentTypeEnum;
import pers.wayease.duolaimall.pay.converter.PaymentInfoConverter;
import pers.wayease.duolaimall.pay.mapper.PaymentInfoMapper;
import pers.wayease.duolaimall.pay.pojo.dto.PaymentInfoDto;
import pers.wayease.duolaimall.pay.pojo.model.PaymentInfo;
import pers.wayease.duolaimall.pay.service.AlipayService;
import pers.wayease.duolaimall.pay.thirdparty.PayHelper;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

/**
 * @author 为伊WaYease <a href="mailto:yu_weiyi@outlook.com">yu_weiyi@outlook.com</a>
 * @version 1.0
 * @project duolaimall
 * @package pers.wayease.duolaimall.pay.service.impl
 * @name AlipayServiceImpl
 * @description Alipay service implement class.
 * @since 2024-10-19 04:47
 */
@Service
@Slf4j
public class AlipayServiceImpl implements AlipayService {

    @Autowired
    private AlipayConfiguration alipayConfiguration;

    @Autowired
    private PaymentInfoMapper paymentInfoMapper;

    @Autowired
    private PaymentInfoConverter paymentInfoConverter;

    @Autowired
    private OrderServiceClient orderServiceClient;

    @Autowired
    private PayHelper payHelper;

    @Autowired
    private RedissonClient redissonClient;

    @Override
    public String createAlipay(Long orderId) throws AlipayApiException {
        OrderInfoDto orderInfoDto = orderServiceClient.getOrderInfo(orderId).getData();
        if (orderInfoDto == null || !OrderStatusEnum.UNPAID.name().equals(orderInfoDto.getOrderStatus())) {
            throw new BaseException(ResultCodeEnum.FAIL);
        }
        LambdaQueryWrapper<PaymentInfo> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper
                .eq(PaymentInfo::getOrderId, orderId);
        Long orderCount = paymentInfoMapper.selectCount(lambdaQueryWrapper);
        if (orderCount == 0) {
            PaymentInfo paymentInfo = paymentInfoConverter.orderInfoDto2PaymentInfo(orderInfoDto);
            paymentInfo.setId(null);
            paymentInfo.setPaymentType(PaymentTypeEnum.ALIPAY.name());
            paymentInfo.setTradeNo(null);
            paymentInfo.setPaymentStatus(PaymentStatusEnum.UNPAID.name());

            int insertedRows = paymentInfoMapper.insert(paymentInfo);
            if (insertedRows == 0) {
                throw new BaseException(ResultCodeEnum.FAIL);
            }
        }

        String clientHtml = payHelper.createClient(orderInfoDto);
        return clientHtml;
    }

    @Override
    public String callbackNotify(Map<String, String> paramsMap) throws AlipayApiException {
        boolean rasCheck = AlipaySignature.rsaCheckV1(paramsMap, alipayConfiguration.getAlipayPublicKey(), AlipayConfiguration.CHARSET, alipayConfiguration.SIGN_TYPE);
        if (!rasCheck){
            return "failure";
        }
        String outTradeNo = paramsMap.get("out_trade_no");
        String notifyId = paramsMap.get("notify_id");
        String totalAmount = paramsMap.get("total_amount");
        String tradeStatus = paramsMap.get("trade_status");
        String appId = paramsMap.get("app_id");

        if (!alipayConfiguration.getAppId().equals(appId)){
            return "failure";
        }

        PaymentInfoDto paymentInfoDTO = queryPaymentInfoByOutTradeNoAndPaymentType(outTradeNo, PaymentTypeEnum.ALIPAY.name());

        if (paymentInfoDTO == null){
            log.warn("Payment info not found.");
            return "failure";
        }

        BigDecimal totalAmountNum = new BigDecimal(totalAmount);
        if (!paymentInfoDTO.getTotalAmount().equals(totalAmountNum) || !"TRADE_SUCCESS".equals(tradeStatus)) {
            return "failure";
        }

        // checked

        String key= RedisConstant.PAY_CALLBACK_NOTIFY + ":" + notifyId;
        RBucket<Object> bucket = redissonClient.getBucket(key);
        boolean successSet = bucket.trySet(outTradeNo);
        if (!successSet){
            // done before
            return "success";
        }

        // update

        Boolean updatePayRet = successPay(outTradeNo,PaymentTypeEnum.ALIPAY.name(),paramsMap);
        if (!updatePayRet){
            bucket.delete();
            return "failure";
        }
        return "success";
    }

    @Override
    public PaymentInfoDto getPaymentInfoDtoByOutTradeNo(String outTradeNo) {
        LambdaQueryWrapper<PaymentInfo> payInfoLambdaQueryWrapper = new LambdaQueryWrapper<>();
        payInfoLambdaQueryWrapper.eq(PaymentInfo::getOutTradeNo,outTradeNo);
        PaymentInfo paymentInfo = paymentInfoMapper.selectOne(payInfoLambdaQueryWrapper);
        PaymentInfoDto paymentInfoDto = paymentInfoConverter.paymentInfoPo2Dto(paymentInfo);
        return paymentInfoDto;
    }

    @Override
    public String getAlipayInfo(String outTradeNo) throws AlipayApiException {
        return payHelper.getTradeStatus(outTradeNo);
    }

    @Override
    public void closePaymentInfo(String outTradeNo) {
        PaymentInfoDto paymentInfoDTO = getPaymentInfoDtoByOutTradeNo(outTradeNo);
        PaymentInfo paymentInfo = new PaymentInfo();
        paymentInfo.setId(paymentInfoDTO.getId());
        paymentInfo.setPaymentStatus(PaymentStatusEnum.CLOSED.name());
        int updateRows = paymentInfoMapper.updateById(paymentInfo);
        if (updateRows<1){
            throw new BaseException(ResultCodeEnum.FAIL);
        }
    }

    private PaymentInfoDto queryPaymentInfoByOutTradeNoAndPaymentType(String outTradeNo, String payTypeName) {
        LambdaQueryWrapper<PaymentInfo> payInfoLambdaQueryWrapper = new LambdaQueryWrapper<>();
        payInfoLambdaQueryWrapper.eq(PaymentInfo::getOutTradeNo,outTradeNo);
        payInfoLambdaQueryWrapper.eq(PaymentInfo::getPaymentType, payTypeName);
        PaymentInfo paymentInfo = paymentInfoMapper.selectOne(payInfoLambdaQueryWrapper);
        return paymentInfoConverter.paymentInfoPo2Dto(paymentInfo);
    }

    private Boolean successPay(String outTradeNo, String name, Map<String, String> paramsMap) {
        LambdaQueryWrapper<PaymentInfo> paymentInfoWrapper=new LambdaQueryWrapper<>();
        paymentInfoWrapper.eq(PaymentInfo::getOutTradeNo,outTradeNo);
        paymentInfoWrapper.eq(PaymentInfo::getPaymentType,name);
        PaymentInfo paymentInfo = paymentInfoMapper.selectOne(paymentInfoWrapper);

        Date current = new Date();
        paymentInfo.setCallbackTime(current);
        paymentInfo.setCallbackContent(JSON.toJSONString(paramsMap));
        paymentInfo.setUpdateTime(current);
        paymentInfo.setTradeNo(paramsMap.get("trade_no"));
        paymentInfo.setPaymentStatus(PaymentStatusEnum.PAID.name());

        int updateRow = paymentInfoMapper.updateById(paymentInfo);
        if (updateRow < 1) {
            return false;
        }

        Result<Void> result = orderServiceClient.successPay(paymentInfo.getOrderId());
        if (!ResultCodeEnum.SUCCESS.getCode().equals(result.getCode())) {
            return false;
        }
        return true;
    }
}
