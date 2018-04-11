package com.bob.biz;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by bob on 2018/1/18.
 *
 * @author bob   <bobyang_coder@163.com>
 * @version v1.0
 * @since 2018/1/18
 */
@Aspect
@Component
public class HttpApiDESAspect {


    @Before("@annotation(EncryDecry)")
    public void doBefore(JoinPoint jp, EncryDecry des) {
        HttpServletRequest webRequest = getWebRequest();

    }

    @Around("@annotation(EncryDecry)")
    public Object doAround(ProceedingJoinPoint pjp, EncryDecry redisCache) throws Throwable {

        return null;
    }

    public static HttpServletRequest getWebRequest() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
        return request;
    }
}
