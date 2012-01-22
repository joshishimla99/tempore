package ar.fi.uba.tempore.dao;

import org.hibernate.Query;

import ar.fi.uba.tempore.dao.util.GenericHibernateDAO;
import ar.fi.uba.tempore.entity.Alert;

public class AlertDAO extends GenericHibernateDAO<Alert, Integer> {

	public Integer deleteAllUserAlert(Integer projectUserId) {
		String hql = "delete " +
				" from Alert as a " +
				" where a.userProject.id = :projectUserId"; 
		
		Query query = this.getSession().createQuery(hql).setInteger("projectUserId", projectUserId);
		
		return query.executeUpdate();
	}

	
}
