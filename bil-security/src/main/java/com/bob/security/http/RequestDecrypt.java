package com.bob.security.http;

import java.lang.annotation.*;

/**
 * 请求解密
 *
 * @author bob   <bobyang_coder@163.com>
 * @version v1.0
 * @since 2018/1/19
 */
@Documented
@Target({ElementType.PARAMETER, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface RequestDecrypt {

    /**
     * http请求中需要解密的参数名称
     *
     * @return
     */
    String value() default "data";
}
