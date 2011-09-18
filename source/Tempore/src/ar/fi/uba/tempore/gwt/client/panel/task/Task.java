package ar.fi.uba.tempore.gwt.client.panel.task;

import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.DragAppearance;
import com.smartgwt.client.types.HeaderControls;
import com.smartgwt.client.types.LayoutPolicy;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.types.VerticalAlignment;
import com.smartgwt.client.widgets.HeaderControl;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.events.CloseClickHandler;
import com.smartgwt.client.widgets.events.CloseClientEvent;
import com.smartgwt.client.widgets.events.DoubleClickEvent;
import com.smartgwt.client.widgets.events.DoubleClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.TextAreaItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

public class Task extends Window {

	public Task() {

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
						HeaderControl.SETTINGS, new EditTaskHandler()), HeaderControls.CLOSE_BUTTON);

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
				com.google.gwt.user.client.Window.alert("Desplegar subtareas de la tarea seleccionada");
			}
			
		});
		
	}
	
	private class EditTaskHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			final Window winModal = new Window();  
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
    		TextItem taskNameLabel = new TextItem();
    		taskNameLabel.setTitle("Nombre");
    		taskNameLabel.setLength(30);
    		taskNameLabel.setRequired(true);
    
    		// description
    		TextAreaItem taskDescription = new TextAreaItem();
    		taskDescription.setTitle("Descripci&oacute;n");
    		taskDescription.setLength(150);
    		taskDescription.setRequired(true);
    		
    		// Nombre de la tarea
    		TextItem estimatedTimeLabel = new TextItem();
    		estimatedTimeLabel.setTitle("Tiempo Estimado");
    		estimatedTimeLabel.setKeyPressFilter("[0-9.]");
    		estimatedTimeLabel.setRequired(true);  
            
    		
    		IButton editTaskButton = new IButton();
    		editTaskButton.setTitle("Guardar");
    		editTaskButton.setIcon("../images/ico/save.ico");
    		editTaskButton.addClickHandler(new ClickHandler() {
    			public void onClick(ClickEvent event) {
    				//TODO: save it to the DB
    						form.validate(false);
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
    		
            form.setFields(taskNameLabel,taskDescription, estimatedTimeLabel);  
            VLayout vLayout = new VLayout();
            HLayout buttonLayout = new HLayout();
            buttonLayout.setMembersMargin(10);
            buttonLayout.setAlign(Alignment.CENTER);
            buttonLayout.addMember(editTaskButton);
            buttonLayout.addMember(cancelTaskButton);
            
            vLayout.addMember(form);
            vLayout.addMember(buttonLayout);
            
            winModal.addItem(vLayout);  
            winModal.show();  
			
		}
	}
}
