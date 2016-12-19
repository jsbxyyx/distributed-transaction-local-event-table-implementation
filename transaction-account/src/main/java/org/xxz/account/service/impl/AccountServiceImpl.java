package org.xxz.account.service.impl;

import javax.annotation.Resource;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.xxz.account.mapper.AccountMapper;
import org.xxz.account.service.AccountService;
import org.xxz.domain.account.Account;
import org.xxz.domain.account.AccountDO;

@Service
public class AccountServiceImpl implements AccountService {
    
    @Resource
    private AccountMapper accountMapper;

    @Override
    public int addAccount(AccountDO accountDO) {
        accountMapper.insertAccount(accountDo2Account(accountDO));
        
        return 0;
    }
    
    private Account accountDo2Account(AccountDO accountDO) {
        Account account = new Account();
        BeanUtils.copyProperties(accountDO, account);
        return account;
    }

}
