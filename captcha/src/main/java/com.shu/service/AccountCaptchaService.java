package com.shu.service;

import com.shu.Exception.AccountCaptchaException;

import java.util.List;

/**
 * @author: jiangshubian
 * @Description:
 * @Date: Create in 2017-06-19 15:27
 * @Version: 1.0.0
 */
public interface AccountCaptchaService {
    /**
     * @return
     * @throws AccountCaptchaException current exception
     */
    String gererateCaptchaKey() throws AccountCaptchaException;

    /**
     * @param captchaKey captcha content
     * @return
     * @throws AccountCaptchaException current exception
     */
    byte[] gererateCaptchaImage(String captchaKey) throws AccountCaptchaException;

    /**
     * @param captchaKey
     * @param captchaVal
     * @return
     * @throws AccountCaptchaException current exception
     */
    boolean validateCaptcha(String captchaKey, String captchaVal) throws AccountCaptchaException;

    /**
     * @return
     * @throws AccountCaptchaException
     */
    List<String> getPreDefinedTexts() throws AccountCaptchaException;

    /**
     *
     * @param preDefinedTexts
     * @throws AccountCaptchaException
     */
    void setPreDefinedTexts(List<String> preDefinedTexts) throws AccountCaptchaException;
}
