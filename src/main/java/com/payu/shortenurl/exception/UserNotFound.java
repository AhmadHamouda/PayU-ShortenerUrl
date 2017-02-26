/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.payu.shortenurl.exception;

import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author Ahmad Hamouda on 2/24/17.
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "User not found")
public class UserNotFound extends RuntimeException {

    private static final Logger LOG = Logger.getLogger(UserNotFound.class);

    @Override
    public String getMessage() {
        LOG.error("User not found");
        return "User not found";
    }

}
