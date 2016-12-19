package org.xxz.domain.account;

import lombok.Data;

@Data
public class AccountDO implements java.io.Serializable {
    
    private static final long serialVersionUID = 2576577261110847618L;
    
    private long userId;
    private int amount; // 单位：分 

}
