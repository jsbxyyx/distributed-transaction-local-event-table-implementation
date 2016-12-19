package org.xxz.base.components.mq;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@ConfigurationProperties(prefix = "mq")
@Data
public class RocketMqConfig {
    
    @Value("${mq.topic}")
    private String topic;

}
