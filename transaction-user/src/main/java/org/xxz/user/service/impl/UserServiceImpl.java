package org.xxz.user.service.impl;

import java.util.Date;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.xxz.base.util.JsonMapper;
import org.xxz.domain.account.Account;
import org.xxz.domain.event.EventPublish;
import org.xxz.domain.user.User;
import org.xxz.domain.user.UserDO;
import org.xxz.enums.EventPublishStatus;
import org.xxz.enums.EventType;
import org.xxz.user.mapper.UserMapper;
import org.xxz.user.service.EventPublishService;
import org.xxz.user.service.UserService;
import org.xxz.util.IDUtil;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class UserServiceImpl implements UserService {
    
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private EventPublishService evnetPublishService;

    @Transactional
    @Override
    public int addUser(UserDO userDo) {
        // 1.保存用户
        User user = userDo2User(userDo);
        userMapper.insertUser(user);
        
        // 2.保存时间
        EventPublish event = new EventPublish();
        event.setCreateTime(new Date());
        event.setEventType(EventType.USER_CREATED.getKey());
        event.setId(IDUtil.uuid());
        event.setStatus(EventPublishStatus.NEW.getKey());
        
        Account account = new Account();
        account.setUserId(user.getUserId());
        account.setAmount(200);
        
        event.setPayload(JsonMapper.nonDefaultMapper().toJson(account));
        evnetPublishService.saveEventPublish(event);
        
        return 0;
    }
    
    private User userDo2User(UserDO userDo) {
        User user = new User();
        BeanUtils.copyProperties(userDo, user);
        return user;
    }

}
