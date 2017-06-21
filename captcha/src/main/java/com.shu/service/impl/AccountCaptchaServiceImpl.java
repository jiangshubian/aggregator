package com.shu.service.impl;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import com.shu.Exception.AccountCaptchaException;
import com.shu.service.AccountCaptchaService;
import com.shu.utils.RandomGenerator;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.*;

/**
 * @author: jiangshubian
 * @Description:
 * @Date: Create in 2017-06-19 15:34
 * @Version: 1.0.0
 */
public class AccountCaptchaServiceImpl implements AccountCaptchaService, InitializingBean {
    /**
     *
     */
    private DefaultKaptcha producer;

    /**
     * 保存验证码key和验证码的
     */
    private Map<String, String> captchaMap = new LinkedHashMap<String, String>();

    /**
     *自定义验证码内容
     */
    private List<String> preDefaultTexts;

    /**
     * 自定义验证码内容编号，按照preDefaultTexts长度为周期轮训获取
     */
    private int textCount = 0;

    @Override
    public String gererateCaptchaKey() throws AccountCaptchaException {
        String key = RandomGenerator.getRandomStr(6);
        String val = getCaptchaText();
        captchaMap.put(key, val);
        return key;
    }

    /**
     * 轮训获取验证码内容
     *
     * @return
     */
    private String getCaptchaText() {
        if (preDefaultTexts != null && !preDefaultTexts.isEmpty()) {
            String text = preDefaultTexts.get(textCount);
            textCount = (textCount + 1) % preDefaultTexts.size(); //以preDefaultTexts长度为周期进行内容轮训
            return text;
        } else {
            return producer.createText();
        }
    }

    @Override
    public byte[] gererateCaptchaImage(String captchaKey) throws AccountCaptchaException {
        String text = captchaMap.get(captchaKey);
        if (text == null) throw new AccountCaptchaException("Captcha key " + captchaKey + " not found!");
        BufferedImage image = producer.createImage(text);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            ImageIO.write(image, "jpg", out);
        } catch (IOException e) {
            throw new AccountCaptchaException("Failed to write captcha stream!", e);
        }
        return out.toByteArray();
    }

    @Override
    public boolean validateCaptcha(String captchaKey, String captchaVal) throws AccountCaptchaException {
        String text = captchaMap.get(captchaKey);
        if (text == null) throw new AccountCaptchaException("Captcha key " + captchaKey + " not found!");
        if (text.equals(captchaVal)) {
            captchaMap.remove(captchaKey);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public List<String> getPreDefinedTexts() throws AccountCaptchaException {
        return preDefaultTexts;
    }

    @Override
    public void setPreDefinedTexts(List<String> preDefinedTexts) throws AccountCaptchaException {
        this.preDefaultTexts = preDefinedTexts;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        producer = new DefaultKaptcha();
        producer.setConfig(new Config(new Properties()));
    }
}
