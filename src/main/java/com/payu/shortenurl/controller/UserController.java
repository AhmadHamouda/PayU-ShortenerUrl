package com.payu.shortenurl.controller;

import com.payu.shortenurl.dto.UserDto;
import com.payu.shortenurl.dto.util.UserUtil;
import com.payu.shortenurl.service.UserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.text.ParseException;

/**
 * @author Ahmad Hamouda on 2/24/17.
 */
@Controller
public class UserController {
    @Autowired
    UserService userService;
    @Autowired
    UserUtil userUtil;
    @ApiOperation(value = "Create an user", notes = "Creates a user. A unique id is generated and user name should be unique.")
    @RequestMapping(value = "/user", method = {RequestMethod.POST},
            consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public void createUser(@RequestBody UserDto userDto) throws ParseException {
        userService.createUser(userUtil.assemble(userDto));
    }

}
