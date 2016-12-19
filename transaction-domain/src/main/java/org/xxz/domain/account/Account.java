package org.xxz.domain.account;

import lombok.Data;

@Data
public class Account implements java.io.Serializable {
    
    private static final long serialVersionUID = -3824348071822153333L;
    
    private long userId;
    private int amount; // 单位：分

}
