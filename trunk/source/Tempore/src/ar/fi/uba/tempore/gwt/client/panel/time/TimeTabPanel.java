package ar.fi.uba.tempore.gwt.client.panel.time;


import ar.fi.uba.tempore.gwt.client.panel.TabsPanelContainer;

import com.smartgwt.client.types.AnimationAcceleration;
import com.smartgwt.client.types.Side;
import com.smartgwt.client.widgets.layout.VLayout;
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


		final DragDropTimePanel dragdrop = new DragDropTimePanel();
		
		final VLayout vLayout = new VLayout();
		vLayout.setWidth100();
		vLayout.setHeight100();
		vLayout.setMembersMargin(6);
		
		
		vLayout.addMember(dragdrop);
		
		
		topTabSet.addChild(vLayout); 	
		
		topTabSet.addTabDeselectedHandler(new TabDeselectedHandler() {
			
			@Override
			public void onTabDeselected(TabDeselectedEvent event) {
				// TODO Auto-generated method stub
//				dragdrop.freeSubTab();
			}
		});
		
		topTabSet.addTabSelectedHandler(new TabSelectedHandler() {
			
			@Override
			public void onTabSelected(TabSelectedEvent event) {
				// TODO Auto-generated method stub
//				dragdrop.refreshSubTab();
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
