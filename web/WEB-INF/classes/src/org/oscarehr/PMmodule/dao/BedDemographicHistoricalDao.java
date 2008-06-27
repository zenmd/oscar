package org.oscarehr.PMmodule.dao;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.oscarehr.PMmodule.model.BedDemographicHistorical;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.quatro.dao.security.SecroleDao;

/**
 *
 * @author JZhang
 */


public class BedDemographicHistoricalDao extends HibernateDaoSupport {
	   private Log log = LogFactory.getLog(SecroleDao.class);

	   public BedDemographicHistorical findById(Integer bedId, Integer admissionId) {
			log.debug("getting BedDemographicHistorical instance with id: " + bedId + "/" + admissionId);
			BedDemographicHistorical history = null;
			try {
				List lst = this.getHibernateTemplate().find("from BedDemographicHistorical r where r.bedId='" + bedId + "'" + " and r.admissionId='" + admissionId + "'");
				if(lst != null && lst.size() > 0)
					history = (BedDemographicHistorical) lst.get(0);
				return history;
			} catch (RuntimeException re) {
				log.error("get failed", re);
				throw re;
			}
		}
	   
	    public void saveOrUpdate(BedDemographicHistorical history) {
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
	            log.debug("BedDemographicHistoricalDao : save: BedID=" + history.getBedId());
	        }
	    }
	    
}