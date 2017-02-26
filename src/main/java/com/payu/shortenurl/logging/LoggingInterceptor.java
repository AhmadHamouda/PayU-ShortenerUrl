package com.payu.shortenurl.logging;

import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

/**
 * @author Ahmad Hamouda on 2/24/17.
 */
@Aspect
@Component
public class LoggingInterceptor {

    private static final Logger LOG = Logger.getLogger(LoggingInterceptor.class);

    @Before("execution(* com.orange.olc.oa..*.*())")
    public void logBeforeMethod(JoinPoint joinPoint) {
        StringBuilder logMessage = new StringBuilder();
        logMessage.append("UserDto :");
        if (SecurityContextHolder.getContext().getAuthentication() != null && SecurityContextHolder.getContext().getAuthentication().getPrincipal() != null && SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof User) {
            logMessage.append(((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername());
        } else {
            logMessage.append("Anonymous user");
        }
        logMessage.append(" ,Enter :");
        logMessage.append(joinPoint.getTarget().getClass().getName());
        logMessage.append(".");
        logMessage.append(joinPoint.getSignature().getName());

        LOG.info(logMessage.toString());

        Object[] args = joinPoint.getArgs();
        if (args != null && args.length > 0) {
            StringBuilder logParammaters = new StringBuilder();
            logParammaters.append("Method paramaters ( ");
            for (Object arg : args) {
                logParammaters.append(arg == null ? "null" : arg).append(",");
            }
            if (args.length > 0) {
                logParammaters.deleteCharAt(logParammaters.length() - 1);
            }
            logParammaters.append(" ).");

            LOG.debug(logParammaters.toString());
        }

    }

    @AfterReturning("execution(* com.orange.olc.oa..*.*())")
    public void logAfterMethodSuccess(JoinPoint joinPoint) {

        StringBuilder logMessage = new StringBuilder();
        logMessage.append("Finish :");
        logMessage.append(joinPoint.getTarget().getClass().getName());
        logMessage.append(".");
        logMessage.append(joinPoint.getSignature().getName());
        logMessage.append("successfully.");

        LOG.info(logMessage.toString());

    }

    @AfterThrowing(pointcut = "execution(* com.orange.olc.oa..*.*())", throwing = "ex")
    public void logAfterMethodFaill(JoinPoint joinPoint, Throwable ex) {

        StringBuilder logMessage = new StringBuilder();
        logMessage.append("Finish :");
        logMessage.append(joinPoint.getTarget().getClass().getName());
        logMessage.append(".");
        logMessage.append(joinPoint.getSignature().getName());
        logMessage.append(", with error: ");
        logMessage.append(ex);

        LOG.error(logMessage.toString());
    }

}
