package ar.fi.uba.tempore.gwt.client.login;

import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.fields.DataSourceTextField;

public class LoginDS extends DataSource{

	private static LoginDS instance = null;
	private DataSourceTextField login;
	
	public static LoginDS getInstance(){
		
		if (instance == null) {
			instance = new LoginDS();			
		}
		return instance;
	}
	
	public LoginDS(){
		login = new DataSourceTextField("login","Login",50,true);
			
		setFields(login);
		
	}

	public DataSourceTextField getLogin() {
		return login;
	}

	public void setLogin(DataSourceTextField login) {
		this.login = login;
	}
}
