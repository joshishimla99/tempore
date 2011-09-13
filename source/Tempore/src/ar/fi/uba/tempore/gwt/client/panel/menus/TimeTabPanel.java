package ar.fi.uba.tempore.gwt.client.panel.menus;

import ar.fi.uba.tempore.gwt.client.panel.time.WeekGridTime;

import com.smartgwt.client.types.Side;
import com.smartgwt.client.widgets.tab.TabSet;
 
import com.smartgwt.client.widgets.tab.Tab;


public class TimeTabPanel extends SubTabPanel {
	
	public TimeTabPanel(){
		super();		
		
		final TabSet topTabSet = new TabSet();  
		topTabSet.setSize("96%", "95%");
		topTabSet.setDestroyPanes(true);
		topTabSet.setCanEditTabTitles(false);
		topTabSet.setCanCloseTabs(false);
        topTabSet.setTabBarPosition(Side.TOP);
  
        Tab tTab1 = new Tab("Calendario", "../images/32x32/Calender.png");  
        tTab1.setIconWidth(16);
        tTab1.setIconHeight(16);
        tTab1.setWidth(100);
        
        WeekGridTime calendario = new WeekGridTime();

        
        tTab1.setPane(calendario);  
  
        Tab tTab2 = new Tab("D&D", "../images/32x32/Refresh.png");  
        tTab2.setIconWidth(16);
        tTab2.setIconHeight(16);
        
        Tab tTab3 = new Tab("Validacion", "pieces/16/pawn_green.png");
  
        topTabSet.addTab(tTab1);  
        topTabSet.addTab(tTab2);
        topTabSet.addTab(tTab3);
        
        this.addChild(topTabSet);		
		
	}

}
