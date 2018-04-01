package com.bob.security.http.support;

import com.bob.security.http.ResponseEncrypt;
import org.springframework.core.MethodParameter;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * Created by bob on 2018/1/22.
 *
 * @author bob   <bobyang_coder@163.com>
 * @version v1.0
 * @since 2018/1/22
 */
public class ResponseHandlerMethodReturnValueHandler implements HandlerMethodReturnValueHandler {
    private RequestDecryptResponseEncryptProcessor requestDecryptResponseEncryptProcessor;

    public ResponseHandlerMethodReturnValueHandler() {
    }

    public ResponseHandlerMethodReturnValueHandler(RequestDecryptResponseEncryptProcessor requestDecryptResponseEncryptProcessor) {
        this.requestDecryptResponseEncryptProcessor = requestDecryptResponseEncryptProcessor;
    }

    @Override
    public boolean supportsReturnType(MethodParameter returnType) {
        return (null != returnType.getMethodAnnotation(ResponseEncrypt.class))
                || (null != returnType.getParameterAnnotation(ResponseEncrypt.class));
    }

    @Override
    public void handleReturnValue(Object returnValue, MethodParameter returnType, ModelAndViewContainer mavContainer, NativeWebRequest webRequest) throws Exception {
        requestDecryptResponseEncryptProcessor.encryptResponse(returnValue, returnType, mavContainer, webRequest);
    }

    public RequestDecryptResponseEncryptProcessor getRequestDecryptResponseEncryptProcessor() {
        return requestDecryptResponseEncryptProcessor;
    }

    public void setRequestDecryptResponseEncryptProcessor(RequestDecryptResponseEncryptProcessor requestDecryptResponseEncryptProcessor) {
        this.requestDecryptResponseEncryptProcessor = requestDecryptResponseEncryptProcessor;
    }
}
