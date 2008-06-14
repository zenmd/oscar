package org.oscarehr.PMmodule.dao;

import java.util.Calendar;
import java.util.List;

import org.apache.commons.lang.StringEscapeUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.oscarehr.PMmodule.model.Admission;
import org.oscarehr.PMmodule.model.Program;
import org.oscarehr.PMmodule.web.formbean.ClientForm;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.quatro.common.KeyConstants;
import com.quatro.util.Utility;
public class AdmissionDao extends HibernateDaoSupport {
	private MergeClientDao mergeClientDao;
	public void setMergeClientDao(MergeClientDao mergeClientDao) {
		this.mergeClientDao = mergeClientDao;
	}
	private ClientHistoryDao clientHisDao;
	public void setClientHistoryDao(ClientHistoryDao clientHisDao)
	{
		this.clientHisDao = clientHisDao;
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
          Admission  admission = this.getAdmission(Integer.valueOf(split[i]));
    	  getHibernateTemplate().bulkUpdate("update QuatroIntakeDB i set i.intakeStatus='" +
          		KeyConstants.INTAKE_STATUS_DISCHARGED + "'" + 
                  " where i.id=?", new Object[]{admission.getIntakeId()});

    	  getHibernateTemplate().bulkUpdate("update Admission q set q.admissionStatus='" +
        		KeyConstants.INTAKE_STATUS_DISCHARGED + "'," + 
                " q.dischargeNotes='auto-discharge for other intake admission'," +
                " q.dischargeDate=? where q.id=?", new Object[]{cal, Integer.valueOf(split[i])});
    	  admission.setAdmissionStatus(KeyConstants.INTAKE_STATUS_DISCHARGED);
    	  clientHisDao.saveClientHistory(admission, null, null);
        }  
    }
    
    public List getAdmissionList(Integer clientId, boolean activeOnly, String providerNo, Integer shelterId) {
    	String progSQL = Utility.getUserOrgSqlString(providerNo, shelterId);
    	String clientIds=mergeClientDao.getMergedClientIds(clientId);
    	// remove "()"
    	clientIds=clientIds.substring(1,clientIds.length()-1);
    	String[] cIds= clientIds.split(",");
    	Object[] clients=new Object[cIds.length];
    	for(int i=0;i<cIds.length;i++){
    		clients[i] =Integer.valueOf(cIds[i]);
    	}
    	Criteria criteria = getSession().createCriteria(Admission.class);
    	criteria.add(Restrictions.in("clientId",clients));
    	if (activeOnly) criteria.add(Restrictions.eq("admissionStatus",KeyConstants.INTAKE_STATUS_ADMITTED));
    	criteria.add(Restrictions.sqlRestriction(" program_Id in " + progSQL));
    	
    	criteria.addOrder(Order.desc("admissionDate"));
    	List results = criteria.list();

		return results;
	}
    
    public List getAdmissionListByProgram(Integer programId){
    	String queryStr = "SELECT a.admissionDate, a.admissionNotes, a.ovPassStartDate, a.ovPassEndDate, a.id, "
    		+ " c.FirstName, c.LastName, a.clientId" 
    		+ " FROM Admission a, Demographic c" 
    		+ " WHERE a.programId=?"
    		+ " AND a.admissionStatus='" + KeyConstants.INTAKE_STATUS_ADMITTED + "'"
    		+ " AND a.clientId = c.DemographicNo";
        List  lst= getHibernateTemplate().find(queryStr, new Object[] { programId});
        return lst;
    }
    
    public List getClientsListByProgram(Program program, ClientForm form){
    	Integer programId = program.getId();
    	Integer facilityId = program.getFacilityId();
    	
    	String queryStr = "SELECT a.admissionDate, a.admissionNotes, a.ovPassStartDate, a.ovPassEndDate, a.id, "
    		+ " c.FirstName, c.LastName, a.clientId, rm.name, a.bedName, " 
    		+ " a.intakeId, a.intakeHeadId "
    		+ " FROM Admission a, Demographic c, RoomDemographic rd, Room rm" 
    		+ " WHERE a.programId=?"
    		+ " AND a.admissionStatus='" + KeyConstants.INTAKE_STATUS_ADMITTED + "'"
    		+ " AND a.clientId = c.DemographicNo"
    		+ " AND rd.id.demographicNo = a.clientId"
    		+ " AND rd.id.roomId = rm.id"
    		+ " AND rm.facilityId = '" + facilityId + "'";
    	if(form != null){
	    	String fname = form.getFirstName();
	    	if(fname != null && fname.length()>0){
	    		fname = StringEscapeUtils.escapeSql(fname);
				fname = fname.toLowerCase();
	    		queryStr = queryStr + " AND lower(c.FirstName) like '%" + fname + "%'";
	    	}
	    	String lname = form.getLastName();
	    	if(lname != null && lname.length()>0){
	    		lname = StringEscapeUtils.escapeSql(lname);
	    		lname = lname.toLowerCase();
	    		queryStr = queryStr + " AND lower(c.LastName) like '%" + lname + "%'";
	    	}
//	    	String bed = form.getBed();
//	    	if(bed != null && bed.length()>0){
//	    		bed = StringEscapeUtils.escapeSql(bed);
//	    		bed = bed.toLowerCase();
//	    		queryStr = queryStr + " AND lower(b.name) like '%" + bed + "%'";
//	    	}
//	    	String room = form.getRoom();
//	    	if(room != null && room.length()>0){
//	    		room = StringEscapeUtils.escapeSql(room);
//	    		room = room.toLowerCase();
//	    		queryStr = queryStr + " AND lower(rm.name) like '%" + room + "%'";
//	    	}
	    	String clientId = form.getClientId();
	    	if(clientId != null && clientId.length()>0){
	    		clientId = StringEscapeUtils.escapeSql(clientId);
	    		clientId = clientId.toLowerCase();
	    		queryStr = queryStr + " AND lower(a.clientId) like '%" + clientId + "%'";
	    	}
	    	
    	}
    	queryStr = queryStr + " ORDER BY a.intakeId";
        List  lst= getHibernateTemplate().find(queryStr, new Object[] { programId});
        
        return lst;
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

    public List getCurrentAdmissions(Integer clientId,String providerNo, Integer shelterId) {
        if (clientId == null || clientId.intValue() <= 0) {
            throw new IllegalArgumentException();
        }
        return getAdmissionList(clientId, true, providerNo, shelterId);
    }
    
    public Admission getRecentAdmission(Integer clientId, String providerNo,Integer shelterId){
    	List admissions =this.getAdmissionList(clientId, false,providerNo,shelterId);
    	Admission admObj=null;
    	if(!admissions.isEmpty()){
    		admObj=(Admission)admissions.get(0);
    	}
    	else admObj= new Admission();
    	return admObj;
    }
    
    public Admission getCurrentBedProgramAdmission(Integer demographicNo) {
        if (demographicNo == null || demographicNo.intValue() <= 0) {
            throw new IllegalArgumentException();
        }
        String clientIds =mergeClientDao.getMergedClientIds(demographicNo);
        String queryStr = "from Admission a where a.clientId in "+clientIds+" and a.admissionStatus='"+KeyConstants.INTAKE_STATUS_ADMITTED +
         "' and programType='Bed' ORDER BY a.admissionDate DESC";

        List admissions = getHibernateTemplate().find(queryStr);
        if (admissions.size() > 0) {
        	return (Admission) admissions.get(0);
        }
        else
        {
        	return null;
        }
    }
    
    public List getAdmissionsByProgramId(Integer programId, Boolean automaticDischarge, Integer days) {
        if (programId == null || programId.intValue() <= 0) {
            throw new IllegalArgumentException();
        }
       
        //today's date
        Calendar calendar = Calendar.getInstance();
     
        //today's date -  days
        calendar.add(Calendar.DAY_OF_YEAR, days.intValue());
        
//        Date sevenDaysAgo = calendar.getTime();
        
        String queryStr = "FROM Admission a WHERE a.programId=? and a.automaticDischarge=? and a.dischargeDate>= ? ORDER BY a.dischargeDate DESC";

        List rs = getHibernateTemplate().find(queryStr, new Object[] { programId, automaticDischarge, calendar });

        return rs;
    }

    public List getCurrentAdmissionsByProgramId(Integer programId) {
        if (programId == null || programId.intValue() <= 0) {
            throw new IllegalArgumentException();
        }

        List results = this.getHibernateTemplate().find("from Admission a where a.programId = ? and a.admissionStatus='"+KeyConstants.INTAKE_STATUS_ADMITTED+"'", programId);

        return results;
    }

    public Admission getAdmission(Integer id) {
        if (id == null || id.intValue() <= 0) {
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

    public void updateDischargeInfo(Admission admission){
    	String sSQL="update QuatroIntakeDB q set q.intakeStatus='" + 
    	KeyConstants.INTAKE_STATUS_DISCHARGED + "' where q.id=?";

    	getHibernateTemplate().bulkUpdate(sSQL, new Object[]{admission.getIntakeId()});

    	sSQL="update Admission q set q.communityProgramCode=?," + 
        "q.dischargeDate=?, q.admissionStatus=?, q.dischargeReason=?, " +
        "q.transportationType=?, q.dischargeNotes=?,q.lastUpdateDate=?,q.providerNo=? where q.id=?";

        getHibernateTemplate().bulkUpdate(sSQL, 
			new Object[]{admission.getCommunityProgramCode(),  
				admission.getDischargeDate(), admission.getAdmissionStatus(),
				admission.getDischargeReason(), admission.getTransportationType(),
				admission.getDischargeNotes(),admission.getLastUpdateDate(),admission.getProviderNo(), admission.getId()});
    }
}

