package com.payu.shortenurl.controller;

import com.payu.shortenurl.AbstractJSONRestTest;
import com.payu.shortenurl.Application;
import com.payu.shortenurl.TestSetUp;
import com.payu.shortenurl.model.Url;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.util.AssertionErrors.fail;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Ahmad Hamouda on 2/24/17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@ActiveProfiles("test")
@Transactional
@WithUserDetails("admin")
public class UrlControllerWithUserTest extends AbstractJSONRestTest {
    private static final Logger LOG = Logger.getLogger(UrlControllerWithUserTest.class);

    @Autowired
    TestSetUp setUp;

    @Test
    public void testShortenUrl() {
        try {
            LOG.info("Test shorten url");
            LOG.debug("URL is: " + setUp.getLongUrl());
            mockMvc.perform(post("/user/shortUrl/").param("url", setUp.getLongUrl())).
                    andExpect(status().isCreated()).
                    andExpect(content().string(setUp.getShortUrlWithUser(setUp.getLongUrl())));
        } catch (Exception ex) {
            LOG.error(ex);
            fail(ex.getMessage());
        }
    }

    @Test
    public void testRedirectUrl() {
        try {
            LOG.info("Test redirect url");
            Url url = setUp.createUrlWithUser();
            LOG.debug("URL is: " + setUp.getLongUrl());
            mockMvc.perform(get("/r/"+url.getShortenUrl())).
                    andExpect(status().isFound()).
                    andExpect(redirectedUrl(url.getUrl()));
        } catch (Exception ex) {
            LOG.error(ex);
            fail(ex.getMessage());
        }
    }

    @Test
    public void testNonExistRedirectUrl() {
        try {
            LOG.info("Test non exist redirect url");
            LOG.debug("URL is: " + setUp.getWrongShortUrl());
            mockMvc.perform(get("/r/"+ setUp.getWrongShortUrl())).
                    andExpect(status().isNotFound());
        } catch (Exception ex) {
            LOG.error(ex);
            fail(ex.getMessage());
        }
    }

    @Test
    public void testExpiredTimeUrl() {
        try {
            LOG.info("Test expired time redirect url");
            Url url = setUp.createDateExpiredUrlWithUser();
            LOG.debug("URL is: " + setUp.getLongUrl());
            mockMvc.perform(get("/r/"+ url.getShortenUrl())).
                    andExpect(status().isGone());
        } catch (Exception ex) {
            LOG.error(ex);
            fail(ex.getMessage());
        }
    }

    @Test
    public void testExpiredHitsUrl() {
        try {
            LOG.info("Test expired hit redirect url");
            Url url = setUp.createExpiredHitUrlWithUser();
            LOG.debug("URL is: " + setUp.getLongUrl());
            mockMvc.perform(get("/r/"+ url.getShortenUrl())).
                    andExpect(status().isGone());
        } catch (Exception ex) {
            LOG.error(ex);
            fail(ex.getMessage());
        }
    }
}
