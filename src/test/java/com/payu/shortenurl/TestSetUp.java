/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.payu.shortenurl;


import com.payu.shortenurl.dao.UrlRepository;
import com.payu.shortenurl.dao.UserRepository;
import com.payu.shortenurl.dto.UserDto;
import com.payu.shortenurl.dto.util.UserUtil;
import com.payu.shortenurl.model.Url;
import com.payu.shortenurl.model.User;
import com.payu.shortenurl.util.ShortenUrlGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ActiveProfiles;

import java.time.ZonedDateTime;
import java.util.Date;

/**
 * @author Ahmad Hamouda on 2/24/17.
 */
@Component
@ActiveProfiles("test")
public class TestSetUp {

    private static final PasswordEncoder ENCODER = new BCryptPasswordEncoder();
    private final String longUrl = "http://example.com/prodct?ref=01652%26type=kn";
    private final String wrongShortUrl = "nonExistUrl";
    @Autowired
    UserRepository userRepository;
    @Autowired
    UrlRepository urlRepository;
    @Autowired
    UserUtil userUtil;
    @Autowired
    ShortenUrlGenerator shortenUrlGenerator;
    @Value("${expiry.period}")
    private int expiryPeriodCount;
    @Value("${expiry.shorter.url.hit}")
    private int expiryHitCounts;
    @Value("${register.expiry.period}")
    private int registerExpiryPeriodCount;
    @Value("${register.expiry.shorter.url.hit}")
    private int registerExpiryHitCounts;
    @Value("${domain.name}")
    private String domainName;

    public static void main(String[] args) {
        TestSetUp t = new TestSetUp();
    }

    public String getLongUrl() {
        return longUrl;
    }

    public String getWrongShortUrl() {
        return wrongShortUrl;
    }

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return ENCODER;
    }

    public User createUser() {
        User user = userUtil.assemble(setUserDto());
        user.setRole(User.Role.USER);
        return userRepository.save(user);
    }

    public UserDto setUserDto() {
        UserDto userDto = new UserDto();
        userDto.setPassword("TestPassword");
        userDto.setUserName("TestDtoUser");
        return userDto;
    }

    public String getShortUrl(String url) {
        return domainName+urlRepository.getByUrl(url).getShortenUrl();
    }

    public String getShortUrlWithUser(String url) {
        return domainName+urlRepository.getByUrlAndUser(url,userRepository.getByUserName("admin")).getShortenUrl();
    }

    public Url createUrl() {
        Url url = new Url(shortenUrlGenerator.generateShorten(longUrl), longUrl);
        return urlRepository.save(url);
    }

    public Url createUrlWithUser() {
        Url url = new Url(shortenUrlGenerator.generateShorten(longUrl), longUrl, userRepository.getByUserName("admin"));
        return urlRepository.save(url);
    }

    public Url createDateExpiredUrl() {
        ZonedDateTime now = ZonedDateTime.now();
        ZonedDateTime oldDate = now.plusDays(-(expiryPeriodCount + 1));
        Date expiredDate = Date.from(oldDate.toInstant());
        Url url = new Url(0, expiredDate, shortenUrlGenerator.generateShorten(longUrl), longUrl, null);
        return urlRepository.save(url);
    }

    public Url createExpiredHitUrl() {
        Url url = new Url(expiryHitCounts, new Date(), shortenUrlGenerator.generateShorten(longUrl), longUrl, null);
        return urlRepository.save(url);
    }

    public Url createDateExpiredUrlWithUser() {
        ZonedDateTime now = ZonedDateTime.now();
        ZonedDateTime oldDate = now.plusDays(-(registerExpiryPeriodCount + 1));
        Date expiredDate = Date.from(oldDate.toInstant());
        Url url = new Url(0, expiredDate, shortenUrlGenerator.generateShorten(longUrl), longUrl, userRepository.getByUserName("admin"));
        return urlRepository.save(url);
    }

    public Url createExpiredHitUrlWithUser() {
        Url url = new Url(registerExpiryHitCounts, new Date(), shortenUrlGenerator.generateShorten(longUrl), longUrl, userRepository.getByUserName("admin"));
        return urlRepository.save(url);
    }
}
