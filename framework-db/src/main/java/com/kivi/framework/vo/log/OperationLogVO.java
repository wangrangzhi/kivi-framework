package com.kivi.framework.vo.log;

import com.kivi.framework.util.kit.DateTime;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class OperationLogVO {
    private String id;

    private String logType;

    private String logName;

    private String userId;

    private String className;

    private String method;

    private DateTime createTime;

    private String succeed;

    private String message;

    private String ip;

    private DateTime updateTime;

    
}