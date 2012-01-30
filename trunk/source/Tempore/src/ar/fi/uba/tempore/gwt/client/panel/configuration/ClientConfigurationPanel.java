package ar.fi.uba.tempore.gwt.client.panel.configuration;


import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.ListGridEditEvent;
import com.smartgwt.client.types.RowEndEditAction;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

public class ClientConfigurationPanel extends Canvas {
	
	private static final String HELPTEXT = "<br><b>Configuraci&oacute;n de Clientes</b><br>Esta p&aacute;gina le permitir&aacute; modificar, agregar y eliminar clientes. Las modificaciones realizadas en esta p&aacute;gina ser&aacute;n utilizadas para crear y modificar proyectos." +
	"<br> La informaci&oacute;n que se maneja para cada cliente es la siguiente:" +
	"<br><b>Nombre: </b>Nombre del cliente" +
	"<br><b>Dirrecci&oacute;n: </b>Dirrecci&oacute;n del cliente" +
	"<br><b>Provincia: </b>Provincia donde se encuentra f&iacute;sicamnete el cliente." +
	"<br><b>Pa&iacute;s: </b>Pa&iacute;s donde se encuentra f&iacute;sicamnete el cliente." +
	"<br><b>Tel&eacute;fono: </b>Tel&eacute;fono donde es posible localizar al cliente." +
	"<br><b>CUIT: </b>CUIT del cliente." +
	"<br><b>C&oacute;digo Postal: </b>C&oacute;digo Postal del cliente." +
    "<br><br><b>Creaci&oacute;n de Nuevo Cliente</b><br>Para crear un nuevo cliente seleccione el bot&oacute;n Nuevo y complete los campos obligatorios (Nombre, Direcci&oacute;n, Provincia y Pa&iacute;). Si uno o m&aacute;s de los campos obligatorios no son ingresados, no ser&aacute; posible guardar al cliente." +  
    "<br><br><b>Modificaci&oacute;n de un Cliente</b><br>Seleccione al cliente que desee modificar y autom&aacute;ticamente todos los campos ser&aacute;n editables." +  
    "<br><br><b>Eliminaci&oacute;n de un Cliente</b><br>Seleccione al cliente que desee eliminar y presione el bot&oacute;n Eliminar.";
	
	
	
	public ClientConfigurationPanel() {
		super();
		updateContent();
	}

	public void updateContent() {
		final VLayout vLayout = new VLayout();
		vLayout.setWidth100();
		vLayout.setHeight100();
		vLayout.setMembersMargin(6);
		
		final Label title = new Label("Configuraci&oacute;n de Clientes");
		title.setWidth(200);
		title.setHeight(15);
		title.setIcon("[SKIN]/actions/help.png");
		title.setStyleName("Informal");
		title.setIconOrientation("right");
        title.addIconClickHandler(new com.smartgwt.client.widgets.events.IconClickHandler() {
			
			@Override
			public void onIconClick(
					com.smartgwt.client.widgets.events.IconClickEvent event) {
				 	SC.say(HELPTEXT);
				
			}
		});

		
		final ListGrid grid = new ListGrid();
		ClientConfigurationDataSource dataSource = new ClientConfigurationDataSource(); 		
		grid.setDataSource(dataSource);
		grid.setWidth100();
		grid.setHeight100();		
		grid.setAutoFetchData(true);
		grid.setCanEdit(true);
		grid.setEditEvent(ListGridEditEvent.CLICK);		
		grid.setListEndEditAction(RowEndEditAction.NEXT);
		grid.setAutoSaveEdits(true);
		
		
		// BOTONERA 
		final HLayout btnHLayout = new HLayout();		
		btnHLayout.setWidth100();
		btnHLayout.setMembersMargin(6);
		btnHLayout.setAlign(Alignment.RIGHT);
		//btnHLayout.setAlign(VerticalAlignment.CENTER);
		
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
		
		//Agrego los componentes al panel
		vLayout.addMember(title);
		vLayout.addMember(btnHLayout);
		vLayout.addMember(grid);	
		this.addChild(vLayout);
	}
}

