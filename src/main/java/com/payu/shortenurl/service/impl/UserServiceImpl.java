package com.payu.shortenurl.service.impl;

import com.payu.shortenurl.dao.UserRepository;
import com.payu.shortenurl.exception.UserAlreadyExists;
import com.payu.shortenurl.model.User;
import com.payu.shortenurl.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * @author Ahmad Hamouda on 2/24/17.
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    PasswordEncoder encoder;

    @Override
    public void createUser(User user) {
        if (userExists(user.getUserName())) {
            throw new UserAlreadyExists();
        }
        user.setActive(true);
        user.setRole(User.Role.USER);
        user.setPassword(encoder.encode(user.getPassword()));
        System.out.println(user.getUserName());
        System.out.println(user.getPassword());
        userRepository.save(user);
    }

    @Override
    public User getByUserName(String userName) {
        return userRepository.getByUserName(userName);
    }

    private boolean userExists(String username) {
        return userRepository.userNameExists(username);
    }
}
