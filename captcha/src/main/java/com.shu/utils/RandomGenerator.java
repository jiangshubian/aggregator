package com.shu.utils;

import java.util.Random;

/**
 * @author: jiangshubian
 * @Description:
 * @Date: Create in 2017-06-19 14:36
 * @Version: 1.0.0
 */
public final class RandomGenerator {
    private RandomGenerator() {
        super();
    }

    /**
     * 随机对象
     */
    static final Random RANDOM = new Random(47);
    /**
     * 数字和字母字符数组
     */
    static final char[] DIGITS = {
            '0', '1', '2', '3', '4', '5',
            '6', '7', '8', '9', 'a', 'b',
            'c', 'd', 'e', 'f', 'g', 'h',
            'i', 'j', 'k', 'l', 'm', 'n',
            'o', 'p', 'q', 'r', 's', 't',
            'u', 'v', 'w', 'x', 'y', 'z'
    };

    /**
     * @param size create random num length.
     * @return
     */
    public static synchronized String getRandomStr(int size) {
        if (size <= 0) throw new IllegalArgumentException("params must be size >= 0 ,but now " + size);

        StringBuilder stringBuilder = new StringBuilder(size);
        do {
            stringBuilder.append(DIGITS[RANDOM.nextInt(DIGITS.length)]);
        } while (stringBuilder.length() < size);
        return stringBuilder.toString();
    }

}