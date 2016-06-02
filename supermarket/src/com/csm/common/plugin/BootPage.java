package com.csm.common.plugin;

import java.io.Serializable;
import java.util.List;

import com.jfinal.plugin.activerecord.Page;

/**
 * bootstrap table 需要返回对象数据结构
 * @author zhiguo
 *
 * @param <T>
 */
public class BootPage<T>  extends Page implements Serializable {
	
	private static final long serialVersionUID = -3571929285828281909L;
	
	private List<T> rows;
	
	private int total;
	

	public BootPage(Page<T> page) {
		super(page.getList(), page.getPageNumber(), page.getPageSize(), page.getTotalPage(), page.getTotalRow());
		this.rows = page.getList();
		this.total = page.getTotalRow();
		
	}

	public List<T> getRows() {
		return rows;
	}

	public void setRows(List<T> rows) {
		this.rows = rows;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}
	
	

}
