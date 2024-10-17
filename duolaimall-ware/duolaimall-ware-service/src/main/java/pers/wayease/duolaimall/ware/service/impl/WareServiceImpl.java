package pers.wayease.duolaimall.ware.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pers.wayease.duolaimall.common.constant.ResultCodeEnum;
import pers.wayease.duolaimall.common.exception.BaseException;
import pers.wayease.duolaimall.order.pojo.dto.OrderInfoDto;
import pers.wayease.duolaimall.ware.client.OrderServiceClient;
import pers.wayease.duolaimall.ware.converter.WareOrderTaskConverter;
import pers.wayease.duolaimall.ware.mapper.WareOrderTaskDetailMapper;
import pers.wayease.duolaimall.ware.mapper.WareOrderTaskMapper;
import pers.wayease.duolaimall.ware.mapper.WareSkuMapper;
import pers.wayease.duolaimall.ware.pojo.constant.TaskStatusEnum;
import pers.wayease.duolaimall.ware.pojo.dto.WareOrderTaskDto;
import pers.wayease.duolaimall.ware.pojo.dto.WareSkuDto;
import pers.wayease.duolaimall.ware.pojo.model.WareOrderTask;
import pers.wayease.duolaimall.ware.pojo.model.WareOrderTaskDetail;
import pers.wayease.duolaimall.ware.pojo.model.WareSku;
import pers.wayease.duolaimall.ware.service.WareService;

import java.util.*;

/**
 * @author 为伊WaYease <a href="mailto:yu_weiyi@outlook.com">yu_weiyi@outlook.com</a>
 * @version 1.0
 * @project duolaimall
 * @package pers.wayease.duolaimall.ware.service.impl
 * @name WareServiceImpl
 * @description Ware service implement class.
 * @since 2024-10-15 20:57
 */
@Service
public class WareServiceImpl implements WareService {

    @Autowired
    private WareSkuMapper wareSkuMapper;
    @Autowired
    private WareOrderTaskMapper wareOrderTaskMapper;
    @Autowired
    private WareOrderTaskDetailMapper wareOrderTaskDetailMapper;

    @Autowired
    private WareOrderTaskConverter wareOrderTaskConverter;

    @Autowired
    private OrderServiceClient orderServiceClient;

    @Override
    public Boolean hasStock(Long skuId, Integer num) {
        Integer stock = wareSkuMapper.selectStockBySkuId(String.valueOf(skuId));
        if (stock == null || stock < num) {
            // stock not enough
            return false;
        }
        // stock enough
        return true;
    }

    @Override
    public void decreaseStock(Long orderId) {
        OrderInfoDto orderInfoDto = orderServiceClient.getOrderInfo(orderId).getData();
        WareOrderTask wareOrderTask = wareOrderTaskConverter.orderInfoDto2wareOrderTask(orderInfoDto);
        wareOrderTask.setTaskStatus(TaskStatusEnum.PAID.name());

        saveWareOrderTask(wareOrderTask);

        List<WareOrderTask> orderTaskList = checkOrderSplit(wareOrderTask);

        if (orderTaskList != null && orderTaskList.size() >= 2) {
            for (WareOrderTask orderTask : orderTaskList) {
                lockStock(orderTask);
            }
        } else {
            lockStock(wareOrderTask);
        }
    }

    @Transactional
    public List<WareOrderTask> checkOrderSplit(WareOrderTask wareOrderTask) {
        List<WareOrderTaskDetail> wareOrderTaskDetailList = wareOrderTask.getDetails();
        List<String> skuIdList = new ArrayList<>();
        for (WareOrderTaskDetail wareOrderTaskDetail : wareOrderTaskDetailList) {
            skuIdList.add(wareOrderTaskDetail.getSkuId());
        }
        List<WareSkuDto> wareSkuDtoList = getWareSkuDto(skuIdList);

        if(wareSkuDtoList.size() == 1) {
            WareSkuDto wareSkuDto = wareSkuDtoList.get(0);
            wareOrderTask.setWareId(wareSkuDto.getWareId());
        } else {
            String orderId = wareOrderTask.getOrderId();
            List<WareOrderTaskDto> wareOrderTaskDtoList = orderServiceClient.orderSplit(orderId, wareSkuDtoList).getData();
            List<WareOrderTask> wareOrderTaskList = wareOrderTaskConverter.wareOrderTaskDtoList2PoList(wareOrderTaskDtoList);
            if (wareOrderTaskDtoList.size() >= 2) {
                for (WareOrderTask subOrderTask : wareOrderTaskList) {
                    subOrderTask.setTaskStatus(TaskStatusEnum.DEDUCTED.name());
                    saveWareOrderTask(subOrderTask);
                }
                updateStatusWareOrderTaskByOrderId(wareOrderTask.getOrderId(), TaskStatusEnum.SPLIT);
                return wareOrderTaskList;
            } else {
                throw new BaseException(ResultCodeEnum.FAIL);
            }
        }
        return null;
    }

    public List<WareSkuDto> getWareSkuDto(List<String> skuIdList) {
        LambdaQueryWrapper<WareSku> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper
                .in(WareSku::getSkuId, skuIdList);
        List<WareSku> wareSkuList = wareSkuMapper.selectList(lambdaQueryWrapper);
        Map<String, List<String>> wareSkuMap = new HashMap<>();

        for (WareSku wareSku : wareSkuList) {
            List<String> wareSkuIdList = wareSkuMap.get(wareSku.getWarehouseId());
            if (wareSkuIdList == null) {
                wareSkuIdList = new ArrayList<>();
            }
            wareSkuIdList.add(wareSku.getSkuId());
            wareSkuMap.put(wareSku.getWarehouseId(), wareSkuIdList);
        }
        List<WareSkuDto> wareSkuDtoList = wareSkuMap.keySet().stream()
                .map(key -> {
                    WareSkuDto wareSkuDto = new WareSkuDto();
                    wareSkuDto.setWareId(key);
                    wareSkuDto.setSkuIds(wareSkuMap.get(key));
                    return wareSkuDto;
                })
                .toList();
        return wareSkuDtoList;
    }

    public List<Map<String, Object>> convertWareSkuMapList(Map<String, List<String>> wareSkuMap) {
        List<Map<String, Object>> wareSkuMapList = new ArrayList<>();
        for (Map.Entry<String, List<String>> entry : wareSkuMap.entrySet()) {
            Map<String, Object> skuWareMap = new HashMap<>();
            String wareid = entry.getKey();
            skuWareMap.put("wareId", wareid);
            List<String> skuids = entry.getValue();
            skuWareMap.put("skuIds", skuids);
            wareSkuMapList.add(skuWareMap);
        }
        return wareSkuMapList;
    }

    public void updateStatusWareOrderTaskByOrderId(String orderId, TaskStatusEnum taskStatusEnum) {
        QueryWrapper<WareOrderTask> queryWrapper = new QueryWrapper();
        queryWrapper.in("order_id", orderId);
        WareOrderTask wareOrderTask = new WareOrderTask();
        wareOrderTask.setTaskStatus(taskStatusEnum.name());
        wareOrderTaskMapper.update(wareOrderTask, queryWrapper);
    }

    @Transactional
    public void lockStock(WareOrderTask wareOrderTask) {

        List<WareOrderTaskDetail> wareOrderTaskDetails = wareOrderTask.getDetails();
        String comment = "";
        for (WareOrderTaskDetail wareOrderTaskDetail : wareOrderTaskDetails) {

            WareSku wareSku = new WareSku();
            wareSku.setWarehouseId(wareOrderTask.getWareId());
            wareSku.setStockLocked(wareOrderTaskDetail.getSkuNum());
            wareSku.setSkuId(wareOrderTaskDetail.getSkuId());

            int availableStock = wareSkuMapper.selectStockBySkuIdForUpdate(wareSku);
            if (availableStock - wareOrderTaskDetail.getSkuNum() < 0) {
                comment += "减库存异常：名称：" + wareOrderTaskDetail.getSkuName() + "，实际可用库存数" + availableStock + ",要求库存" + wareOrderTaskDetail.getSkuNum();
            }
        }

        if (comment.length() > 0) {
            wareOrderTask.setTaskComment(comment);
            wareOrderTask.setTaskStatus(TaskStatusEnum.OUT_OF_STOCK.name());
            updateStatusWareOrderTaskByOrderId(wareOrderTask.getOrderId(), TaskStatusEnum.OUT_OF_STOCK);

        } else {
            for (WareOrderTaskDetail wareOrderTaskDetail : wareOrderTaskDetails) {

                WareSku wareSku = new WareSku();
                wareSku.setWarehouseId(wareOrderTask.getWareId());
                wareSku.setStockLocked(wareOrderTaskDetail.getSkuNum());
                wareSku.setSkuId(wareOrderTaskDetail.getSkuId());

                wareSkuMapper.increaseStockLocked(wareSku); //  加行级写锁 注意索引避免表锁

            }
            wareOrderTask.setTaskStatus(TaskStatusEnum.DEDUCTED.name());
            updateStatusWareOrderTaskByOrderId(wareOrderTask.getOrderId(), TaskStatusEnum.DEDUCTED);
        }

        // 远程调用顶订单服务，修改订单状态
        orderServiceClient.successLockStock(wareOrderTask.getOrderId(),wareOrderTask.getTaskStatus());

        return;
    }

    @Transactional
    public WareOrderTask saveWareOrderTask(WareOrderTask wareOrderTask) {
        wareOrderTask.setCreateTime(new Date());

        QueryWrapper<WareOrderTask> queryWrapper = new QueryWrapper();
        queryWrapper.in("order_id", wareOrderTask.getOrderId());
        WareOrderTask wareOrderTaskOrigin = wareOrderTaskMapper.selectOne(queryWrapper);
        if (wareOrderTaskOrigin != null) {
            return wareOrderTaskOrigin;
        }

        wareOrderTaskMapper.insert(wareOrderTask);

        List<WareOrderTaskDetail> wareOrderTaskDetails = wareOrderTask.getDetails();
        for (WareOrderTaskDetail wareOrderTaskDetail : wareOrderTaskDetails) {
            wareOrderTaskDetail.setTaskId(wareOrderTask.getId());
            wareOrderTaskDetailMapper.insert(wareOrderTaskDetail);
        }
        return wareOrderTask;
    }
}
