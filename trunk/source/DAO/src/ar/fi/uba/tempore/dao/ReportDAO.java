package ar.fi.uba.tempore.dao;

import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.classic.Session;

import ar.fi.uba.tempore.dao.util.HibernateUtil;
import ar.fi.uba.tempore.entity.Project;

public class ReportDAO {

	private Session session;

	public ReportDAO() {

	}
	
	/**
	 * Obtiene la sesion activa
	 * @return
	 */
	private Session getSession() {
		if (session == null || !session.isOpen()) {
			session = HibernateUtil.getSessionFactory().getCurrentSession();
		}
		return session;
	}

	public void flush() {
		getSession().flush();
	}

	public void clear() {
		getSession().clear();
	}
	
	
	public List getProjectTimes (Date ini, Date end){	
		List<Project> list = null;
		
		String hql = "select new map(p.id as id,p.name as name, sum(tu.hourCount) as total)" +
						"from Task as t " +
						"inner join t.project as p " +
						"inner join t.taskUserList as tu " +
						"group by p";
		
		System.out.println(hql);
		
		Query query = this.getSession().createQuery(hql);
//		query = query.setDate("ini", ini).setDate("end", end);
		list = query.list();
		
		return list;
	}

	
}
