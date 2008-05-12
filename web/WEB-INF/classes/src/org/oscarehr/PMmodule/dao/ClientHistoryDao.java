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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.indivo.xml.phr.dischargesummary.DischargeSummary;
import org.oscarehr.PMmodule.model.ClientHistory;
import org.oscarehr.PMmodule.model.ClientReferral;
import org.oscarehr.PMmodule.model.Program;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import java.util.Calendar;
public class ClientHistoryDao extends HibernateDaoSupport {

    private Log log = LogFactory.getLog(getClass());

    public List getClientHistories(Integer clientId, String providerNo) {
    	
        Criteria criteria = getSession().createCriteria(ClientHistory.class);
        String sql = "'P' || program_id in (select a.code from lst_orgcd a, secuserrole b where a.fullcode like b.orgcd || '%' and b.provider_no='" + providerNo + "')";
        criteria.add(Restrictions.sqlRestriction(sql));
        criteria.add(Restrictions.eq("ClientId", clientId));

        List results = criteria.list();
        
        if (log.isDebugEnabled()) {
            log.debug("getReferrals: # of results=" + results.size());
        }

        return results;
    }

    public void saveClientHistory(ClientHistory history) {
        if (history == null) {
            throw new IllegalArgumentException();
        }

        this.getHibernateTemplate().saveOrUpdate(history);

        if (log.isDebugEnabled()) {
            log.debug("saveClientReferral: id=" + history.getId());
        }

    }
    public void saveClientHistory(ClientReferral referral) {
        if (referral == null) {
            return;
        }
        ClientHistory history = new ClientHistory();
        history.setClientId(Integer.valueOf(referral.getClientId().toString()));
        history.setAction("REFERRAL");
        history.setActionDate(referral.getReferralDate());
        history.setHistoryDate(Calendar.getInstance().getTime());
        history.setNotes(referral.getNotes());
        history.setProgramId(Integer.valueOf(referral.getProgramId().toString()));
        history.setProviderNo(referral.getProviderNo());
       
        this.getHibernateTemplate().saveOrUpdate(history);

        if (log.isDebugEnabled()) {
            log.debug("saveClientReferral: id=" + history.getId());
        }

    }
}
