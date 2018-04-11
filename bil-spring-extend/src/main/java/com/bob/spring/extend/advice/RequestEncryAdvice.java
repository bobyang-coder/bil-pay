package com.bob.spring.extend.advice;

import com.bob.spring.extend.RequestDecrypt;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdvice;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;


/**
 * Created by bob on 2018/1/21.
 *
 * @author bob   <bobyang_coder@163.com>
 * @version v1.0
 * @since 2018/1/21
 */
@Component
@ControllerAdvice
public class RequestEncryAdvice implements RequestBodyAdvice {

    private static final Logger log = LoggerFactory.getLogger(RequestEncryAdvice.class);

    @Override
    public boolean supports(MethodParameter methodParameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        RequestDecrypt methodAnnotation = methodParameter.getMethodAnnotation(RequestDecrypt.class);
        RequestDecrypt parameterAnnotation = methodParameter.getParameterAnnotation(RequestDecrypt.class);
        return methodParameter.getParameterAnnotation(RequestDecrypt.class) != null
                && methodParameter.getParameterAnnotation(RequestBody.class) != null;
    }

    @Override
    public Object handleEmptyBody(Object body, HttpInputMessage inputMessage, MethodParameter parameter
            , Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        return body;
    }

    @Override
    public HttpInputMessage beforeBodyRead(HttpInputMessage request, MethodParameter parameter, Type targetType
            , Class<? extends HttpMessageConverter<?>> converterType) throws IOException {
        RequestDecrypt requestDecode = parameter.getMethodAnnotation(RequestDecrypt.class);

//        String json = IOUtils.toString(request.getBody(), "UTF-8");

//        String json2 = "{\"age\":10}";
//        InputStream inputStream = IOUtils.toInputStream(json2, "UTF-8");
        if (requestDecode == null) {
            //controller方法不要求加解密
            return request;
        }
        log.info("解密完成: {}");
        return request;
    }

    @Override
    public Object afterBodyRead(Object body, HttpInputMessage inputMessage, MethodParameter parameter, Type targetType
            , Class<? extends HttpMessageConverter<?>> converterType) {
        return body;
    }
}
