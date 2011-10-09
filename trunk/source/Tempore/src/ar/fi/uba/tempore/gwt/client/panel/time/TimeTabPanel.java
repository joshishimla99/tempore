package ar.fi.uba.tempore.gwt.client.panel.time;


import ar.fi.uba.tempore.gwt.client.panel.TabsPanelContainer;
import ar.fi.uba.tempore.gwt.client.panel.project.ProjectPanel;

import com.smartgwt.client.types.AnimationAcceleration;
import com.smartgwt.client.types.Side;
import com.smartgwt.client.widgets.tab.Tab;
import com.smartgwt.client.widgets.tab.TabSet;
import com.smartgwt.client.widgets.tab.events.TabDeselectedEvent;
import com.smartgwt.client.widgets.tab.events.TabDeselectedHandler;
import com.smartgwt.client.widgets.tab.events.TabSelectedEvent;
import com.smartgwt.client.widgets.tab.events.TabSelectedHandler;



public class TimeTabPanel extends TabsPanelContainer {
	
	

	public TimeTabPanel(){
		super();		
		setSize("99%", "99%");

		final TabSet topTabSet = new TabSet();  
		topTabSet.setSize("100%", "100%");
		topTabSet.setCanDropBefore(true);
		topTabSet.setCanDragReposition(false);
		topTabSet.setCanAcceptDrop(false);
		topTabSet.setAnimateMoveAcceleration(AnimationAcceleration.SMOOTH_START);
		topTabSet.setDestroyPanes(true);
		topTabSet.setCanEditTabTitles(false);
		topTabSet.setCanCloseTabs(false);
		topTabSet.setTabBarPosition(Side.TOP);

		final Tab tTab1 = new Tab("Calendario", "../images/32x32/Calender.png");  
		tTab1.setIconWidth(16);
		tTab1.setIconHeight(16);
		tTab1.setWidth(100);

		final WeekGridTime calendario = new WeekGridTime();
		final DragDropTimePanel dragdrop = new DragDropTimePanel();

		tTab1.setPane(calendario);  
		
		final Tab tTab2 = new Tab("D&D", "../images/32x32/Refresh.png");  
		tTab2.setIconWidth(16);
		tTab2.setIconHeight(16);              
		tTab2.setPane(dragdrop);

		topTabSet.addTab(tTab2);  
		topTabSet.addTab(tTab1);  
		
		topTabSet.addTabDeselectedHandler(new TabDeselectedHandler() {
			
			@Override
			public void onTabDeselected(TabDeselectedEvent event) {
				// TODO Auto-generated method stub
				dragdrop.freeSubTab();
				calendario.freeSubTab();
			}
		});
		
		topTabSet.addTabSelectedHandler(new TabSelectedHandler() {
			
			@Override
			public void onTabSelected(TabSelectedEvent event) {
				// TODO Auto-generated method stub
				dragdrop.refreshSubTab();
				calendario.refreshSubTab();
			}
		});
		
		this.addChild(topTabSet);	
		
	}

	@Override
	public void refreshPanel() {

	}

	@Override
	public void freePanel() {
		
	}

}
