package ar.fi.uba.tempore.dao;

import java.util.List;

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

}
