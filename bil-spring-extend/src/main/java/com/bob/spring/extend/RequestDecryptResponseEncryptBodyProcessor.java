package com.bob.spring.extend;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpInputMessage;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

/**
 * Created by bob on 2018/1/20.
 *
 * @author bob   <bobyang_coder@163.com>
 * @version v1.0
 * @since 2018/1/20
 */
public class RequestDecryptResponseEncryptBodyProcessor {
    protected final Logger log = LoggerFactory.getLogger(getClass());

    public final String decryptRequestBody(HttpInputMessage inputMessage, Charset charset) throws IOException {
        InputStream inputStream = inputMessage.getBody();
        String input = IOUtils.toString(inputStream, charset);
        HttpHeaders httpHeaders = inputMessage.getHeaders();
        return doDecryptRequestBody(input, httpHeaders, charset);
    }

    public final String encryptResponseBody(String input, HttpHeaders httpHeaders, Charset charset) {
        return doEncryptResponseBody(input, httpHeaders, charset);
    }

    protected String doDecryptRequestBody(String input, HttpHeaders httpHeaders, Charset charset) {
        return input;
    }

    protected String doEncryptResponseBody(String input, HttpHeaders httpHeaders, Charset charset) {
        return input;
    }
}
