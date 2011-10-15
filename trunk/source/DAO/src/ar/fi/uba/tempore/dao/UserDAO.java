package ar.fi.uba.tempore.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;

import ar.fi.uba.tempore.dao.util.GenericHibernateDAO;
import ar.fi.uba.tempore.entity.User;

public class UserDAO extends GenericHibernateDAO<User, Integer> {

	public User validateUser(String userName, String password) {

		User user = new User();
		user.setUserName(userName);
		user.setPassword(password);
		List<User> findByExample = this.findByExample(user);
		if (!findByExample.isEmpty()){
			user = findByExample.get(0);
		}
		
		return user;
	}
	
	
	/**
	 * Usuarios NO asignados al proyecto
	 * @param projectId Id del proyecto
	 * @return Listado de usuarios
	 */
	@SuppressWarnings("unchecked")
	public List<User> getUserNotAssignedToProject (Integer projectId){
		List<User> list = null;
		if (projectId != null){
			String hql = "select u" +
					" from User u" +
					" where u.id not in (select up.user.id " +
					" from UserProject up " +
					" where up.project.id = " + projectId + 
					")";

			Query createQuery = this.getSession().createQuery(hql);
			list = createQuery.list();
		} else {
			list = new ArrayList<User>();
		}
		return list;		
	}
}
