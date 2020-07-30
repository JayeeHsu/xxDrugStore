package com.xiaoxiang.xxdrugstore.payment;


import com.xiaoxiang.xxdrugstore.mq.ActiveMQUtil;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.jms.*;

@SpringBootTest
class XxdrugstorePaymentApplicationTests {

    @Autowired
    ActiveMQUtil activeMQUtil;

    @Test
    void contextLoads() throws JMSException {
        ConnectionFactory connectionFactory=activeMQUtil.getConnectionFactory();

        Connection connection = connectionFactory.createConnection();
        System.out.println(connection);


    }

}
