package com.kivi.framework.service;

import com.kivi.framework.dto.SvrStatusReqDTO;
import com.kivi.framework.dto.SvrStatusRspDTO;
import com.kivi.framework.dto.warapper.WarpperReqDTO;
import com.kivi.framework.dto.warapper.WarpperRspDTO;

/**
 * 应用服务状态
 * 
 * @author Eric
 *
 */
public interface SvrStatusService {

    /**
     * 获取英语状态
     * 
     * @param req
     * @return
     */
    WarpperRspDTO<SvrStatusRspDTO> status( WarpperReqDTO<SvrStatusReqDTO> req );
}
