package ar.fi.uba.tempore.gwt.client.panel;

import com.smartgwt.client.widgets.Canvas;

/*
 * Este panel es el contenedor de Ppal, Tarea, proyectos, etc.
 * 
 */
public class ContextPanel extends Canvas{

	public ContextPanel(){
		this.addChild(new MainPanel());
	}
	
	
}
