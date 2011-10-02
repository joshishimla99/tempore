package ar.fi.uba.tempore.gwt.client.exception;

public class TaskWithHoursChargedException extends Exception{

	/**
	 * Esta exception sera lanzada cuando se quiera eliminar una tarea y ella o sus hijas tenga horas cargadas.
	 */
	
	private static final long serialVersionUID = 776515502734509179L;
	private String name;
	
	public TaskWithHoursChargedException(){
		this.name = "tarea desconocida";
	}
	
	public TaskWithHoursChargedException(String name){
		this.name = name;
	}
	
	public String getTaskName(){
		return this.name;
	}
}
