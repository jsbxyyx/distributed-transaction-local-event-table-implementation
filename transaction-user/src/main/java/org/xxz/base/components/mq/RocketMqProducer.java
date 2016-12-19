package org.xxz.base.components.mq;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.client.producer.DefaultMQProducer;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@ConfigurationProperties(prefix = "mq")
public class RocketMqProducer {
    
    @Value("${mq.producerGroup}")
    private String producerGroup;
    @Value("${mq.namesrvAddr}")
    private String namesrvAddr;
    
    @Getter
    private DefaultMQProducer producer;
    
    @PostConstruct
    public void init() throws MQClientException {
        log.info("==> rocketmq producer init!");
        producer = new DefaultMQProducer(producerGroup);
        producer.setNamesrvAddr(namesrvAddr);
        producer.setInstanceName(String.valueOf(System.currentTimeMillis()));
        producer.start();
    }

}
