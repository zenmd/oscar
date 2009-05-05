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

package org.oscarehr.PMmodule.dao;

import java.util.List;

import org.oscarehr.PMmodule.model.FacilityMessage;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import com.quatro.util.Utility;

public class FacilityMessageDAO extends HibernateDaoSupport  {
	public FacilityMessage getMessage(Integer id) {
		return (FacilityMessage)this.getHibernateTemplate().get(FacilityMessage.class,id);
	}
	
	public List getMessages() {
		return this.getHibernateTemplate().find("from FacilityMessage fm order by fm.expiry_date desc");
	}
	
	public void saveMessage(FacilityMessage mesg) {
		this.getHibernateTemplate().saveOrUpdate(mesg);
	}

	public List getMessagesByShelterId(String providerNo,Integer shelterId) {
		String orgSql = Utility.getUserOrgStringByFac(providerNo, shelterId);
		String sql;
		sql = "from FacilityMessage fm where fm.expiry_date >=sysdate and fm.facilityId in " + orgSql + "  order by fm.facilityId, fm.expiry_date desc";
		return this.getHibernateTemplate().find(sql);
	}

	public List getActiveMessagesByFacilityId(String providerNo,Integer facilityId) {
		String orgSql = Utility.getUserOrgStringByFac(providerNo, null);
		String sql;
		sql = "from FacilityMessage fm where fm.expiry_date >=sysdate and fm.facilityId =?" + " and fm.facilityId in " + orgSql + " order by fm.facilityId, fm.expiry_date desc";
		return this.getHibernateTemplate().find(sql, facilityId);
	}
	public List getMessagesByFacilityId(String providerNo,Integer facilityId) {
		String orgSql = Utility.getUserOrgStringByFac(providerNo, null);
		String sql;
		sql = "from FacilityMessage fm where fm.facilityId =?" + " and fm.facilityId in " + orgSql + " order by fm.facilityId, fm.expiry_date desc";
		return this.getHibernateTemplate().find(sql, facilityId);
	}
}
