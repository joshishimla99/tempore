package ar.fi.uba.tempore.gwt.client.login;
/**
 * Clase que contiene la informacion del usuario que esta logeado en la sesion
 * @author Ludmila
 *
 */
public class SessionUser {

	private String username;
	private String role;
	private String name;
	private String lastname;
	private String id;
	
	public SessionUser(){
		
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
