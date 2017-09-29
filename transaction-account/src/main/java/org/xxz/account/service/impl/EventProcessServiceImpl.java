package org.xxz.account.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.xxz.account.mapper.EventProcessMapper;
import org.xxz.account.service.AccountService;
import org.xxz.account.service.EventProcessService;
import org.xxz.base.util.JsonMapper;
import org.xxz.domain.account.AccountDO;
import org.xxz.domain.event.EventProcess;
import org.xxz.enums.EventProcessStatus;
import org.xxz.enums.EventType;

import java.util.List;

@Service
public class EventProcessServiceImpl implements EventProcessService {
    
    @Autowired
    private EventProcessMapper eventProcessMapper;
    @Autowired
    private AccountService accountService;
    
    @Override
    public int saveEventProcess(EventProcess p) {
        eventProcessMapper.insertEventProcess(p);
        return 0;
    }
    
    @Override
    public List<EventProcess> getNewEventProcesses() {
        return eventProcessMapper.getNewEventProcesses();
    }

    @Transactional
    @Override
    public void dealPayload(EventProcess e) {
        String eventType = e.getEventType();
        if(StringUtils.equals(eventType, EventType.USER_CREATED.getKey())) {
            AccountDO accountDO = JsonMapper.nonDefaultMapper().fromJson(e.getPayload(), AccountDO.class);
            accountService.addAccount(accountDO);
            e.setStatus(EventProcessStatus.PROCESSED.getKey());
            eventProcessMapper.updateEventProcessStatus(e);
        }
    }

}
