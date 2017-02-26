package com.payu.shortenurl.dto.util;

import com.payu.shortenurl.dto.UserDto;
import com.payu.shortenurl.model.User;
import org.springframework.stereotype.Component;

/**
 * @author Ahmad Hamouda on 2/24/17.
 */
@Component
public class UserUtil {
    public User assemble(UserDto userDtO) {
        User user = new User();
        if (userDtO.getId() != 0) {
            user.setId(userDtO.getId());
        }
        user.setUserName(userDtO.getUserName());
        user.setPassword(userDtO.getPassword());
        user.setActive(userDtO.isActive());
        return user;
    }

    public UserDto deAssemble(User user) {
        UserDto userDtO = new UserDto();
        userDtO.setId(user.getId());
        userDtO.setPassword(user.getPassword());
        userDtO.setActive(user.isActive());
        userDtO.setUserName(user.getUserName());
        return userDtO;
    }
}
