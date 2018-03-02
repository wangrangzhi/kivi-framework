package com.kivi.framework.db.page;

import org.springframework.beans.BeanUtils;

import com.github.pagehelper.PageInfo;
import com.kivi.framework.vo.page.PageInfoVO;

import lombok.Getter;
import lombok.Setter;

/**
 * 由于PageHelper插件中的PageInfo的对象属性为乱码，所以重新定义
 *
 */
@Setter
@Getter
public class PageInfoKtf<T> extends PageInfoVO<T> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PageInfoKtf(PageInfo<T> pageInfo) {
		BeanUtils.copyProperties(pageInfo, this);
	}
	
	public PageInfoVO<T> getPageInfoVO(){
		return this;
	}

}
