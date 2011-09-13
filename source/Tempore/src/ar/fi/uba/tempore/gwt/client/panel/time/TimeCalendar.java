package ar.fi.uba.tempore.gwt.client.panel.time;

import com.smartgwt.client.types.AnimationAcceleration;
import com.smartgwt.client.types.AnimationEffect;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.calendar.Calendar;
import com.smartgwt.client.widgets.calendar.CalendarEvent;
import com.smartgwt.client.widgets.calendar.events.CalendarEventClick;
import com.smartgwt.client.widgets.calendar.events.EventClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.ButtonItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.form.fields.events.ClickEvent;
import com.smartgwt.client.widgets.form.fields.events.ClickHandler;

public class TimeCalendar extends Calendar{
	
   public TimeCalendar(){
		super();
		initialSettings();

}
   
   
   private void initialSettings(){
	   setCanCreateEvents(false);
       setCanEditEvents(true);
       setCanDeleteEvents(true);
       setShowShadow(false);
//       setShowDragShadow(true);
//       setWeekEventBorderOverlap(true);
       setDisableWeekends(false);
       setFirstDayOfWeek(1);
       setShowMonthView(false);

		setWeekViewTitle("Semana");
		setDayViewTitle("Dia");		
		
        setSize("90%", "90%");
        setEdgeMarginSize(1);
        setAnimateShowEffect(AnimationEffect.FADE);
        setAnimateAcceleration(AnimationAcceleration.SMOOTH_START_END);		
		setWorkdayStart("9:00am");
		setWorkdayEnd("6:00pm");	
        setShowWeekends(true);  
        setShowWorkday(true);  
        setScrollToWorkday(true);  
        setAutoFetchData(true);  
        
	    // Show a custom editor for existing events
        addEventClickHandler(new EventClickHandler() {

            @Override
            public void onEventClick(CalendarEventClick event) {
                editEvent(event.getEvent());
                event.cancel();
                
            }
        });
       
   }
   
   private DynamicForm editForm;
   private Window ven;
   private CalendarEvent currentEvent;
   
   public void editEvent(CalendarEvent calendarEvent) {
       currentEvent = calendarEvent;
       if (editForm == null) {
    	   ven = new Window();
           editForm = new DynamicForm();
           
           
           TextItem titleItem = new TextItem("name", "Event Name");
           TextItem descriptionItem = new TextItem("desc", "Description");
           ButtonItem saveButton = new ButtonItem("Save");
           saveButton.addClickHandler(new ClickHandler() {
               
               @Override
               public void onClick(ClickEvent event) {
                   
//                   this.updateEvent(currentEvent, 
//                           currentEvent.getStartDate(), 
//                           currentEvent.getEndDate(), 
//                           event.getForm().getValueAsString("name"),
//                           event.getForm().getValueAsString("desc"));
                   
                   editForm.hide();
                   
               }
           });
           editForm.setItems(titleItem, descriptionItem, saveButton);
       }
       editForm.setValue("name", currentEvent.getName());
       editForm.setValue("desc", currentEvent.getDescription());
       ven.addItem(editForm);
       ven.setWidth(260);
       ven.show();
       
   }
  

}