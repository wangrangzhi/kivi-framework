package com.kivi.framework.enums;

public enum KtfServiceStatus {
    offline( "00", "离线" ),
    online( "01", "在线" );

    private String code;
    private String desc;

    private KtfServiceStatus( String code, String desc ) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public void setCode( String code ) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc( String desc ) {
        this.desc = desc;
    }

}
