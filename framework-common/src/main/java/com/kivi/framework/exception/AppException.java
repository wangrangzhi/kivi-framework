package com.kivi.framework.exception;

import com.kivi.framework.component.ErrorKit;
import com.kivi.framework.constant.GlobalErrorConst;
import com.kivi.framework.util.kit.StrKit;

import lombok.Getter;

@Getter
public class AppException extends RuntimeException {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    protected String          errorCode        = "";

    protected String          errorMessage     = "";

    protected String          errorTip         = "";

    // 业务异常跳转的页面
    protected String          urlPath          = "";

    public AppException() {
        this(GlobalErrorConst.E_UNDEFINED, null, null);
    }

    public AppException( String errorCode ) {
        this(errorCode, null, null);
    }

    public AppException( String errorCode, String errorTip ) {
        this(errorCode, errorTip, null);
    }

    public AppException( String errorCode, Throwable cause ) {
        this(errorCode, null, cause);
    }

    public AppException( Throwable cause ) {
        this(GlobalErrorConst.E_UNDEFINED, null, cause);
    }

    public AppException( String errorCode, String errorTip, Throwable cause ) {
        this(errorCode, ErrorKit.me().getDesc(errorCode), errorTip, cause);
    }

    public AppException( String errorCode, String errorMessage, String errorTip, Throwable cause ) {
        super(StrKit.joinIgnoreNull(StrKit.VERTICAL_BAR, errorCode, errorMessage, errorTip), cause);
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.errorTip = errorTip;
    }

    @Override
    public String toString() {
        return StrKit.join(StrKit.VERTICAL_BAR, errorCode, errorMessage, errorTip, urlPath);
    }

}
