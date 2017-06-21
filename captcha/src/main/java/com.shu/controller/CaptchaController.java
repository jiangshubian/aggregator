package com.shu.controller;

import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.Producer;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: jiangshubian
 * @Description:
 * @Date: Create in 2017-06-19 16:55
 * @Version: 1.0.0
 */
@Controller
@RequestMapping("/kaptcha")
public class CaptchaController {
    /**
     *
     */
    @Autowired
    private Producer captchaProducer;


    /**
     *
     */
    private static Logger logger = Logger.getLogger("CaptchaController");

    /**
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getKaptchaImage", method = RequestMethod.GET)
    public void getKaptchaImage(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession();
        String code = (String) session.getAttribute(Constants.KAPTCHA_SESSION_KEY);
        System.out.println("******************验证码是: " + code + "******************");

        response.setDateHeader("Expires", 0);

        // Set standard HTTP/1.1 no-cache headers.
        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");

        // Set IE extended HTTP/1.1 no-cache headers (use addHeader).
        response.addHeader("Cache-Control", "post-check=0, pre-check=0");

        // Set standard HTTP/1.0 no-cache header.
        response.setHeader("Pragma", "no-cache");

        // return a jpeg
        response.setContentType("image/jpeg");

        // create the text for the image
        String capText = captchaProducer.createText();

        // store the text in the session
        session.setAttribute(Constants.KAPTCHA_SESSION_KEY, capText);

        // create the image with the text
        BufferedImage bi = captchaProducer.createImage(capText);
        ServletOutputStream out = response.getOutputStream();
        // write the data out
        ImageIO.write(bi, "jpg", out);
        try {
            out.flush();
        } finally {
            out.close();
        }
    }


    /**
     * @param request
     * @param response
     */
    @RequestMapping(value = "/checkRandCode", method = RequestMethod.POST)
    public void checkRandCode(HttpServletRequest request,
                              HttpServletResponse response) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            String randCode = request.getParameter("randCode");
            logger.info("randCode: " + randCode);
            String status = "0";
            String code = (String) request.getSession().getAttribute(Constants.KAPTCHA_SESSION_KEY);
            if (randCode.toLowerCase().equals(code.toLowerCase())) status = "1";
            map.put("status", status);
            map.put("description", "");
            String data = JSONObject.fromObject(map).toString();
            logger.info("返回给页面的数据为: " + data);
            response.getWriter().print(data);
            response.getWriter().flush();
            response.getWriter().close();
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
        }
    }
}