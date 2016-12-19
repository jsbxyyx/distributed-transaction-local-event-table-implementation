package org.xxz.account.mapper;

import org.springframework.stereotype.Repository;
import org.xxz.domain.account.Account;

@Repository
public interface AccountMapper {
    
    int insertAccount(Account account);

}
