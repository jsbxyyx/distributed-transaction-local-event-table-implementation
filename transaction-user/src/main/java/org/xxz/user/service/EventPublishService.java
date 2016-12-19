package org.xxz.user.service;

import java.util.List;

import org.xxz.domain.event.EventPublish;

public interface EventPublishService {

    int saveEventPublish(EventPublish eventPublish);
    
    List<EventPublish> getNewEventPublishs();

    int dealPayload(EventPublish e);


}
