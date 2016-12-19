package org.xxz.account.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.xxz.account.service.AccountService;
import org.xxz.domain.account.AccountDO;
import org.xxz.domain.common.Result;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/account")
public class AccountController {
    
    @Resource
    private AccountService accountService;
    
    @PostMapping("/addAccount")
    public Result addAccount(@RequestBody AccountDO accountDO) {
        log.info("==>" + accountDO);
        Result r = new Result();
        accountService.addAccount(accountDO);
        return r;
    }

}
