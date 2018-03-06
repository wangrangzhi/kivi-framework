package com.kivi.framework.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Bean类 - 分页
 */

public class PagerDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final Integer MAX_PAGE_SIZE = 99999;// 每页最大记录数限制
	public static final Integer MAX_PAGE = 5;//最多显示的页码数，只支持奇数
	
	// 排序方式（递增、递减）
	public enum Order {
		asc, desc
	}
	
	private int begin = 0;
	private int end = 10;
	

	private int pageNo = 1;// 当前页码
	private int pageSize = 10;// 每页记录数
	private int totalCount;// 总记录数
    private BigDecimal totalAmt;// (对于交易流水类记录)总金额
	private List<?> result;// 返回结果
	private List<Integer> pages = new ArrayList<Integer>();
	private int pageCount = 0;//总页数
	
	// 获取总页数
	public int getPageCount() {
		return pageCount;
	}

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		if (pageNo < 1) {
			pageNo = 1;
		}
		this.pageNo = pageNo;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		if (pageSize < 1) {
			pageSize = 1;
		} else if (pageSize > MAX_PAGE_SIZE) {
			pageSize = MAX_PAGE_SIZE;
		}
		this.pageSize = pageSize;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
		pageCount = totalCount / pageSize;
		if (totalCount % pageSize > 0) {
			pageCount++;
		}
	}

	public List<?> getResult() {
		return result;
	}

	public void setResult(List<?> result) {
		this.result = result;
	}

	public List<Integer> getPages() {
		pages.clear();
		int pageCount = getPageCount();
		if(pageCount<=MAX_PAGE){
			for(int i=1;i<=pageCount;i++){
				pages.add(i);
			}
		}else{
			if(pageNo <= (MAX_PAGE+1)/2)
			{
				//返回页面按钮从1到最大页面
				for(int i=1;i<=MAX_PAGE;i++){
					pages.add(i);
				}
			}else if((pageCount - pageNo) <= MAX_PAGE/2){
				//返回页面按钮从总页面到 (总页面-最大页面)
				for(int i=pageCount-MAX_PAGE+1;i<=pageCount;i++){
					pages.add(i);
				}
			}else{
				for(int i=pageNo-MAX_PAGE/2;i<=MAX_PAGE/2+pageNo;i++){
					pages.add(i);
				}
			}
		}
		return pages;
	}

	public void setPages(List<Integer> pages) {
		this.pages = pages;
	}

	public int getBegin() {
		this.begin = getPageSize()*(getPageNo()-1);
		return begin;
	}

	public int getEnd() {
		this.end = getPageSize()*getPageNo();
		return end;
	}

    public BigDecimal getTotalAmt() {
        return totalAmt;
    }

    public void setTotalAmt(BigDecimal totalAmt) {
        this.totalAmt = totalAmt;
    }

}