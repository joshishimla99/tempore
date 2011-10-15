package ar.fi.uba.tempore.gwt.client.panel.resource;

import java.util.List;

import ar.fi.uba.tempore.dto.ProjectDTO;
import ar.fi.uba.tempore.dto.UserDTO;
import ar.fi.uba.tempore.dto.UserProjectDTO;
import ar.fi.uba.tempore.gwt.client.UserProjectServicesClient;
import ar.fi.uba.tempore.gwt.client.UserServicesClient;
import ar.fi.uba.tempore.gwt.client.panel.TabsPanelContainer;
import ar.fi.uba.tempore.gwt.client.panel.project.ProjectPanel;
import ar.fi.uba.tempore.gwt.server.image.ImageServlet;
import ar.fi.uba.temporeutils.observer.ProjectObserver;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.SelectionType;
import com.smartgwt.client.types.Visibility;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Button;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
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
	private final TileGrid tileGrid = new TileGrid();
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
		userTileGrid.setTileWidth(120);  
		userTileGrid.setTileHeight(160);  
		userTileGrid.setHeight("50%");  
		userTileGrid.setCanDrag(true);
		userTileGrid.setCanAcceptDrop(true);
		userTileGrid.setShowAllRecords(true);  
		userTileGrid.setAnimateTileChange(true);

		//userTileGrid.addDoubleClickHandler(asignByDoubleClick);
		
		final DetailViewerField pictureField = new DetailViewerField(IMAGE_NAME);
		pictureField.setType("image");
		pictureField.setImageWidth(100);
		pictureField.setImageHeight(100);
		pictureField.setImageURLPrefix(ImageServlet.URL_PREFIX);
		final DetailViewerField nameField = new DetailViewerField(NAME);
		nameField.setCellStyle("resourceUserName");
		final DetailViewerField userField = new DetailViewerField(USER_NAME);  
		final DetailViewerField emailField = new DetailViewerField(EMAIL);
		userTileGrid.setFields(pictureField, nameField, userField, emailField);
		userTileGrid.setData(new Record[]{});


		//BOTON HABILITADOR PARA CAMBIAR OWNER
		final Button changeOwnerBtn = new Button("Cambiar Lider del Proyecto");  
		changeOwnerBtn.setShowRollOver(false);
		changeOwnerBtn.setWidth100();
		changeOwnerBtn.setActionType(SelectionType.CHECKBOX);
		changeOwnerBtn.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				if (changeOwnerBtn.getSelected()){
					userTileGrid.setVisibility(Visibility.HIDDEN);
					changeOwner = true;
					tileGrid.setBackgroundColor("#FFFF99");
				} else {
					userTileGrid.setVisibility(Visibility.VISIBLE);
					changeOwner = false;
					tileGrid.setBackgroundColor("#FFFFFF");
				}
			}
		});

		tileGrid.setWidth100();  
		tileGrid.setHeight("50%");  
		tileGrid.setTileWidth(120);  
		tileGrid.setTileHeight(180);  
		tileGrid.setCanAcceptDrop(true);
		tileGrid.setCanDrag(true);
		tileGrid.setShowAllRecords(true);
		tileGrid.setAutoFetchData(true);
		tileGrid.addSelectionChangedHandler(new SelectionChangedHandler() {
			@Override
			public void onSelectionChanged(SelectionChangedEvent event) {
				if (changeOwner){
					UserProjectDTO data = new UserProjectDTO();
					ResourceDataSource.getInstance().copyValues((ListGridRecord)event.getRecord(), data);
					UserProjectServicesClient.Util.getInstance().changeOwner(data, new AsyncCallback<Void>() {
						@Override
						public void onSuccess(Void result) {
							tileGrid.fetchData();
							ProjectPanel.getInstance().fetchData();
						}
						@Override
						public void onFailure(Throwable caught) {
							SC.say("No se pudo actualizar el Owner del proyecto");
						}
					});
				}
			}
		});
		tileGrid.setDataSource(ResourceDataSource.getInstance());
		
		//USUARIOS ASIGANDOS
		final DetailViewerField pictureField2 = new DetailViewerField(IMAGE_NAME);
		pictureField2.setType("image");
		pictureField2.setImageWidth(100);
		pictureField2.setImageHeight(100);
		pictureField2.setImageURLPrefix(ImageServlet.URL_PREFIX);
		final DetailViewerField nameField2 = new DetailViewerField(NAME);
		nameField2.setCellStyle("resourceUserName");
		final DetailViewerField userField2 = new DetailViewerField(USER_NAME);  
		final DetailViewerField emailField2 = new DetailViewerField(EMAIL);
		final DetailViewerField ownerField2 = new DetailViewerField(IS_OWNER);
		ownerField2.setCellStyle("resourceOwner");
		ownerField2.setDetailFormatter(new DetailFormatter() {  
			public String format(Object value, Record record, DetailViewerField field) {
				if (value.toString().equals("1")){
					return "Creador";  
				} 
				return "";
			}  
		});
		tileGrid.setFields(pictureField2, nameField2, userField2, emailField2, ownerField2);
		
		VLayout vLayout = new VLayout();
		vLayout.setWidth100();
		vLayout.setHeight100();

		vLayout.addMember(userTileGrid);
		vLayout.addMember(changeOwnerBtn);
		vLayout.addMember(tileGrid);

		this.addChild(vLayout);
	}

	@Override
	public void updateProjectSelected() {
		ProjectDTO selected = ProjectPanel.getInstance().getSelected();
		if (selected != null) {			
			tileGrid.invalidateCache();
			tileGrid.fetchData();

			userTileGrid.setData(new Record[]{});
			UserServicesClient.Util.getInstance().getUserNotAssignedToProject(selected.getId(), new AsyncCallback<List<UserDTO>>() {
				@Override
				public void onSuccess(List<UserDTO> result) {
					for (UserDTO dto : result) {
						//GWT.log(dto.getName() + ", " + dto.getImageName());
						Record t = new Record();
						t.setAttribute(IMAGE_NAME, dto.getImageName());
						t.setAttribute(NAME, dto.getName());
						t.setAttribute(USER_NAME, dto.getUserName());
						t.setAttribute(EMAIL, dto.getEmail());
						t.setAttribute(USER_ID, dto.getId());
						userTileGrid.addData(t);
					}
				}
				@Override
				public void onFailure(Throwable caught) {
					SC.say("Error al cargar los usaurio para asignar");
				}
			});
		}		
	}

	
//	private DoubleClickHandler asignByDoubleClick = new DoubleClickHandler() {
//		@Override
//		public void onDoubleClick(DoubleClickEvent event) {
//			TileRecord selectedRecord = userTileGrid.getSelectedRecord();
//			userTileGrid.removeData(selectedRecord);
//			
//			tileGrid.addData(selectedRecord);
//		}
//	};
}

