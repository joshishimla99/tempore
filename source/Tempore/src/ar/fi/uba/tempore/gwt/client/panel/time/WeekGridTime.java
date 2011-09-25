package ar.fi.uba.tempore.gwt.client.panel.time;

import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.events.DoubleClickHandler;
import com.smartgwt.client.widgets.events.DoubleClickEvent;

public class WeekGridTime extends VLayout{
	
	public TimeCalendar calendar;
	
	/**
	 * 
	 */
	public WeekGridTime()
	{
		calendar = new TimeCalendar();                 
		calendar.addDoubleClickHandler(new DoubleClickHandler() {
			public void onDoubleClick(DoubleClickEvent event) {
				new AddTaskWindow(calendar).show();				
			}
		});

         this.addChild(calendar);  
        calendar.redraw();  		
	}	

	public TimeCalendar getCalendar() {
		return calendar;
	}


}
