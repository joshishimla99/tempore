package ar.fi.uba.tempore.gwt.client.panel;

import java.util.List;

import ar.fi.uba.tempore.dto.ProjectDTO;
import ar.fi.uba.tempore.gwt.client.ProjectServicesClient;
import ar.fi.uba.tempore.gwt.client.ProjectServicesClientAsync;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

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
			}
		});
	}

}