package com.bob.test.web;

import org.springframework.stereotype.Service;

/**
 * Created by bob on 2018/1/25.
 *
 * @author bob   <bobyang_coder@163.com>
 * @version v1.0
 * @since 2018/1/25
 */
@Service
public class TestServiceApiImpl implements TestServiceApi {

    @Override
    public void test() throws Exception {
        Exception e = null;
        try {
            System.out.println("我是环绕通知-start");
            System.out.println("前置通知");
            System.out.println("hello world,我是被代理对象");
            System.out.println("我是环绕通知-end");
        } catch (Exception se) {
            e = se;
        }
        System.out.println("我是后置通知");
        if (null != e) {
            System.out.println("我是异常通知");
            throw e;
        }
        System.out.println("我是正常返回通知");
    }

    /**  有异常
     * 环绕通知-start
     前置通知
     hello world
     后置通知
     异常通知
     */


    /**
     * 我是环绕通知-start
     前置通知
     hello world,我是被代理对象
     我是后置通知
     我是异常通知
     */
}
