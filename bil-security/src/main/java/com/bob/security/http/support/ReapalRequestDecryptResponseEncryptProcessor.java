package com.bob.security.http.support;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * 融宝加解密
 *
 * @author bob   <bobyang_coder@163.com>
 * @version v1.0
 * @since 2018/1/22
 */
@Component
public class ReapalRequestDecryptResponseEncryptProcessor implements RequestDecryptResponseEncryptProcessor {

//    @Autowired
//    private CommonBusiness commonBusiness;

    @Override
    public Object decryptRequest(MethodParameter parameter, ModelAndViewContainer modelAndViewContainer
            , NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory) throws Exception {
//        BaseReq req = null;
//        Class parameterType = parameter.getParameterType();
//        if (BaseReq.class.isAssignableFrom(parameterType)) {
//            HttpServletRequest request = nativeWebRequest.getNativeRequest(HttpServletRequest.class);
//            req = commonBusiness.decryptReqDataAndCheckSign(request, parameterType);
//        }
//        return req;
        return null;
    }

    @Override
    public void encryptResponse(Object returnValue, MethodParameter methodParameter, ModelAndViewContainer mavContainer
            , NativeWebRequest webRequest) throws Exception {
//        mavContainer.setRequestHandled(true);
//        String merchantId = webRequest.getParameter("merchant_id");
//        String result = commonBusiness.encryRes(merchantId, returnValue);
//        HttpServletResponse response = webRequest.getNativeResponse(HttpServletResponse.class);
//        response.setContentType("application/json; charset=utf-8");
//        PrintWriter writer = response.getWriter();
//        writer.write(result);
//        writer.flush();
    }
}