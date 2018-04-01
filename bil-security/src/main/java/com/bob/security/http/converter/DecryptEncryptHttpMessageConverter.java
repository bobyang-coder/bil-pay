package com.bob.security.http.converter;

import com.alibaba.fastjson.JSON;
import com.bob.security.http.RequestDecrypt;
import com.bob.security.http.ResponseEncrypt;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * Created by bob on 2018/1/20.
 *
 * @author bob   <bobyang_coder@163.com>
 * @version v1.0
 * @since 2018/1/20
 */
public class DecryptEncryptHttpMessageConverter implements HttpMessageConverter {

    @Override
    public boolean canRead(Class clazz, MediaType mediaType) {
        return null != clazz.getAnnotation(RequestDecrypt.class);
    }

    @Override
    public boolean canWrite(Class clazz, MediaType mediaType) {
        return null != clazz.getAnnotation(ResponseEncrypt.class);
    }

    @Override
    public List<MediaType> getSupportedMediaTypes() {
        return Collections.singletonList(MediaType.ALL);
    }

    @Override
    public Object read(Class clazz, HttpInputMessage inputMessage) throws IOException, HttpMessageNotReadableException {
        InputStream in = inputMessage.getBody();
        HashMap map = JSON.parseObject(in, HashMap.class);
        try {
            return clazz.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void write(Object o, MediaType contentType, HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException {
        String json = JSON.toJSONString(o);
        ByteArrayOutputStream outnew = new ByteArrayOutputStream();
        outnew.write(json.getBytes());
        outnew.writeTo(outputMessage.getBody());
    }

}