package ar.fi.uba.tempore.gwt.client.panel.resource;

import ar.fi.uba.tempore.gwt.client.panel.TabsPanelContainer;

import com.smartgwt.client.data.Record;
import com.smartgwt.client.widgets.events.DropEvent;
import com.smartgwt.client.widgets.events.DropHandler;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.tile.TileGrid;
import com.smartgwt.client.widgets.tile.TileRecord;
import com.smartgwt.client.widgets.viewer.CellStyleHandler;
import com.smartgwt.client.widgets.viewer.DetailFormatter;
import com.smartgwt.client.widgets.viewer.DetailViewerField;

public class ResourceTabPanel extends TabsPanelContainer{
	public static final String USER_ID = "userIdCol";
	public static final String USER_PROJECT_ID = "userProjectIdCol";
	
	public static final String NAME = "nameCol";
	public static final String LAST_NAME = "lastNameCol";
	public static final String USER_NAME = "userNameCol";
	public static final String EMAIL = "emailCol";
	public static final String IMAGE_NAME = "imageName";
	//TODO falta saber si es cliente o no	
	
	public ResourceTabPanel(){
		
		final TileGrid userTileGrid = new TileGrid();  
        userTileGrid.setTileWidth(120);  
        userTileGrid.setTileHeight(160);  
        userTileGrid.setHeight("50%");  
        //userTileGrid.setCanAcceptDrop(true);
        userTileGrid.setCanDrag(true);
        userTileGrid.setAutoFetchData(true);
        userTileGrid.setShowAllRecords(true);  
        userTileGrid.setAutoFetchData(true);  
        userTileGrid.setAnimateTileChange(true);  
          
        userTileGrid.setDataSource(UserDataSource.getInstance());

        final DetailViewerField pictureField = new DetailViewerField(IMAGE_NAME);
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
        final DetailViewerField userIdField = new DetailViewerField(USER_ID);
        userTileGrid.setFields(pictureField, nameField, userField, emailField);  

        
        
        final TileGrid tileGrid = new TileGrid();  
        tileGrid.setWidth100();  
        tileGrid.setHeight("50%");  
        tileGrid.setTileWidth(120);  
        tileGrid.setTileHeight(160);  
        tileGrid.setAutoFetchData(true);
        tileGrid.setCanAcceptDrop(true);
        tileGrid.setData(new TileRecord[]{});  
        tileGrid.setCanDrag(true);  
        //tileGrid.setDataSource(UserProjectDataSource.getInstance());
        tileGrid.addDropHandler(new DropHandler() {
			@Override
			public void onDrop(DropEvent event) {
//				Canvas dragTarget = EventHandler.getDragTarget();  
//                SC.say("Se deposito el objeto: " +  dragTarget.getID()); 
			}
		});
        
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
        final DetailViewerField userIdField2 = new DetailViewerField(USER_ID);
        tileGrid.setFields(pictureField2, nameField2, userField2, emailField2);
        
        
        VLayout vLayout = new VLayout();
        vLayout.setWidth100();
        vLayout.setHeight100();
        
        vLayout.addMember(userTileGrid);
        vLayout.addMember(tileGrid);
        
        this.addChild(vLayout);
	}

}

