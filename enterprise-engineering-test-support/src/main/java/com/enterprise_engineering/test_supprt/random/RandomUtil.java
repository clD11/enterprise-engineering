package com.enterprise_engineering.test_supprt.random;

import java.util.UUID;

public class RandomUtil {

    public static String randomString() {
        return UUID.randomUUID().toString();
    }

    public static Integer nextInteger() {
        return new java.util.Random().nextInt();
    }

    public static int nextNegativeInteger() {
        int positive = nextInteger();
        return positive == 0 ? -1 : positive * -1;
    }

}
