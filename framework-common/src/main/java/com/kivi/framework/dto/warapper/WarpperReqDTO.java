package com.kivi.framework.dto.warapper;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

/**
 * 请求包装类
 * 
 * @author Eric
 *
 */
@Setter
@Getter
public class WarpperReqDTO<T> implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    /**
     * 客户端IP
     */
    private String            clientIp;

    /**
     * 调用发起应用名称
     */
    private String            fromAppName;

    /**
     * 请求内容
     */
    private T                 reqObject;

}
