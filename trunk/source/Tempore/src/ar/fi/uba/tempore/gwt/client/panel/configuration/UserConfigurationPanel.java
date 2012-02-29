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
import com.smartgwt.client.widgets.form.fields.CheckboxItem;
import com.smartgwt.client.widgets.form.fields.HiddenItem;
import com.smartgwt.client.widgets.form.fields.PasswordItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

public class UserConfigurationPanel extends Canvas {

	private static final String HELPTEXT = "<br><b>Configuraci&oacute;n de Usuarios</b><br>Esta p&aacute;gina le permitir&aacute; modificar, agregar y eliminar usuarios al sistema. Las modificaciones realizadas en esta p&aacute;gina ser&aacute;n utilizadas para asociar usuarios a los proyectos." +
	"<br> La informaci&oacute;n que se maneja para cada usuario es la siguiente:" +
	"<br><b>Usuario: </b>Nombre del usuario a ser utilizado para ingresar al sistema." +
	"<br><b>Nombre: </b>Nombre del usuario." +
	"<br><b>Apellido: </b>Apellido del usuario." +
	"<br><b>Email: </b>Email del usuario." +
    "<br><br><b>Creaci&oacute;n de Nuevo Usuario</b><br>Para crear un nuevo usuario seleccione el bot&oacute;n Nuevo y complete todos los campos. Si uno o m&aacute;s de los campos no son ingresados, no ser&aacute; posible guardar al cliente." +  
    "<br><br><b>Modificaci&oacute;n de un Usuario</b><br>Seleccione al usuario que desee modificar y autom&aacute;ticamente todos los campos ser&aacute;n editables." +  
    "<br><br><b>Eliminaci&oacute;n de un Usuario</b><br>Seleccione al usuario que desee eliminar y presione el bot&oacute;n Eliminar.";
	
	public UserConfigurationPanel() {
		super();
		updateContent();
	}

	public void updateContent() {
		final VLayout vLayout = new VLayout();
		vLayout.setWidth100();
		vLayout.setHeight100();
		vLayout.setMembersMargin(6);

		//TITULO
		Label title = new Label("Configuraci&oacute;n de Usuarios");
		title.setWidth100();
		title.setHeight(15);
		title.setIcon("[SKIN]/actions/help.png");
		title.setStyleName("titleStyleInformal");
		title.setIconOrientation("right");
        title.addIconClickHandler(new com.smartgwt.client.widgets.events.IconClickHandler() {
			
			@Override
			public void onIconClick(
					com.smartgwt.client.widgets.events.IconClickEvent event) {
				 	SC.say(HELPTEXT);
				
			}
		});

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
				final PasswordItem n3 = new PasswordItem(UserConfigurationDataSource.PASSWORD);
				final TextItem n4 = new TextItem(UserConfigurationDataSource.EMAIL);
				final TextItem n5 = new TextItem(UserConfigurationDataSource.ADDRESS);
				final TextItem n6 = new TextItem(UserConfigurationDataSource.COUNTRY);
				final TextItem n7 = new TextItem(UserConfigurationDataSource.PHONE);
				final TextItem n8 = new TextItem(UserConfigurationDataSource.ZIP_CODE);
				final CheckboxItem n9 = new CheckboxItem(UserConfigurationDataSource.IS_ADMIN);
				
				final DynamicForm form = new DynamicForm();  				
				form.setNumCols(4);  
				form.setDataSource(dataSource);				
				
				form.setFields(n0,n1,n2,n3,n4,n5,n6,n7,n8,n9,field);
				form.addDrawHandler(new DrawHandler() {  
					public void onDraw(DrawEvent event) {  
						form.editRecord(record);  
					}  
				});  
				
				
				
				final ImgClient image = new ImgClient(record.getAttribute(UserConfigurationDataSource.IMAGE_NAME));
				image.setWidth(150);
				image.setHeight(150);
				image.addUpdateImageHandler(new UpdateImgHandler() {
					@Override
					public void onUpdatedImg(String fileName) {
						//Actualizo el nombre del archivo
						field.setValue(fileName);
					}
				});				
				
				

				IButton saveButton = new IButton("Guardar"); 
				saveButton.setIcon("../images/ico/save.ico");
				saveButton.addClickHandler(new ClickHandler() {  
					public void onClick(ClickEvent event) {  
						if (form.validate()){
							grid.collapseRecord(record);
							form.saveData();  
						}
					}  
				});  

				IButton cancelButton = new IButton("Cancelar"); 
				cancelButton.setIcon("../images/ico/back.ico");
				cancelButton.addClickHandler(new ClickHandler() {  
					public void onClick(ClickEvent event) {  
						grid.collapseRecord(record);  
					}  
				});  

				//LAYOUTS 
				
				//Botones
				final HLayout hLayoutBtn = new HLayout(10);  
				hLayoutBtn.setAlign(Alignment.CENTER);  
				hLayoutBtn.addMember(saveButton);  
				hLayoutBtn.addMember(cancelButton);  

				
				final VLayout vLayout = new VLayout(5);
				vLayout.setWidth("80%");
				vLayout.setPadding(15);
				vLayout.addMember(form);  
				vLayout.addMember(hLayoutBtn);
				
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
		grid.setAutoSaveEdits(true);
		grid.setLoadingDataMessage("${loadingImage}&nbsp;Cargando...");

		ListGridField user = new ListGridField (UserConfigurationDataSource.USER_NAME);
		ListGridField name = new ListGridField (UserConfigurationDataSource.NAME);
		ListGridField lastName = new ListGridField (UserConfigurationDataSource.LAST_NAME);
		ListGridField email = new ListGridField (UserConfigurationDataSource.EMAIL);
		grid.setFields(user, name, lastName, email);

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