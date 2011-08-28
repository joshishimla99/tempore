package ar.fi.uba.tempore.gwt.client.panel.project;

import java.util.List;

import ar.fi.uba.tempore.dto.ProjectDTO;
import ar.fi.uba.tempore.gwt.client.ProjectServicesClient;
import ar.fi.uba.tempore.gwt.client.ProjectServicesClientAsync;
import ar.fi.uba.tempore.gwt.client.callback.ProjectCallback;

import com.google.gwt.core.client.GWT;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.tree.TreeNode;

public class ProjectPanel extends VLayout {

//	List<ProjectDTO> projects;
	private final ProjectServicesClientAsync projectService = (ProjectServicesClientAsync) GWT.create(ProjectServicesClient.class);
	
	
	public ProjectPanel() {
		final Label projectLabel = new Label();
		this.addChild(projectLabel);
		
		final Canvas canvas = new Canvas();		
		
		List<ProjectDTO> lista = null;
		
		/*ProjectServicesClientAsync service = (ProjectServicesClientAsync) GWT.create(ProjectServicesClientAsync.class);
		ServiceDefTarget serviceDef = (ServiceDefTarget) service;
		serviceDef.setServiceEntryPoint(GWT.getModuleBaseURL()+ "ProjectServices");
		*/
		ProjectCallback myUserCallback = new ProjectCallback(lista);
		/*
		com.google.gwt.user.client.Window.alert("Invocando al servicio!!!");
		projectService.getProjects(new AsyncCallback<List<ProjectDTO>>() {
			
			@Override
			public void onSuccess(List<ProjectDTO> result) {
				// TODO Auto-generated method stub
				com.google.gwt.user.client.Window.alert("OK");
			}
			
			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				com.google.gwt.user.client.Window.alert("ERROR: " + caught.getMessage());
			}
		});
		*/
		projectService.getProjects(myUserCallback);
		
		/*(new AsyncCallback<List<ProjectDTO>>(){

			@Override
			public void onFailure(Throwable caught) {
				Label errorLabel = new Label();
				errorLabel.setIcon("/images/64x64/Alert.png");
				errorLabel.setContents("Ha ocurrido un error intentando recuperar el listado de proyectos");
				errorLabel.setStyleName("label-errorMessages");
				errorLabel.draw();
				canvas.addChild(errorLabel);
			}

			@Override
			public void onSuccess(List<ProjectDTO> result) {
				
				projectLabel.setContents(result.get(0).getName());
				projectLabel.draw();
				Tree tree = new Tree();
				tree.setModelType(TreeModelType.CHILDREN);
				tree.setRoot(new TreeNode ("root", new TreeNode(result.get(0).getName()), new TreeNode(result.get(1).getName())));
				
				///LUD
				TreeGrid treeGrid = new TreeGrid();  
		        treeGrid.setWidth(300);  
		        treeGrid.setHeight(400);  
		  
		        TreeGridField field = new TreeGridField("Name", "Tree from local data");  
		        field.setCanSort(false);  
		  
		        treeGrid.setFields(field);  
		  
		        final Tree treeUser = new Tree();  
		        treeUser.setModelType(TreeModelType.PARENT);  
		        treeUser.setNameProperty("Name");  
		        treeUser.setIdField("EmployeeId");  
		        treeUser.setParentIdField("ReportsTo");  
		        treeUser.setShowRoot(true);  
		  
		        EmployeeTreeNode root = new EmployeeTreeNode("4", "1", "Charles Madigen");  
		        EmployeeTreeNode node2 = new EmployeeTreeNode("188", "4", "Rogine Leger");  
		        EmployeeTreeNode node3 = new EmployeeTreeNode("189", "4", "Gene Porter");  
		        EmployeeTreeNode node4 = new EmployeeTreeNode("265", "189", "Olivier Doucet");  
		        EmployeeTreeNode node5 = new EmployeeTreeNode("264", "189", "Cheryl Pearson");  
		        treeUser.setData(new TreeNode[]{root, node2, node3, node4, node5});  
		  
		        treeGrid.addDrawHandler(new DrawHandler() {  
		            public void onDraw(DrawEvent event) {  
		            	treeUser.openAll();  
		            }  
		        });  
		          
		        treeGrid.setData(tree);  
		        canvas.addChild(treeGrid);
		        treeGrid.draw();
			}
		});*/
		
		
		this.addChild(canvas);
		canvas.draw();
	}

	 public static class EmployeeTreeNode extends TreeNode {  
		  
	        public EmployeeTreeNode(String employeeId, String reportsTo, String name) {  
	            setEmployeeId(employeeId);  
	            setReportsTo(reportsTo);  
	            setName(name);  
	        }  
	  
	        public void setEmployeeId(String value) {  
	            setAttribute("EmployeeId", value);  
	        }  
	  
	        public void setReportsTo(String value) {  
	            setAttribute("ReportsTo", value);  
	        }  
	  
	        public void setName(String name) {  
	            setAttribute("Name", name);  
	        }  
	    }  
}
