package com.bob.security.http.support;

import com.bob.security.http.RequestDecrypt;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * Created by bob on 2018/1/21.
 *
 * @author bob   <bobyang_coder@163.com>
 * @version v1.0
 * @since 2018/1/21
 */
public class RequestHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {

    private RequestDecryptResponseEncryptProcessor requestDecryptResponseEncryptProcessor;

    public RequestHandlerMethodArgumentResolver() {
        requestDecryptResponseEncryptProcessor = new DefaultRequestDecryptResponseEncryptProcessor();
    }

    public RequestHandlerMethodArgumentResolver(RequestDecryptResponseEncryptProcessor requestDecryptResponseEncryptProcessor) {
        this.requestDecryptResponseEncryptProcessor = requestDecryptResponseEncryptProcessor;
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(RequestDecrypt.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest
            , WebDataBinderFactory binderFactory) throws Exception {
        return requestDecryptResponseEncryptProcessor.decryptRequest(parameter, mavContainer, webRequest, binderFactory);
    }


    public RequestDecryptResponseEncryptProcessor getRequestDecryptResponseEncryptProcessor() {
        return requestDecryptResponseEncryptProcessor;
    }

    public void setRequestDecryptResponseEncryptProcessor(RequestDecryptResponseEncryptProcessor requestDecryptResponseEncryptProcessor) {
        this.requestDecryptResponseEncryptProcessor = requestDecryptResponseEncryptProcessor;
    }
}
