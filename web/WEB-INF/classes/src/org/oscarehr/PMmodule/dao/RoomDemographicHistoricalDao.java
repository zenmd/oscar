/*******************************************************************************
 * Copyright (c) 2008, 2009 Quatro Group Inc. and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU General Public License
 * which accompanies this distribution, and is available at
 * Free Software Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA 02111-1307, USA.
 *
 * Contributors:
 *     <Quatro Group Software Systems inc.>  <OSCAR Team>
 *******************************************************************************/
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

		public RoomDemographicHistorical getLatestRoomDemographicHistory(Integer admissionId){
			if(admissionId==null)  return null;
			RoomDemographicHistorical history = null;
			List lst = this.getHibernateTemplate().find("from RoomDemographicHistorical r where r.admissionId='" + admissionId + "' order by r.usageStart desc");
			if(lst != null && lst.size() > 0) history = (RoomDemographicHistorical) lst.get(0);
			return history;
		}
	    
}