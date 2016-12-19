package org.xxz.account.service;

import java.util.List;

import org.xxz.domain.event.EventProcess;

public interface EventProcessService {

    int saveEventProcess(EventProcess p);
    
    List<EventProcess> getNewEventProcesses();

    void dealPayload(EventProcess e);


}
