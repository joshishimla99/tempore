package ar.fi.uba.tempore.gwt.client.panel.time;

import ar.fi.uba.tempore.gwt.client.panel.project.ProjectPanel;
import ar.fi.uba.tempore.gwt.client.panel.project.ProjectTabPanel;
import ar.fi.uba.temporeutils.observer.ProjectObserver;

import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.events.DoubleClickHandler;
import com.smartgwt.client.widgets.events.DoubleClickEvent;

public class WeekGridTime extends VLayout implements ProjectObserver{
	
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

	@Override
	public void updateProjectSelected() {
		// TODO Auto-generated method stub
		
	}
	
	public void refreshSubTab(){
		ProjectPanel.getInstance().addObserver(this);
		updateProjectSelected();
	}

	public void freeSubTab() {
		// TODO Auto-generated method stub
		ProjectPanel.getInstance().removeObserver(this);
	}


}
