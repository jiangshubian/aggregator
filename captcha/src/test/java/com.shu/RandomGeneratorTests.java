package com.shu;

import com.shu.utils.RandomGenerator;
import static org.junit.Assert.*;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

/**
 * @author: jiangshubian
 * @Description:
 * @Date: Create in 2017-06-20 15:17
 * @Version: 1.0.0
 */
public class RandomGeneratorTests {

    @Test
    public void testGetRandomStr() {
        Set<String> captcha = new HashSet<String>(100);
        for (int i = 0; i < 100; i++) {
            String text = RandomGenerator.getRandomStr(4);
            assertFalse(captcha.contains(text));
            captcha.add(text);
        }
        System.out.println(captcha);
    }
}
