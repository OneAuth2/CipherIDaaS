package com.portal.aop;


import javassist.bytecode.SignatureAttribute;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Map;

@Component
@Aspect
public class GatewayAspect {

    @Pointcut("@annotation(com.portal.aop.TokenAuth)")
    public void tokenPointcut() {
    }

   /* @Before("tokenPointcut()")
    public void beforePointcut(JoinPoint joinPoint) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        Map<String, String[]> s = request.getParameterMap();
        System.out.println("==================aspect================="+s.size());
    }*/



    @Before("tokenPointcut()")
    public void beforePointcut(JoinPoint joinPoint) {
        MethodSignature methodSignature =  (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        TokenAuth annotation = method.getAnnotation(TokenAuth.class);
        System.out.println("准备");
    }

    @After("tokenPointcut()")
    public void afterPointcut(JoinPoint joinPoint) {
        MethodSignature methodSignature =  (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        TokenAuth annotation = method.getAnnotation(TokenAuth.class);
        System.out.println("结束");
    }


}
