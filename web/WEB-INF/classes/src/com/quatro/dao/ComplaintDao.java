package com.quatro.dao;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.criterion.Example;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.quatro.model.Complaint;

/**
 *
 * @author JZhang
 */

public class ComplaintDao extends HibernateDaoSupport {
	private static final Log log = LogFactory.getLog(ComplaintDao.class);

	// property constants
	public static final String SOURCE = "source";

	public static final String METHOD = "method";

	public static final String FIRSTNAME = "firstname";

	public static final String LASTNAME = "lastname";

	public static final String STANDARDS = "standards";

	public static final String DESCRIPTION = "description";

	public static final String SATISFIED_WITH_OUTCOME = "satisfiedWithOutcome";

	public static final String STANDARDS_BREACHED = "standardsBreached";

	public static final String OUTSTANDING_ISSUES = "outstandingIssues";

	public static final String STATUS = "status";

	public static final String DURATION = "duration";

	public static final String PERSON1 = "person1";

	public static final String TITLE1 = "title1";

	public static final String PERSON2 = "person2";

	public static final String TITLE2 = "title2";

	public static final String PERSON3 = "person3";

	public static final String TITLE3 = "title3";

	public static final String PERSON4 = "person4";

	public static final String TITLE4 = "title4";

	public static final String CLIENT_ID = "clientId";

	public static final String PROGRAM_ID = "programId";

	
	
   public List getSources() {
	   log.debug("finding all LstComplaintSource instances");
		try {
			String queryString = "from LstComplaintSource";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public List getMethods() {
		log.debug("finding all LstComplaintMethod instances");
		try {
			String queryString = "from LstComplaintMethod";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public List getOutcomes() {
		log.debug("finding all LstComplaintOutcome instances");
		try {
			String queryString = "from LstComplaintOutcome";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public List getSections() {
		log.debug("finding all LstComplaintSection instances");
		try {
			String queryString = "from LstComplaintSection";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public List getSubsections() {
		log.debug("finding all LstComplaintSubsection instances");
		try {
			String queryString = "from LstComplaintSubsection";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}
	
	
	
	public void save(Complaint transientInstance) {
		log.debug("saving Complaint instance");
		try {
			getSession().saveOrUpdate(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(Complaint persistentInstance) {
		log.debug("deleting Complaint instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public Complaint findById(java.lang.Integer id) {
		log.debug("getting Complaint instance with id: " + id);
		try {
			Complaint instance = (Complaint) getSession().get(
					"com.quatro.model.Complaint", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(Complaint instance) {
		log.debug("finding Complaint instance by example");
		try {
			List results = getSession().createCriteria(
					"com.quatro.model.Complaint").add(Example.create(instance))
					.list();
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}

	public List findByProperty(String propertyName, Object value) {
		log.debug("finding Complaint instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from Complaint as model where model."
					+ propertyName + "= ?"
					+ " order by model.id desc";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findBySource(Object source) {
		return findByProperty(SOURCE, source);
	}

	public List findByMethod(Object method) {
		return findByProperty(METHOD, method);
	}

	public List findByFirstname(Object firstname) {
		return findByProperty(FIRSTNAME, firstname);
	}

	public List findByLastname(Object lastname) {
		return findByProperty(LASTNAME, lastname);
	}

	public List findByStandards(Object standards) {
		return findByProperty(STANDARDS, standards);
	}

	public List findByDescription(Object description) {
		return findByProperty(DESCRIPTION, description);
	}

	public List findBySatisfiedWithOutcome(Object satisfiedWithOutcome) {
		return findByProperty(SATISFIED_WITH_OUTCOME, satisfiedWithOutcome);
	}

	public List findByStandardsBreached(Object standardsBreached) {
		return findByProperty(STANDARDS_BREACHED, standardsBreached);
	}

	public List findByOutstandingIssues(Object outstandingIssues) {
		return findByProperty(OUTSTANDING_ISSUES, outstandingIssues);
	}

	public List findByStatus(Object status) {
		return findByProperty(STATUS, status);
	}

	public List findByDuration(Object duration) {
		return findByProperty(DURATION, duration);
	}

	public List findByPerson1(Object person1) {
		return findByProperty(PERSON1, person1);
	}

	public List findByTitle1(Object title1) {
		return findByProperty(TITLE1, title1);
	}

	public List findByPerson2(Object person2) {
		return findByProperty(PERSON2, person2);
	}

	public List findByTitle2(Object title2) {
		return findByProperty(TITLE2, title2);
	}

	public List findByPerson3(Object person3) {
		return findByProperty(PERSON3, person3);
	}

	public List findByTitle3(Object title3) {
		return findByProperty(TITLE3, title3);
	}

	public List findByPerson4(Object person4) {
		return findByProperty(PERSON4, person4);
	}

	public List findByTitle4(Object title4) {
		return findByProperty(TITLE4, title4);
	}

	public List findByClientId(Object clientId) {
		return findByProperty(CLIENT_ID, clientId);
	}

	public List findByProgramId(Object programId) {
		return findByProperty(PROGRAM_ID, programId);
	}

	public List findAll() {
		log.debug("finding all Complaint instances");
		try {
			String queryString = "from Complaint";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public Complaint merge(Complaint detachedInstance) {
		log.debug("merging Complaint instance");
		try {
			Complaint result = (Complaint) getSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(Complaint instance) {
		log.debug("attaching dirty Complaint instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Complaint instance) {
		log.debug("attaching clean Complaint instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
}