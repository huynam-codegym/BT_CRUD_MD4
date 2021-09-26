package com.codegym.logger;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.Arrays;


@Aspect
@Component
public class Logger {
    //    cach 1
    @AfterReturning(pointcut = "within(com.codegym.controller.*)", returning = "e")
    public void log(JoinPoint joinPoint, Object e) {
        String className = joinPoint.getTarget().getClass().getSimpleName();
        String method = joinPoint.getSignature().getName();
        String args = Arrays.toString(joinPoint.getArgs());
        System.out.println(className +" "+method);
        System.out.println(args);
        System.out.println(e.toString());
    }
}
