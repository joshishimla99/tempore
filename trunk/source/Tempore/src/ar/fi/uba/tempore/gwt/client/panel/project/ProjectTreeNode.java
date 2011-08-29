package ar.fi.uba.tempore.gwt.client.panel.project;

import ar.fi.uba.tempore.gwt.client.panel.Constant;

import com.smartgwt.client.widgets.tree.TreeNode;

public class ProjectTreeNode extends TreeNode {
	private String projectId;
	private String dependsOf;
	private String iconType;

	public ProjectTreeNode(String projectId, String dependsOf,
			String projectName, int iconType) {
		setProjectId(projectId);
		setDependsOf(dependsOf);
		setProjectName(projectName);
		setIconType(iconType);
	}

	private void setIconType(int iconType) {
		switch (iconType) {
		case Constant.ICON_PROJECT:
//			fireEvent(event);
			break;
		case Constant.ICON_RESOURCE:
			setIcon("../images/32x32/User.png");
			break;
		case Constant.ICON_TASK:
			setIcon("../images/32x32/Calender.png");
			break;
		}

	}

	public void setProjectId(String value) {
		setAttribute("ProjectId", value);
	}

	public void setDependsOf(String value) {
		setAttribute("DependsOf", value);
	}

	public void setProjectName(String name) {
		setAttribute("ProjectName", name);
	}

	public void setIconType(String iconType) {
		this.iconType = iconType;
	}

	public String getIconType() {
		return iconType;
	}

	public String getProjectId() {
		return projectId;
	}

	public String getDependsOf() {
		return dependsOf;
	}
}