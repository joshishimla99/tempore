package ar.fi.uba.tempore.gwt.client.panel.project;

import java.util.ArrayList;
import java.util.List;

import ar.fi.uba.tempore.dto.ProjectDTO;
import ar.fi.uba.temporeutils.observer.ProjectObserved;
import ar.fi.uba.temporeutils.observer.ProjectObserver;

import com.google.gwt.user.client.Window;
import com.smartgwt.client.types.GroupStartOpen;
import com.smartgwt.client.types.ListGridFieldType;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.grid.events.RecordClickEvent;
import com.smartgwt.client.widgets.grid.events.RecordClickHandler;

/**
 * Panel con el arbol de proyectos, es singleton
 * @author Ludmila
 *
 */
public class ProjectPanel extends ListGrid implements ProjectObserved {

	private static ProjectPanel instance = null;
	private ProjectPanelDataSource dataSource = null;
	private List<ProjectObserver> listObserver = new ArrayList<ProjectObserver>();

	static public ProjectPanel getInstance() {
		if (instance == null) {
			instance = new ProjectPanel();			
		}
		return instance;
	}
	
	private ProjectPanel() {
		super();
		
		this.setCanResizeFields(true);
		
		dataSource = new ProjectPanelDataSource();		
		this.setDataSource(dataSource);
		this.setAutoFetchData(true);
		this.setHeight100();
		this.setCellHeight(24);
		this.setImageSize(16);
		this.setShowHeader(false);
		this.setLeaveScrollbarGap(false);
		this.setShowFilterEditor(false);
		this.setFilterOnKeypress(true);
		this.setGroupStartOpen(GroupStartOpen.ALL);

		//Columnas a ser visualizadas
		ListGridField image = new ListGridField("image");
		image.setType(ListGridFieldType.ICON);
		image.setIcon("../images/png/24x24/Briefcase.png");
		image.setIconHeight(16);
		image.setWidth(25);
		image.setCanFilter(false);
		
		ListGridField name = new ListGridField(ProjectPanelDataSource.NAME_FIELD);
		ListGridField state = new ListGridField(ProjectPanelDataSource.STATE_NAME_FIELD);
		this.setFields(image,name, state);
		this.hideField(ProjectPanelDataSource.STATE_NAME_FIELD);
		
		this.addRecordClickHandler(new RecordClickHandler() {
			@Override
			public void onRecordClick(RecordClickEvent event) {				
				notifyObservers();				
			}
		});
		
		this.redraw();	
	}
	
	@Override  
    protected String getCellCSSText(ListGridRecord record, int rowNum, int colNum) {  
        return "font-weight:bold;";  
    }  

	/**
	 * Obtiene el Proyecto DTO seleccionado
	 * @return Si hay seleccionado devuelve el DTO. De lo contrario devuelve null.
	 */
	public ProjectDTO getSelected(){
		ProjectDTO dto = null;
		ListGridRecord recSel = this.getSelectedRecord();
		if (recSel != null) {
			dto = new ProjectDTO();
			dataSource.copyValues(recSel, dto);
		}
		
		return dto ;
	}
		
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addObserver (ProjectObserver observer){
		if (!listObserver.contains(observer)){
			listObserver.add(observer);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void removeObserver(ProjectObserver observer){
		listObserver.remove(observer);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void notifyObservers(){
		for (ProjectObserver po : listObserver){
			if (po != null){
				po.updateProjectSelected();
			}
		}
	}
	
	/**
	 * Habilita el filtro de la grilla
	 */
	public void showFilter(){
		//TODO limpiar filtro cuando se deshabilita
		this.setShowFilterEditor(!this.getShowFilterEditor());
		this.redraw();
	}
	
	/**
	 * Agrupa por estado del proyecto
	 */
	public void viewByStateProjectGroup (){
		this.groupBy(ProjectPanelDataSource.STATE_NAME_FIELD);
	}
	
	/**
	 * Agrupa por el cliente del proyecto
	 */
	public void viewByClientProjectGroup (){
		//TODO falta implementar
		Window.alert("Falta agregar el cliente al Proyecto");
	}
	
	/**
	 * Elimina las agrupaciones
	 */
	public void viewWithoutGroup (){
		this.ungroup();
	}
}
