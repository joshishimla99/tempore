package ar.fi.uba.tempore.entity.reports;

import java.io.Serializable;

public class ProjectsTimes implements Serializable {

	private static final long serialVersionUID = 9201442143606465959L;
	
	private String projectName;
	private Long hourCounted;
	
	
	public ProjectsTimes(String projectName, Long hourCounted) {
		this.projectName = projectName;
		this.hourCounted = hourCounted;
	}
	
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public Long getHourCounted() {
		return hourCounted;
	}
	public void setHourCounted(Long hourCounted) {
		this.hourCounted = hourCounted;
	}
}
