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
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.ListIterator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.oscarehr.PMmodule.model.Admission;
import org.oscarehr.PMmodule.model.AdmissionSearchBean;
import org.oscarehr.PMmodule.model.ClientReferral;
import org.oscarehr.PMmodule.model.ProgramQueue;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import com.quatro.common.KeyConstants;

public class AdmissionDao extends HibernateDaoSupport {

    private Log log = LogFactory.getLog(AdmissionDao.class);
    
    public List getAdmissions_archiveView(Integer programId, Integer demographicNo) {
        Admission admission = null;

        if (programId == null || programId <= 0) {
            throw new IllegalArgumentException();
        }

        if (demographicNo == null || demographicNo <= 0) {
            throw new IllegalArgumentException();
        }

        String queryStr = "FROM Admission a WHERE admission_status='"+KeyConstants.INTAKE_STATUS_DISCHARGED+"' and a.programId=? AND a.clientId=? order by am_id DESC";
        List rs = getHibernateTemplate().find(queryStr, new Object[] { programId, demographicNo });
        /*
        if (!rs.isEmpty()) {
            admission = ((Admission) rs.get(0));
        }
         */
        if (log.isDebugEnabled()) {
            log.debug((admission != null) ? "getAdmission:" + admission.getId() : "getAdmission: not found");
        }

        return rs;
    }
    public List<Admission> getAdmissionList(Integer intakeId,String status){
    	 
    	String queryStr = "FROM Admission a WHERE a.intakeId=? AND a.admissionStatus=?";
         return this.getHibernateTemplate().find(queryStr, new Object[] { intakeId, status});       
    }
    public Admission getAdmission(Integer programId, Integer demographicNo) {
        Admission admission = null;

        if (programId == null || programId <= 0) {
            throw new IllegalArgumentException();
        }

        if (demographicNo == null || demographicNo <= 0) {
            throw new IllegalArgumentException();
        }

        String queryStr = "FROM Admission a WHERE a.programId=? AND a.clientId=?";
        List rs = getHibernateTemplate().find(queryStr, new Object[] { programId, demographicNo });

        if (!rs.isEmpty()) {
            admission = ((Admission) rs.get(0));
        }

        if (log.isDebugEnabled()) {
            log.debug((admission != null) ? "getAdmission:" + admission.getId() : "getAdmission: not found");
        }

        return admission;
    }

    public Admission getCurrentAdmission(Integer demographicNo) {
        Admission admission = null;

        if (demographicNo == null || demographicNo <= 0) {
            throw new IllegalArgumentException();
        }

        String queryStr = "FROM Admission a WHERE a.clientId=? AND a.admissionStatus='"+KeyConstants.INTAKE_STATUS_ADMITTED+"' ORDER BY a.admissionDate DESC";
        List rs = getHibernateTemplate().find(queryStr, new Object[] {demographicNo });

        if (!rs.isEmpty()) {
            admission = ((Admission) rs.get(0));
        }

        if (log.isDebugEnabled()) {
            log.debug((admission != null) ? "getCurrentAdmission:" + admission.getId() : "getCurrentAdmission: not found");
        }

        return admission;
    }

    public Admission getCurrentAdmission(Integer programId, Integer demographicNo) {
        Admission admission = null;

        if (demographicNo == null || demographicNo <= 0) {
            throw new IllegalArgumentException();
        }
        if (programId == null || programId <= 0) {
            return getCurrentAdmission(demographicNo);
        }

        String queryStr = "FROM Admission a WHERE a.programId=? AND a.clientId=? AND a.admissionStatus='"+KeyConstants.INTAKE_STATUS_ADMITTED+"' ORDER BY a.admissionDate DESC";
        List rs = getHibernateTemplate().find(queryStr, new Object[] { programId, demographicNo });

        if (!rs.isEmpty()) {
            admission = ((Admission) rs.get(0));
        }

        if (log.isDebugEnabled()) {
            log.debug((admission != null) ? "getCurrentAdmission:" + admission.getId() : "getCurrentAdmission: not found");
        }

        return admission;
    }

    public List getAdmissions() {
        String queryStr = "FROM Admission a ORDER BY a.AdmissionDate DESC";
        List rs = getHibernateTemplate().find(queryStr);

        if (log.isDebugEnabled()) {
            log.debug("getAdmissions # of admissions: " + rs.size());
        }

        return rs;
    }

    public List getAdmissions(Integer demographicNo) {
        if (demographicNo == null || demographicNo <= 0) {
            throw new IllegalArgumentException();
        }

        String queryStr = "FROM Admission a WHERE a.clientId=? ORDER BY a.admissionDate DESC";
        List rs = getHibernateTemplate().find(queryStr, new Object[] { demographicNo });

        if (log.isDebugEnabled()) {
            log.debug("getAdmissions for clientId " + demographicNo + ", # of admissions: " + rs.size());
        }

        return rs;
    }

   
   public List getAdmissionsByFacility(Integer demographicNo, Integer facilityId) {
        if (demographicNo == null || demographicNo <= 0) {
            throw new IllegalArgumentException();
        }

        String queryStr = "FROM Admission a WHERE a.clientId=? and a.programId in " +
           "(select s.id from Program s where s.facilityId=? or s.facilityId is null) ORDER BY a.admissionDate DESC";
        List rs = getHibernateTemplate().find(queryStr, new Object[] { demographicNo,  facilityId });

        if (log.isDebugEnabled()) {
            log.debug("getAdmissionsByFacility for clientId " + demographicNo + ", # of admissions: " + rs.size());
        }

        return rs;
    }
   
   
   
    public List<Admission> getAdmissionsByProgramId(Integer programId, Boolean automaticDischarge, Integer days) {
        if (programId == null || programId <= 0) {
            throw new IllegalArgumentException();
        }
       
        //today's date
        Calendar calendar = Calendar.getInstance();
     
        //today's date -  days
        calendar.add(Calendar.DAY_OF_YEAR, days);
        
        Date sevenDaysAgo = calendar.getTime();
        
        String queryStr = "FROM Admission a WHERE a.programId=? and a.automaticDischarge=? and a.dischargeDate>= ? ORDER BY a.dischargeDate DESC";

        @SuppressWarnings("unchecked")
        List<Admission> rs = getHibernateTemplate().find(queryStr, new Object[] { programId, automaticDischarge, sevenDaysAgo });

        if (log.isDebugEnabled()) {
            log.debug("getAdmissions for programId " + programId + ", # of admissions: " + rs.size());
        }

        return rs;
    }
    
    public List getCurrentAdmissions(Integer demographicNo) {
        if (demographicNo == null || demographicNo <= 0) {
            throw new IllegalArgumentException();
        }

        String queryStr = "FROM Admission a WHERE a.clientId=? AND a.admissionStatus='"+KeyConstants.INTAKE_STATUS_ADMITTED+"' ORDER BY a.admissionDate DESC";
        List rs = getHibernateTemplate().find(queryStr, new Object[] { demographicNo });

        if (log.isDebugEnabled()) {
            log.debug("getCurrentAdmissions for clientId " + demographicNo + ", # of admissions: " + rs.size());
        }

        return rs;

    }

    public List<Admission> getCurrentAdmissionsByFacility(Integer demographicNo, Integer facilityId) {
        if (demographicNo == null || demographicNo <= 0) {
            throw new IllegalArgumentException();
        }

        if (facilityId == null || facilityId < 0) {
            throw new IllegalArgumentException();
        }

        String queryStr = "FROM Admission a WHERE a.clientId=? and a.programId in " +
           "(select s.id from Program s where s.facilityId=? or s.facilityId is null) AND a.admissionStatus='"+KeyConstants.INTAKE_STATUS_ADMITTED+"' ORDER BY a.admissionDate DESC";

        @SuppressWarnings("unchecked")
        List<Admission> rs = getHibernateTemplate().find(queryStr, new Object[] { demographicNo, facilityId });

        if (log.isDebugEnabled()) {
            log.debug("getAdmissionsByFacility for clientId " + demographicNo + ", # of admissions: " + rs.size());
        }

        return rs;

    }
    
    public Admission getCurrentExternalProgramAdmission(ProgramDao programDAO, Integer demographicNo) {
        if (programDAO == null) {
            throw new IllegalArgumentException();
        }

        if (demographicNo == null || demographicNo <= 0) {
            throw new IllegalArgumentException();
        }

        String queryStr = "FROM Admission a WHERE a.clientId=? AND a.admissionStatus='"+KeyConstants.INTAKE_STATUS_ADMITTED+"' ORDER BY a.admissionDate DESC";

        Admission admission = null;
        List rs = new ArrayList();
        try{
          rs = getHibernateTemplate().find(queryStr, new Object[] { demographicNo });
        }catch(org.springframework.orm.hibernate3.HibernateObjectRetrievalFailureException ex)
        {
        	;
        }

        if (rs.isEmpty()) {
            return null;
        }

        ListIterator listIterator = rs.listIterator();
        while (listIterator.hasNext()) {
            try {
                admission = (Admission) listIterator.next();
                if (programDAO.isExternalProgram(admission.getProgramId())) {
                    return admission;
                }
            } catch (Exception ex) {
                return null;
            }
        }
        return null;
    }
    public List getAdmissionList(Integer clientId, Integer facilityId, String providerNo) {

    	String progSQL = "(select p.id from Program p where facilityId =? and 'P' || p.program_id in (select a.code from lst_orgcd a, secuserrole b " +
    			       " where a.fullcode like '%' || b.orgcd || '%' and b.provider_no=?)";
    	
    	
    	List results = getHibernateTemplate().find("from Admission i where i.clientId = ? and i.programId in " + progSQL +
			" order by i.admissionDate desc",
			new Object[] {clientId, facilityId, providerNo });

		return results;
	}

    
    // TODO: rewrite
    public Admission getCurrentBedProgramAdmission(ProgramDao programDAO, Integer demographicNo) {
        if (programDAO == null) {
            throw new IllegalArgumentException();
        }

        if (demographicNo == null || demographicNo <= 0) {
            throw new IllegalArgumentException();
        }

        String queryStr = "FROM Admission a WHERE a.clientId=? AND a.admissionStatus='"+KeyConstants.INTAKE_STATUS_ADMITTED+"' ORDER BY a.admissionDate DESC";

        Admission admission = null;
        List rs = new ArrayList();
        try{
          rs = getHibernateTemplate().find(queryStr, new Object[] { demographicNo });
        }catch(org.springframework.orm.hibernate3.HibernateObjectRetrievalFailureException ex){
        	;
        }
        
        if (rs.isEmpty()) {
            return null;
        }

        ListIterator listIterator = rs.listIterator();
        while (listIterator.hasNext()) {
            try {
                admission = (Admission) listIterator.next();
                if (programDAO.isBedProgram(admission.getProgramId())) {
                    return admission;
                }
            } catch (Exception ex) {
                return null;
            }
        }
        return null;
    }

    // TODO: rewrite
    @SuppressWarnings("unchecked")
    public List<Admission> getCurrentServiceProgramAdmission(ProgramDao programDAO, Integer demographicNo) {
        if (programDAO == null) {
            throw new IllegalArgumentException();
        }

        if (demographicNo == null || demographicNo <= 0) {
            throw new IllegalArgumentException();
        }

        String queryStr = "FROM Admission a WHERE a.clientId=? AND a.admissionStatus='"+KeyConstants.INTAKE_STATUS_ADMITTED+"' ORDER BY a.admissionDate DESC";

        Admission admission = null;
        List<Admission> admissions = new ArrayList<Admission>();
        List<Admission> rs = new ArrayList<Admission>();
        try{
          rs = getHibernateTemplate().find(queryStr, new Object[] { demographicNo });
        }catch(org.springframework.orm.hibernate3.HibernateObjectRetrievalFailureException ex){
        	;
        }

        if (rs.isEmpty()) {
            return null;
        }
        ListIterator listIterator = rs.listIterator();
        while (listIterator.hasNext()) {
            try {
                admission = (Admission) listIterator.next();
                if (programDAO.isServiceProgram(admission.getProgramId())) {
                    admissions.add(admission);
                }
            } catch (Exception ex) {
                return null;
            }
        }
        return admissions;
    }

    public Admission getCurrentCommunityProgramAdmission(ProgramDao programDAO, Integer demographicNo) {
        if (programDAO == null) {
            throw new IllegalArgumentException();
        }

        if (demographicNo == null || demographicNo <= 0) {
            throw new IllegalArgumentException();
        }

        String queryStr = "FROM Admission a WHERE a.clientId=? AND a.admissionStatus='"+KeyConstants.INTAKE_STATUS_ADMITTED+"' ORDER BY a.admissionDate DESC";

        Admission admission = null;
        List rs = new ArrayList();
        try{
           rs = getHibernateTemplate().find(queryStr, new Object[] { demographicNo });
        }catch(org.springframework.orm.hibernate3.HibernateObjectRetrievalFailureException ex)
        {
          ;
        }

        if (rs.isEmpty()) {
            return null;
        }

        ListIterator listIterator = rs.listIterator();
        while (listIterator.hasNext()) {
            try {
                admission = (Admission) listIterator.next();
                if (programDAO.isCommunityProgram(admission.getProgramId())) {
                    return admission;
                }
            } catch (Exception ex) {
                return null;
            }
        }
        return null;
    }

    public List getCurrentAdmissionsByProgramId(Integer programId) {
        if (programId == null || programId <= 0) {
            throw new IllegalArgumentException();
        }

        List results = this.getHibernateTemplate().find("from Admission a where a.programId = ? and a.admissionStatus='"+KeyConstants.INTAKE_STATUS_ADMITTED+"'", programId);

        if (log.isDebugEnabled()) {
            log.debug("getAdmissionsByProgramId for programId " + programId + ", # of admissions: " + results.size());
        }
        return results;
    }

    public Admission getAdmission(Integer id) {

        if (id == null || id <= 0) {
            throw new IllegalArgumentException();
        }

        Admission admission = (Admission) this.getHibernateTemplate().get(Admission.class, id);

        if (log.isDebugEnabled()) {
            log.debug("getAdmission: id= " + id + ", found: " + (admission != null));
        }

        return admission;
    }

       public void saveAdmission(Admission admission) {
        if (admission == null) {
            throw new IllegalArgumentException();
        }

        getHibernateTemplate().saveOrUpdate(admission);
      //  getHibernateTemplate().flush();

        if (log.isDebugEnabled()) {
            log.debug("saveAdmission: id= " + admission.getId());
        }
    }

    public List getAdmissionsInTeam(Integer programId, Integer teamId) {
        if (programId == null || programId <= 0) {
            throw new IllegalArgumentException();
        }

        if (teamId == null || teamId <= 0) {
            throw new IllegalArgumentException();
        }

        List results = this.getHibernateTemplate().find("from Admission a where a.programId = ? and a.teamId = ? and a.admissionStatus='"+KeyConstants.INTAKE_STATUS_ADMITTED+"'", new Object[] { programId, teamId });

        if (log.isDebugEnabled()) {
            log.debug("getAdmissionsInTeam: programId= " + programId + ",teamId=" + teamId + ",# results=" + results.size());
        }

        return results;
    }

    public Admission getTemporaryAdmission(Integer demographicNo) {
        Admission result = null;
        if (demographicNo == null || demographicNo <= 0) {
            throw new IllegalArgumentException();
        }

        List results = this.getHibernateTemplate().find("from Admission a where a.temporaryAdmission = true and a.admissionStatus='"+KeyConstants.INTAKE_STATUS_ADMITTED+"' and a.clientId = ?", demographicNo);

        if (!results.isEmpty()) {
            result = (Admission) results.get(0);
        }

        if (log.isDebugEnabled()) {
            log.debug("getTemporaryAdmission: demographicNo= " + demographicNo + ",found=" + (result != null));
        }

        return result;
    }

    public List search(AdmissionSearchBean searchBean) {
        if (searchBean == null) {
            throw new IllegalArgumentException();
        }

        Criteria criteria = getSession().createCriteria(Admission.class);

        if (searchBean.getProviderNo() != null && searchBean.getProviderNo().length()>0) {
            criteria.add(Restrictions.eq("providerNo", searchBean.getProviderNo()));
        }

        if (searchBean.getAdmissionStatus() != null && searchBean.getAdmissionStatus().length() > 0) {
            criteria.add(Restrictions.eq("admissionStatus", searchBean.getAdmissionStatus()));
        }

        if (searchBean.getClientId() != null && searchBean.getClientId() > 0) {
            criteria.add(Restrictions.eq("clientId", searchBean.getClientId()));
        }

        if (searchBean.getProgramId() != null && searchBean.getProgramId() > 0) {
            criteria.add(Restrictions.eq("programId", searchBean.getProgramId()));
        }

        if (searchBean.getStartDate() != null && searchBean.getEndDate() != null) {
            criteria.add(Restrictions.between("admissionDate", searchBean.getStartDate(), searchBean.getEndDate()));
        }
        List results = criteria.list();

        if (log.isDebugEnabled()) {
            log.debug("search: # of results: " + results.size());
        }

        return results;
    }

    public List getClientIdByProgramDate(int programId, Date dt) {
        String q = "FROM Admission a WHERE a.programId=? and a.admissionDate<=? and (a.dischargeDate>=? or (a.dischargeDate is null))";
        List rs = this.getHibernateTemplate().find(q, new Object[] { new Integer(programId), dt, dt });
        return rs;
    }
    
    public Integer getLastClientStatusFromAdmissionByProgramIdAndClientId(Integer programId, Integer demographicId) {
        if (programId == null || programId <= 0) {
            throw new IllegalArgumentException();
        }

        if (demographicId == null || demographicId <= 0) {
            throw new IllegalArgumentException();
        }

        List results = this.getHibernateTemplate().find("from Admission a where a.programId = ? and a.clientId = ? and a.admissionStatus='"+KeyConstants.INTAKE_STATUS_DISCHARGED+"' order by a.id DESC", new Object[] { programId, demographicId });
        if(results.isEmpty())
            return 0;
        
        Admission admission = null;
        ListIterator listIterator = results.listIterator();
        while (listIterator.hasNext()) {
            try {
                admission = (Admission) listIterator.next();
                Integer clientStatusId = admission.getClientStatusId();
                if(clientStatusId == null )
                    return 0;
                else
                    return clientStatusId;
            } catch (Exception ex) {
                return 0;
            }
        }
    
        if (log.isDebugEnabled()) {
            log.debug("getAdmissionsByProgramIdAndClientId: programId= " + programId + ",demographicId=" + demographicId + ",# results=" + results.size());
        }

        return 0;
    }
}
