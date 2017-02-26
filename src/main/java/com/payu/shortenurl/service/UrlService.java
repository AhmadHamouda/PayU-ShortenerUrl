package com.payu.shortenurl.service;

import com.payu.shortenurl.model.User;

/**
 * @author Ahmad Hamouda on 2/24/17.
 */
public interface UrlService {
    public String shortUrl(String url, User user);

    public String redirectUrl(String shortenUrl, User user);
}
