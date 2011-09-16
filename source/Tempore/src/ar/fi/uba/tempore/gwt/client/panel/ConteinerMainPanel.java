package ar.fi.uba.tempore.gwt.client.panel;

import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

/**
 * Clase principal que contiene al panel de login y ademas al conteneder de la aplicacion, una vez logeado el usuario
 * @author Ludmila
 *
 */
public class ConteinerMainPanel extends VLayout {

	private static final String TEMPORE_CONTEINER = "Tempore_Conteiner";

	public ConteinerMainPanel(){
		this.setWidth100();
		this.setHeight100();
		this.setStyleName(TEMPORE_CONTEINER);
		this.setMargin(20);

		//Navegacion / Contenido
		final HLayout hLayoutData = new HLayout();
		hLayoutData.addMember(new ExplorerPanel());
		hLayoutData.addMember(new TabsPanel());


		this.addMember(new HeaderPanel());
		this.addMember(hLayoutData);	
	}

}
