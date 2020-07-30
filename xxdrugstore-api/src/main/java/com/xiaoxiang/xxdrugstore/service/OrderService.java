package com.xiaoxiang.xxdrugstore.service;



import com.xiaoxiang.xxdrugstore.bean.OmsCartItem;
import com.xiaoxiang.xxdrugstore.bean.OmsOrder;

import java.util.List;


public interface OrderService {

    String checkTradeCode(String memberId, String tradeCode);

    String genTradeCode(String memberId);

    void saveOrder(OmsOrder omsOrder, List<OmsCartItem>  omsCartItems  );

    OmsOrder getOrderByOutTradeNo(String outTradeNo);

  void  updateOrder(OmsOrder omsOrder);
}
