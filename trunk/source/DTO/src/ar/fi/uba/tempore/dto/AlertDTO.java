package ar.fi.uba.tempore.dto;

import java.io.Serializable;

public class AlertDTO implements Serializable {

	private static final long serialVersionUID = 2087608124623022391L;
	private Integer id;
	private String name;
	private String description;
	
	//private UserProject userProject;
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
		
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}		
}
