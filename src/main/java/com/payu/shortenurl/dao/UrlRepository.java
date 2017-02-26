/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.payu.shortenurl.dao;

import com.payu.shortenurl.model.Url;
import com.payu.shortenurl.model.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * @author Ahmad Hamouda on 2/24/17.
 */
@RepositoryRestResource(exported = false)
public interface UrlRepository extends PagingAndSortingRepository<Url, Integer> {

    @Query(value = "SELECT u FROM Url u WHERE u.url = ?1 AND u.user IS NULL")
    Url getByUrl(String url);

    @Query(value = "SELECT  u FROM Url u WHERE u.shortenUrl = ?1 AND u.user IS NULL")
    Url getByShortenUrl(String shortenUrl);

    Url getByUrlAndUser(String url, User user);

    Url getByShortenUrlAndUser(String shortenUrl, User user);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM  Url u WHERE ((u.creationDate < ?1 OR u.hitCount >= ?2 ) AND u.user IS NULL) OR ((u.creationDate < ?3 OR u.hitCount >= ?4 ) AND u.user IS NOT NULL)")
    void deleteExpiredUrls(Date expiryPeriodCount, int expiryHitCounts, Date registerExpiryPeriodCount, int registerExpiryHitCounts);
}
