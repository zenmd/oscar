package org.oscarehr.PMmodule.dao;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.oscarehr.PMmodule.model.RoomDemographicHistorical;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.quatro.dao.security.SecroleDao;

/**
 *
 * @author JZhang
 */


public class RoomDemographicHistoricalDao extends HibernateDaoSupport {
	   private Log log = LogFactory.getLog(SecroleDao.class);

//	    public List getRoles() {
//	        List results = this.getHibernateTemplate().find("from Secrole r");
//
//	        log.debug("getRoles: # of results=" + results.size());
//
//	        return results;
//	    }
//
//	    public Secrole getRole(Integer id) {
//	        if (id == null || id.intValue() <= 0) {
//	            throw new IllegalArgumentException();
//	        }
//
//	        Secrole result = (Secrole) this.getHibernateTemplate().get(Secrole.class, id);
//
//	        log.debug("getRole: id=" + id + ",found=" + (result != null));
//
//	        return result;
//	    }
//	    
//	    public Secrole getRoleByName(String roleName) {
//	    	Secrole result = null;
//	    	if (roleName == null || roleName.length() <= 0) {
//	            throw new IllegalArgumentException();
//	        }
//
//	        List lst = this.getHibernateTemplate().find("from Secrole r where r.roleName='" + roleName + "'");
//	        if(lst != null && lst.size() > 0)
//	        	result = (Secrole) lst.get(0);
//
//	        log.debug("getRoleByName: roleName=" + roleName + ",found=" + (result != null));
//
//	        return result;
//	    }
//
//	    
//	    public List getDefaultRoles() {
//	        return this.getHibernateTemplate().find("from Secrole r where r.userDefined=0");
//	    }
	   
	   public RoomDemographicHistorical findById(Integer roomId, Integer admissionId) {
			log.debug("getting RoomDemographicHistorical instance with id: " + roomId + "/" + admissionId);
			RoomDemographicHistorical history = null;
			try {
				List lst = this.getHibernateTemplate().find("from RoomDemographicHistorical r where r.roomId='" + roomId + "'" + " and r.admissionId='" + admissionId + "'");
				if(lst != null && lst.size() > 0)
					history = (RoomDemographicHistorical) lst.get(0);
				return history;
			} catch (RuntimeException re) {
				log.error("get failed", re);
				throw re;
			}
		}
	   
	    public void saveOrUpdate(RoomDemographicHistorical history) {
	        if (history == null) {
	            throw new IllegalArgumentException();
	        }
	        try{
		        getHibernateTemplate().saveOrUpdate(history);
		    } catch (RuntimeException re) {
				log.error("save/update failed", re);
				throw re;
			}
	        if (log.isDebugEnabled()) {
	            log.debug("RoomDemographicHistoricalDao : save: RoomID=" + history.getRoomId());
	        }
	    }
	    
}