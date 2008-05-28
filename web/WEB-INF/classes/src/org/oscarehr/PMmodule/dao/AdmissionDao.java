package org.oscarehr.PMmodule.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Calendar;
import java.util.ListIterator;

import com.quatro.common.KeyConstants;

import org.hibernate.Criteria;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.mapping.Constraint;
import org.oscarehr.PMmodule.model.Admission;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

public class AdmissionDao extends HibernateDaoSupport {
	public List getAdmissionsByFacility(Integer demographicNo, Integer facilityId) {
	  if (demographicNo == null || demographicNo <= 0) {
	    throw new IllegalArgumentException();
	  }

	  String queryStr = "FROM Admission a WHERE a.clientId=? and a.programId in " +
	        "(select s.id from Program s where s.facilityId=? or s.facilityId is null) ORDER BY a.admissionDate DESC";
	        List rs = getHibernateTemplate().find(queryStr, new Object[] { demographicNo,  facilityId });

	        return rs;
	    }

	public void saveAdmission(Admission admission, Integer intakeId, Integer queueId, Integer referralId) {
        if (admission == null) {
            throw new IllegalArgumentException();
        }

        getHibernateTemplate().saveOrUpdate(admission);

        getHibernateTemplate().bulkUpdate("update QuatroIntakeDB q set q.intakeStatus='" +
                KeyConstants.INTAKE_STATUS_ADMITTED + "' where q.id=?", intakeId);
        
        getHibernateTemplate().bulkUpdate("delete ProgramQueue q where q.Id=?",queueId);

        getHibernateTemplate().bulkUpdate("update ClientReferral c set c.status='" +
                KeyConstants.INTAKE_STATUS_ADMITTED + "' where c.Id=?", referralId);
    }

    public void updateAdmission(Admission admission) {
        if (admission == null || admission.getId().intValue()==0) {
            throw new IllegalArgumentException();
        }

        getHibernateTemplate().update(admission);
    }

    public void dischargeAdmission(String admissionIds) {
    	if(admissionIds==null || admissionIds.length()==0) return;
        
    	Calendar cal = Calendar.getInstance();
        String[] split= admissionIds.split(",");
        for(int i=0;i<split.length;i++){
    	  getHibernateTemplate().bulkUpdate("update Admission q set q.admissionStatus='" +
        		KeyConstants.INTAKE_STATUS_DISCHARGED + "'" + 
                " and q.dischargeNotes='auto-discharge for other intake admission'" +
                " and q.dischargeDate=? where q.id=?", new Object[]{cal, Integer.valueOf(split[i])});
        }  
    }
    
    //for admission auto-discharge purpose
    public List getIntakeAdmissionList(Integer clientId) {
    	List results = getHibernateTemplate().find("from Admission i where i.clientId = ? and " +
  			"i.admissionStatus='" + KeyConstants.INTAKE_STATUS_ADMITTED + "'",
			new Object[] {clientId});

		return results;
	}

    public List getAdmissionList(Integer clientId, Integer facilityId, String providerNo) {
    	String progSQL = "";
    	if (facilityId ==0 ) {
    		progSQL = "program_id in (select p.program_id from program p where 'P' || p.program_id in " +
    				"(select a.code from lst_orgcd a, secuserrole b " +
    			    " where a.fullcode like '%' || b.orgcd || '%' and b.provider_no='" + providerNo + "'))";
    	}
    	else
    	{
        	progSQL = "program_id in (select p.program_id from program p where p.facility_id =" +
    		facilityId.toString() + " and 'P' || p.program_id in (select a.code from lst_orgcd a, secuserrole b " +
    			       " where a.fullcode like '%' || b.orgcd || '%' and b.provider_no='" + providerNo + "'))";
    	}
    	Criteria criteria = getSession().createCriteria(Admission.class);
    	criteria.add(Restrictions.sqlRestriction(progSQL));
    	criteria.add(Expression.eq("clientId",clientId));
    	criteria.addOrder(Order.desc("admissionDate"));
    	List results = criteria.list();

		return results;
	}
    
    public Admission getAdmissionByIntakeId(Integer intakeId){
    	String queryStr = "FROM Admission a WHERE a.intakeId=?";
        List  lst= getHibernateTemplate().find(queryStr, new Object[] { intakeId});
        if(lst.size()==0)
          return null;
        else
          return (Admission) lst.get(0);
    }

    public Admission getAdmissionByAdmissionId(Integer admissionId){
    	String queryStr = "FROM Admission a WHERE a.id=?";
        List  lst= getHibernateTemplate().find(queryStr, new Object[] {admissionId});
        if(lst.size()==0)
          return null;
        else
          return (Admission) lst.get(0);
    }

    public List<Admission> getCurrentAdmissionsByFacility(Integer demographicNo, Integer facilityId) {
        if (demographicNo == null || demographicNo <= 0) {
            throw new IllegalArgumentException();
        }

        if (facilityId == null || facilityId < 0) {
            throw new IllegalArgumentException();
        }

        String queryStr = "FROM Admission a WHERE a.clientId=? and a.programId in " +
           "(select s.id from Program s where s.facilityId=? or s.facilityId is null) AND a.admissionStatus='"
          + KeyConstants.INTAKE_STATUS_ADMITTED+"' ORDER BY a.admissionDate DESC";

        List<Admission> rs = getHibernateTemplate().find(queryStr, new Object[] { demographicNo, facilityId });

        return rs;

    }
    
    public Admission getRecentAdmissionByFacility(Integer clientId, Integer facilityId){
    	List admissions =this.getAdmissionsByFacility(clientId, facilityId);
    	Admission admObj=null;
    	if(!admissions.isEmpty()){
    		admObj=(Admission)admissions.get(0);
    	}
    	else admObj= new Admission();
    	return admObj;
    }
    
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
    
    public List<Admission> getAdmissionsByProgramId(Integer programId, Boolean automaticDischarge, Integer days) {
        if (programId == null || programId <= 0) {
            throw new IllegalArgumentException();
        }
       
        //today's date
        Calendar calendar = Calendar.getInstance();
     
        //today's date -  days
        calendar.add(Calendar.DAY_OF_YEAR, days);
        
//        Date sevenDaysAgo = calendar.getTime();
        
        String queryStr = "FROM Admission a WHERE a.programId=? and a.automaticDischarge=? and a.dischargeDate>= ? ORDER BY a.dischargeDate DESC";

        List<Admission> rs = getHibernateTemplate().find(queryStr, new Object[] { programId, automaticDischarge, calendar });

        return rs;
    }

    public List getCurrentAdmissionsByProgramId(Integer programId) {
        if (programId == null || programId <= 0) {
            throw new IllegalArgumentException();
        }

        List results = this.getHibernateTemplate().find("from Admission a where a.programId = ? and a.admissionStatus='"+KeyConstants.INTAKE_STATUS_ADMITTED+"'", programId);

        return results;
    }

    public Admission getAdmission(Integer id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException();
        }

        Admission admission = (Admission) this.getHibernateTemplate().get(Admission.class, id);
        return admission;
    }

    public void saveAdmission(Admission admission) {
        if (admission == null) {
            throw new IllegalArgumentException();
        }

        getHibernateTemplate().saveOrUpdate(admission);
    }
    
    public List getCurrentAdmissions(Integer demographicNo) {
        if (demographicNo == null || demographicNo <= 0) {
            throw new IllegalArgumentException();
        }

        String queryStr = "FROM Admission a WHERE a.clientId=? AND a.admissionStatus='"+KeyConstants.INTAKE_STATUS_ADMITTED+"' ORDER BY a.admissionDate DESC";
        List rs = getHibernateTemplate().find(queryStr, new Object[] { demographicNo });

        return rs;

    }
    public List getAdmissions(Integer demographicNo) {
        if (demographicNo == null || demographicNo <= 0) {
            throw new IllegalArgumentException();
        }

        String queryStr = "FROM Admission a WHERE a.clientId=? ORDER BY a.admissionDate DESC";
        List rs = getHibernateTemplate().find(queryStr, new Object[] { demographicNo });
        return rs;
    }

    //Lillian add 2008-05-23
    public Admission getRecentAdmission(Integer clientId){
    	List admissions =this.getAdmissions(clientId);
    	Admission admObj=null;
    	if(!admissions.isEmpty()){
    		admObj=(Admission)admissions.get(0);
    	}
    	else admObj= new Admission();
    	return admObj;
    }
    public void updateDischargeInfo(Admission admission){
        String sSQL="update Admission q set q.communityProgramCode=?," + 
        "q.dischargeDate=?, q.admissionStatus=?, q.dischargeReason=?, " +
        "q.transportationType=?, q.dischargeNotes=? where q.id=?";
		getHibernateTemplate().bulkUpdate(sSQL, 
			new Object[]{admission.getCommunityProgramCode(),  
				admission.getDischargeDate(), admission.getAdmissionStatus(),
				admission.getDischargeReason(), admission.getTransportationType(),
				admission.getDischargeNotes(), admission.getId()});
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

        return admission;
    }
    
}

