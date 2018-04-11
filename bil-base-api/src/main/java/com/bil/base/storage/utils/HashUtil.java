package com.bil.base.storage.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 计算hash工具
 *
 * @author bob   <bobyang_coder@163.com>
 * @version v1.0
 * @since 2018/4/6
 */
public class HashUtil {

    public static final int MOD_VALUE = 4095;
    private static MessageDigest MESSAGE_DIGEST;

    static {
        try {
            MESSAGE_DIGEST = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public static long hash(String key) {
        MESSAGE_DIGEST.reset();
        MESSAGE_DIGEST.update(key.getBytes());
        byte[] bKey = MESSAGE_DIGEST.digest();
        //具体的哈希函数实现细节--每个字节 & 0xFF 再移位
        long result = ((long) (bKey[3] & 0xFF) << 24)
                | ((long) (bKey[2] & 0xFF) << 16
                | ((long) (bKey[1] & 0xFF) << 8) | (long) (bKey[0] & 0xFF));
        return (result & 0xffffffffL);
    }

    public static int hashMod(String key) {
        return (int) (hash(key) & MOD_VALUE);
    }

    public static void main(String[] args) {
        System.out.println(hash("111") + "：" + hashMod("111"));
        System.out.println(hashMod("1"));
        System.out.println(hashMod("1100010001"));
        System.out.println(hashMod("111111"));
        System.out.println(hashMod("1112222"));
    }
}
