package ar.fi.uba.tempore.gwt.client.panel.project;

import java.util.List;

import ar.fi.uba.tempore.dto.ProjectDTO;
import ar.fi.uba.tempore.gwt.client.ProjectServicesClient;
import ar.fi.uba.tempore.gwt.client.ProjectServicesClientAsync;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.smartgwt.client.types.TreeModelType;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.tree.Tree;
import com.smartgwt.client.widgets.tree.TreeNode;

public class ProjectPanel extends VLayout {

	List<ProjectDTO> projects;
	private final ProjectServicesClientAsync projectService = GWT.create(ProjectServicesClient.class);

	public ProjectPanel() {
		final Label projectLabel = new Label();
		this.addChild(projectLabel);
		
		projectService.getProjects(new AsyncCallback<List<ProjectDTO>>(){

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onSuccess(List<ProjectDTO> result) {
				
				projectLabel.setContents(result.get(0).getName());
				projectLabel.draw();
				Tree tree = new Tree();
				tree.setModelType(TreeModelType.CHILDREN);
				tree.setRoot(new TreeNode ("root", new TreeNode(result.get(0).getName()), new TreeNode(result.get(1).getName())));
			}
		});
	}

}
