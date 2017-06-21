package com.shu.Exception;

/**
 * @author: jiangshubian
 * @Description:
 * @Date: Create in 2017-06-19 15:28
 * @Version: 1.0.0
 */
public class AccountCaptchaException extends Exception {
    public AccountCaptchaException() {
    }

    public AccountCaptchaException(String message) {
        super(message);
    }

    public AccountCaptchaException(String message, Throwable cause) {
        super(message, cause);
    }

    public AccountCaptchaException(Throwable cause) {
        super(cause);
    }

    public AccountCaptchaException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
