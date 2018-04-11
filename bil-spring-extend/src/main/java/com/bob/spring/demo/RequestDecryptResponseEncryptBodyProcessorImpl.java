package com.bob.spring.demo;

import com.bob.spring.extend.RequestDecryptResponseEncryptBodyProcessor;
import org.springframework.http.HttpHeaders;

import java.nio.charset.Charset;

/**
 * Created by bob on 2018/1/20.
 *
 * @author bob   <bobyang_coder@163.com>
 * @version v1.0
 * @since 2018/1/20
 */
public class RequestDecryptResponseEncryptBodyProcessorImpl extends RequestDecryptResponseEncryptBodyProcessor {

    @Override
    protected String doDecryptRequestBody(String input, HttpHeaders httpHeaders, Charset charset) {
        log.info("==========doDecryptRequestBody");
        return super.doDecryptRequestBody(input, httpHeaders, charset);
    }

    @Override
    protected String doEncryptResponseBody(String input, HttpHeaders httpHeaders, Charset charset) {
        log.info("==========doEncryptResponseBody");
        return super.doEncryptResponseBody(input, httpHeaders, charset);
    }

}
