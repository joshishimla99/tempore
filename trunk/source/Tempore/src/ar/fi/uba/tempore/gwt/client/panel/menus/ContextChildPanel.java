package ar.fi.uba.tempore.gwt.client.panel.menus;

/*
 * El objetivo de esta clase es englobar los paneles del contextPanel. De esta manera, todos los
 * paneles deberan implementar el metodo UpdateContent() que sera el encargado de actualizar el
 * contenido de los mismos, cuando sean seleccionados en el menu.
 * */
public interface ContextChildPanel {
	
	public void UpdateContent();

}
