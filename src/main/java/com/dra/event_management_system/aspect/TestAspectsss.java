package com.dra.event_management_system.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import com.dra.event_management_system.annotation.TestingAnn;

@Aspect
@Component
public class TestAspectsss {

    @Before("@annotation(testingAnnr)")
    public void sdsdsdsd(TestingAnn testingAnnr){
        System.out.println(testingAnnr.s()+" 333 heloooowscscscs");
    }

    @Before("execution(* com.dra.event_management_system.service.*.*(..))")
    public void ggg(JoinPoint joinPoint){
        System.out.println("333333333333333333333" + joinPoint.getSignature().getName());
    }



}
