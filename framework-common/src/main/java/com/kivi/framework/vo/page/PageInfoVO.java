package com.kivi.framework.vo.page;

import java.io.Serializable;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * 由于PageHelper插件中的PageInfo的对象属性为乱码，所以重新定义
 *
 */
@Setter
@Getter
public class PageInfoVO<T> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// 当前页
	protected int pageNum;

	// 每页的数量
	protected int pageSize;
	// 当前页的数量
	protected int size;
	// 排序
	protected String orderBy;

	// 由于startRow和endRow不常用，这里说个具体的用法
	// 可以在页面中"显示startRow到endRow 共size条数据"
	// 当前页面第一个元素在数据库中的行号
	protected int startRow;
	// 当前页面最后一个元素在数据库中的行号
	protected int endRow;

	// 总页数
	protected int pages;

	// 总数
	protected long total;
	// 结果集
	protected List<T> list;

	// 第一页
	protected int firstPage;
	// 前一页
	protected int prePage;
	// 下一页
	protected int nextPage;
	// 最后一页
	protected int lastPage;

	// 是否为第一页
	protected boolean isFirstPage = false;

	// 是否为最后一页
	protected boolean isLastPage = false;

	// 是否有前一页
	protected boolean hasPreviousPage = false;

	// 是否有下一页
	protected boolean hasNextPage = false;
	// 导航页码数
	protected int navigatePages;
	// 所有导航页号
	protected int[] navigatepageNums;

	public boolean isFirstPage() {
		return isFirstPage;
	}

	public void setFirstPage(boolean isFirstPage) {
		this.isFirstPage = isFirstPage;
	}

	public boolean isLastPage() {
		return isLastPage;
	}

	public void setLastPage(boolean isLastPage) {
		this.isLastPage = isLastPage;
	}

	public boolean getIsFirstPage() {
		return isFirstPage;
	}

	public void setIsFirstPage(boolean isFirstPage) {
		this.isFirstPage = isFirstPage;
	}

	public boolean getIsLastPage() {
		return isLastPage;
	}

	public void setIsLastPage(boolean isLastPage) {
		this.isLastPage = isLastPage;
	}

}
