package org.oscarehr.PMmodule.dao;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringEscapeUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.oscarehr.PMmodule.model.Admission;
import org.oscarehr.PMmodule.model.Program;
import org.oscarehr.PMmodule.model.ProgramClientInfo;
import org.oscarehr.PMmodule.model.QuatroIntake;
import org.oscarehr.PMmodule.model.RoomDemographic;
import org.oscarehr.PMmodule.model.RoomDemographicHistorical;
import org.oscarehr.PMmodule.model.RoomDemographicPK;
import org.oscarehr.PMmodule.web.formbean.ClientForm;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.oscarehr.PMmodule.model.ProgramQueue;
import org.oscarehr.PMmodule.model.ClientReferral;

import com.quatro.common.KeyConstants;
import com.quatro.util.Utility;
public class AdmissionDao extends HibernateDaoSupport {
	private MergeClientDao mergeClientDao;
	private RoomDemographicDAO roomDemographicDAO;
	private RoomDemographicHistoricalDao roomDemographicHistoricalDao;
	private ProgramQueueDao programQueueDao;
    private ClientReferralDAO clientReferralDAO;
	private ClientHistoryDao clientHisDao;

	public void setMergeClientDao(MergeClientDao mergeClientDao) {
		this.mergeClientDao = mergeClientDao;
	}
	public void setClientHistoryDao(ClientHistoryDao clientHisDao)
	{
		this.clientHisDao = clientHisDao;
	}

	public void saveAdmission(Admission admission, Integer intakeId){//, Integer queueId, Integer referralId) {
        if (admission == null) {
            throw new IllegalArgumentException();
        }

        getHibernateTemplate().saveOrUpdate(admission);

        getHibernateTemplate().bulkUpdate("update QuatroIntakeDB q set q.intakeStatus='" +
        		KeyConstants.INTAKE_STATUS_ADMITTED + "' where q.id=?", intakeId);

        ProgramQueue queue = programQueueDao.getProgramQueueByIntakeId(intakeId);
        if(queue!=null) getHibernateTemplate().bulkUpdate("delete ProgramQueue q where q.Id=?",queue.getId());

        ClientReferral referral = clientReferralDAO.getReferralByIntakeId(intakeId);
        if(referral!=null) getHibernateTemplate().bulkUpdate("update ClientReferral c set c.status='" +
                KeyConstants.STATUS_ACCEPTED + "' where c.Id=?", referral.getId());
                
    }
	public List saveAdmission(QuatroIntake intake, Integer intakeHeadId){
        if (intake == null) {
            throw new IllegalArgumentException();
        }
        List adms = getCurrentAdmissions(intake.getClientId(), KeyConstants.SYSTEM_USER_PROVIDER_NO, new Integer(0));
        if(adms.size() > 0) {
        	Admission adm0 = (Admission) adms.get(0);
        	dischargeAdmission(adm0.getId().toString(), "Joined in a family");
        }
        
        ArrayList lst = new ArrayList();
        Admission adm = new Admission();
        adm.setClientId(intake.getClientId());
        adm.setIntakeId(intake.getId());
        adm.setIntakeHeadId(intakeHeadId);
        adm.setAdmissionDate(Calendar.getInstance());
        adm.setAdmissionNotes("Admitted directly from family join in" );
        adm.setAdmissionStatus(KeyConstants.INTAKE_STATUS_ADMITTED);
        adm.setProgramId(intake.getProgramId());
        adm.setFamilyMember(true);
        adm.setId(new Integer(0));
        adm.setLastUpdateDate(Calendar.getInstance());
        adm.setNotSignReason("FH");
        adm.setProviderNo(intake.getStaffId());
        
        getHibernateTemplate().saveOrUpdate(adm);
        Integer admissionId = adm.getId();
       
        Admission admHead = getAdmissionByIntakeId(intakeHeadId);
        RoomDemographic rdmHead = roomDemographicDAO.getRoomDemographicByAdmissionId(admHead.getId());
        
    	RoomDemographic room = new RoomDemographic();
    	room.setAdmissionId(admissionId);
    	room.setAssignStart(Calendar.getInstance().getTime());
    	room.setBedId(null);
    	room.setComment("Join a family");
    	room.setProviderNo(intake.getStaffId());
    	RoomDemographicPK id = new RoomDemographicPK();
    	id.setDemographicNo(intake.getClientId());
    	id.setRoomId(rdmHead.getId().getRoomId());
    	room.setId(id);
    	room.setLastUpdateDate(intake.getLastUpdateDate());
    	roomDemographicDAO.saveRoomDemographic(room);
    	
    	lst.add(adm);
    	lst.add(room);
    	return lst;
    }

    public void updateAdmission(Admission admission) {
        if (admission == null) {
            throw new IllegalArgumentException();
        }

        getHibernateTemplate().update(admission);
    }

    public void dischargeAdmission(String admissionIds, String comments) {
    	if(admissionIds==null || admissionIds.length()==0) return;
    	if (Utility.IsEmpty(comments)) comments = "auto-discharge for other intake admission";
           	
    	Calendar cal = Calendar.getInstance();
        String[] split= admissionIds.split(",");
        for(int i=0;i<split.length;i++){
          Admission  admission = this.getAdmission(Integer.valueOf(split[i]));
	      admission.setAdmissionStatus(KeyConstants.INTAKE_STATUS_DISCHARGED);
          admission.setDischargeReason(oscar.OscarProperties.getInstance().getProperty("AUTO_DISCHARGE_REASON_CODE"));
    	  admission.setDischargeDate(Calendar.getInstance());
    	  admission.setAutomaticDischarge(true);
          getHibernateTemplate().bulkUpdate("update QuatroIntakeDB i set i.intakeStatus='" +
          		KeyConstants.INTAKE_STATUS_DISCHARGED + "'" + 
                  " where i.id=?", new Object[]{admission.getIntakeId()});

    	  getHibernateTemplate().bulkUpdate("update Admission q set q.admissionStatus='" +
        		KeyConstants.INTAKE_STATUS_DISCHARGED + "'," + 
                " q.dischargeNotes='" + comments + "'," +
                " q.dischargeReason='" + KeyConstants.AUTO_DISCHARGE_REASON + "'," +
                " q.communityProgramCode='" + KeyConstants.AUTO_DISCHARGE_DISPOSITION + "'," +  
                " q.dischargeDate=? where q.id=?", new Object[]{cal, Integer.valueOf(split[i])});

    	  RoomDemographic rdm = roomDemographicDAO.getRoomDemographicByDemographic(admission.getClientId());
  	      if(rdm!=null){
  	    	  roomDemographicDAO.deleteRoomDemographic(rdm);
  	     	  // update room_history
              Integer roomId = rdm.getId().getRoomId();
              RoomDemographicHistorical history = roomDemographicHistoricalDao.findById(roomId, admission.getId());
              if(history!=null){
           	   	history.setUsageEnd(Calendar.getInstance());
                  roomDemographicHistoricalDao.saveOrUpdate(history);
              }
  	      }
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
    
    public List getClientsListForBedProgram(Program program, ClientForm form){
    	Integer programId = program.getId();
    	
    	String queryStr = "SELECT a.admissionDate, a.admissionNotes, a.ovPassStartDate, a.ovPassEndDate, a.id, "
    		+ " c.FirstName, c.LastName, a.clientId, rd.roomName, rd.bedName, " 
    		+ " a.intakeId, a.intakeHeadId "
    		+ " FROM Admission a, Demographic c, RoomDemographic rd" 
    		+ " WHERE a.programId=?"
    		+ " AND a.admissionStatus='" + KeyConstants.INTAKE_STATUS_ADMITTED + "'"
    		+ " AND a.clientId = c.DemographicNo"
    		+ " AND rd.id.demographicNo = a.clientId";
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
	    	String clientId = form.getClientId();
	    	if(clientId != null && clientId.length()>0){
	    		clientId = StringEscapeUtils.escapeSql(clientId);
	    		clientId = clientId.toLowerCase();
	    		queryStr = queryStr + " AND lower(a.clientId) like '%" + clientId + "%'";
	    	}
	    	
    	}
    	queryStr = queryStr + " ORDER BY a.intakeId";
        List  lst= getHibernateTemplate().find(queryStr, new Object[] { programId});
    	List clientsLst = new ArrayList();
    	Iterator it = lst.iterator();
    	while(it.hasNext()){
    		Object[] objLst = (Object[])it.next();
    		ProgramClientInfo pClient = new ProgramClientInfo();
    		if(objLst[0]!=null){
    			SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
    		    String admissionDate = formatter.format(((Calendar)objLst[0]).getTime());
    		    pClient.setAdmissionDate(admissionDate);
    		}
    		if(objLst[1]!=null)
    			pClient.setAdmissionNote(objLst[1].toString());
    		if(objLst[2]!=null && objLst[3]!=null){
	    		Calendar now = Calendar.getInstance();
	    		Calendar start = (Calendar)objLst[2];
	    		Calendar end = (Calendar)objLst[3];
	    		if(start.after(now) || end.before(now)){
	    			pClient.setIsLatepassHolder("0");
	    		}else{
	    			pClient.setIsLatepassHolder("1");
	    		}
    		}else{
    			pClient.setIsLatepassHolder("0");
    		}
    		pClient.setAdmissionId((Integer)objLst[4]);
    		pClient.setFirstName((String)objLst[5]);
    		pClient.setLastName((String)objLst[6]);
    		pClient.setClientId(objLst[7].toString());
    		pClient.setRoom((String)objLst[8]);
            pClient.setBed((String)objLst[9]);
            Integer intakeId = null;
            Integer intakeHeadId = null;
            if(objLst[10] != null)
            	intakeId = (Integer)objLst[10];
            if(objLst[11] != null)
            	intakeHeadId = (Integer)objLst[11];
            if(intakeHeadId == null || intakeHeadId.equals(intakeId)){
            	pClient.setIsHead(true);
            }else{
            	pClient.setIsHead(false);
            }
            if("1".equals(pClient.getIsLatepassHolder()) || !pClient.getIsHead())
            	pClient.setIsDischargeable("0");
            else
            	pClient.setIsDischargeable("1");
            
    		clientsLst.add(pClient);
    	}
    	return clientsLst;
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
        if (clientId == null ) {
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
        String queryStr = "from Admission a where a.clientId in " + clientIds + " and " +
           "a.admissionStatus='" + KeyConstants.INTAKE_STATUS_ADMITTED + "'" +  
           " and programType='Bed' ORDER BY a.admissionDate DESC";

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
        if (programId == null) {
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
        if (programId == null ) {
            throw new IllegalArgumentException();
        }

        List results = this.getHibernateTemplate().find("from Admission a where a.programId = ? and " +
      		"a.admissionStatus='" + KeyConstants.INTAKE_STATUS_ADMITTED + "'", programId);

        return results;
    }

    public Admission getAdmission(Integer id) {
        if (id == null) {
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

	public void setRoomDemographicDAO(RoomDemographicDAO roomDemographicDAO) {
		this.roomDemographicDAO = roomDemographicDAO;
	}

	public void setRoomDemographicHistoricalDao(
			RoomDemographicHistoricalDao roomDemographicHistoricalDao) {
		this.roomDemographicHistoricalDao = roomDemographicHistoricalDao;
	}
	public void setClientReferralDAO(ClientReferralDAO clientReferralDAO) {
		this.clientReferralDAO = clientReferralDAO;
	}
	public void setProgramQueueDao(ProgramQueueDao programQueueDao) {
		this.programQueueDao = programQueueDao;
	}
}

