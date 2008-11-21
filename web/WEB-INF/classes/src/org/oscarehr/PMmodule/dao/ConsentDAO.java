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

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.oscarehr.PMmodule.model.Consent;
import org.oscarehr.PMmodule.model.ConsentInterview;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.oscarehr.PMmodule.model.ConsentDetail;

import com.quatro.common.KeyConstants;
import com.quatro.util.Utility;

public class ConsentDAO extends HibernateDaoSupport {

    private Logger log = LogManager.getLogger(ConsentDAO.class);
    private MergeClientDao mergeClientDao;

    public void setMergeClientDao(MergeClientDao mergeClientDao) {
		this.mergeClientDao = mergeClientDao;
	}
	public List getConsentsDetailList(Integer clientNo,String providerNo, Integer shelterId){
    	String clientIds = mergeClientDao.getMergedClientIds(clientNo);
    	String progSQL = Utility.getUserOrgQueryString(providerNo, shelterId);
		List results =this.getHibernateTemplate().find("from ConsentDetail a where a.demographicNo in " +clientIds+" and a.programId in " + progSQL + " order by startDate desc");
    	if(results.size()>0){
    		Iterator items=results.iterator();
    		Calendar today =new GregorianCalendar();
    		while(items.hasNext()){
    			ConsentDetail cdObj=(ConsentDetail)items.next();    			
    			if(null==cdObj.getEndDate() || cdObj.getEndDate().before(cdObj.getStartDate())) cdObj.setEndDate(today);
    			if(cdObj.getStatus().equals(KeyConstants.STATUS_ACTIVE)) cdObj.setLnkAction("Withdraw");
    			if(cdObj.getStatus().equals(KeyConstants.STATUS_ACTIVE) && today.after(cdObj.getEndDate())){
    				cdObj.setStatus(KeyConstants.STATUS_EXPIRED);
    				cdObj.setLnkAction("View");
    			}
    			if(cdObj.getStatus().equals(KeyConstants.STATUS_WITHDRAW))cdObj.setLnkAction("View"); 
    			if(cdObj.getStatus().equals(KeyConstants.STATUS_EXPIRED))cdObj.setLnkAction("View");
    			if (cdObj.getStartDate().getTime().getTime()<=System.currentTimeMillis() && cdObj.getEndDate().getTime().getTime()>=System.currentTimeMillis()){
    				cdObj.setLnkAction(KeyConstants.STATUS_WITHDRAW);    				
    			}    			
    		}				
    	}
    	
    	return results;
    }
    public ConsentDetail getConsentDetail(Integer rId){
    	return (ConsentDetail)this.getHibernateTemplate().get(ConsentDetail.class,rId);
    }
    public void saveConsentDetail(ConsentDetail consent) {
        if (consent == null) {
            throw new IllegalArgumentException();
        }

        this.getHibernateTemplate().saveOrUpdate(consent);

        if (log.isDebugEnabled()) {
            log.debug("saveConsentDetail:id=" + consent.getId());
        }
    }

    public List getConsents() {
        List results = this.getHibernateTemplate().find("from Consent");

        if (log.isDebugEnabled()) {
            log.debug("getConsents: # of results=" + results.size());
        }

        return results;
    }

    public Consent getConsent(Integer id) {

        if (id == null || id.intValue() <= 0) {
            throw new IllegalArgumentException();
        }

        Consent result = (Consent)this.getHibernateTemplate().get(Consent.class, id);

        if (log.isDebugEnabled()) {
            log.debug("getConsent: id=" + id + ",found=" + (result != null));
        }
        return result;
    }

    public Consent getConsentByDemographic(Integer demographicNo) {
        if (demographicNo == null || demographicNo.intValue() <= 0) {
            throw new IllegalArgumentException();
        }

        Consent result = null;

        List list = this.getHibernateTemplate().find("from Consent c where c.demographicNo=?", demographicNo);
        if (!list.isEmpty()) result = (Consent)list.get(0);

        if (log.isDebugEnabled()) {
            log.debug("getConsentByDemographic:id=" + demographicNo + ",found=" + (result != null));
        }

        return result;
    }

    public void saveConsent(Consent consent) {
        if (consent == null) {
            throw new IllegalArgumentException();
        }

        this.getHibernateTemplate().saveOrUpdate(consent);

        if (log.isDebugEnabled()) {
            log.debug("saveConsent:id=" + consent.getId());
        }
    }

    public Consent getMostRecentConsent(Integer demographicNo) {
        if (demographicNo == null || demographicNo.intValue() <= 0) {
            throw new IllegalArgumentException();
        }
        Consent result = null;

        List results = this.getHibernateTemplate().find("from Consent c where c.demographicNo = ? order by c.dateSigned DESC", demographicNo);
        if (!results.isEmpty()) {
            result = (Consent)results.get(0);
        }

        if (log.isDebugEnabled()) {
            log.debug("getMostRecentConsent:id=" + demographicNo + ",found=" + (result != null));
        }

        return result;
    }

    public void saveConsentInterview(ConsentInterview consent) {
        if (consent == null) {
            throw new IllegalArgumentException();
        }

        this.getHibernateTemplate().save(consent);

        if (log.isDebugEnabled()) {
            log.debug("saveConsentInterview: " + consent.getId());
        }
    }

    public List getConsentInterviews() {

        List results = this.getHibernateTemplate().find("from ConsentInterview");

        if (log.isDebugEnabled()) {
            log.debug("getConsentInterviews: # of results=" + results.size());
        }
        return results;
    }

    public ConsentInterview getConsentInterview(Integer id) {
        if (id == null || id.intValue() <= 0) {
            throw new IllegalArgumentException();
        }

        ConsentInterview result = (ConsentInterview)this.getHibernateTemplate().get(ConsentInterview.class, id);

        if (log.isDebugEnabled()) {
            log.debug("getConsent: id=" + id + ",found=" + (result != null));
        }

        return result;
    }

    public ConsentInterview getConsentInterviewByDemographicNo(Integer demographicNo) {
        if (demographicNo == null || demographicNo.intValue() <= 0) {
            throw new IllegalArgumentException();
        }

        ConsentInterview result = null;
        List results = this.getHibernateTemplate().find("from ConsentInterview ci where ci.demographicNo = ?", demographicNo);

        if (!results.isEmpty()) {
            result = (ConsentInterview)results.get(0);
        }

        if (log.isDebugEnabled()) {
            log.debug("getConsentBydemographicNo: demographicNo=" + demographicNo + ",found=" + (result != null));
        }

        return result;
    }
}
