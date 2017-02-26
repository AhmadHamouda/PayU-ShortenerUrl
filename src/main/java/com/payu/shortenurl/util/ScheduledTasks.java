package com.payu.shortenurl.util;

import com.payu.shortenurl.dao.UrlRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.util.Date;

/**
 * @author Ahmad Hamouda on 2/24/17.
 */
@Component
public class ScheduledTasks {

    @Autowired
    UrlRepository urlRepository;
    @Value("${expiry.period}")
    private int expiryPeriodCount;
    @Value("${expiry.shorter.url.hit}")
    private int expiryHitCounts;
    @Value("${register.expiry.period}")
    private int registerExpiryPeriodCount;
    @Value("${register.expiry.shorter.url.hit}")
    private int registerExpiryHitCounts;

    @Scheduled(cron = "0 0 * * * *")
    public void deleteExpiredUrls() {
        ZonedDateTime now = ZonedDateTime.now();
        ZonedDateTime oldDate = now.plusDays(-expiryPeriodCount);
        ZonedDateTime registerOldDate = now.plusDays(-registerExpiryPeriodCount);
        urlRepository.deleteExpiredUrls(Date.from(oldDate.toInstant()), expiryHitCounts, Date.from(registerOldDate.toInstant()), registerExpiryHitCounts);
    }
}