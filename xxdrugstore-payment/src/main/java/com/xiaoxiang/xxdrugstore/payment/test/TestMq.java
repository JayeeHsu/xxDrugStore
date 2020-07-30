package com.xiaoxiang.xxdrugstore.payment.test;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQTextMessage;

import javax.jms.*;

public class TestMq {
    public static void main(String[] args) {

        ConnectionFactory connect = new ActiveMQConnectionFactory("tcp://localhost:61616");
        try {
            Connection connection = connect.createConnection();
            connection.start();
            //第一个值表示是否使用事务，如果选择true，第二个值相当于选择0
            Session session = connection.createSession(true, Session.SESSION_TRANSACTED);//开启事务
            Queue testqueue = session.createQueue("drink");//队列模式的消息，参数为消息名
            //Topic t=session.createTopic("");//话题模式

            //队列模式中的请求a发出来，假设有A B C三个consumer能够消费，也只有一个能消费到请求a，消费完请求a就出队且不再被消费，如果没有consumer则请求保留，直到有consumer将其消费
            //话题模式中的请求a发出来，假设有A B C三个consumer能够消费，则三个consumer均消费一次a，三个consumer的解决方案可能不同，等于一题多解，每次消费都出队，如果没有consumer则请求作废


            MessageProducer producer = session.createProducer(testqueue);
            TextMessage textMessage=new ActiveMQTextMessage();
            textMessage.setText("今天天气真好！");
            producer.setDeliveryMode(DeliveryMode.PERSISTENT);
            producer.send(textMessage);
            session.commit();//提交事务
            connection.close();//关闭连接

        } catch (JMSException e) {
            e.printStackTrace();
        }

    }
}
