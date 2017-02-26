package com.payu.shortenurl.service;

import com.payu.shortenurl.model.User;

/**
 * @author Ahmad Hamouda on 2/24/17.
 */
public interface UserService {

    void createUser(User user);

    User getByUserName(String userName);

}
