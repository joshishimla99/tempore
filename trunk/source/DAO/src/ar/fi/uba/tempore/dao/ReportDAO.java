package ar.fi.uba.tempore.dao;

import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.classic.Session;

import ar.fi.uba.tempore.dao.util.HibernateUtil;
import ar.fi.uba.tempore.entity.reports.ProjectsTimes;

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
	
	
	@SuppressWarnings("unchecked")
	public List<ProjectsTimes> getProjectsTimes (Date ini, Date end){	
		List<ProjectsTimes> list = null;
		
		String hql = "select new ar.fi.uba.tempore.entity.reports.ProjectsTimes(p.name as name, sum(tu.hourCount) as total)" +
				"from Project as p " +
				"inner join p.taskList as t " +
				"left outer join t.taskUserList as tu " +
				"with (tu.date >= :iniDate " +
				"and tu.date <= :endDate) " +
				"group by p " +
				"order by total desc";
		
		Query query = this.getSession().createQuery(hql);
		query = query.setDate("iniDate", ini).setDate("endDate", end);
		list = query.list();
		
		return list;
	}

	
}
