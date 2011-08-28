package ar.fi.uba.tempore.dao.util;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.classic.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Example;

public abstract class GenericHibernateDAO<T, ID extends Serializable> {

	private Class<T> persistentClass;
	private Session session;

	@SuppressWarnings("unchecked")
	public GenericHibernateDAO() {
		this.persistentClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
	}
	
	/**
	 * Obtiene la sesion activa
	 * @return
	 */
	protected Session getSession() {
		if (session == null || !session.isOpen()) {
			session = HibernateUtil.getSessionFactory().getCurrentSession();
			//throw new IllegalStateException("Session has not been set on DAO before usage");
		}
		return session;
	}

	/**
	 * Devuelve la clase que se persiste
	 * @return
	 */
	public Class<T> getPersistentClass() {
		return persistentClass;
	}

	/**
	 * Busca por Id
	 * @param id
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public T findById(ID id) {
		T entity = (T) this.getSession().load(getPersistentClass(), id);
		return entity;
	}

	/**
	 * Trae todos los elementos
	 * @return
	 */
	public List<T> findAll() {
		return findByCriteria();
	}

	@SuppressWarnings("unchecked")
	public List<T> findByExample(T exampleInstance) {
		Criteria crit = getSession().createCriteria(getPersistentClass());
		Example example = Example.create(exampleInstance);
//		for (String exclude : excludeProperty) {
//			example.excludeProperty(exclude);
//		}
		crit.add(example);
		return crit.list();
	}

	/**
	 * Guarda o Actualiza
	 * @param entity
	 * @return
	 */
	public T makePersistent(T entity) {
		getSession().saveOrUpdate(entity);
		return entity;
	}

	/**
	 * Delete
	 * @param entity
	 */
	public void delete(T entity) {
		getSession().delete(entity);
	}

	public void flush() {
		getSession().flush();
	}

	public void clear() {
		getSession().clear();
	}

	/**
	 * Use this inside subclasses as a convenience method.
	 */
	@SuppressWarnings("unchecked")
	protected List<T> findByCriteria(Criterion... criterion) {
		Criteria crit = getSession().createCriteria(getPersistentClass());
		for (Criterion c : criterion) {
			crit.add(c);
		}
		return crit.list();
	}
	
}
