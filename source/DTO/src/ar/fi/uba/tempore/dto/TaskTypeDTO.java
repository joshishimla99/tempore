package ar.fi.uba.tempore.dto;

import com.google.gwt.user.client.rpc.IsSerializable;

public class TaskTypeDTO implements IsSerializable{

	private Integer id;
	private String name;
	private String description;

	public TaskTypeDTO(){
	}
	
	public TaskTypeDTO(Integer id){
		setId(id);
	}
	
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
