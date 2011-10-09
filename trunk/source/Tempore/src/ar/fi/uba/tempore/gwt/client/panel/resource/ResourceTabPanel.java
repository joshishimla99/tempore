package ar.fi.uba.tempore.gwt.client.panel.resource;

import java.util.List;

import ar.fi.uba.tempore.dto.ProjectDTO;
import ar.fi.uba.tempore.dto.UserDTO;
import ar.fi.uba.tempore.gwt.client.UserProjectServicesClient;
import ar.fi.uba.tempore.gwt.client.panel.TabsPanelContainer;
import ar.fi.uba.tempore.gwt.client.panel.project.ProjectPanel;
import ar.fi.uba.temporeutils.observer.ProjectObserver;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.events.DropEvent;
import com.smartgwt.client.widgets.events.DropHandler;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.tile.TileGrid;
import com.smartgwt.client.widgets.viewer.CellStyleHandler;
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

	private final TileGrid userTileGrid = new TileGrid();
	private final TileGrid tileGrid = new TileGrid();
	
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
		GWT.log("Se crea Tab Resource");
		userTileGrid.setTileWidth(120);  
		userTileGrid.setTileHeight(160);  
		userTileGrid.setHeight("50%");  
		userTileGrid.setCanDrag(true);
		userTileGrid.setCanAcceptDrop(true);
		userTileGrid.setShowAllRecords(true);  
		userTileGrid.setAnimateTileChange(true);
		
		final DetailViewerField pictureField = new DetailViewerField(IMAGE_NAME);
		pictureField.setType("image");
		pictureField.setImageWidth(100);
		pictureField.setImageHeight(100);
		pictureField.setImageURLPrefix("http://localhost:8080/Tempore/imageServlet.img?id=");
		final DetailViewerField nameField = new DetailViewerField(NAME);
		nameField.setCellStyleHandler(new CellStyleHandler() {
			@Override
			public String execute(Object value, DetailViewerField field, Record record) {
				return "bold";
			}
		});
		final DetailViewerField userField = new DetailViewerField(USER_NAME);  
		userField.setDetailFormatter(new DetailFormatter() {  
			public String format(Object value, Record record, DetailViewerField field) {  
				return "User: " + value;  
			}  
		});  
		final DetailViewerField emailField = new DetailViewerField(EMAIL);  
		userTileGrid.setFields(pictureField, nameField, userField, emailField);
		userTileGrid.setData(new Record[]{});

		
		
		
		tileGrid.setWidth100();  
		tileGrid.setHeight("50%");  
		tileGrid.setTileWidth(120);  
		tileGrid.setTileHeight(160);  
		tileGrid.setCanAcceptDrop(true);
		tileGrid.setCanDrag(true);
		tileGrid.setShowAllRecords(true);
		tileGrid.setAutoFetchData(true);
		tileGrid.addDropHandler(new DropHandler() {
			@Override
			public void onDrop(DropEvent event) {
				//				Canvas dragTarget = EventHandler.getDragTarget();  
				//                SC.say("Se deposito el objeto: " +  dragTarget.getID()); 
			}
		});

		tileGrid.setDataSource(AssignedUserDataSource.getInstance());

		final DetailViewerField pictureField2 = new DetailViewerField(IMAGE_NAME);
		pictureField2.setType("image");
		pictureField2.setImageWidth(100);
		pictureField2.setImageHeight(100);
		pictureField2.setImageURLPrefix("http://localhost:8080/Tempore/imageServlet.img?id=");
		final DetailViewerField nameField2 = new DetailViewerField(NAME);
		nameField2.setCellStyleHandler(new CellStyleHandler() {
			@Override
			public String execute(Object value, DetailViewerField field, Record record) {
				return "bold";
			}
		});
		final DetailViewerField userField2 = new DetailViewerField(USER_NAME);  
		userField2.setDetailFormatter(new DetailFormatter() {  
			public String format(Object value, Record record, DetailViewerField field) {  
				return "User: " + value;  
			}  
		});
		final DetailViewerField emailField2 = new DetailViewerField(EMAIL);
		tileGrid.setFields(pictureField2, nameField2, userField2, emailField2);


		VLayout vLayout = new VLayout();
		vLayout.setWidth100();
		vLayout.setHeight100();

		vLayout.addMember(userTileGrid);
		vLayout.addMember(tileGrid);

		this.addChild(vLayout);
	}

	@Override
	public void updateProjectSelected() {
		GWT.log("Resource - Actualizacion de Proyecto");
		ProjectDTO selected = ProjectPanel.getInstance().getSelected();
		if (selected != null) {			
			tileGrid.invalidateCache();
			tileGrid.fetchData();
			
			userTileGrid.setData(new Record[]{});
			UserProjectServicesClient.Util.getInstance().getUserNotAssignedToProject(selected.getId(), new AsyncCallback<List<UserDTO>>() {
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
}

