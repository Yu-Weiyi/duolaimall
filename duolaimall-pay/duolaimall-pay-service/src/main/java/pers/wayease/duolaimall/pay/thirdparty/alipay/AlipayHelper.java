package pers.wayease.duolaimall.pay.thirdparty.alipay;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.domain.AlipayTradeCloseModel;
import com.alipay.api.domain.AlipayTradePagePayModel;
import com.alipay.api.domain.AlipayTradeQueryModel;
import com.alipay.api.request.AlipayTradeCloseRequest;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.response.AlipayTradeCloseResponse;
import com.alipay.api.response.AlipayTradePagePayResponse;
import com.alipay.api.response.AlipayTradeQueryResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import pers.wayease.duolaimall.common.constant.ResultCodeEnum;
import pers.wayease.duolaimall.common.exception.BaseException;
import pers.wayease.duolaimall.order.pojo.dto.OrderInfoDto;
import pers.wayease.duolaimall.pay.thirdparty.PayHelper;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author 为伊WaYease <a href="mailto:yu_weiyi@outlook.com">yu_weiyi@outlook.com</a>
 * @version 1.0
 * @project duolaimall
 * @package pers.wayease.duolaimall.pay.thirdparty.alipay
 * @name AlipayHelper
 * @description Alipay helper class.
 * @since 2024-10-19 06:08
 */
@Component
@Slf4j
public class AlipayHelper implements PayHelper {

    @Value("${alipay.config.return_order_url}")
    String returnPaymentUrl;
    @Value("${alipay.config.notify_payment_url}")
    String notifyPaymentUrl;

    @Autowired
    private AlipayClient alipayClient;

    @Override
    public String createClient(OrderInfoDto orderInfoDto) throws AlipayApiException {
        AlipayTradePagePayRequest alipayTradePagePayRequest = new AlipayTradePagePayRequest();
        AlipayTradePagePayModel alipayTradePagePayModel = new AlipayTradePagePayModel();

        alipayTradePagePayModel.setOutTradeNo(orderInfoDto.getOutTradeNo());
        alipayTradePagePayModel.setTotalAmount(String.valueOf(orderInfoDto.getTotalAmount()));
        alipayTradePagePayModel.setSubject(orderInfoDto.getTradeBody());
        alipayTradePagePayModel.setProductCode("FAST_INSTANT_TRADE_PAY");

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date originTime = orderInfoDto.getExpireTime();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(originTime);
        calendar.add(Calendar.HOUR_OF_DAY, 8);// time zone +8
        Date correctedTime = calendar.getTime();
        String expireTime = sdf.format(correctedTime);
        log.info("set expire time: {}.", expireTime);

        alipayTradePagePayModel.setTimeExpire(expireTime);

        alipayTradePagePayRequest.setReturnUrl(returnPaymentUrl);
        alipayTradePagePayRequest.setNotifyUrl(notifyPaymentUrl);

        alipayTradePagePayRequest.setBizModel(alipayTradePagePayModel);

        AlipayTradePagePayResponse alipayTradePagePayResponse = alipayClient.pageExecute(alipayTradePagePayRequest, "POST");
        if (alipayTradePagePayResponse.isSuccess()) {
            return alipayTradePagePayResponse.getBody();
        }else {
            throw new BaseException(ResultCodeEnum.FAIL);
        }
    }

    @Override
    public String getTradeStatus(String outTradeNo) throws AlipayApiException {
        AlipayTradeQueryRequest queryRequest = new AlipayTradeQueryRequest();
        AlipayTradeQueryModel queryModel = new AlipayTradeQueryModel();
        queryModel.setOutTradeNo(outTradeNo);
        queryRequest.setBizModel(queryModel);

        AlipayTradeQueryResponse queryResponse = alipayClient.execute(queryRequest);
        return queryResponse.getTradeStatus();
    }

    @Override
    public boolean closeAlipay(String outTradeNo) throws AlipayApiException {
        AlipayTradeCloseRequest closeRequest = new AlipayTradeCloseRequest();
        AlipayTradeCloseModel closeModel = new AlipayTradeCloseModel();
        closeModel.setOutTradeNo(outTradeNo);
        closeRequest.setBizModel(closeModel);

        AlipayTradeCloseResponse closeResponse = alipayClient.execute(closeRequest);
        return closeResponse.isSuccess();
    }
}
