package com.payu.shortenurl.logging;

import org.springframework.web.bind.annotation.Mapping;

import java.lang.annotation.*;

/**
 * @author Ahmad Hamouda on 2/24/17.
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Mapping
public @interface Loggable {

    /**
     * set the logging message literally
     *
     * @return the logged message
     */
    String message() default "";

}
