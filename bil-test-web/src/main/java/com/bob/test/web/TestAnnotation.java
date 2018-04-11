package com.bob.test.web;

import java.lang.annotation.*;

/**
 * Created by bob on 2018/1/26.
 *
 * @author bob   <bobyang_coder@163.com>
 * @version v1.0
 * @since 2018/1/26
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface TestAnnotation {
}
