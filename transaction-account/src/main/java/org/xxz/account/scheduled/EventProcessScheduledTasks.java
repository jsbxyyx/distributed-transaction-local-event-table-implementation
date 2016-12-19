package org.xxz.account.scheduled;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.xxz.account.service.EventProcessService;
import org.xxz.domain.event.EventProcess;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class EventProcessScheduledTasks {
    
    @Autowired
    private EventProcessService eventProcessService;
    
    @Scheduled(cron = "*/5 * * * * *")
    public void processEventTask() {
        log.info("==> into processEventTask");
        List<EventProcess> events = eventProcessService.getNewEventProcesses();
        // send mq
        if(events != null && !events.isEmpty()) {
            for(EventProcess e : events) {
                eventProcessService.dealPayload(e);
            }
        }
    }

}
