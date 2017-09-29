package org.xxz.user.service.impl;

import com.alibaba.rocketmq.client.exception.MQBrokerException;
import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.client.producer.DefaultMQProducer;
import com.alibaba.rocketmq.client.producer.SendResult;
import com.alibaba.rocketmq.client.producer.SendStatus;
import com.alibaba.rocketmq.common.message.Message;
import com.alibaba.rocketmq.remoting.exception.RemotingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.xxz.base.components.mq.RocketMqConfig;
import org.xxz.base.components.mq.RocketMqProducer;
import org.xxz.base.util.JsonMapper;
import org.xxz.domain.event.EventPublish;
import org.xxz.enums.EventPublishStatus;
import org.xxz.user.mapper.EventPublishMapper;
import org.xxz.user.service.EventPublishService;

import java.util.List;

@Slf4j
@Service
public class EventPublishServiceImpl implements EventPublishService {
    
    @Autowired
    private EventPublishMapper eventPublishMapper;
    @Autowired
    private RocketMqProducer rocketMqProducer;
    @Autowired
    private RocketMqConfig rocketMqConfig;

    @Override
    public List<EventPublish> getNewEventPublishs() {
        return eventPublishMapper.getNewEventPublishs();
    }

    @Override
    public int saveEventPublish(EventPublish eventPublish) {
        eventPublishMapper.insertEventPublish(eventPublish);
        return 0;
    }

    @Transactional
    @Override
    public int dealPayload(EventPublish e) {
        DefaultMQProducer producer = rocketMqProducer.getProducer();
        try {
            Message msg = new Message(rocketMqConfig.getTopic(), e.getEventType(), JsonMapper.nonDefaultMapper().toJsonAsBytes(e));
            SendResult sendResult = producer.send(msg);
            if(SendStatus.SEND_OK.equals(sendResult.getSendStatus())) {
                e.setStatus(EventPublishStatus.PUBLISHED.getKey());
                eventPublishMapper.updateEventPublishStatus(e);
            }
        } catch (MQClientException | RemotingException | MQBrokerException | InterruptedException e1) {
            log.error("==>" + e1);
            throw new RuntimeException(e1);
        }
        return 0;
    }

}
