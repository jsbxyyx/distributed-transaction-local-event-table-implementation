package org.xxz.user.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.xxz.domain.common.Result;
import org.xxz.domain.user.UserDO;
import org.xxz.user.service.UserService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {
    
    @Resource
    private UserService userService;
    
    @PostMapping("/addUser")
    public Result addUser(@RequestBody UserDO userDo) {
        log.info("==>" + userDo);
        Result r = new Result();
        userService.addUser(userDo);
        return r;
    }

}
