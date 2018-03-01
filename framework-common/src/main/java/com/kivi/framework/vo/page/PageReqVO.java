package com.kivi.framework.vo.page;

import java.io.Serializable;

import com.kivi.framework.constant.enums.Order;
import com.kivi.framework.util.kit.StrKit;

import lombok.Getter;
import lombok.Setter;

/**
 */
@Setter
@Getter
public class PageReqVO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer limit;

	private Integer offset;

	private String sort;

	private String order;

	private Boolean openSort;

	private Boolean asc;

	public PageReqVO() {
		this.limit = 10;
		this.offset = 0;
		this.order = Order.ASC.getCode();

		initValue();
	}

	public PageReqVO(Integer limit, Integer offset, String sort, String order) {
		this.limit = limit;
		this.offset = offset;
		this.sort = sort;
		this.order = order;
		initValue();
	}

	private void initValue() {
		if (StrKit.isEmpty(sort)) {
			setOpenSort(false);
			this.order = null;
		} else {
			if (Order.ASC.getCode().equals(order)) {
				setAsc(true);
			} else {
				setAsc(false);
			}
		}
	}

	public String getOrderBy() {
		if (!openSort)
			return null;

		return StrKit.builder(sort, " ", order).toString();
	}

}
