package ar.fi.uba.tempore.gwt.server;

import java.util.ArrayList;
import java.util.List;

import ar.fi.uba.tempore.dto.UserDTO;
import ar.fi.uba.tempore.service.UserServices;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class UserServicesImpl extends RemoteServiceServlet implements ar.fi.uba.tempore.gwt.client.UserServicesClient {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6786157718346471647L;

	@Override
	public List<UserDTO> getUsers() {
		System.out.println("***************** INICIO SERVICO *********************");
		List<UserDTO> userList = new ArrayList<UserDTO>();
		
		UserDTO user = new UserDTO();
		user.setName("Ludmila Lis");
		user.setLastName("Rinaudo");
		user.setAddress("Eduardo Madero 900 - 21");
		user.setClient(false);
		user.setCompany("Gemalto");
		user.setCountry("Argentina");
		user.setEmail("ludmila.rinaudo@gemalto.com");
		user.setPassword("12345678");
		user.setPhone("1532070761");
		user.setRole("Admin");
		user.setUserName("lrinaudo");
		user.setZipCode("1670");
		userList.add(user);
		System.out.println("***************** FIN SERVICO *********************");
		return userList;
	}

}
