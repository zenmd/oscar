package org.oscarehr.PMmodule.dao;

import java.util.List;
import java.util.Calendar;
import com.quatro.common.KeyConstants;

import org.oscarehr.PMmodule.model.Admission;
import org.oscarehr.PMmodule.model.QuatroAdmission;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

public class QuatroAdmissionDao extends HibernateDaoSupport {
    public void saveAdmission(QuatroAdmission admission, Integer intakeId, Integer queueId, Integer referralId) {
        if (admission == null) {
            throw new IllegalArgumentException();
        }

        getHibernateTemplate().saveOrUpdate(admission);

        getHibernateTemplate().bulkUpdate("update QuatroIntakeDB q set q.intakeStatus='" +
                KeyConstants.INTAKE_STATUS_ADMITTED + "' where q.id=?", intakeId);
        
        getHibernateTemplate().bulkUpdate("delete ProgramQueue q where q.Id=?", new Long(queueId.longValue()));

        getHibernateTemplate().bulkUpdate("update ClientReferral c set c.Status='" +
                KeyConstants.INTAKE_STATUS_ADMITTED + "' where c.Id=?", new Long(referralId.longValue()));
    }

    public void updateAdmission(QuatroAdmission admission) {
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
    	  getHibernateTemplate().bulkUpdate("update QuatroAdmission q set q.admissionStatus='" +
        		KeyConstants.INTAKE_STATUS_DISCHARGED + "'" + 
                " and q.dischargeNotes='auto-discharge for other intake admission'" +
                " and q.dischargeDate=? where q.id=?", new Object[]{cal, Integer.valueOf(split[i])});
        }  
    }
    
    //for admission auto-discharge purpose
    public List getAdmissionList(Integer clientId, Integer facilityId) {
    	List results = getHibernateTemplate().find("from QuatroAdmission i where i.clientId = ? and i.programId in " +
			"(select p.id from Program p where p.facilityId =?) order by i.admissionDate desc",
			new Object[] {clientId, facilityId});

		return results;
	}

    public List getAdmissionList(Integer clientId, Integer facilityId, String providerNo) {
    	List results = getHibernateTemplate().find("from Admission i where i.clientId = ? and i.programId in " +
    			"(select p.id from Program p, ProgramProvider q where p.facilityId =?" + 
    			" and p.id= q.ProgramId and q.ProviderNo=?) order by i.admissionDate desc",
    			new Object[] {clientId, facilityId, providerNo });

    	return results;
	}

    public QuatroAdmission getAdmission(Integer intakeId){
    	String queryStr = "FROM QuatroAdmission a WHERE a.intakeId=?";
        List  lst= getHibernateTemplate().find(queryStr, new Object[] { intakeId});
        if(lst.size()==0)
          return null;
        else
          return (QuatroAdmission) lst.get(0);
    }
    
    public List<QuatroAdmission> getCurrentAdmissionsByFacility(Integer demographicNo, Integer facilityId) {
        if (demographicNo == null || demographicNo <= 0) {
            throw new IllegalArgumentException();
        }

        if (facilityId == null || facilityId < 0) {
            throw new IllegalArgumentException();
        }

        String queryStr = "FROM QuatroAdmission a WHERE a.clientId=? and a.programId in " +
           "(select s.id from Program s where s.facilityId=? or s.facilityId is null) AND a.admissionStatus='"
          + KeyConstants.INTAKE_STATUS_ADMITTED+"' ORDER BY a.admissionDate DESC";

        List<QuatroAdmission> rs = getHibernateTemplate().find(queryStr, new Object[] { demographicNo, facilityId });

        return rs;

    }
    
}
