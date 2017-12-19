package com.mistty.nowcoder.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Date;

@Aspect
@Component
public class LogAspect {
    private static final Logger logger= LoggerFactory.getLogger(LogAspect.class);
    @Before("execution(* com.mistty.nowcoder.controller.*.*(..))")
    public void beforeMethod(JoinPoint joinPoint){
        StringBuilder sd = new StringBuilder();
        for(Object arg: joinPoint.getArgs()){
            sd.append("arg: "+arg.toString()+"|");
        }
        logger.info("before method" + sd.toString());
    }
    @After("execution(* com.mistty.nowcoder.controller.*.*(..))")
    public void afterMethod(){
        logger.info("after method" + new Date());
    }
}
