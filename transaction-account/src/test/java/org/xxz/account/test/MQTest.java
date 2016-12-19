package org.xxz.account.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.xxz.base.components.mq.RocketMqConfig;

import com.alibaba.rocketmq.client.exception.MQBrokerException;
import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.client.producer.DefaultMQProducer;
import com.alibaba.rocketmq.common.message.Message;
import com.alibaba.rocketmq.remoting.exception.RemotingException;

@RunWith(SpringRunner.class)
@SpringBootTest
@Rollback
public class MQTest {

    @Autowired
    private DefaultMQProducer defaultMQProducer;
    @Autowired
    private RocketMqConfig rockerMqConfig;
    
    private String tag = "test";
    
    @Test
    public void contextLoads() {
    }

    @Test
    public void testSendMsg() throws InterruptedException, RemotingException, MQClientException, MQBrokerException {
        String content = "hello mq";
        Message message = new Message(rockerMqConfig.getTopic(), tag, "1", content.getBytes());
        defaultMQProducer.send(message);
    }

}
