package com.payu.shortenurl.service.impl;


import com.payu.shortenurl.dao.UrlRepository;
import com.payu.shortenurl.exception.UrlNotFound;
import com.payu.shortenurl.exception.UrlShortenExpired;
import com.payu.shortenurl.model.Url;
import com.payu.shortenurl.model.User;
import com.payu.shortenurl.service.UrlService;
import com.payu.shortenurl.util.ShortenUrlGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.Date;

/**
 * @author Ahmad Hamouda on 2/24/17.
 */
@Service
public class UrlServiceImpl implements UrlService {
    @Autowired
    UrlRepository urlRepository;
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
    @Override
    public String shortUrl(String url, User user) {
        if (user == null && urlRepository.getByUrl(url) != null) {
            return domainName+urlRepository.getByUrl(url).getShortenUrl();
        } else if (urlRepository.getByUrlAndUser(url, user) != null) {
            return domainName+urlRepository.getByUrlAndUser(url, user).getShortenUrl();
        } else {
            String shortUrl = shortenUrlGenerator.generateShorten(url);
            Url savedUrl = urlRepository.save(new Url(shortUrl, url, user));
            return domainName+savedUrl.getShortenUrl();
        }
    }

    @Override
    public String redirectUrl(String shortenUrl, User user) {
        Url url = null;
        if (user != null) {
            url = urlRepository.getByShortenUrlAndUser(shortenUrl, user);
            if (url != null && !(url.getUser().isActive() && isActive(url.getCreationDate(), registerExpiryPeriodCount) && url.getHitCount() < registerExpiryHitCounts)) {
                urlRepository.delete(url.getId());
                throw new UrlShortenExpired();
            }

        } else {
            url = urlRepository.getByShortenUrl(shortenUrl);
            if (url != null && !(isActive(url.getCreationDate(), expiryPeriodCount) && url.getHitCount() < expiryHitCounts)) {
                urlRepository.delete(url.getId());
                throw new UrlShortenExpired();
            }
        }
        if (url == null) {
            throw new UrlNotFound();
        }
        url.incrementHitCount();
        urlRepository.save(url);
        return url.getUrl();
    }

    private boolean isActive(Date creationDate, int period) {
        ZonedDateTime now = ZonedDateTime.now();
        ZonedDateTime oldDate = now.plusDays(-period);
        return oldDate.toInstant().isBefore(creationDate.toInstant());
    }
}
