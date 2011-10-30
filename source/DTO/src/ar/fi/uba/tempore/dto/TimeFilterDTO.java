package ar.fi.uba.tempore.dto;

import java.util.Date;

import com.google.gwt.user.client.rpc.IsSerializable;

public class TimeFilterDTO implements IsSerializable {
	private Date dateFilter;
	private Integer userId;
	
	
	public Date getDateFilter() {
		return dateFilter;
	}
	public void setDateFilter(Date dateFilter) {
		this.dateFilter = dateFilter;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
}
