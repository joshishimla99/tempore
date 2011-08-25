package ar.fi.uba.tempore.gwt.client.panel.project;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import ar.fi.uba.tempore.dto.ProjectDTO;
import ar.fi.uba.tempore.gwt.client.ProjectServicesClient;
import ar.fi.uba.tempore.gwt.client.ProjectServicesClientAsync;
import ar.fi.uba.tempore.gwt.client.callback.ProjectCallback;
import ar.fi.uba.tempore.gwt.client.panel.Constant;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.smartgwt.client.types.TreeModelType;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.events.DrawEvent;
import com.smartgwt.client.widgets.events.DrawHandler;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.tree.Tree;
import com.smartgwt.client.widgets.tree.TreeGrid;
import com.smartgwt.client.widgets.tree.TreeGridField;
import com.smartgwt.client.widgets.tree.TreeNode;

public class ProjectPanel extends VLayout {

	private final ProjectServicesClientAsync projectService = (ProjectServicesClientAsync) GWT.create(ProjectServicesClient.class);

	public ProjectPanel() {
		final Canvas canvas = new Canvas();		
		Label projectLabel = new Label();
		
		List<ProjectDTO> lista = null;
		ProjectCallback myUserCallback = new ProjectCallback(lista);
		projectService.getProjects(myUserCallback);
//		projectService.getProjects(new AsyncCallback<List<ProjectDTO>>() {
//		
//			@Override
//			public void onFailure(Throwable caught) {
//				// Label errorLabel = new Label();
//				// errorLabel.setIcon("/images/64x64/Alert.png");
//				// errorLabel.setContents("Ha ocurrido un error intentando recuperar el listado de proyectos");
//				// errorLabel.setStyleName("label-errorMessages");
//				// errorLabel.draw();
//				// canvas.addChild(errorLabel);
//			}
//
//			@Override
//			public void onSuccess(List<ProjectDTO> result) {
				// TODO: Sacar esto cuando funcione
				List<ProjectDTO> result =  allProject();
				 projectLabel.setContents(result.get(0).getName());
				 projectLabel.draw();
				 Tree tree = new Tree();
				 tree.setModelType(TreeModelType.CHILDREN);
				 tree.setRoot(new TreeNode ("root", new
				 TreeNode(result.get(0).getName()), new
				 TreeNode(result.get(1).getName())));
				
//			}
//		});

		// /LUD
		TreeGrid treeGrid = new TreeGrid();
		treeGrid.setWidth(200);
		treeGrid.setHeight(400);

		TreeGridField field = new TreeGridField("ProjectName",
				"Projectos Tempore");
		field.setCanSort(false);

		treeGrid.setFields(field);

		final Tree treeProject = new Tree();
		treeProject.setModelType(TreeModelType.PARENT);
		treeProject.setNameProperty("ProjectName");
		treeProject.setIdField("ProjectId");
		treeProject.setParentIdField("DependsOf");
		treeProject.setShowRoot(true);
		ProjectTreeNode rootNode = new ProjectTreeNode("4", "1", "Proyectos", Constant.ICON_PROJECT);
		ProjectTreeNode resourceNode = new ProjectTreeNode("", "", "Recursos", Constant.ICON_RESOURCE);
		ProjectTreeNode taskNode = new ProjectTreeNode("", "", "Tareas", Constant.ICON_TASK);

		treeProject.setRoot(rootNode);
		this.loadProjects(allProject(), treeProject, rootNode, resourceNode, taskNode);
//TEST dejarlo comentado
//		ProjectTreeNode node3 = new ProjectTreeNode("189", "4",
//				"EFD154-Mwallet Telcel", Constant.ICON_PROJECT);
//		ProjectTreeNode node4 = new ProjectTreeNode("265", "189", "Recursos",
//				Constant.ICON_RESOURCE);
//		ProjectTreeNode node5 = new ProjectTreeNode("264", "189", "Tareas",
//				Constant.ICON_TASK);
//		ProjectTreeNode node6 = new ProjectTreeNode("190", "4",
//				"EFD145-PDM Claro", Constant.ICON_PROJECT);
//		ProjectTreeNode node7 = new ProjectTreeNode("267", "190", "Recursos",
//				Constant.ICON_RESOURCE);
//		ProjectTreeNode node8 = new ProjectTreeNode("266", "190", "Tareas",
//				Constant.ICON_TASK);
//
//		treeProject.setData(new TreeNode[] { rootNode, node3, node4, node5, node6,
//				node7, node8 });
		//TEST dejarlo comentado
		treeGrid.addDrawHandler(new DrawHandler() {
			public void onDraw(DrawEvent event) {
				treeProject.openAll();
			}
		});
//
//		treeGrid.setData(treeProject);
//		canvas.addChild(treeGrid);
//		treeGrid.draw();

		this.addChild(canvas);
		canvas.draw();
	}

	/**
	 * Carga en el tree el nodo del proyecto
	 * @param projects
	 * @param treeProject
	 * @param root
	 * @param resourceNode
	 * @param taskNode
	 */
	private void loadProjects(List<ProjectDTO> projects, Tree treeProject, ProjectTreeNode root, ProjectTreeNode resourceNode, ProjectTreeNode taskNode) {
		for (Iterator<ProjectDTO> i = projects.iterator(); i.hasNext();) {
			ProjectDTO project = (ProjectDTO) i.next();
			ProjectTreeNode node = new ProjectTreeNode(Integer.toString(project.getId()), root.getProjectId(), project.getName(),
					Constant.ICON_PROJECT);
			treeProject.add(node, root);
			addResourceAndTask(resourceNode, taskNode, treeProject, node);
		}
	}

	/**
	 * Agrega al tree los nodos task y resources 
	 * @param resourceNode
	 * @param taskNode
	 * @param treeProject
	 * @param root
	 */
	private void addResourceAndTask(ProjectTreeNode resourceNode, ProjectTreeNode taskNode, Tree treeProject, ProjectTreeNode root) {
		resourceNode.setProjectId(root.getProjectId() + Constant.ID_RESOURCE_GENERATOR);
		resourceNode.setDependsOf(root.getProjectId());
		taskNode.setProjectId(root.getProjectId() + Constant.ID_TASK_GENERATOR);
		taskNode.setDependsOf(root.getProjectId());
		treeProject.add(resourceNode, root);
		treeProject.add(taskNode, root);
	}

	private List<ProjectDTO> allProject() {
		List<ProjectDTO> list = new ArrayList<ProjectDTO>();

		ProjectDTO p = new ProjectDTO();
		p.setId(new Integer(1));
		p.setName("Proyecto 1");
		list.add(p);

		p.setId(new Integer(2));
		p.setName("Proyecto 2");
		list.add(p);

		p.setId(new Integer(3));
		p.setName("Proyecto 3");
		list.add(p);

		p.setId(new Integer(4));
		p.setName("Proyecto 4");
		list.add(p);

		// TODO Buscar en BBDD

		return list;
	}
}
