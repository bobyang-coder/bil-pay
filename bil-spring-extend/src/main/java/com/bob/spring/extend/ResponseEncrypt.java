package com.bob.spring.extend;

import java.lang.annotation.*;

/**
 * 响应加密
 *
 * @author bob   <bobyang_coder@163.com>
 * @version v1.0
 * @since 2018/1/19
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ResponseEncrypt {
}
