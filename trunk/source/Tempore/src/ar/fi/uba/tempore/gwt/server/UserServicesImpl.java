
package ar.fi.uba.tempore.gwt.server;

import java.util.ArrayList;
import java.util.List;

import javax.naming.ServiceUnavailableException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.dozer.DozerBeanMapper;

import ar.fi.uba.mail.RandomString;
import ar.fi.uba.mail.SendMailSSL;
import ar.fi.uba.tempore.dao.UserDAO;
import ar.fi.uba.tempore.dto.UserDTO;
import ar.fi.uba.tempore.entity.User;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class UserServicesImpl extends RemoteServiceServlet implements ar.fi.uba.tempore.gwt.client.UserServicesClient {
	
	private final Logger log = Logger.getLogger(this.getClass());
	
	private static final long serialVersionUID = -6786157718346471647L;
	private final DozerBeanMapper mapper = new DozerBeanMapper();

	
	public UserDTO validateUser (String userName, String password){
		UserDTO dto = null;
//		HttpServletRequest request = this.getThreadLocalRequest();
//		HttpSession session = request.getSession();
//		
//		if (session.isNew()){
//			log.info("Session EXISTENTE");
//		} else {
//			log.info("NUEVA SESSION!!!!");
//		}

		UserDAO uDAO = new UserDAO(); 
		log.info("Users - VALIDATE USER");

		User user = uDAO.validateUser(userName, password);
		if (user != null && user.getId() != null) {
			dto = mapper.map(user, UserDTO.class);
//			session.setAttribute("userName", dto.getUserName());
		}
		
		return dto;
	}
	
	@Override
	public String getUserLoggued(){
		HttpServletRequest request = this.getThreadLocalRequest();
		HttpSession session = request.getSession();
//		if (session.isNew()){
//			log.info("Session EXISTENTE" + session.getAttribute("username"));
//		} else {
//			log.info("NUEVA SESSION!!!!");
//		}
		return (String) session.getAttribute("username");
	}
	
	@Override
	public List<UserDTO> fetch() {
		UserDAO uDAO = new UserDAO(); 
		log.info("FETCH - Users");
		List<UserDTO> userList = new ArrayList<UserDTO>();
		
		List<User> users = uDAO.findAll();
		for (User u : users) {
			UserDTO uDTO = mapper.map(u, UserDTO.class);
			userList.add(uDTO);
		}
		return userList;
	}

	@Override
	public UserDTO add(UserDTO userDTO) {
		if (userDTO.getPassword()==null){
			userDTO.setPassword("1234");
		}
		return update(userDTO);
	}

	@Override
	public UserDTO update(UserDTO userDTO) {
		UserDAO uDAO = new UserDAO(); 
		log.info("UPDATE - User");
		User user = mapper.map(userDTO, User.class);
		try {
			user.setPassword(uDAO.hashPassword(userDTO.getPassword()));
		} catch (ServiceUnavailableException e) {
			e.printStackTrace();
		}
		User newUser = uDAO.makePersistent(user);
		UserDTO newUserDto = mapper.map(newUser, UserDTO.class);
		return newUserDto;
	}

	@Override
	public void remove(UserDTO userDTO) {
		UserDAO uDAO = new UserDAO(); 
		log.info("REMOVE - User");
		User user = mapper.map(userDTO, User.class);
		uDAO.delete(user);
	}
	
	
	/**
	 * Obtiene el listado de usauarios que no estan asignado al proyecto 
	 * @param projectId Id del proyecto a observar
	 * @return Lista de usuarios
	 */
	public List<UserDTO> getUserNotAssignedToProject(Integer projectId){
		UserDAO uDAO = new UserDAO(); 
		log.info("GET USER NOT ASSIGNED TO PROJECT - id=" + projectId);
		List<User> userNotAssignedToProject = uDAO.getUserNotAssignedToProject(projectId);
		
		List<UserDTO> list = new ArrayList<UserDTO>();
		for (User user : userNotAssignedToProject) {
			UserDTO map = mapper.map(user, UserDTO.class);
			list.add(map);
		}
		return list;
	}

	@Override
	public boolean validateUserName(String userName) {
		UserDAO uDAO = new UserDAO(); 
		log.info("Users - VALIDATE USERNAME FOR RECOVERY PASSWORD");

		return uDAO.validateUserName(userName);
	}

	@Override
	public void recoveryUserPassword(String userName) {
		UserDAO uDAO = new UserDAO();
		User user = uDAO.getUser(userName);
		if (user != null){
			String password = generatePassword(userName);
			try {
				user.setPassword(uDAO.hashPassword(password));
				uDAO.makePersistent(user);
			
				SendMailSSL mail = new SendMailSSL(userName, user.getEmail(), password);
				mail.sendMail();
			} catch (Exception e) {
				e.printStackTrace();
			}		
		}
	}
	
	private String generatePassword(String userName){
		RandomString random = new RandomString(5);
		String password = random.nextString();
		return password;
	}
	
	@Override
	public UserDTO updateNotAdmin(UserDTO userDTO) {
		log.info("UPDATE USER - " + userDTO.getUserName());
		UserDAO uDAO = new UserDAO();
		//seteo admin en No
		userDTO.setAdmin("N");
		User user = mapper.map(userDTO, User.class);
		try {
			user.setPassword(uDAO.hashPassword(userDTO.getPassword()));
		} catch (ServiceUnavailableException e) {
			e.printStackTrace();
		}
		User makePersistent = uDAO.makePersistent(user);
		UserDTO result = mapper.map(makePersistent, UserDTO.class);
		
		return result;
	}
}

