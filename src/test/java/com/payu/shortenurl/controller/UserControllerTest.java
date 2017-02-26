package com.payu.shortenurl.controller;

import com.payu.shortenurl.AbstractJSONRestTest;
import com.payu.shortenurl.Application;
import com.payu.shortenurl.TestSetUp;
import com.payu.shortenurl.dto.UserDto;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.util.AssertionErrors.fail;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Ahmad Hamouda on 2/24/17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@ActiveProfiles("test")
@Transactional
public class UserControllerTest extends AbstractJSONRestTest {
    private static final Logger LOG = Logger.getLogger(UserControllerTest.class);

    @Autowired
    TestSetUp setUp;

    @Test
    public void testCreateUser() {
        try {
            LOG.info("Test create user");
            UserDto setupUserDto = setUp.setUserDto();
            String json = json(setupUserDto);
            LOG.debug(json);
            mockMvc.perform(post("/user").content(json).contentType(contentType)).
                    andExpect(status().isCreated());
        } catch (Exception ex) {
            LOG.error(ex);
            fail(ex.getMessage());
        }
    }

    @Test
    public void testCreateExistUser() {
        try {
            LOG.info("Test create exist user");
            UserDto setupUserDto = setUp.setUserDto();
            setUp.createUser();
            String json = json(setupUserDto);
            LOG.debug(json);
            mockMvc.perform(post("/user").content(json).contentType(contentType)).
                    andExpect(status().isConflict());
        } catch (Exception ex) {
            LOG.error(ex);
            fail(ex.getMessage());
        }
    }
}
