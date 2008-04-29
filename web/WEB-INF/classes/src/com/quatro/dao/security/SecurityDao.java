package com.quatro.dao.security;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.criterion.Example;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.quatro.model.security.Security;

/**
 * 
 * @author JZhang
 *
 */

public class SecurityDao extends HibernateDaoSupport {
	private static final Log log = LogFactory.getLog(SecurityDao.class);
	// property constants
	public static final String USER_NAME = "userName";
	public static final String PASSWORD = "password";
	public static final String PIN = "pin";
	public static final String _BREMOTELOCKSET = "BRemotelockset";
	public static final String _BLOCALLOCKSET = "BLocallockset";
	public static final String _BEXPIRESET = "BExpireset";

	
	
	public List getSecuserroles() {
		log.debug("All User list");
		try {
			// String queryString = "select securityNo, userName, providerNo from Security";
			
			String queryString =  "select sur.providerNo, sur.roleName, sur.orgcd"
				+ " from Secuserrole sur";
				//+ " where s.providerNo = p.providerNo";
				//+ " and p.providerNo = sur.providerNo";
			
			/* SQL:
			select s.security_No, s.user_Name, p.last_Name, p.first_Name, sur.role_Name, sur.orgcd 
			from Security s, Provider p LEFT OUTER JOIN secuserrole sur ON p.provider_No = sur.provider_No
			where s.provider_No = p.provider_No
			*/
			return this.getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find All User list failed", re);
			throw re;
		}
	}
	
	public List getUsers() {
		log.debug("All User list");
		try {
			// String queryString = "select securityNo, userName, providerNo from Security";
			String queryString =  "select s.securityNo, s.userName, p.lastName, p.firstName, p.providerNo"
				+ " from Security s, SecProvider p"
				+ " where s.providerNo = p.providerNo";
				
			return this.getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find All User list failed", re);
			throw re;
		}
	}
	
	public void save(Security transientInstance) {
		log.debug("saving Security instance");
		try {
			this.getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(Security persistentInstance) {
		log.debug("deleting Security instance");
		try {
			this.getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public Security findById(java.lang.Integer id) {
		log.debug("getting Security instance with id: " + id);
		try {
			Security instance = (Security) this.getHibernateTemplate().get(
					"com.quatro.model.security.Security", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(Security instance) {
		log.debug("finding Security instance by example");
		try {
			List results =  getSession().createCriteria(
					"com.quatro.model.security.Security").add(
					Example.create(instance)).list();
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}

	public List findByProperty(String propertyName, Object value) {
		log.debug("finding Security instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from Security as model where model."
					+ propertyName + "= ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByUserName(Object userName) {
		return findByProperty(USER_NAME, userName);
	}

	public List findByPassword(Object password) {
		return findByProperty(PASSWORD, password);
	}

	public List findByPin(Object pin) {
		return findByProperty(PIN, pin);
	}

	public List findByBRemotelockset(Object BRemotelockset) {
		return findByProperty(_BREMOTELOCKSET, BRemotelockset);
	}

	public List findByBLocallockset(Object BLocallockset) {
		return findByProperty(_BLOCALLOCKSET, BLocallockset);
	}

	public List findByBExpireset(Object BExpireset) {
		return findByProperty(_BEXPIRESET, BExpireset);
	}

	public List findAll() {
		log.debug("finding all Security instances");
		try {
			String queryString = "from Security";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public Security merge(Security detachedInstance) {
		log.debug("merging Security instance");
		try {
			Security result = (Security) getSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(Security instance) {
		log.debug("attaching dirty Security instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Security instance) {
		log.debug("attaching clean Security instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
}