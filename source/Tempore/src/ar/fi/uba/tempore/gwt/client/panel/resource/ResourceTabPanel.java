package ar.fi.uba.tempore.gwt.client.panel.resource;

import java.util.List;

import ar.fi.uba.tempore.dto.ProjectDTO;
import ar.fi.uba.tempore.dto.UserDTO;
import ar.fi.uba.tempore.dto.UserProjectDTO;
import ar.fi.uba.tempore.gwt.client.UserProjectServicesClient;
import ar.fi.uba.tempore.gwt.client.UserServicesClient;
import ar.fi.uba.tempore.gwt.client.panel.TabsPanelContainer;
import ar.fi.uba.tempore.gwt.client.panel.project.ProjectPanel;
import ar.fi.uba.temporeutils.image.ImgClient;
import ar.fi.uba.temporeutils.observer.ProjectObserver;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.DragAppearance;
import com.smartgwt.client.types.SelectionType;
import com.smartgwt.client.types.Visibility;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Button;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.events.DragStartEvent;
import com.smartgwt.client.widgets.events.DragStartHandler;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.tile.TileGrid;
import com.smartgwt.client.widgets.tile.events.SelectionChangedEvent;
import com.smartgwt.client.widgets.tile.events.SelectionChangedHandler;
import com.smartgwt.client.widgets.viewer.DetailFormatter;
import com.smartgwt.client.widgets.viewer.DetailViewerField;

public class ResourceTabPanel extends TabsPanelContainer implements ProjectObserver{
	public static final String USER_ID = "userIdCol";
	public static final String USER_PROJECT_ID = "userProjectIdCol";

	public static final String NAME = "nameCol";
	public static final String LAST_NAME = "lastNameCol";
	public static final String USER_NAME = "userNameCol";
	public static final String EMAIL = "emailCol";
	public static final String IMAGE_NAME = "imageName";
	public static final String IS_OWNER = "ownerCol";

	private final TileGrid userTileGrid = new TileGrid();
	private final TileGrid assignedTileGrid = new TileGrid();
	private final Button changeOwnerBtn = new Button("Cambiar Lider del Proyecto");
	private boolean changeOwner = false;


	@Override
	public void refreshPanel() {
		ProjectPanel.getInstance().addObserver(this);
		updateProjectSelected();
	}

	@Override
	public void freePanel() {
		ProjectPanel.getInstance().removeObserver(this);
	}	

	public ResourceTabPanel(){
		super();
	
		userTileGrid.setTileWidth(120);  
		userTileGrid.setTileHeight(160);  
		userTileGrid.setHeight("50%");  
		userTileGrid.setShowAllRecords(true);  
		userTileGrid.setAnimateTileChange(true);
		userTileGrid.setCanDrag(true);
		userTileGrid.setCanAcceptDrop(true);
		
		final DetailViewerField pictureField = new DetailViewerField(IMAGE_NAME);
		pictureField.setType("image");
		pictureField.setImageWidth(100);
		pictureField.setImageHeight(100);
		pictureField.setImageURLPrefix(ImgClient.URL_PREFIX);
		final DetailViewerField nameField = new DetailViewerField(NAME);
		nameField.setCellStyle("resourceUserName");
		final DetailViewerField userField = new DetailViewerField(USER_NAME);  
		final DetailViewerField emailField = new DetailViewerField(EMAIL);
		userTileGrid.setFields(pictureField, nameField, userField, emailField);
		userTileGrid.setDragAppearance(DragAppearance.NONE);
		userTileGrid.setData(new Record[]{});

		//BOTON HABILITADOR PARA CAMBIAR OWNER
		  
		changeOwnerBtn.setShowRollOver(false);
		changeOwnerBtn.setWidth100();
		changeOwnerBtn.setActionType(SelectionType.CHECKBOX);
		changeOwnerBtn.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				changeOwner = !changeOwner;
				if (changeOwner){
					userTileGrid.setVisibility(Visibility.HIDDEN);					
				} else {
					userTileGrid.setVisibility(Visibility.VISIBLE);
				}
			}
		});

		assignedTileGrid.setWidth100();  
		assignedTileGrid.setHeight("50%");  
		assignedTileGrid.setTileWidth(120);  
		assignedTileGrid.setTileHeight(180);  
//		assignedTileGrid.setShowAllRecords(true);
		assignedTileGrid.setAutoFetchData(true);
		assignedTileGrid.setCanAcceptDrop(true);
		assignedTileGrid.setCanDrag(true);
		assignedTileGrid.setCanReorderTiles(false);

		assignedTileGrid.addSelectionChangedHandler(changeProjectOwner);
		assignedTileGrid.setDataSource(ResourceDataSource.getInstance());
		assignedTileGrid.addDragStartHandler(new DragStartHandler() {
			@Override
			public void onDragStart(DragStartEvent event) {
				GWT.log("Start Drag...");
				//TODO evitar que el dueño sea movido
//				TileRecord selectedRecord = assignedTileGrid.getSelectedRecord();
				
//				final TileGrid theDragTargetGrid = (TileGrid)EventHandler.getDragTarget();
//	            TileRecord theSelectedRecord = theDragTargetGrid.getSelectedRecord();
//	            int theTileDropPosition = theDragTargetGrid.getRecordIndex(theSelectedRecord);
//	            GWT.log("Indice seleccionado = " + theTileDropPosition);
			}
		});
		
		
		//USUARIOS ASIGANDOS
		final DetailViewerField imageAsigned = new DetailViewerField(IMAGE_NAME);
		imageAsigned.setType("image");
		imageAsigned.setImageWidth(100);
		imageAsigned.setImageHeight(100);
		imageAsigned.setImageURLPrefix(ImgClient.URL_PREFIX);
		final DetailViewerField nameAsigned = new DetailViewerField(NAME);
		nameAsigned.setCellStyle("resourceUserName");
		final DetailViewerField userAsigned = new DetailViewerField(USER_NAME);  
		final DetailViewerField emailAsigned = new DetailViewerField(EMAIL);
		final DetailViewerField ownerAsigned = new DetailViewerField(IS_OWNER);
		ownerAsigned.setCellStyle("resourceOwner");
		ownerAsigned.setDetailFormatter(new DetailFormatter() {  
			public String format(Object value, Record record, DetailViewerField field) {
				if (value!=null && value.toString().equals("1")){
					return "Creador";  
				} 
				return "";
			}  
		});
		assignedTileGrid.setFields(imageAsigned,nameAsigned, userAsigned, emailAsigned, ownerAsigned);
		assignedTileGrid.setDragAppearance(DragAppearance.NONE);
		
		
		VLayout vLayout = new VLayout();
		vLayout.setWidth100();
		vLayout.setHeight100();

		vLayout.addMember(userTileGrid);
		vLayout.addMember(changeOwnerBtn);
		vLayout.addMember(assignedTileGrid);

		this.addChild(vLayout);
	}

	/**
	 * Metodo que se invoca cuando en el panel de proyecto se cambia.
	 */
	@Override
	public void updateProjectSelected() {
		ProjectDTO selected = ProjectPanel.getInstance().getSelected();
		if (selected != null) {
			updateAsignedTileGrid();
			
			boolean isProjectOwner = selected.getIsOwner()==1;
			//Permisos dentro de la pantalla
			changeOwnerBtn.setVisible(isProjectOwner);
			if (isProjectOwner){				
				updateUserTileGrid(selected.getId());
				userTileGrid.show();
				assignedTileGrid.setBackgroundColor("rgb(255,255,255)");
			} else {
				userTileGrid.hide();
				assignedTileGrid.setBackgroundColor("rgb(220,220,220)");
			}
		}		
	}

	/**
	 * Actualiza las imagenes en el panel de recursos asignados
	 */
	private void updateAsignedTileGrid() {
		assignedTileGrid.invalidateCache();
		assignedTileGrid.fetchData();		
	}

	/**
	 * Actualiza las imagenes del panel de los recusos SIN asignar.
	 * @param projectId Id del proyecto que debe mostrar
	 */
	private void updateUserTileGrid(Integer projectId) {
		//Actualizo los usuarios
		userTileGrid.setData(new Record[]{});
		UserServicesClient.Util.getInstance().getUserNotAssignedToProject(projectId, new AsyncCallback<List<UserDTO>>() {
			@Override
			public void onSuccess(List<UserDTO> result) {
				for (UserDTO dto : result) {
					Record t = new Record();
					t.setAttribute(IMAGE_NAME, dto.getImageName());
					t.setAttribute(NAME, dto.getName());
					t.setAttribute(USER_NAME, dto.getUserName());
					t.setAttribute(EMAIL, dto.getEmail());
					t.setAttribute(USER_ID, dto.getId());
					
					userTileGrid.addData(t);
				}
				userTileGrid.redraw();
			}
			@Override
			public void onFailure(Throwable caught) {
				SC.say("Error al cargar los usaurio para asignar");
			}
		});			
		
	}

	/**
	 * Metodo para cambiar el usuario creador del proyecto
	 */
	private SelectionChangedHandler changeProjectOwner = new SelectionChangedHandler() {
		@Override
		public void onSelectionChanged(SelectionChangedEvent event) {
			if (changeOwner){
				final UserProjectDTO data = new UserProjectDTO();
				ResourceDataSource.getInstance().copyValues((ListGridRecord)event.getRecord(), data);
				UserProjectServicesClient.Util.getInstance().changeOwner(data, new AsyncCallback<Integer>() {
					@Override
					public void onSuccess(Integer projectId) {
						assignedTileGrid.fetchData();
						ProjectPanel.getInstance().forceToFetchData(projectId);
						
						//vuelvo a la normalidad
						changeOwner = false;
						userTileGrid.setVisibility(Visibility.VISIBLE);
					}
					@Override
					public void onFailure(Throwable caught) {
						SC.say("No se pudo actualizar el Owner del proyecto");
					}
				});
			}
		}
	};
}

