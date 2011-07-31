package ar.fi.uba.tempore.gwt.client;


public class MenuService {
	
	private static MenuService instance = null;
	
	private MenuService(){
	}
	
	public static MenuService getInstance(){
		if(instance == null) {
			instance = new MenuService();
		}
		return instance;
	}
	
}
