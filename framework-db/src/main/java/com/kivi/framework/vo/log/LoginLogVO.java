package com.kivi.framework.vo.log;

import com.kivi.framework.util.kit.DateTime;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class LoginLogVO {
    private String id;

    private String logName;

    private String userId;

    private DateTime createTime;

    private String succeed;

    private String message;

    private String ip;

    private String regionInfo;

    private DateTime updateTime;

    
}