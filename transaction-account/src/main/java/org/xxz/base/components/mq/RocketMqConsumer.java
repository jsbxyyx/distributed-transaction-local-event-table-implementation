package org.xxz.base.components.mq;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.apache.commons.codec.binary.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.xxz.account.service.EventProcessService;
import org.xxz.domain.event.EventProcess;
import org.xxz.enums.EventProcessStatus;
import org.xxz.enums.EventType;

import com.alibaba.rocketmq.client.consumer.DefaultMQPushConsumer;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import com.alibaba.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.common.consumer.ConsumeFromWhere;
import com.alibaba.rocketmq.common.message.MessageExt;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@ConfigurationProperties(prefix = "mq")
public class RocketMqConsumer {
    
    private DefaultMQPushConsumer consumer = null;
    
    @Value("${mq.consumerGroup}")
    private String consumerGroup;
    @Value("${mq.namesrvAddr}")
    private String namesrvAddr;
    @Autowired
    private RocketMqConfig rocketMqConfig;
    @Autowired
    private EventProcessService eventProcessService;

    @PostConstruct
    public void init() throws MQClientException {
        
        log.info("==> rocketmq consumer init!");
        
        consumer = new DefaultMQPushConsumer(consumerGroup);
        consumer.setNamesrvAddr(namesrvAddr);
        // fixed The consumer group[MessageConsumerGroup] has been created
        consumer.setInstanceName(String.valueOf(System.currentTimeMillis()));
        
        consumer.subscribe(rocketMqConfig.getTopic(), "*");
        
        //设置Consumer第一次启动是从队列头部开始消费还是队列尾部开始消费<br>
        //如果非第一次启动，那么按照上次消费的位置继续消费
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
        
        //设置为集群消费(区别于广播消费)
        //consumer.setMessageModel(MessageModel.CLUSTERING);
        
        consumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
                for (MessageExt msg : msgs) {
                    log.info("==>" + new String(msg.getBody()));
                    if(StringUtils.equals(EventType.USER_CREATED.getKey(), msg.getTags())) {
                        ObjectMapper mapper = new ObjectMapper();
                        try {
                            EventProcess p = mapper.readValue(msg.getBody(), EventProcess.class);
                            p.setCreateTime(new Date());
                            p.setStatus(EventProcessStatus.NEW.getKey());
                            eventProcessService.saveEventProcess(p);
                        } catch (JsonParseException e) {
                            e.printStackTrace();
                        } catch (JsonMappingException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });
        consumer.start();
        log.info("==> rocketmq consumer start success!");
    }
    
    @PreDestroy
    public void destroy() {
        if(consumer != null) {
            consumer.shutdown();
        }
    }

}
