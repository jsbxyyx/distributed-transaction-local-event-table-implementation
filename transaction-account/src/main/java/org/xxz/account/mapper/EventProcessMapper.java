package org.xxz.account.mapper;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.xxz.domain.event.EventProcess;

@Repository
public interface EventProcessMapper {
    
    int insertEventProcess(EventProcess eventProcess);
    
    int updateEventProcessStatus(EventProcess eventProcess);
    
    /**
     * 获取所有状态为NEW的事件
     * @return
     */
    List<EventProcess> getNewEventProcesses();

}
