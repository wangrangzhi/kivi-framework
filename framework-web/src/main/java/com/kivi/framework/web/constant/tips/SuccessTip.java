package com.kivi.framework.web.constant.tips;

import com.kivi.framework.constant.GlobalErrorConst;

/**
 * 返回给前台的成功提示
 *
 */
public class SuccessTip extends Tip {

    public SuccessTip() {
        super.httpStatus = 200;
        super.code = GlobalErrorConst.SUCCESS;
        super.message = "操作成功";
    }
}
