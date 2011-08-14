package ar.fi.uba.tempore.service;

import java.util.ArrayList;
import java.util.List;

import ar.fi.uba.tempore.dto.UserDTO;

public class UserServices {

	public List<UserDTO> allUsers() {
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
		
	/*	user.setName("Juan Pablo");
		user.setLastName("Gigante");
		user.setAddress("Corrientes 900 - 21");
		user.setClient(false);
		user.setCompany("Nobleza Picardo");
		user.setCountry("Argentina");
		user.setEmail("juanPablo.gigante@picardo.com");
		user.setPassword("12345678");
		user.setPhone("1532070760");
		user.setRole("Admin");
		user.setUserName("jgigante");
		user.setZipCode("1670");
		userList.add(user);
        		
		user.setName("Nicolas");
		user.setLastName("Garcia");
		user.setAddress("9 de Julio 900 - 21");
		user.setClient(false);
		user.setCompany("EmpresaX");
		user.setCountry("Argentina");
		user.setEmail("nicolas.garcia@empresaX.com");
		user.setPassword("12345678");
		user.setPhone("1532070768");
		user.setRole("Admin");
		user.setUserName("ngarcia");
		user.setZipCode("1670");
		userList.add(user);
		
		user.setName("Guillermo");
		user.setLastName("Pantaleo");
		user.setAddress("Suipacha 900 - 21");
		user.setClient(true);
		user.setCompany("itMentor");
		user.setCountry("Argentina");
		user.setEmail("guillermo.pantaleo@itMentor.com");
		user.setPassword("12345678");
		user.setPhone("1532070754");
		user.setRole("Client");
		user.setUserName("gpantaleo");
		user.setZipCode("1670");
		userList.add(user);
		
		user.setName("Ignacio");
		user.setLastName("Fernandez");
		user.setAddress("Jujuy 900 - 21");
		user.setClient(false);
		user.setCompany("Tata");
		user.setCountry("Argentina");
		user.setEmail("ignacio.fernandez@tata.com");
		user.setPassword("12345678");
		user.setPhone("ifernandez");
		user.setRole("User");
		user.setUserName("ifernandez");
		user.setZipCode("1670");
		userList.add(user);
		
		user.setName("Carlos");
		user.setLastName("Perez");
		user.setAddress("Eduardo Madero 900 - 21");
		user.setClient(true);
		user.setCompany("Microsoft");
		user.setCountry("Brasil");
		user.setEmail("carlos.perez@microsoft.com");
		user.setPassword("12345678");
		user.setPhone("1532070723");
		user.setRole("Client");
		user.setUserName("cperez");
		user.setZipCode("1670");
		userList.add(user);
		
		user.setName("Mauro");
		user.setLastName("Arnoni");
		user.setAddress("Independencia 900 - 21");
		user.setClient(false);
		user.setCompany("PetroleraX");
		user.setCountry("Argentina");
		user.setEmail("mauro.arnoni@petroleraX.com");
		user.setPassword("12345678");
		user.setPhone("1532070756");
		user.setRole("User");
		user.setUserName("marnoni");
		user.setZipCode("1670");
		userList.add(user);
		
		user.setName("Diego");
		user.setLastName("Riccillo");
		user.setAddress("Eduardo Madero 900 - 21");
		user.setClient(false);
		user.setCompany("Gemalto");
		user.setCountry("Argentina");
		user.setEmail("diego.riccillo@gemalto.com");
		user.setPassword("12345678");
		user.setPhone("1532070760");
		user.setRole("Admin");
		user.setUserName("driccillo");
		user.setZipCode("1670");
		userList.add(user);
		
		user.setName("Maria");
		user.setLastName("Durante");
		user.setAddress("Eduardo Madero 900 - 21");
		user.setClient(false);
		user.setCompany("Gemalto");
		user.setCountry("Brasil");
		user.setEmail("maria.durante@gemalto.com");
		user.setPassword("12345678");
		user.setPhone("1532070761");
		user.setRole("User");
		user.setUserName("mdurante");
		user.setZipCode("1670");
		userList.add(user);
		
		user.setName("Julio");
		user.setLastName("Lopez");
		user.setAddress("Jujuy 900 - 21");
		user.setClient(false);
		user.setCompany("Tata");
		user.setCountry("Argentina");
		user.setEmail("julio.lopez@tata.com");
		user.setPassword("12345678");
		user.setPhone("1534070761");
		user.setRole("Admin");
		user.setUserName("jlopez");
		user.setZipCode("1980");
		userList.add(user);
		
		user.setName("Julio");
		user.setLastName("Martinez");
		user.setAddress("Teochican 43 - 21");
		user.setClient(false);
		user.setCompany("Tata");
		user.setCountry("Colombia");
		user.setEmail("julio.martinez@tata.com");
		user.setPassword("12345678");
		user.setPhone("15340707561");
		user.setRole("User");
		user.setUserName("jmartinez");
		user.setZipCode("1980");
		userList.add(user);
		*/
		//TODO: obtener la info de la BD
		return userList;
	}
}
