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
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.ButtonItem;
import com.smartgwt.client.widgets.form.fields.StaticTextItem;
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
		// this.clear();

		// TODO: obtener de la base de datos las tareas del proyecto
		// seleccionado
		// create tareas...
		for (int i = 1; i <= 2; i++) {
			Task portlet = new Task();
			portlet.setTitle("Tarea");

			Label label = new Label();
			label.setAlign(Alignment.CENTER);
			label.setLayoutAlign(VerticalAlignment.CENTER);
			label.setContents("Nombre de la tarea");
			label.setBackgroundColor(colors[Random.nextInt(colors.length - 1)]);
			portlet.addItem(label);
			portalLayout.addTask(portlet);

			final VLayout vLayout = new VLayout(15);
			vLayout.setMargin(10);
			vLayout.setWidth100();

			form = new DynamicForm();
			form.setAutoWidth();
			form.setNumCols(5);

			final StaticTextItem numColItem = new StaticTextItem();
			numColItem.setVisible(false);
			// numColItem.setTitle("Columns");
			// numColItem.setValue(portalLayout.getMembers().length);

			final ButtonItem addColumn = new ButtonItem("addColumn",
					"Agregar Column");
			addColumn.setIcon("silk/application_side_expand.png");
			addColumn.setAutoFit(true);
			addColumn.setStartRow(false);
			addColumn.setEndRow(false);

			addColumn.addClickHandler(new com.smartgwt.client.widgets.form.fields.events.ClickHandler() {
						public void onClick(
								com.smartgwt.client.widgets.form.fields.events.ClickEvent event) {
							portalLayout.addMember(new TaskColumn());
							numColItem.setValue(portalLayout.getMembers().length);

						}
					});

			final ButtonItem removeColumn = new ButtonItem("removeColumn",
					"Eliminar Columna");
			removeColumn.setIcon("silk/application_side_contract.png");
			removeColumn.setAutoFit(true);
			removeColumn.setStartRow(false);
			removeColumn.setEndRow(false);

			removeColumn.addClickHandler(new com.smartgwt.client.widgets.form.fields.events.ClickHandler() {
						public void onClick(
								com.smartgwt.client.widgets.form.fields.events.ClickEvent event) {

							Canvas[] canvases = portalLayout.getMembers();
							int numMembers = canvases.length;
							if (numMembers > 0) {
								Canvas lastMember = canvases[numMembers - 1];
								portalLayout.removeMember(lastMember);
								numColItem.setValue(numMembers - 1);
							}

						}
					});

			final ButtonItem addPortlet = new ButtonItem("addPortlet",
					"Agregar Tarea");
			addPortlet.setIcon("../images/application_view_tile.png");
			addPortlet.setAutoFit(true);

			addPortlet.setStartRow(false);
			addPortlet.setEndRow(false);
			addPortlet.addClickHandler(new com.smartgwt.client.widgets.form.fields.events.ClickHandler() {
						public void onClick(
								com.smartgwt.client.widgets.form.fields.events.ClickEvent event) {

							final Task newTask = new Task();
							newTask.setTitle("Nombre de la Tarea");

							Label taskState = new Label();
							taskState.setLayoutAlign(VerticalAlignment.TOP);
							taskState.setContents("Estado: Cerrada");
							
							Label taskHour = new Label();
							taskHour.setAlign(Alignment.LEFT);
							taskHour.setLayoutAlign(VerticalAlignment.TOP);
							taskHour.setContents("Horas Consumidas: 34");
							taskHour.setBackgroundColor("666699");

							Label estimatedHours = new Label();
							estimatedHours.setAlign(Alignment.LEFT);
							estimatedHours.setLayoutAlign(VerticalAlignment.TOP);
							estimatedHours.setContents("Horas estimadas: 67");
							estimatedHours.setBackgroundColor("666699");
							
							newTask.addItem(taskState);
							newTask.addItem(taskHour);
							newTask.addItem(estimatedHours);

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
									+ addPortlet.getLeft());
							outline.setTop(form.getAbsoluteTop());
							outline.setWidth(addPortlet.getWidth());
							outline.setHeight(addPortlet.getHeight());
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
					});

			form.setItems(numColItem, addPortlet, addColumn, removeColumn);

			vLayout.addMember(form);
			vLayout.addMember(portalLayout);
			addChild(vLayout);
			redraw();
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
