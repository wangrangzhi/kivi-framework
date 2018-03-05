package com.kivi.framework.web.constant.dictmap;

import com.kivi.framework.web.constant.dictmap.base.AbstractDictMap;

/**
 * 通知的映射
 *
 */
public class NoticeMap extends AbstractDictMap {

    @Override
    public void init() {
        put("title", "标题");
        put("content", "内容");
    }

    @Override
    protected void initBeWrapped() {
    }
}
