package org.xxz.user.mapper;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.xxz.domain.event.EventPublish;

@Repository
public interface EventPublishMapper {
    
    int insertEventPublish(EventPublish eventPublish);
    
    int updateEventPublishStatus(EventPublish eventPublish);

    List<EventPublish> getNewEventPublishs();

}
