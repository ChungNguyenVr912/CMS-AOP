package com.cms_aop.logger;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class MyLogger {
    static {
        PropertyConfigurator.configure("src/main/resources/log4j/configuration/log4j.properties");
    }
    private static final Logger logger = LogManager.getLogger(MyLogger.class);
    @After("execution(* com.cms_aop.service.GeneralService.save(..))")
    public void customerCreateLogging(){
        logger.info("New customer created!");
    }
}
