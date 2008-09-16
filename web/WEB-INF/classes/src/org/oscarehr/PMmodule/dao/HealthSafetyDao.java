/*
* 
* Copyright (c) 2001-2002. Centre for Research on Inner City Health, St. Michael's Hospital, Toronto. All Rights Reserved. *
* This software is published under the GPL GNU General Public License. 
* This program is free software; you can redistribute it and/or 
* modify it under the terms of the GNU General Public License 
* as published by the Free Software Foundation; either version 2 
* of the License, or (at your option) any later version. * 
* This program is distributed in the hope that it will be useful, 
* but WITHOUT ANY WARRANTY; without even the implied warranty of 
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the 
* GNU General Public License for more details. * * You should have received a copy of the GNU General Public License 
* along with this program; if not, write to the Free Software 
* Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA 02111-1307, USA. * 
* 
* <OSCAR TEAM>
* 
* This software was written for 
* Centre for Research on Inner City Health, St. Michael's Hospital, 
* Toronto, Ontario, Canada 
*/

package org.oscarehr.PMmodule.dao;

import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.oscarehr.PMmodule.model.HealthSafety;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

public class HealthSafetyDao extends HibernateDaoSupport {

    private Logger log = LogManager.getLogger(HealthSafetyDao.class);
    private MergeClientDao mergeClientDao;
    public void setMergeClientDao(MergeClientDao mergeClientDao) {
		this.mergeClientDao = mergeClientDao;
	}

	public HealthSafety getHealthSafetyByDemographic(Integer demographicNo) {
        if (demographicNo == null || demographicNo.intValue() <= 0) {
            throw new IllegalArgumentException();
        }

        HealthSafety result = null;
        String clientIds =mergeClientDao.getMergedClientIds(demographicNo);
        List list = this.getHibernateTemplate().find("from HealthSafety c where c.demographicNo in "+clientIds+" order by c.updateDate desc");
        if (!list.isEmpty()) result = (HealthSafety)list.get(0);

        if (log.isDebugEnabled()) {
            log.debug("getHealthSafetyByDemographic:id=" + demographicNo + ",found=" + (result != null));
        }

        return result;
    }
	public void deleteHealthSafetyByDemographic(Integer demographicNo) {
        if (demographicNo == null || demographicNo.intValue() <= 0) {
            throw new IllegalArgumentException();
        }
        //hack: this is for preparing a history of HS records. Delete all for now 
        while(true)
        {
        	HealthSafety hs = getHealthSafetyByDemographic(demographicNo);
        	if (hs == null) break;
        	getHibernateTemplate().delete(hs);
        }
        
        if (log.isDebugEnabled()) {
            log.debug("delteHealthSafetyByDemographic:id=" + demographicNo);
        }
        return;
    }

    public void saveHealthSafetyByDemographic(HealthSafety healthsafety) {
        if (healthsafety == null) {
            throw new IllegalArgumentException();
        }

        this.getHibernateTemplate().save(healthsafety);

        if (log.isDebugEnabled()) {
            log.debug("saveHealthSafetyByDemographic:id=" + healthsafety.getId());
        }
    }

}
