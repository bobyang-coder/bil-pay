package com.bob.spring.extend.advice;

import com.bob.spring.extend.ResponseEncrypt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * Created by bob on 2018/1/20.
 *
 * @author bob   <bobyang_coder@163.com>
 * @version v1.0
 * @since 2018/1/20
 */
@Component
@ControllerAdvice
public class ResponseEncryptAdvice implements ResponseBodyAdvice {

    private static final Logger log = LoggerFactory.getLogger(ResponseEncryptAdvice.class);

    @Override
    public boolean supports(MethodParameter returnType, Class converterType) {
        return returnType.getMethodAnnotation(ResponseEncrypt.class) != null;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType
            , Class selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        ResponseEncrypt responseEncode = returnType.getMethodAnnotation(ResponseEncrypt.class);
        return "加密替换";
    }
}
