package com.cms_aop.logger;

import com.cms_aop.model.Customer;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
public class MyLogger {
    private static final Logger logger = LogManager.getLogger(MyLogger.class);

    static {
        PropertyConfigurator.configure("src/main/resources/log4j/configuration/log4j.properties");
    }

    private Long customerId;

    @Pointcut("execution(* com.cms_aop.service.impl.CustomerService.save(..))")
    public void customerServiceSaveMethod() {
    }

    @After("customerServiceSaveMethod()")
    public void customerCreateLogging() {
        if (customerId != null) {
            logger.info(String.format("Customer with id: %d updated!", customerId));
        } else {
            logger.info("New customer created! ");
        }
    }

    @Before("customerServiceSaveMethod()")
    private void getSaveMethodArgument(JoinPoint joinPoint) {
        customerId = ((Customer) joinPoint.getArgs()[0]).getId();
    }

    @AfterThrowing(pointcut = "execution( * com.cms_aop.service.impl.CustomerService.*(..))", throwing = "exception")
    public void loggingForCustomer(JoinPoint joinPoint, Exception exception) {
        String className = joinPoint.getTarget().getClass().getSimpleName();
        String method = joinPoint.getSignature().getName();
        String args = Arrays.toString(joinPoint.getArgs());
        logger.error(String.format("[CMS] Exception: %s.%s%s: %s%n", className, method, args, exception.getMessage()));
    }
}
