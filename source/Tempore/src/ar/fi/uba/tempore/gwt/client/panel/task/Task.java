package ar.fi.uba.tempore.gwt.client.panel.task;

import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.DragAppearance;
import com.smartgwt.client.types.HeaderControls;
import com.smartgwt.client.types.LayoutPolicy;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.types.VerticalAlignment;
import com.smartgwt.client.widgets.HeaderControl;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.events.DoubleClickEvent;
import com.smartgwt.client.widgets.events.DoubleClickHandler;

public class Task extends Window {

	private Label estimation;
	private String name;
	private String description;
	private Label status;
	
	public Task(String taskName, String taskEstimation, String taskDescription, String taskStatus) {

		this.name = taskName;
		this.description = taskDescription;
		
		setShowShadow(false);
//		setMinimized(true);
		// enable predefined component animation
		setAnimateMinimize(true);
		
		
		// Window is draggable with "outline" appearance by default.
		// "target" is the solid appearance.
		setDragAppearance(DragAppearance.OUTLINE);
		setCanDrop(true);

		// customize the appearance and order of the controls in the window
		// header
		setHeaderControls(HeaderControls.MINIMIZE_BUTTON,
				HeaderControls.HEADER_LABEL, new HeaderControl(
						HeaderControl.SETTINGS, new EditTaskHandler(this)), HeaderControls.CLOSE_BUTTON);

		// show either a shadow, or translucency, when dragging a portlet
		// (could do both at the same time, but these are not visually
		// compatible effects)
		// setShowDragShadow(true);
		setDragOpacity(30);

		// these settings enable the portlet to autosize its height only to fit
		// its contents
		// (since width is determined from the containing layout, not the
		// portlet contents)
		setVPolicy(LayoutPolicy.NONE);
		setOverflow(Overflow.VISIBLE);
		addDoubleClickHandler(new DoubleClickHandler(){

			@Override
			public void onDoubleClick(DoubleClickEvent event) {
			}
			
		});
		
		this.setTitle(this.name);

		status = new Label();
		status.setLayoutAlign(VerticalAlignment.TOP);
		status.setContents("Estado: " + taskStatus);
		
		Label taskHour = new Label();
		taskHour.setAlign(Alignment.LEFT);
		taskHour.setLayoutAlign(VerticalAlignment.TOP);
		taskHour.setContents("Horas Consumidas: 34");
		taskHour.setBackgroundColor("666699");

		estimation = new Label();
		estimation.setAlign(Alignment.LEFT);
		estimation.setLayoutAlign(VerticalAlignment.TOP);
		estimation.setContents("Horas estimadas: " + taskEstimation);
		estimation.setBackgroundColor("666699");
		
		this.addItem(status);
		this.addItem(taskHour);
		this.addItem(estimation);

		
	}
	
	
	private class EditTaskHandler implements ClickHandler {

		private Task task;
		
		public EditTaskHandler(Task task) {
			this.task = task;
		}

		@Override
		public void onClick(ClickEvent event) {
			EditTaskModalWindow editTaskModalWin = new EditTaskModalWindow(this.task.name, this.task.description, this.task.estimation.getContents());
			editTaskModalWin.show();
		}
	}
}
