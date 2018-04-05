package com.kivi.framework.constant.enums;

public enum KtfDBStatus {
    UP( "UP", "正常" ), DOWN( "DOWN", "宕机" );

    private KtfDBStatus( String code, String desc ) {
        this.code = code;
        this.desc = desc;
    }

    public String code;
    public String desc;

}
