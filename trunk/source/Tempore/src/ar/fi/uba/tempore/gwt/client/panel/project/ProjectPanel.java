package ar.fi.uba.tempore.gwt.client.panel.project;

import java.util.ArrayList;
import java.util.List;

import ar.fi.uba.tempore.dto.ProjectDTO;
import ar.fi.uba.temporeutils.observer.ProjectObserved;
import ar.fi.uba.temporeutils.observer.ProjectObserver;

import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.GroupStartOpen;
import com.smartgwt.client.types.ListGridFieldType;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.grid.events.DataArrivedEvent;
import com.smartgwt.client.widgets.grid.events.DataArrivedHandler;
import com.smartgwt.client.widgets.grid.events.SelectionChangedHandler;
import com.smartgwt.client.widgets.grid.events.SelectionEvent;

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
		ListGridField image = new ListGridField(ProjectPanelDataSource.STATE_ID_FIELD);
		image.setAlign(Alignment.CENTER);
		image.setType(ListGridFieldType.IMAGE);
		image.setImageURLPrefix("../images/png/24x24/Briefcase");  
        image.setImageURLSuffix(".png");
		image.setWidth(25);
		image.setCanFilter(false);
		
		ListGridField imageOwner = new ListGridField(ProjectPanelDataSource.IS_OWNER_FIELD);
//		imageOwner.setCellFormatter(new CellFormatter() {
//			@Override
//			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
//				if (value.toString().equals("1")){
//					return "Creador";
//				}
//				return "Asignado";
//			}
//		});
		imageOwner.setAlign(Alignment.CENTER);
		imageOwner.setType(ListGridFieldType.IMAGE);
		imageOwner.setImageURLPrefix("../images/png/explorer/Favourites");  
		imageOwner.setImageURLSuffix(".png");
		imageOwner.setWidth(25);
		imageOwner.setCanFilter(false);
		
		ListGridField name = new ListGridField(ProjectPanelDataSource.NAME_FIELD);
		ListGridField state = new ListGridField(ProjectPanelDataSource.STATE_NAME_FIELD);
		ListGridField client = new ListGridField(ProjectPanelDataSource.CLIENT_NAME_FIELD);
		this.setFields(image,imageOwner,name, state, client);
		this.hideField(ProjectPanelDataSource.STATE_NAME_FIELD);
		this.hideField(ProjectPanelDataSource.CLIENT_NAME_FIELD);
		
		this.addSelectionChangedHandler(new SelectionChangedHandler() {
			@Override
			public void onSelectionChanged(SelectionEvent event) {
				notifyObservers();
			}
		});
		
		//dejo seleccionado el primer elemento
		this.addDataArrivedHandler(new DataArrivedHandler() {
			@Override
			public void onDataArrived(DataArrivedEvent event) {
				selectSingleRecord(0);
			}
		});
		
		//Notificamos la carga final de los proyectos
		notifyObservers();
		
		this.redraw();	
	}
	
	@Override  
    protected String getCellCSSText(ListGridRecord record, int rowNum, int colNum) {  
        return "font-weight:bold;";  
    }  

	
	/**
	 * Fuerza a que se refresque la pantalla
	 */
	public void forceToFetchData(Integer projectId){
		this.invalidateCache();
		this.fetchData();
		this.redraw();
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
		this.setShowFilterEditor(!this.getShowFilterEditor());
		this.redraw();
	}
	
	/**
	 * Agrupa por estado del proyecto
	 */
	public void viewByStateProjectGroup (){
		this.groupBy(ProjectPanelDataSource.STATE_NAME_FIELD);
		this.setGroupStartOpen("all");
	}
	
	/**
	 * Agrupa por el cliente del proyecto
	 */
	public void viewByClientProjectGroup (){
		this.groupBy(ProjectPanelDataSource.IS_OWNER_FIELD);
		this.setGroupStartOpen("all");
	}
	
	/**
	 * Elimina las agrupaciones
	 */
	public void viewWithoutGroup (){
		this.ungroup();
	}	
}
