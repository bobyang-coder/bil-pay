package com.bil.base.storage.utils;

import java.util.Random;

/**
 * Created by bob on 2018/4/7.
 *
 * @author bob   <bobyang_coder@163.com>
 * @version v1.0
 * @since 2018/4/7
 */
public class IdGenrator {

    private static final Random RANDOM = new Random(System.currentTimeMillis());


    public static int get() {
        return RANDOM.nextInt();
    }

    public static void main(String[] args) {
        System.out.println(get());
        System.out.println(get());
        System.out.println(get());
    }
}
