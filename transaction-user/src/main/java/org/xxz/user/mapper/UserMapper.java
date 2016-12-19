package org.xxz.user.mapper;

import org.springframework.stereotype.Repository;
import org.xxz.domain.user.User;

@Repository
public interface UserMapper {
    
    int insertUser(User user);

}
