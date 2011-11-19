package ar.fi.uba.tempore.dto.reports;

import java.io.Serializable;

public class ProjectsTimesDTO implements Serializable {

	private static final long serialVersionUID = 9201442143606465959L;
	
	private String projectName;
	private Long hourCounted;
		
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public Long getHourCounted() {
		return hourCounted==null?0L:hourCounted;
	}
	public void setHourCounted(Long hourCounted) {
		this.hourCounted = hourCounted;
	}
}
