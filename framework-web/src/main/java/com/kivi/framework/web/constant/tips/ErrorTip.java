package com.kivi.framework.web.constant.tips;

import com.kivi.framework.component.ErrorKit;

/**
 * 返回给前台的错误提示
 *
 */
public class ErrorTip extends Tip {

    public ErrorTip( String code, String message ) {
        super();
        this.code = code;
        this.message = message;
        this.httpStatus = 200;
    }

    public ErrorTip( String code ) {
        this.code = code;
        this.message = ErrorKit.me().getDesc(code);
        this.httpStatus = 200;
    }
}
