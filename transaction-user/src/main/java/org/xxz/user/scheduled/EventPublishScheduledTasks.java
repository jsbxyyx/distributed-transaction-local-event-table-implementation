package org.xxz.user.scheduled;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.xxz.domain.event.EventPublish;
import org.xxz.user.service.EventPublishService;

import java.util.List;

@Slf4j
@Component
public class EventPublishScheduledTasks {
    
    @Autowired
    private EventPublishService eventPublishService;
    
    @Scheduled(cron = "*/5 * * * * *")
    public void publishEventTask() {
        log.info("==> into publishEventTask");
        List<EventPublish> events = eventPublishService.getNewEventPublishs();
        for(EventPublish e : events) {
            eventPublishService.dealPayload(e);
        }
        
    }

}
