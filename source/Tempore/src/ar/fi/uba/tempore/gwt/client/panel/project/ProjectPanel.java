package ar.fi.uba.tempore.gwt.client.panel.project;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import ar.fi.uba.tempore.dto.ProjectDTO;
import ar.fi.uba.tempore.gwt.client.ProjectServicesClient;
import ar.fi.uba.tempore.gwt.client.ProjectServicesClientAsync;
import ar.fi.uba.tempore.gwt.client.panel.Constant;

import com.google.gwt.core.client.GWT;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.smartgwt.client.types.TreeModelType;

import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.events.DrawEvent;
import com.smartgwt.client.widgets.events.DrawHandler;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.tree.Tree;
import com.smartgwt.client.widgets.tree.TreeGrid;
import com.smartgwt.client.widgets.tree.TreeGridField;
import com.smartgwt.client.widgets.tree.TreeNode;

public class ProjectPanel extends VLayout {

	private final ProjectServicesClientAsync projectService = (ProjectServicesClientAsync) GWT.create(ProjectServicesClient.class);
	private Tree treeProject;
	private Canvas canvas;
	private ProjectTreeNode rootNode;
	private TreeGrid treeGrid; 
	
	public ProjectPanel() {
		
		canvas = new Canvas();		
		
		projectService.getProjects(new AsyncCallback<List<ProjectDTO>>() {
			
			@Override
			public void onSuccess(List<ProjectDTO> result) {
				treeGrid = new TreeGrid();
				treeGrid.setWidth(200);
				treeGrid.setHeight(400);

				TreeGridField field = new TreeGridField("ProjectName","Projectos Tempore");
				field.setCanSort(false);

				treeGrid.setFields(field);

				treeProject = new Tree();
				treeProject.setModelType(TreeModelType.PARENT);
				treeProject.setNameProperty("ProjectName");
				treeProject.setIdField("ProjectId");
				treeProject.setParentIdField("DependsOf");
				treeProject.setShowRoot(true);
				rootNode = new ProjectTreeNode("4", "1", "Proyectos", Constant.ICON_PROJECT);

				treeProject.setRoot(rootNode);
				loadProjects(allProject(), treeProject, rootNode);
				treeGrid.setData(treeProject);
				treeGrid.addDrawHandler(new DrawHandler() {
					public void onDraw(DrawEvent event) {
						treeProject.openAll();
					}
				});
				canvas.addChild(treeGrid);
			}
			
			@Override
			public void onFailure(Throwable caught) {
				Label errorLabel = new Label();
				errorLabel.setIcon("/images/64x64/Alert.png");
				errorLabel.setContents("Ha ocurrido un error intentando recuperar el listado de proyectos");
				errorLabel.setStyleName("label-errorMessages");
				errorLabel.draw();
				canvas.addChild(errorLabel);
			}
		});
    	this.addChild(canvas);
		canvas.draw();
	}
	
	public ListGridRecord getProjectSelected(){
		return this.treeGrid.getSelectedRecord();
	}
	
	/**
	 * Se encarga de actualizar el arbol de proyectos cuando hay un cambio
	 */
	public void updateProjectTree(){
		loadProjects(allProject(), treeProject, rootNode);
		canvas.redraw();
	}
	
	/**
	 * Carga en el tree el nodo del proyecto
	 * @param projects
	 * @param treeProject
	 * @param root
	 * @param resourceNode
	 * @param taskNode
	 */
	private void loadProjects(List<ProjectDTO> projects, Tree treeProject, ProjectTreeNode root) {
		for (Iterator<ProjectDTO> i = projects.iterator(); i.hasNext();) {
			ProjectDTO project = (ProjectDTO) i.next();
			ProjectTreeNode node = new ProjectTreeNode(Integer.toString(project.getId()), root.getProjectId(), project.getName(),
					Constant.ICON_PROJECT);
			addResourceAndTask(node, project.getId());
			treeProject.add(node, root);
		}
	}

	/**
	 * Agrega al tree los nodos task y resources 
	 * @param resourceNode
	 * @param taskNode
	 * @param treeProject
	 * @param root
	 */
	private void addResourceAndTask(ProjectTreeNode root, int projectId) {
		TreeNode[] children = new TreeNode[2];
		ProjectTreeNode resourceNode = new ProjectTreeNode("", "", "Recursos", Constant.ICON_RESOURCE);
		ProjectTreeNode taskNode = new ProjectTreeNode("", "", "Tareas", Constant.ICON_TASK);
		resourceNode.setProjectId(Integer.toString(projectId + Constant.ID_RESOURCE_GENERATOR));
		resourceNode.setDependsOf(Integer.toString(projectId));
		taskNode.setProjectId(Integer.toString(projectId + Constant.ID_TASK_GENERATOR));
		taskNode.setDependsOf(Integer.toString(projectId));
		children[0]= taskNode;
		children[1]=resourceNode;
		root.setChildren(children);
	}
	
	 private List<ProjectDTO> allProject() {
			List<ProjectDTO> list = new ArrayList<ProjectDTO>();

			ProjectDTO p1 = new ProjectDTO();
			p1.setId(new Integer(1));
			p1.setName("Proyecto 1");
			list.add(p1);
			
			ProjectDTO p2 = new ProjectDTO();
			p2.setId(new Integer(2));
			p2.setName("Proyecto 2");
			list.add(p2);

			ProjectDTO p3 = new ProjectDTO();
			p3.setId(new Integer(3));
			p3.setName("Proyecto 3");
			list.add(p3);

			ProjectDTO p4 = new ProjectDTO();
			p4.setId(new Integer(4));
			p4.setName("Proyecto 4");
			list.add(p4);

			// TODO Buscar en BBDD

			return list;
		}
}
