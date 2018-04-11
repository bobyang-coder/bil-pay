package com.bob.test.classloder;

/**
 * Created by bob on 2018/2/2.
 *
 * @author bob   <bobyang_coder@163.com>
 * @version v1.0
 * @since 2018/2/2
 */
public class AppMain {
    public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        BilClassloader bilClassloader = new BilClassloader(null, "");
        Class<Test> aClass = (Class<Test>) bilClassloader.loadClass("com.bob.test.classloder.Test");
        Test obj = aClass.newInstance();
        obj.test();
    }
}
