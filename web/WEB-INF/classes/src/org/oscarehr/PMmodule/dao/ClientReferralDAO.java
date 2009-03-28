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

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.criterion.Expression;
import org.oscarehr.PMmodule.model.Admission;
import org.oscarehr.PMmodule.model.ClientReferral;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import com.quatro.common.KeyConstants;

import com.quatro.common.KeyConstants;
import com.quatro.util.Utility;

public class ClientReferralDAO extends HibernateDaoSupport {

    private Logger log = LogManager.getLogger(getClass());
    private MergeClientDao mergeClientDao;
    public void setMergeClientDao(MergeClientDao mergeClientDao) {
		this.mergeClientDao = mergeClientDao;
	}
    
/*
	public List getReferrals() {
        List results = this.getHibernateTemplate().find("from ClientReferral");

        if (log.isDebugEnabled()) {
            log.debug("getReferrals: # of results=" + results.size());
        }

        return results;
    }
*/
    
    public List getReferrals(Integer clientId,String providerNo,Integer shelterId) {

        if (clientId == null || clientId.intValue() <= 0) {
            throw new IllegalArgumentException();
        }
        String clientIds =mergeClientDao.getMergedClientIds(clientId);
        String sql=	"from ClientReferral cr where cr.clientId in " +clientIds 
        	+" and (cr.programId in " +Utility.getUserOrgQueryString(providerNo,shelterId);
        sql+=" or cr.fromProgramId in " +Utility.getUserOrgQueryString(providerNo,shelterId)+")";
        sql+=" order by cr.referralDate desc";
        List results = this.getHibernateTemplate().find(sql);
        if (log.isDebugEnabled()) {
            log.debug("getReferrals: clientId=" + clientId + ",# of results=" + results.size());
        }
        return results;
    }

    public List getManualReferrals(Integer clientId,String providerNo,Integer shelterId) {

        if (clientId == null || clientId.intValue() <= 0) {
            throw new IllegalArgumentException();
        }
        String clientIds =mergeClientDao.getMergedClientIds(clientId);
        String sql=	"from ClientReferral cr where cr.clientId in " +clientIds 
        	+" and (cr.programId in " +Utility.getUserOrgQueryString(providerNo,shelterId);
        sql+=" or cr.fromProgramId in " +Utility.getUserOrgQueryString(providerNo,shelterId)+")" +
             " and cr.autoManual='M'";
        sql+=" order by cr.referralDate desc";
        List results = this.getHibernateTemplate().find(sql);
        if (log.isDebugEnabled()) {
            log.debug("getReferrals: clientId=" + clientId + ",# of results=" + results.size());
        }
        return results;
    }
    
    public List getReferralsByIntakeId(Integer intakeId) {
        String sSQL="from ClientReferral cr where cr.fromIntakeId = ?";
        List results = this.getHibernateTemplate().find(sSQL, intakeId);
        return results;
    }
    
    public ClientReferral getReferralAutoByIntakeId(Integer intakeId) {
        String sSQL="from ClientReferral cr where cr.fromIntakeId = ? and cr.autoManual='A'";
        List results = this.getHibernateTemplate().find(sSQL, intakeId);
        if(results.size()==0) return null;
        return (ClientReferral)results.get(0);
    }
    
    public ClientReferral getReferralsByProgramId(Integer clientId, Integer programId) {

        if (clientId == null || clientId.intValue() <= 0) {
            throw new IllegalArgumentException();
        }
        if (programId == null || programId.intValue() <= 0) {
            throw new IllegalArgumentException();
        }
        String clientIds =mergeClientDao.getMergedClientIds(clientId);
        String sSQL="from ClientReferral cr where cr.clientId in " +clientIds+" and cr.programId =?";
        List results = this.getHibernateTemplate().find(sSQL, programId);
        if(results.size()==0) return null;
        return (ClientReferral)results.get(0);
    }
/*    
    public List getReferralsByFacility(Integer clientId, Integer shelterId) {

        if (clientId == null || clientId.intValue() <= 0) {
            throw new IllegalArgumentException();
        }
        if (shelterId == null || shelterId.intValue() < 0) {
            throw new IllegalArgumentException();
        }
        String clientIds =mergeClientDao.getMergedClientIds(clientId);
        String sSQL="from ClientReferral cr where cr.clientId in " + clientIds+
                    " and ( (cr.shelterId=?) or (cr.programId in (select s.id from Program s where s.shelterId=? or s.shelterId is null)))";
        List results = this.getHibernateTemplate().find(sSQL, new Object[] {shelterId, shelterId });
//        		"from ClientReferral cr where cr.ClientId = ?", clientId);

        if (log.isDebugEnabled()) {
            log.debug("getReferralsByFacility: clientId=" + clientId + ",# of results=" + results.size());
        }
       // results = displayResult(results);
        return results;
    }
*/    
    // [ 1842692 ] RFQ Feature - temp change for pmm referral history report
    // - suggestion: to add a new field to the table client_referral (Referring program/agency)
    public List displayResult(List lResult) {
    	List ret = new ArrayList();
    	//ProgramDao pd = new ProgramDao();
    	//AdmissionDao ad = new AdmissionDao();
    	
//    	for(Object element : lResult) {
    	for(int i=0;i<lResult.size();i++) {
//    		ClientReferral cr = (ClientReferral) element;
    		ClientReferral cr = (ClientReferral) lResult.get(i);
    		System.out.println(cr.getId() + "|" + cr.getProgramName() + "|" + cr.getClientId());

            ClientReferral result = null;
            List results = this.getHibernateTemplate().find("from ClientReferral r where r.clientId = ? and r.Id < ? order by r.Id desc", new Object[] {cr.getClientId(), cr.getId()});

            // temp - completionNotes/Referring program/agency, notes/External
        	String completionNotes = "";
        	String notes = "";
            if (!results.isEmpty()) {
                result = (ClientReferral)results.get(0);
        		System.out.println("--" + result.getId() + "|" + result.getProgramName() + "|" + result.getClientId());
            	completionNotes = result.getProgramName();
            	notes = isExternalProgram(result.getProgramId()) ? "Yes" : "No";
        		System.out.println("--" + result.getProgramId().toString());
            } else {
            	// get program from table admission
        		System.out.println("--" + cr.getClientId());
            	List lr = getAdmissions(cr.getClientId());
            	if(lr!=null && lr.iterator().hasNext()){
            	Admission admission = (Admission) lr.get(lr.size() - 1);
            	completionNotes = admission.getProgramName(); 
            	notes = isExternalProgram(admission.getProgramId()) ? "Yes" : "No";
            	}
            }
            
            // set the values for added report fields
            cr.setCompletionNotes(completionNotes);
            cr.setNotes(notes);
            
        	ret.add(cr);
    	}
    	
    	return ret;
    }
    
    private boolean isExternalProgram(Integer programId) {
		boolean result = false;

		if (programId == null || programId.intValue() <= 0) {
			throw new IllegalArgumentException();
		}

		String queryStr = "FROM Program p WHERE p.id = ? AND p.type = 'external'";
		List rs = getHibernateTemplate().find(queryStr, programId);

		if (!rs.isEmpty()) {
			result = true;
		}

		if (log.isDebugEnabled()) {
			log.debug("isCommunityProgram: id=" + programId + " : " + result);
		}

		return result;
	}
	
    private List getAdmissions(Integer demographicNo) {
        if (demographicNo == null || demographicNo.intValue() <= 0) {
            throw new IllegalArgumentException();
        }
        String clientIds =mergeClientDao.getMergedClientIds(demographicNo);
        String queryStr = "FROM Admission a WHERE a.clientId in "+clientIds+" ORDER BY a.admissionDate DESC";
        List rs = getHibernateTemplate().find(queryStr);
        return rs;
    }
    // end of change

    public List getActiveReferrals(Integer clientId,String providerNo,Integer shelterId) {

        if (clientId == null || clientId.intValue() <= 0) {
            throw new IllegalArgumentException();
        }
        String clientIds =mergeClientDao.getMergedClientIds(clientId);
        String sql=	"from ClientReferral cr where cr.status='" + KeyConstants.STATUS_PENDING + "'" + 
        		" and cr.clientId in " +clientIds 
        	+" and (cr.programId in " +Utility.getUserOrgQueryString(providerNo,shelterId);
        sql+=" or cr.fromProgramId in " +Utility.getUserOrgQueryString(providerNo,shelterId)+")";
        List results = this.getHibernateTemplate().find(sql);
        if (log.isDebugEnabled()) {
            log.debug("getReferrals: clientId=" + clientId + ",# of results=" + results.size());
        }
        return results;
    }

    public List getActiveManualReferrals(Integer clientId,String providerNo,Integer shelterId) {

        if (clientId == null || clientId.intValue() <= 0) {
            throw new IllegalArgumentException();
        }
        String clientIds =mergeClientDao.getMergedClientIds(clientId);
        String sql=	"from ClientReferral cr where cr.status='" + KeyConstants.STATUS_PENDING + "'" + 
        		" and cr.clientId in " +clientIds 
        	+" and (cr.programId in " +Utility.getUserOrgQueryString(providerNo,shelterId);
        sql+=" or cr.fromProgramId in " +Utility.getUserOrgQueryString(providerNo,shelterId)+")";
        sql +=" and cr.autoManual='" +  KeyConstants.MANUAL + "'";
        List results = this.getHibernateTemplate().find(sql);
        return results;
    }
    
    public ClientReferral getClientReferral(Integer id) {
        if (id == null || id.intValue()<= 0) {
            throw new IllegalArgumentException();
        }

        ClientReferral result = (ClientReferral)this.getHibernateTemplate().get(ClientReferral.class, id);

        if (log.isDebugEnabled()) {
            log.debug("getClientReferral: id=" + id + ",found=" + (result != null));
        }

        return result;
    }
    public void delete(ClientReferral refValue){
    	getHibernateTemplate().delete(refValue);
    }
    public void saveClientReferral(ClientReferral referral) {
        if (referral == null) {
            throw new IllegalArgumentException();
        }

        this.getHibernateTemplate().saveOrUpdate(referral);

        if (log.isDebugEnabled()) {
            log.debug("saveClientReferral: id=" + referral.getId());
        }

    }

    public List search(ClientReferral referral) {
        Criteria criteria = getSession().createCriteria(ClientReferral.class);

        if (referral != null && referral.getProgramId().intValue() > 0) {
            criteria.add(Expression.eq("ProgramId", referral.getProgramId()));
        }

        return criteria.list();
    }
}
