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

package com.quatro.dao.security;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.quatro.model.security.Secobjprivilege;

public class SecobjprivilegeDao extends HibernateDaoSupport {

    private Log log = LogFactory.getLog(SecobjprivilegeDao.class);

  
    public void save(Secobjprivilege secobjprivilege) {
        if (secobjprivilege == null) {
            throw new IllegalArgumentException();
        }
        
        getHibernateTemplate().saveOrUpdate(secobjprivilege);

        if (log.isDebugEnabled()) {
            log.debug("SecobjprivilegeDao : save: " + secobjprivilege.getRoleusergroup() + ":" + secobjprivilege.getObjectname());
        }
    }
    
    public String getFunctionDesc(String function_code) {
		try {
			String queryString = "select description from Secobjectname obj where obj.objectname='" + function_code+"'";
			
			List lst = getHibernateTemplate().find(queryString);
			if(lst.size()>0 && lst.get(0)!=null)
				return lst.get(0).toString();
			else
				return "";
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}
	public String getAccessDesc(String accessType_code) {
		try {
			String queryString = "select description from Secprivilege obj where obj.privilege='" + accessType_code +"'";
			
			List lst = getHibernateTemplate().find(queryString);
			if(lst.size()>0 && lst.get(0)!=null)
				return lst.get(0).toString();
			else
				return "";
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}
    public List getFunctions(String roleName) {
        if (roleName == null) {
            throw new IllegalArgumentException();
        }
        
        List result = findByProperty("roleusergroup", roleName);

        if (log.isDebugEnabled()) {
            log.debug("SecobjprivilegeDao : getFunctions: ");
        }
        return result;
    }
    public List findByProperty(String propertyName, Object value) {
		log.debug("finding Secobjprivilege instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from Secobjprivilege as model where model."
					+ propertyName + "= ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}
}
