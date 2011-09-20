package ar.fi.uba.tempore.gwt.client.panel.configuration;

import ar.fi.uba.temporeutils.image.ImgClient;
import ar.fi.uba.temporeutils.image.UpdateImgHandler;

import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.events.DrawEvent;
import com.smartgwt.client.widgets.events.DrawHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.HiddenItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

public class UserConfigurationPanel extends Canvas {

	public UserConfigurationPanel() {
		super();
		updateContent();
	}

	public void updateContent() {
		VLayout vLayout = new VLayout();
		vLayout.setWidth100();
		vLayout.setHeight100();
		vLayout.setMembersMargin(6);

		//TITULO
		Label title = new Label("Configuraci&oacute;n de Alertas");
		title.setWidth(200);
		title.setHeight(15);


		final UserConfigurationDataSource dataSource = UserConfigurationDataSource.getInstance();

		//GRILLA
		final ListGrid grid = new ListGrid(){
			@Override
			protected Canvas getExpansionComponent(final ListGridRecord record){

				final ListGrid grid = this;  
					
				
				final HiddenItem field = new HiddenItem(UserConfigurationDataSource.IMAGE_NAME);
				final TextItem n0 = new TextItem(UserConfigurationDataSource.NAME);
				final TextItem n1 = new TextItem(UserConfigurationDataSource.LAST_NAME);
				final TextItem n2 = new TextItem(UserConfigurationDataSource.USER_NAME);
				final TextItem n3 = new TextItem(UserConfigurationDataSource.PASSWORD);
				final TextItem n4 = new TextItem(UserConfigurationDataSource.EMAIL);
				final TextItem n5 = new TextItem(UserConfigurationDataSource.ADDRESS);
				final TextItem n6 = new TextItem(UserConfigurationDataSource.COUNTRY);
				final TextItem n7 = new TextItem(UserConfigurationDataSource.PHONE);
				final TextItem n8 = new TextItem(UserConfigurationDataSource.ZIP_CODE);				
				
				final DynamicForm df = new DynamicForm();  				
				df.setNumCols(4);  
				df.setDataSource(dataSource);				
				
				df.setFields(n0,n1,n2,n3,n4,n5,n6,n7,n8,field);
				df.addDrawHandler(new DrawHandler() {  
					public void onDraw(DrawEvent event) {  
						df.editRecord(record);  
					}  
				});  
				
				
				
				final ImgClient image = new ImgClient(record.getAttribute(UserConfigurationDataSource.IMAGE_NAME));
				image.setWidth(200);
				image.setHeight(120);
				image.addUpdateImageHandler(new UpdateImgHandler() {
					@Override
					public void onUpdatedImg(String fileName) {
						//Actualizo el nombre del archivo
						field.setValue(fileName);
						SC.say("Actualizo archivo: " + fileName);
					}
				});				
				
				

				IButton saveButton = new IButton("Guardar");  
				saveButton.addClickHandler(new ClickHandler() {  
					public void onClick(ClickEvent event) {  
						df.saveData();  
					}  
				});  

				IButton cancelButton = new IButton("Cancelar");  
				cancelButton.addClickHandler(new ClickHandler() {  
					public void onClick(ClickEvent event) {  
						grid.collapseRecord(record);  
					}  
				});  

				//LAYOUTS 
				
				//Botones
				final HLayout hLayout = new HLayout(10);  
				hLayout.setAlign(Alignment.CENTER);  
				hLayout.addMember(saveButton);  
				hLayout.addMember(cancelButton);  

				
				final VLayout vLayout = new VLayout(5);
				vLayout.setWidth("80%");
				vLayout.setPadding(15);
				//vLayout.setMargin(20);
				vLayout.addMember(df);  
				vLayout.addMember(hLayout);
				
				HLayout hLayoutRoot = new HLayout(10);
				hLayoutRoot.addMember(vLayout);
				hLayoutRoot.addMember(image);
				return hLayoutRoot;  
			}
		};
		grid.setCanExpandRecords(true);		
		grid.setDataSource(dataSource);
		grid.setWidth100();
		grid.setHeight100();		
		grid.setAutoFetchData(true);
		//		grid.setCanEdit(true);
		//		grid.setEditEvent(ListGridEditEvent.CLICK);		
		//		grid.setListEndEditAction(RowEndEditAction.NEXT);
		grid.setAutoSaveEdits(true);


		ListGridField user = new ListGridField (UserConfigurationDataSource.USER_NAME);
		ListGridField name = new ListGridField (UserConfigurationDataSource.NAME);
		ListGridField lastName = new ListGridField (UserConfigurationDataSource.LAST_NAME);		
		grid.setFields(user, name, lastName);

		// BOTONERA
		final HLayout btnHLayout = new HLayout();		
		btnHLayout.setWidth100();		
		btnHLayout.setMembersMargin(6);
		btnHLayout.setAlign(Alignment.RIGHT);

		IButton newButton = new IButton("Nuevo");
		newButton.setIcon("../images/ico/add.ico");		
		newButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				grid.startEditingNew();
			}
		});
		btnHLayout.addMember(newButton);	

		final IButton removeButton = new IButton("Eliminar");
		removeButton.setIcon("../images/ico/remove.ico");
		removeButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				grid.removeSelectedData();
			}
		});
		btnHLayout.addMember(removeButton);

		vLayout.addMember(title);
		vLayout.addMember(btnHLayout);
		vLayout.addMember(grid);
		this.addChild(vLayout);
	}
}
