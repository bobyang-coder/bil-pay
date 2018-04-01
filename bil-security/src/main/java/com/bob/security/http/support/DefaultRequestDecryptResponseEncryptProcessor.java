package com.bob.security.http.support;

import com.alibaba.fastjson.JSON;
import com.bob.security.http.RequestDecrypt;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.util.Iterator;

/**
 * Created by bob on 2018/1/22.
 *
 * @author bob   <bobyang_coder@163.com>
 * @version v1.0
 * @since 2018/1/22
 */
public class DefaultRequestDecryptResponseEncryptProcessor implements RequestDecryptResponseEncryptProcessor {
    @Override
    public Object decryptRequest(MethodParameter parameter, ModelAndViewContainer mavContainer
            , NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        RequestDecrypt requestDecrypt = parameter.getParameterAnnotation(RequestDecrypt.class);
        String data = webRequest.getParameter(requestDecrypt.value());
        if (StringUtils.isBlank(data)) {
            return null;
        }
        // 利用fastjson转换为对应的类型
        return JSON.parseObject(data, parameter.getParameterType());
    }

    @Override
    public void encryptResponse(Object returnValue, MethodParameter returnType, ModelAndViewContainer mavContainer
            , NativeWebRequest webRequest) throws Exception {
        //设置请求已处理
        mavContainer.setRequestHandled(true);
        String string = JSON.toJSONString(returnValue);
        HttpServletResponse response = webRequest.getNativeResponse(HttpServletResponse.class);
        response.setContentType("application/json; charset=utf-8");
//4.1.3
//        response.setContentType(MediaType.APPLICATION_JSON_UTF8.toString());
        PrintWriter writer = response.getWriter();
        writer.write(string);
        writer.flush();
    }


    Object decryptRequest2(MethodParameter parameter, NativeWebRequest webRequest) throws IllegalAccessException {
        String objName = parameter.getParameterName() + ".";
        Object o = BeanUtils.instantiate(parameter.getParameterType());
        StringBuffer tmp;
        String[] val;
        Field[] frr = parameter.getParameterType().getDeclaredFields();

        for (Iterator<String> itr = webRequest.getParameterNames(); itr.hasNext(); ) {
            tmp = new StringBuffer(itr.next());
            if (tmp.indexOf(objName) < 0) {
                continue;
            }
            for (int i = 0; i < frr.length; i++) {
                frr[i].setAccessible(true);
                if (tmp.toString().equals(objName + frr[i].getName())) {
                    val = webRequest.getParameterValues(tmp.toString());
                    frr[i].set(o, val[0]);
                }
            }
        }
        return o;
    }

}
