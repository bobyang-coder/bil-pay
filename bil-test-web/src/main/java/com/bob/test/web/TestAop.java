package com.bob.test.web;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

/**
 * Created by bob on 2018/1/26.
 *
 * @author bob   <bobyang_coder@163.com>
 * @version v1.0
 * @since 2018/1/26
 */
@Aspect
@Component
public class TestAop {

    @Before("@annotation(TestAnnotation)")
    public void doBefore(JoinPoint joinPoint) {
        System.out.println("前置通知");
    }

    @Around("@annotation(TestAnnotation)")
    public Object doAround(ProceedingJoinPoint pjp) throws Throwable {
        System.out.println("环绕通知-start");
        Object proceed = pjp.proceed();
        System.out.println("环绕通知-end");
        return proceed;
    }

    @After("@annotation(TestAnnotation)")
    public void doAfter() {
        System.out.println("后置通知");
    }

    @AfterReturning(returning = "object", pointcut = "@annotation(TestAnnotation)")
    public void doAfterReturning(Object object) {
        System.out.println("正常返回通知");
    }

    @AfterThrowing(value = "@annotation(TestAnnotation)")
    public void afterThorwingMethod(JoinPoint jp) {
        System.out.println("异常通知");
    }
}
