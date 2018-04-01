package com.bob.security.http.support;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * http请求、响应加解密处理器
 *
 * @author bob   <bobyang_coder@163.com>
 * @version v1.0
 * @since 2018/1/20
 */
public interface RequestDecryptResponseEncryptProcessor {

    /**
     * 解密请求
     *
     * @param parameter
     * @param mavContainer
     * @param webRequest
     * @param binderFactory
     * @return
     */
    Object decryptRequest(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest
            , WebDataBinderFactory binderFactory)throws Exception ;

    /**
     * 加密响应
     *
     * @param returnValue
     * @param returnType
     * @param mavContainer
     * @param webRequest
     * @return
     */
    void encryptResponse(Object returnValue, MethodParameter returnType, ModelAndViewContainer mavContainer
            , NativeWebRequest webRequest)throws Exception;

}
