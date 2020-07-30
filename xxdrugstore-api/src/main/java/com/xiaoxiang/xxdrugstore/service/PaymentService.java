package com.xiaoxiang.xxdrugstore.service;


import com.xiaoxiang.xxdrugstore.bean.PaymentInfo;

import java.util.Map;

public interface PaymentService {

    void savePaymentInfo(PaymentInfo paymentInfo);

    void updatePayment(PaymentInfo paymentInfo);

    void sendDelayPaymentResultCheckQueue(String outTradeNo, int i);

    Map<String, Object> checkAlipayPayment(String out_trade_no);

}
