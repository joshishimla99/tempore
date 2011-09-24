package ar.fi.uba.tempore.dto;

import com.google.gwt.user.client.rpc.IsSerializable;

public class TaskDTO implements IsSerializable {
	private String id;
	private String name;
	private String description;
	private float estimatedHs;
	private float realHs;
	private String status;

	public String getId() {
		return id;
	}

	public void setId(String id) {
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

	public float getEstimatedHs() {
		return estimatedHs;
	}

	public void setEstimatedHs(float estimatedHs) {
		this.estimatedHs = estimatedHs;
	}

	public float getRealHs() {
		return realHs;
	}

	public void setRealHs(float realHs) {
		this.realHs = realHs;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
