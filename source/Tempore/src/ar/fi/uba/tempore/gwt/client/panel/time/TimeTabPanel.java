package ar.fi.uba.tempore.gwt.client.panel.time;


import ar.fi.uba.tempore.gwt.client.panel.TabsPanelContainer;
import ar.fi.uba.tempore.gwt.client.panel.configuration.HourCountDataSource;
import ar.fi.uba.tempore.gwt.client.panel.configuration.TaskTimeDataSource;

import com.google.gwt.user.client.Window;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.AnimationAcceleration;
import com.smartgwt.client.types.DragDataAction;
import com.smartgwt.client.types.ListGridEditEvent;
import com.smartgwt.client.types.RowEndEditAction;
import com.smartgwt.client.types.Side;
import com.smartgwt.client.widgets.DateChooser;
import com.smartgwt.client.widgets.EdgedCanvas;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.events.DropEvent;
import com.smartgwt.client.widgets.events.DropHandler;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.layout.HStack;
import com.smartgwt.client.widgets.layout.VStack;
import com.smartgwt.client.widgets.tab.Tab;
import com.smartgwt.client.widgets.tab.TabSet;
import com.smartgwt.client.widgets.tree.TreeGrid;



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
