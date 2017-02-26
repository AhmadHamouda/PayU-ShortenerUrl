package com.payu.shortenurl.service.impl;

import com.payu.shortenurl.model.User;
import com.payu.shortenurl.service.UserSecurityService;
import com.payu.shortenurl.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

/**
 * @author Ahmad Hamouda on 2/24/17.
 */
@Service
public class UserSecurityServiceImpl implements UserSecurityService {

    @Autowired
    UserService userService;

    @Override
    public User getUser(User user) {
            if (SecurityContextHolder.getContext().getAuthentication() != null && SecurityContextHolder.getContext().getAuthentication().getPrincipal() != null && SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof org.springframework.security.core.userdetails.User) {
                String userName = ((org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
                user = userService.getByUserName(userName);
            }
            return user;
        }

}
