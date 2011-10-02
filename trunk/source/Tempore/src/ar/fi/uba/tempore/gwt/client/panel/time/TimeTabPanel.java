package ar.fi.uba.tempore.gwt.client.panel.time;


import ar.fi.uba.tempore.gwt.client.panel.TabsPanelContainer;

import com.smartgwt.client.types.AnimationAcceleration;
import com.smartgwt.client.types.Side;
import com.smartgwt.client.widgets.tab.Tab;
import com.smartgwt.client.widgets.tab.TabSet;



public class TimeTabPanel extends TabsPanelContainer {

	public TimeTabPanel(){
		super();		
		setSize("95%", "100%");

		final TabSet topTabSet = new TabSet();  
		topTabSet.setSize("96%", "478px");
		topTabSet.setCanDropBefore(true);
		topTabSet.setCanDragReposition(false);
		topTabSet.setCanAcceptDrop(false);
		topTabSet.setAnimateMoveAcceleration(AnimationAcceleration.SMOOTH_START);
		topTabSet.setDestroyPanes(true);
		topTabSet.setCanEditTabTitles(false);
		topTabSet.setCanCloseTabs(false);
		topTabSet.setTabBarPosition(Side.TOP);

		Tab tTab1 = new Tab("Calendario", "../images/32x32/Calender.png");  
		tTab1.setIconWidth(16);
		tTab1.setIconHeight(16);
		tTab1.setWidth(100);

		WeekGridTime calendario = new WeekGridTime();
		DragDropTimePanel dragdrop = new DragDropTimePanel();

		tTab1.setPane(calendario);  
		
		Tab tTab2 = new Tab("D&D", "../images/32x32/Refresh.png");  
		tTab2.setIconWidth(16);
		tTab2.setIconHeight(16);              
		tTab2.setPane(dragdrop);

		topTabSet.addTab(tTab1);  
		topTabSet.addTab(tTab2);  
		
		this.addChild(topTabSet);				
	}

}
