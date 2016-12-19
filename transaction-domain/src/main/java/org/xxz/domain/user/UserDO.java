package org.xxz.domain.user;

import lombok.Data;

@Data
public class UserDO implements java.io.Serializable {
    
    private static final long serialVersionUID = -8567014314806567017L;
    
    private String username;
    private String password;

}
