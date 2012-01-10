package ar.fi.uba.tempore.gwt.client.panel;

import ar.fi.uba.tempore.gwt.client.panel.counter.CounterTimePanel;
import ar.fi.uba.tempore.gwt.client.panel.project.ProjectPanel;

import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.DragAppearance;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.types.SelectionType;
import com.smartgwt.client.types.VisibilityMode;
import com.smartgwt.client.widgets.ImgButton;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.layout.SectionStack;
import com.smartgwt.client.widgets.layout.SectionStackSection;
import com.smartgwt.client.widgets.toolbar.ToolStrip;

public class ExplorerPanel extends SectionStack {

	private static final String RADIO_GROUP = "view";

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
		this.setVisibilityMode(VisibilityMode.MULTIPLE);  

		SectionStackSection sectionProject = new SectionStackSection("Proyectos");  
		sectionProject.setExpanded(true);  
        sectionProject.setCanCollapse(false);  

		//Botonera de Vistas
		ToolStrip toolStrip = new ToolStrip();
		toolStrip.setBorder("none");
		toolStrip.setAlign(Alignment.RIGHT);		
		toolStrip.setWidth100();
		toolStrip.setHeight(24);  

		ImgButton filterButton = new ImgButton();
		filterButton.setTooltip("Filtrar Nombre del Proyecto");
		filterButton.setSize(16);  
		filterButton.setShowRollOver(false);  
		filterButton.setSrc("../images/png/24x24/Filter.png");  
		filterButton.setActionType(SelectionType.CHECKBOX);		
		filterButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				ProjectPanel.getInstance().showFilter();
			}
		});
		toolStrip.addMember(filterButton);  

		ImgButton freeGroupButton = new ImgButton();
		freeGroupButton.setTooltip("Desagrupar");
		freeGroupButton.setSize(16);  
		freeGroupButton.setShowRollOver(false);  
		freeGroupButton.setSrc("../images/png/24x24/Yellow bookmark.png");  
		freeGroupButton.setActionType(SelectionType.RADIO);  
		freeGroupButton.setRadioGroup(RADIO_GROUP);		
		freeGroupButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				ProjectPanel.getInstance().viewWithoutGroup();
			}
		});
		toolStrip.addMember(freeGroupButton);  

		ImgButton clientGroupButton = new ImgButton();
		clientGroupButton.setTooltip("Agrupar por Owner");
		clientGroupButton.setSize(16);  
		clientGroupButton.setShowRollOver(false);  
		clientGroupButton.setSrc("../images/png/24x24/Red bookmark.png");  
		clientGroupButton.setActionType(SelectionType.RADIO);  
		clientGroupButton.setRadioGroup(RADIO_GROUP); 
		clientGroupButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				ProjectPanel.getInstance().viewByClientProjectGroup();
			}
		});
		toolStrip.addMember(clientGroupButton);  

		ImgButton stateGroupButton = new ImgButton();
		stateGroupButton.setTooltip("Agrupar por Estado");
		stateGroupButton.setSize(16);  
		stateGroupButton.setShowRollOver(false);  
		stateGroupButton.setSrc("../images/png/24x24/Blue bookmark.png");  
		stateGroupButton.setActionType(SelectionType.RADIO);  
		stateGroupButton.setRadioGroup(RADIO_GROUP);
		stateGroupButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				ProjectPanel.getInstance().viewByStateProjectGroup();
			}
		});
		toolStrip.addMember(stateGroupButton);  


		//Agrego los paneles al explorador
		sectionProject.addItem(toolStrip);
		sectionProject.addItem(ProjectPanel.getInstance());
		
		
		//SECTION COUNTER
		SectionStackSection sectionCounter = new SectionStackSection("Contador Online");  
        sectionCounter.setExpanded(true);
        sectionCounter.setCanCollapse(true);
        sectionCounter.setResizeable(false);
        
        sectionCounter.addItem(new CounterTimePanel());
        
        this.addSection(sectionProject);
        this.addSection(sectionCounter); 
	}		
}
