package com.xiaoxiang.xxware.mq;

import com.alibaba.fastjson.JSON;


import com.xiaoxiang.xxware.bean.OmsOrder;
import com.xiaoxiang.xxware.bean.OmsOrderItem;

import com.xiaoxiang.xxware.bean.WmsWareOrderTask;
import com.xiaoxiang.xxware.bean.WmsWareOrderTaskDetail;
import com.xiaoxiang.xxware.enums.TaskStatus;

import com.xiaoxiang.xxware.mapper.WmsWareOrderTaskDetailMapper;
import com.xiaoxiang.xxware.mapper.WmsWareOrderTaskMapper;
import com.xiaoxiang.xxware.mapper.WmsWareSkuMapper;
import com.xiaoxiang.xxware.service.XXwareService;
import com.xiaoxiang.xxware.util.ActiveMQUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.TextMessage;
import java.util.*;

/**
 * @param
 * @return
 */
@Component
public class WareConsumer {

    @Autowired
    WmsWareOrderTaskMapper wareOrderTaskMapper;

    @Autowired
    WmsWareOrderTaskDetailMapper wareOrderTaskDetailMapper;

    @Autowired
    WmsWareSkuMapper wareSkuMapper;

    @Autowired
    ActiveMQUtil activeMQUtil;

    @Autowired
    JmsTemplate jmsTemplate;

    @Autowired
    XXwareService xxwareService;

    @JmsListener(destination = "ORDER_PAY_QUEUE", containerFactory = "jmsQueueListener")
    public void receiveOrder(TextMessage textMessage) throws JMSException {
        String orderTaskJson = textMessage.getText();

        /***
         * 转化并保存订单对象
         */
        OmsOrder orderInfo = JSON.parseObject(orderTaskJson, OmsOrder.class);

        // 将order订单对象转为订单任务对象
        WmsWareOrderTask wareOrderTask = new WmsWareOrderTask();
        wareOrderTask.setConsignee(orderInfo.getReceiverName());
        wareOrderTask.setConsigneeTel(orderInfo.getReceiverPhone());
        wareOrderTask.setCreateTime(new Date());
        wareOrderTask.setDeliveryAddress(orderInfo.getReceiverDetailAddress());
        wareOrderTask.setOrderId(orderInfo.getId());
        ArrayList<WmsWareOrderTaskDetail> wmsWareOrderTaskDetails = new ArrayList<>();

        // 打开订单的商品集合
        List<OmsOrderItem> orderDetailList = orderInfo.getOmsOrderItems();
        for (OmsOrderItem orderDetail : orderDetailList) {
            WmsWareOrderTaskDetail wmsWareOrderTaskDetail = new WmsWareOrderTaskDetail();

            wmsWareOrderTaskDetail.setSkuId(orderDetail.getProductSkuId());
            wmsWareOrderTaskDetail.setSkuName(orderDetail.getProductName());
            wmsWareOrderTaskDetail.setSkuNum(orderDetail.getProductQuantity());
            wmsWareOrderTaskDetails.add(wmsWareOrderTaskDetail);

        }
        wareOrderTask.setDetails(wmsWareOrderTaskDetails);
        wareOrderTask.setTaskStatus(TaskStatus.PAID);
        xxwareService.saveWareOrderTask(wareOrderTask);

        textMessage.acknowledge();

        // 检查该交易的商品是否有拆单需求
        List<WmsWareOrderTask> wareSubOrderTaskList = xxwareService.checkOrderSplit(wareOrderTask);// 检查拆单

        // 库存削减
        if (wareSubOrderTaskList != null && wareSubOrderTaskList.size() >= 2) {
            for (WmsWareOrderTask orderTask : wareSubOrderTaskList) {
                xxwareService.lockStock(orderTask);
            }
        } else {
            xxwareService.lockStock(wareOrderTask);
        }


    }

}
