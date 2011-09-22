package ar.fi.uba.tempore.gwt.client.panel.task;

import ar.fi.uba.tempore.dto.ProjectDTO;
import ar.fi.uba.tempore.gwt.client.panel.TabsPanelContainer;
import ar.fi.uba.tempore.gwt.client.panel.project.ProjectPanel;
import ar.fi.uba.temporeutils.observer.ProjectObserver;

import com.google.gwt.user.client.Random;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.VerticalAlignment;
import com.smartgwt.client.widgets.AnimationCallback;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.Img;
import com.smartgwt.client.widgets.ImgButton;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.events.CloseClickHandler;
import com.smartgwt.client.widgets.events.CloseClientEvent;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.ButtonItem;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.form.fields.StaticTextItem;
import com.smartgwt.client.widgets.form.fields.TextAreaItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.LayoutSpacer;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.layout.VStack;

public class TaskTabPanel extends TabsPanelContainer implements ProjectObserver {
	
	private TaskLayout portalLayout;
	private DynamicForm form;
	private static String[] colors = new String[] { "FF6600", "808000",
			"008000", "008080", "0000FF", "666699", "FF0000", "FF9900",
			"99CC00", "339966", "33CCCC", "3366FF", "800080", "969696",
			"FF00FF", "FFCC00", "FFFF00", "00FF00", "00FFFF", "00CCFF",
			"993366", "C0C0C0", "FF99CC", "FFCC99", "FFFF99", "CCFFCC",
			"CCFFFF", "99CCFF", "CC99FF", "FFFFFF" };
	
	
	public TaskTabPanel(){
		super();
		updateContent();
	}

	@Override
	public void destroy() {
		ProjectPanel.getInstance().removeObserver(this);
		super.destroy();
	}

	@Override
	public void updateProjectSelected() {
		ProjectDTO selected = ProjectPanel.getInstance().getSelected();
		if (selected != null) {
			// TODO: update los campos del form
			// copy(selected, form);
		}

	}

	public void updateContent() {
		ProjectPanel.getInstance().addObserver(this);
		portalLayout = new TaskLayout(3);
		portalLayout.setWidth100();
		portalLayout.setHeight100();

		// TODO: obtener de la base de datos las tareas del proyecto
		// seleccionado
		// create tareas...
//		for (int i = 1; i <= 2; i++) {
//			Task portlet = new Task();
//			portlet.setTitle("Tarea");
//
//			Label label = new Label();
//			label.setAlign(Alignment.CENTER);
//			label.setLayoutAlign(VerticalAlignment.CENTER);
//			label.setContents("Nombre de la tarea");
//			label.setBackgroundColor(colors[Random.nextInt(colors.length - 1)]);
//			portlet.addItem(label);
//			portalLayout.addTask(portlet);
//		}
			final VLayout vLayout = new VLayout(15);
			vLayout.setMargin(10);
			vLayout.setWidth100();
			final HLayout buttonsLayout = new HLayout();
			buttonsLayout.setAlign(Alignment.LEFT);
			
			
			BackButton backButton = new BackButton();
			
			NextButton nextButton = new NextButton();
			buttonsLayout.setHeight(40);
			buttonsLayout.setMembers(backButton, nextButton);
			buttonsLayout.setAlign(Alignment.RIGHT);
			
			form = new DynamicForm();
			form.setAutoWidth();
			form.setNumCols(5);

			final StaticTextItem numColItem = new StaticTextItem();
			numColItem.setVisible(false);
			// numColItem.setTitle("Columns");
			// numColItem.setValue(portalLayout.getMembers().length);
			
			final ButtonItem addTask = createAddTaskButton();
			form.setItems(numColItem, addTask);

			vLayout.addMember(form);
			vLayout.addMember(buttonsLayout);
			vLayout.addMember(portalLayout);
			addChild(vLayout);
			redraw();
	}

	private ButtonItem createAddTaskButton(){
		final ButtonItem addTask = new ButtonItem("addPortlet",
		"Agregar Tarea");
		addTask.setIcon("../images/application_view_tile.png");
		addTask.setAutoFit(true);

		addTask.setStartRow(false);
		addTask.setEndRow(false);
		addTask.addClickHandler(new com.smartgwt.client.widgets.form.fields.events.ClickHandler() {
					public void onClick(
							com.smartgwt.client.widgets.form.fields.events.ClickEvent event) {
						
						NewTaskModalWindow newTaskModalWin = new NewTaskModalWindow(addTask);
						newTaskModalWin.show();
					}
				});
		return addTask;
	}
	
	/**
	 * Boton back
	 */
	
	 public static class BackButton extends ImgButton  {  
	        public BackButton() {  
	        	setSrc("../images/32x32/Back.png");  
	        	setWidth(32);
	        	
	            addClickHandler(new ClickHandler() {  
	                public void onClick(ClickEvent event) {  
	                    goBack();  
	                }
	            });  
	        }
	        
	        private void goBack() {
				// TODO Auto-generated method stub
				
			}  
	 }
	 
	 /**
		 * Boton back
		 */
		
		 public static class NextButton extends ImgButton {  
		        public NextButton() {  
		        	setSrc("../images/32x32/Forward.png");  
		        	setWidth(32);
		            addClickHandler(new ClickHandler() {  
		                public void onClick(ClickEvent event) {  
		                    goForward();  
		                }
		            });  
		        }
		        
		        private void goForward() {
					// TODO Auto-generated method stub
					
				}  
		 }
	/**
	 * Handler to create new task
	 * @author Ludmila
	 *
	 */
	private class NewTaskModalWindow {
		private Window winModal;
		
		public NewTaskModalWindow(final ButtonItem addTask){
			winModal = new Window();  
	        winModal.setWidth(360);  
	        winModal.setHeight(265);  
	        winModal.setTitle("Editar Tarea");  
	        winModal.setShowMinimizeButton(false);  
	        winModal.setIsModal(true);  
	        winModal.setShowModalMask(true);  
	        winModal.centerInPage();  
	        winModal.addCloseClickHandler(new CloseClickHandler() {  
	            public void onCloseClick(CloseClientEvent event) {  
	                winModal.destroy();  
	            }  
	        }); 
	        final DynamicForm form = new DynamicForm();  
	        form.setHeight100();  
	        form.setWidth100();  
	        form.setPadding(5);  
	        form.setLayoutAlign(VerticalAlignment.BOTTOM);  
	        
	     // Nombre de la tarea
			final TextItem taskNameLabel = new TextItem();
			taskNameLabel.setTitle("Nombre");
			taskNameLabel.setLength(30);
			taskNameLabel.setRequired(true);

			// description
			final TextAreaItem taskDescription = new TextAreaItem();
			taskDescription.setTitle("Descripci&oacute;n");
			taskDescription.setLength(150);
			taskDescription.setRequired(true);
			
			final  SelectItem taskStatus = new SelectItem();  
			taskStatus.setTitle("Estado");  
			taskStatus.setValueMap("<span style='color:#FF0000;'>Nueva</span>",  
	                "<span style='color:#00FF00;'>Cerrada</span>",  
	                "<span style='color:#0000FF;'>En progreso</span>");
			
			// Nombre de la tarea
			final TextItem estimatedTimeLabel = new TextItem();
			estimatedTimeLabel.setTitle("Tiempo Estimado");
			estimatedTimeLabel.setKeyPressFilter("[0-9.]");
			estimatedTimeLabel.setRequired(true);  
	        
			
			IButton editTaskButton = new IButton();
			editTaskButton.setTitle("Guardar");
			editTaskButton.setIcon("../images/ico/save.ico");
			editTaskButton.addClickHandler(new ClickHandler(){

				@Override
				public void onClick(ClickEvent event) {
					
					if (form.validate()) {
						// TODO: Update the data base
						winModal.destroy();
						updateTaskList(addTask, taskNameLabel.getValueAsString(),taskStatus.getValueAsString(), estimatedTimeLabel.getValueAsString(), taskDescription.getValueAsString());
					}
				}
				
			});    			

			IButton cancelTaskButton = new IButton();
			cancelTaskButton.setTitle("Cerrar");
			cancelTaskButton.setIcon("../images/ico/close.ico");
			cancelTaskButton.addClickHandler(new ClickHandler() {
				public void onClick(ClickEvent event) {
					winModal.destroy();
				}
			});  
			
	        form.setFields(taskNameLabel,taskDescription, taskStatus, estimatedTimeLabel);  
	        VLayout vLayout = new VLayout();
	        HLayout buttonLayout = new HLayout();
	        buttonLayout.setMembersMargin(10);
	        buttonLayout.setAlign(Alignment.CENTER);
	        buttonLayout.addMember(editTaskButton);
	        buttonLayout.addMember(cancelTaskButton);
	        
	        vLayout.addMember(form);
	        vLayout.addMember(buttonLayout);
	        
	        winModal.addItem(vLayout);
		}
		
		private void updateTaskList(ButtonItem addTask, String name, String status, String estimation, String description){
			final Task newTask = new Task(name, estimation, description, status);
			
			newTask.setVisible(false);
			TaskColumn column = portalLayout.addTask(newTask);

			// also insert a blank spacer element, which will
			// trigger the built-in
			// animateMembers layout animation
			final LayoutSpacer placeHolder = new LayoutSpacer();
			placeHolder.setRect(newTask.getRect());
			column.addMember(placeHolder, 0); // add to top

			// create an outline around the clicked button
			final Canvas outline = new Canvas();
			outline.setLeft(form.getAbsoluteLeft()
					+ addTask.getLeft());
			outline.setTop(form.getAbsoluteTop());
			outline.setWidth(addTask.getWidth());
			outline.setHeight(addTask.getHeight());
			outline.setBorder("2px solid #8289A6");
			outline.draw();
			outline.bringToFront();

			outline.animateRect(newTask.getPageLeft(),
					newTask.getPageTop(),
					newTask.getVisibleWidth(),
					newTask.getViewportHeight(),
					new AnimationCallback() {
						public void execute(boolean earlyFinish) {
							// callback at end of animation -
							// destroy placeholder and outline;
							// show the new portlet
							placeHolder.destroy();
							outline.destroy();
							newTask.show();
						}
					}, 750);
		}
		
		public void show(){
			this.winModal.show();
		}
		
	}
	/**
	 * TaskColumn class definition
	 */
	private class TaskColumn extends VStack {

		public TaskColumn() {

			// leave some space between portlets
			setMembersMargin(6);

			// enable predefined component animation
			setAnimateMembers(true);
			setAnimateMemberTime(300);

			// enable drop handling
			setCanAcceptDrop(true);

			// change appearance of drag placeholder and drop indicator
			setDropLineThickness(4);

			Canvas dropLineProperties = new Canvas();
			dropLineProperties.setBackgroundColor("aqua");
			setDropLineProperties(dropLineProperties);

			setShowDragPlaceHolder(true);

			Canvas placeHolderProperties = new Canvas();
			placeHolderProperties.setBorder("2px solid #8289A6");
			setPlaceHolderProperties(placeHolderProperties);
		}
	}

	/**
	 * TaskLayout class definition
	 */
	private class TaskLayout extends HLayout {
		public TaskLayout(int numColumns) {
			setMembersMargin(6);
			for (int i = 0; i < numColumns; i++) {
				addMember(new TaskColumn());
			}
		}

		public TaskColumn addTask(Task task) {
			// find the column with the fewest portlets
			int fewestPortlets = Integer.MAX_VALUE;
			TaskColumn fewestTasksColumn = null;
			for (int i = 0; i < getMembers().length; i++) {
				int numPortlets = ((TaskColumn) getMember(i)).getMembers().length;
				if (numPortlets < fewestPortlets) {
					fewestPortlets = numPortlets;
					fewestTasksColumn = (TaskColumn) getMember(i);
				}
			}
			fewestTasksColumn.addMember(task);
			return fewestTasksColumn;
		}
	}

}
