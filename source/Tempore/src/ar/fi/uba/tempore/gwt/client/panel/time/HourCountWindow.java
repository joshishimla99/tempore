package ar.fi.uba.tempore.gwt.client.panel.time;

import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.AnimationAcceleration;
import com.smartgwt.client.types.TitleOrientation;
import com.smartgwt.client.types.VerticalAlignment;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.ButtonItem;
import com.smartgwt.client.widgets.form.fields.RowSpacerItem;
import com.smartgwt.client.widgets.form.fields.SliderItem;
import com.smartgwt.client.widgets.form.fields.events.ClickEvent;
import com.smartgwt.client.widgets.form.fields.events.ClickHandler;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridRecord;

public class HourCountWindow extends Window{
	
	final DynamicForm dynamicForm;
	Integer horasTarea;
	ListGrid grilla;
	ListGridRecord registro;
	

	public HourCountWindow(){
		setIsModal(true);
		setShowModalMask(true);
		setTitle("Agregar Tarea");
		
		Label lblNewLabel = new Label("Indique la cantidad de horas");
		lblNewLabel.setSize("253px", "24px");
		lblNewLabel.setValign(VerticalAlignment.BOTTOM);
		addItem(lblNewLabel);
		
		dynamicForm = new DynamicForm();
		dynamicForm.setSize("146px", "61px");
		dynamicForm.setAlign(Alignment.CENTER);
		dynamicForm.setTitleOrientation(TitleOrientation.LEFT);
		dynamicForm.setAnimateAcceleration(AnimationAcceleration.SMOOTH_START);
		setSize("263px", "205px");
		setCanDropComponents(false);
		setAutoSize(true);
		setAutoCenter(true);
		setAnimateMembers(true);
		ButtonItem buttonItem = new ButtonItem("Guardar", "Guardar");
		buttonItem.setAlign(Alignment.RIGHT);
		buttonItem.setWidth(130);
//		com.google.gwt.user.client.Window.alert("Dia y hora ->: " + calendar.getActiveTime().toLocaleString() );
		

		SliderItem slider = new SliderItem("horas", "Horas");
		slider.setWidth(200);
		slider.setWidth("");
		slider.setShowTitle(false);
		slider.setRoundValues(false);
		slider.setMaxValue(24.0f);
		slider.setDefaultValue(4);
		slider.setLeft(50);
		
		buttonItem.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {		
				horasTarea = Integer.parseInt(dynamicForm.getValueAsString("horas"));
				registro.setAttribute(DragDropTimePanel.COL_HOURS, horasTarea);
				grilla.addData(registro);
				hide();
			}
		});

		dynamicForm.setFields(new RowSpacerItem(), slider, buttonItem);
		addItem(dynamicForm);
		dynamicForm.moveTo(6, 1);
	}

	public void showModal(ListGrid hoursCountGrid, ListGridRecord record) {
		show();
		grilla = hoursCountGrid;
		registro = record;		
	}

}
