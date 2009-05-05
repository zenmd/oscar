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
* Toronto, Ontario, Canada  - UPDATED: Quatro Group 2008/2009
*/

package org.caisi.dao;

import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.caisi.model.SystemMessage;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

public class SystemMessageDAO extends HibernateDaoSupport  {

	private static Logger log = LogManager.getLogger(SystemMessageDAO.class);
	
	public SystemMessage getMessage(Integer id) {
		return (SystemMessage)this.getHibernateTemplate().get(SystemMessage.class,id);
	}
	
	public List getMessages() {
		return this.getHibernateTemplate().find("from SystemMessage sm order by sm.expiry_date desc");
	}
	
	public void saveMessage(SystemMessage mesg) {
		this.getHibernateTemplate().saveOrUpdate(mesg);
	}
	
	public List getActiveMessages()
	{
		return this.getHibernateTemplate().find("from SystemMessage sm where sm.expiry_date >= now() order by sm.expiry_date desc");
	}
	public int getActiveMessageCount()
	{
        String query = "select count(*) from system_message t where where sm.expiry_date >= now()";
        Integer count = (Integer) getHibernateTemplate().find(query).get(0);
        return count.intValue();
	}
}
