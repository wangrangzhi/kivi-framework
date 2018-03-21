package com.kivi.framework.serialize;

import java.io.Serializable;

public class TestBean implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    String                    code;

    String                    message;

    public TestBean() {
        this.code = "S00000";
        this.message = "成功";
    }

    public String getCode() {
        return code;
    }

    public void setCode( String code ) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage( String message ) {
        this.message = message;
    }

}
