package com.payu.shortenurl.controller;

import com.payu.shortenurl.model.User;
import com.payu.shortenurl.service.UrlService;
import com.payu.shortenurl.service.UserSecurityService;
import com.payu.shortenurl.service.UserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author Ahmad Hamouda on 2/24/17.
 */
@Controller
public class UrlController {
    @Autowired
    UrlService urlService;
    @Autowired
    UserSecurityService userSecurityService;
    @Value("${domain.name}")
    private String domainName;

    @ApiOperation(value = "Create an shortUrl", notes = "Creates a shortUrl in replace to long URL.")
    @RequestMapping(value = "/user/shortUrl/", method = {RequestMethod.POST})
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public String shortUrl(@RequestParam String url) {
        User user = null;
        user = userSecurityService.getUser(user);
        return urlService.shortUrl(url, user);
    }


    @ApiOperation(value = "Redirect to Original Url", notes = "Get long URL for given shortUrl and redirect to it.")
    @RequestMapping(value = "/r/{shortenUrl}", method = {RequestMethod.GET})
    @ResponseStatus(HttpStatus.FOUND)
    public ModelAndView redirectUrl(@PathVariable String shortenUrl) {
        User user = null;
        user = userSecurityService.getUser(user);
        String url= urlService.redirectUrl(shortenUrl, user);

        return new ModelAndView("redirect:" + url);
    }
}
