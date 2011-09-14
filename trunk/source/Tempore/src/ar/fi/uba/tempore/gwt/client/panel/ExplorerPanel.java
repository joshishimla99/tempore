package ar.fi.uba.tempore.gwt.client.panel;

import ar.fi.uba.tempore.gwt.client.panel.project.ProjectPanel;

import com.smartgwt.client.types.DragAppearance;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.widgets.layout.VLayout;

public class ExplorerPanel extends VLayout {

	private static final String TEMPORE_EXPLORER = "Tempore_Explorer";

	public ExplorerPanel (){
		super();
		this.setStyleName(TEMPORE_EXPLORER); 
		this.setEdgeImage("../edges/blue/sharpframe_10.png");
		this.setDragAppearance(DragAppearance.TARGET);  
		this.setOverflow(Overflow.HIDDEN);
		this.setShowResizeBar(true);
		this.setCanDragResize(true);  
		this.setResizeFrom("R");  
		this.setMinWidth(100);  
		this.setMinHeight(50);
		 
		
		
		//TODO agregar botones para cambiar la vista del ProjectPanel
		this.addMember(ProjectPanel.getInstance());
	}
}
