package ar.fi.uba.tempore.gwt.client.panel.time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.form.DynamicForm;

import com.smartgwt.client.widgets.form.fields.ComboBoxItem;

import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.widgets.form.fields.SpinnerItem;

import com.smartgwt.client.widgets.form.fields.ButtonItem;
import com.smartgwt.client.widgets.form.fields.TextAreaItem;
import com.smartgwt.client.widgets.form.fields.events.ClickHandler;
import com.smartgwt.client.widgets.form.fields.events.ClickEvent;

import com.smartgwt.client.widgets.form.fields.RowSpacerItem;
import com.smartgwt.client.types.AnimationAcceleration;
import com.smartgwt.client.types.TitleOrientation;
import com.smartgwt.client.types.VerticalAlignment;


public class AddTaskWindow extends Window{
	
	public AddTaskWindow(){}
	
	/**
	 * @wbp.parser.constructor
	 */
	@SuppressWarnings("deprecation")
	public AddTaskWindow(final TimeCalendar calendar){
		final TextAreaItem descripcion;
		setIsModal(true);
		setShowModalMask(true);
		setTitle("Agregar Tarea");
		
		Label lblNewLabel = new Label("Seleccione la tarea e indique la cantidad de horas");
		lblNewLabel.setSize("253px", "24px");
		lblNewLabel.setValign(VerticalAlignment.BOTTOM);
		addItem(lblNewLabel);
		
		final DynamicForm dynamicForm = new DynamicForm();
		dynamicForm.setSize("249px", "55px");
		dynamicForm.setAlign(Alignment.CENTER);
		dynamicForm.setTitleOrientation(TitleOrientation.LEFT);
		dynamicForm.setAnimateAcceleration(AnimationAcceleration.SMOOTH_START);
		setSize("297px", "260px");
		setCanDropComponents(false);
		setAutoSize(true);
		setAutoCenter(true);
		setAnimateMembers(true);
		ButtonItem buttonItem = new ButtonItem("Guardar", "Guardar");
		buttonItem.setAlign(Alignment.RIGHT);
		buttonItem.setWidth(130);
//		com.google.gwt.user.client.Window.alert("Dia y hora ->: " + calendar.getActiveTime().toLocaleString() );
		buttonItem.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {		
//				java.util.Date d = null;
//				java.util.Date fechainicio = new java.util.Date("");
//				final Date fin = new Date(calendar.getActiveTime().toString());
//				int horaInicio = fechainicio.getHours();
//				int horaFin = horaInicio + Integer.parseInt(dynamicForm.getValueAsString("horas"));
//				fin.setHours(horaFin);	
//				String fechaString1=calendar.getActiveTime().toLocaleString(); 

				/*INSTACIAMOS UN OBJETO DE TIPO DATE A NULL*/
				Date fechaDate = new Date();
				fechaDate.setHours(18);
//				com.google.gwt.user.client.Window.alert("Dia y hora ->: " + fechaDate.toString() );
				calendar.addEvent(calendar.getActiveTime(), fechaDate, dynamicForm.getValueAsString("tarea"), dynamicForm.getValueAsString("descripcion"));
				hide();
			}
		});
		descripcion = new TextAreaItem("descripcion", "Descripcion");
		descripcion.setAlign(Alignment.LEFT);
		SpinnerItem spinner = new SpinnerItem("horas", "Horas");
		spinner.setValue(0);
		ComboBoxItem comboBoxItem = new ComboBoxItem("tarea", "Tarea");
		comboBoxItem.setTooltip("Elija la tarea a la que asignara horas");
		comboBoxItem.setShowDisabled(false);
		comboBoxItem.setRequired(true);
		comboBoxItem.setDefaultToFirstOption(true);
		comboBoxItem.setAlign(Alignment.LEFT);
		comboBoxItem.setAnimatePickList(true);
		comboBoxItem.setValueMap(new String[] {"Tarea 1", "Tarea 2", "...", "Tarea N"});
		comboBoxItem.setShowAllOptions(true);
		comboBoxItem.setValue("");
		dynamicForm.setFields(new RowSpacerItem(), comboBoxItem, spinner, descripcion, buttonItem);
		addItem(dynamicForm);
		dynamicForm.moveTo(6, 1);
//		com.google.gwt.user.client.Window.alert("Dia y hora ->: " + calendar.getActiveTime().toString());
//		calendar.addEvent(calendar.getActiveTime(), calendar.getActiveTime() , "Test", "Descripcion");
		
	}

}
