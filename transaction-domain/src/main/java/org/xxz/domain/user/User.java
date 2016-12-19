package org.xxz.domain.user;

import lombok.Data;

@Data
public class User implements java.io.Serializable {
    
    
    private static final long serialVersionUID = -8649754719833109973L;
    
    private long userId;
    private String username;
    private String password;

}
