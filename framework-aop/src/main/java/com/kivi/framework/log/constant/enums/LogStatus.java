package com.kivi.framework.log.constant.enums;

/**
 * 业务是否成功的日志记录
 *
 */
public enum LogStatus {

    SUCCESS("成功"),
    FAIL("失败");

    String message;

    LogStatus(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
