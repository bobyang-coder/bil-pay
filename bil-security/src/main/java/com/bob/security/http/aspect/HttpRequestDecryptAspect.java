package com.bob.security.http.aspect;

import com.bob.security.http.RequestDecrypt;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;


/**
 * Created by bob on 2018/1/21.
 *
 * @author bob   <bobyang_coder@163.com>
 * @version v1.0
 * @since 2018/1/21
 */
@Aspect() //切面注解
@Component
public class HttpRequestDecryptAspect {

    private static final Logger logger = LoggerFactory.getLogger(HttpRequestDecryptAspect.class);

    @Around("@annotation(com.bob.security.http.RequestDecrypt)")
    public Object doAround(ProceedingJoinPoint pjp) throws Throwable {
        logger.info("----------loggingBefore----------------");
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        logger.info("url={}", request.getRequestURL());
        logger.info("http_method={}", request.getMethod());
        logger.info("visitor_ip={}", request.getRemoteAddr());
        logger.info("class_method={}.{}", pjp.getSignature().getDeclaringTypeName(), pjp.getSignature().getName());
        logger.info("method_args={}", pjp.getArgs());
        Object[] args = pjp.getArgs();
        for (int i = 0; i < args.length; i++) {
            Object arg = args[i];
             arg.getClass().getAnnotation(RequestDecrypt.class);
        }
        return pjp.proceed();
    }


}
