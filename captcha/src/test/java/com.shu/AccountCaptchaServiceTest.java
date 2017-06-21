package com.shu;

import com.shu.service.AccountCaptchaService;
import com.shu.service.impl.AccountCaptchaServiceImpl;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: jiangshubian
 * @Description:
 * @Date: Create in 2017-06-20 15:30
 * @Version: 1.0.0
 */
public class AccountCaptchaServiceTest {
    private AccountCaptchaService services;

    @Before
    public void prepare() throws Exception {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("account-captcha.xml");
        services = ctx.getBean("accountCaptchaService", AccountCaptchaServiceImpl.class);
    }


    @Test
    public void testGeneratorCaptcha() throws Exception {
        String captchaKey = services.gererateCaptchaKey();
        assertNotNull(captchaKey);

        byte[] captchaImage = services.gererateCaptchaImage(captchaKey);
        assertTrue(captchaImage.length > 0);

        File image = new File("target/" + captchaKey + ".jpg");
        OutputStream out = null;
        try {
            out = new FileOutputStream(image);
            out.write(captchaImage);
        } finally {
            if (out != null) out.close();
        }

        assertTrue(image.exists() && image.length() > 0);
    }

    @Test
    public void testValidataCaptchaCorrent() throws Exception {
        List<String> preDefinedTexts = new ArrayList<String>();
        preDefinedTexts.add("12345");
        preDefinedTexts.add("abcde");
        services.setPreDefinedTexts(preDefinedTexts);


        String captchaKey = services.gererateCaptchaKey();
        services.gererateCaptchaImage(captchaKey);
        assertTrue(services.validateCaptcha(captchaKey, "12345"));

        captchaKey = services.gererateCaptchaKey();
        services.gererateCaptchaImage(captchaKey);
        assertTrue(services.validateCaptcha(captchaKey, "abcde"));
    }

    @Test
    public void testValidataCaptchaIncorrent() throws Exception {
        List<String> preDefinedTexts = new ArrayList<String>();
        preDefinedTexts.add("12345");
        services.setPreDefinedTexts(preDefinedTexts);


        String captchaKey = services.gererateCaptchaKey();
        services.gererateCaptchaImage(captchaKey);
        assertFalse(services.validateCaptcha(captchaKey, "abcde"));

    }

}
