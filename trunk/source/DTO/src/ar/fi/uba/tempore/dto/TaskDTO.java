package ar.fi.uba.tempore.dto;

import com.google.gwt.user.client.rpc.IsSerializable;

public class TaskDTO implements IsSerializable {
	private Integer id;
	private String name;
	private String description;
	private int budget;
	private float realHs;
	private TaskTypeDTO taskTypeDTO;
	private ProjectDTO project;

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

	public int getBudget() {
		return budget;
	}

	public void setBudget(int budget) {
		this.budget = budget;
	}

	public float getRealHs() {
		return realHs;
	}

	public void setRealHs(float realHs) {
		this.realHs = realHs;
	}

	public ProjectDTO getProject() {
		return project;
	}

	public void setProject(ProjectDTO project) {
		this.project = project;
	}

	public void setTaskTypeDTO(TaskTypeDTO taskTypeDTO) {
		this.taskTypeDTO = taskTypeDTO;
	}

	public TaskTypeDTO getTaskTypeDTO() {
		return taskTypeDTO;
	}

}
