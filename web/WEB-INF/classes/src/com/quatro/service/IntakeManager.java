package com.quatro.service;

import java.sql.SQLException;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Calendar;
import java.util.ArrayList;
import java.util.HashMap;
import com.quatro.dao.IntakeDao;
import com.quatro.dao.LookupDao;
import org.oscarehr.PMmodule.dao.ClientDao;
import org.oscarehr.PMmodule.dao.ProgramDao;
import com.quatro.web.intake.OptionList;
import com.quatro.model.QuatroIntakeOptionValue;
import com.quatro.web.intake.IntakeConstant;
import org.apache.struts.util.LabelValueBean;
import org.oscarehr.PMmodule.model.ClientReferral;
import org.oscarehr.PMmodule.model.Demographic;
import org.oscarehr.PMmodule.model.ProgramQueue;
import org.oscarehr.PMmodule.model.QuatroIntakeAnswer;
import org.oscarehr.PMmodule.model.QuatroIntakeDB;
import org.oscarehr.PMmodule.model.QuatroIntake;
import org.oscarehr.PMmodule.model.QuatroIntakeFamily;
import org.oscarehr.PMmodule.model.QuatroIntakeHeader;
import org.oscarehr.PMmodule.dao.ClientHistoryDao;
import com.quatro.common.KeyConstants;
import com.quatro.model.LookupCodeValue;
import org.oscarehr.PMmodule.model.Program;

import org.oscarehr.PMmodule.dao.ClientReferralDAO;
import org.oscarehr.PMmodule.dao.ProgramQueueDao;


import oscar.MyDateFormat;

public class IntakeManager {
    private IntakeDao intakeDao;
    private ClientReferralDAO clientReferralDAO;
    private ProgramQueueDao programQueueDao;

    private LookupDao lookupDao;
    private ClientDao clientDao;
    private ProgramDao programDao;
    private ClientHistoryDao historyDao;

	public IntakeDao getIntakeDao() {
		return intakeDao;
	}

	public void setIntakeDao(IntakeDao intakeDao) {
		this.intakeDao = intakeDao;
	}
	public void setClientHistoryDao(ClientHistoryDao historyDao) {
		this.historyDao = historyDao;
	}

    public void removeInactiveIntakeFamilyMember(String sDependentInakeIds, Integer intakeHeadId){
    	if(sDependentInakeIds==null) return;
    	
    	List lst = intakeDao.getClientIntakeFamily(intakeHeadId.toString());
    	if(lst.size()==0) return;
    	
    	StringBuffer sb = new StringBuffer();
    	if(sDependentInakeIds.equals("")){
          for(int i=0;i<lst.size();i++){
        	QuatroIntakeFamily obj= (QuatroIntakeFamily)lst.get(i);
        	if(obj.getIntakeHeadId().equals(obj.getIntakeId())) continue;  //skip family head
        	sb.append("," + obj.getIntakeId().toString());
          }
    	}else{
    	  String[] split = sDependentInakeIds.split(","); 
          for(int i=0;i<lst.size();i++){
      	    int j=0;
      	    QuatroIntakeFamily obj= (QuatroIntakeFamily)lst.get(i);
        	if(obj.getIntakeHeadId().equals(obj.getIntakeId())) continue;  //skip family head
   	        for(j=0;j<split.length;j++){
    		  if(obj.getIntakeId().toString().equals(split[j])){
                break;    			
    		  }
    	    }
      	    if(j==split.length) sb.append("," + obj.getIntakeId().toString());
          }
    	}

        if(sb.length()==0) return;
    	
  	    //remove inactive family memeber from family intake, 
    	//and update their referal and queue#
    	String sRemovedDependentIds=sb.substring(1);
    	intakeDao.removeInactiveIntakeFamilyMember(sRemovedDependentIds);
    	
    	String[] split2 = sRemovedDependentIds.split(",");
    	
  	    List lst2 = intakeDao.getQuatroIntakeDBByIntakeIds(sRemovedDependentIds);
    	for(int i=0;i<split2.length;i++){
    	  QuatroIntakeDB intake = (QuatroIntakeDB) lst2.get(i);	

    	  if(intake.getIntakeStatus().equals(KeyConstants.INTAKE_STATUS_ACTIVE)){
    	    //create new referral#
    	    ClientReferral referral = new ClientReferral();
            if(intake.getClientId()!=null) referral.setClientId(intake.getClientId());
            referral.setNotes("Intake Automated referral");
            if(intake.getProgramId()!=null) referral.setProgramId(intake.getProgramId());
            referral.setProviderNo(intake.getStaffId());
            referral.setReferralDate(new Date());
            referral.setStatus(KeyConstants.STATUS_ACTIVE);
            clientReferralDAO.saveClientReferral(referral);
          
            //create new queue# 
            ProgramQueue queue = new ProgramQueue();
            queue.setClientId(referral.getClientId());
            queue.setNotes(referral.getNotes());
            queue.setProgramId(referral.getProgramId());
            queue.setProviderNo(Integer.valueOf(referral.getProviderNo()));
            queue.setReferralDate(referral.getReferralDate());
//          queue.setStatus(KeyConstants.STATUS_ACTIVE);
            queue.setReferralId(referral.getId());
            programQueueDao.saveProgramQueue(queue);
          
            //modify referral# and queue# in intake
            intakeDao.updateReferralIdQueueId(intake, referral.getId(), queue.getId());
    	  }
    	}
  	    
    }
    
	public Demographic getClientByDemographicNo(String demographicNo) {
        if (demographicNo == null || demographicNo.length() == 0) {
            return null;
        }
        return clientDao.getClientByDemographicNo(Integer.valueOf(demographicNo));
    }

    //for single person intake check
    public List checkExistBedIntakeByFacility(Integer clientId,String providerNo, Integer shelterId){
    	List programs =programDao.getAllPrograms(null, Program.BED_TYPE, null, providerNo, shelterId);
    	if(programs==null || programs.size()==0) return new ArrayList();
    	return intakeDao.checkExistBedIntakeByPrograms(clientId, programs);
    }
    
    //for family member intake check
	public QuatroIntakeDB findQuatroIntakeDB(Integer clientId, Integer programId){
    	return intakeDao.findQuatroIntakeDB(clientId, programId);
    }

	public Integer getIntakeFamilyHeadId(String intakeId){
		return intakeDao.getIntakeFamilyHeadId(intakeId);
	}
	
	public List getClientFamilyByIntakeId(String intakeId) {
        if (intakeId == null || intakeId.length() == 0) {
            return null;
        }
        
        //avoid to join intake_family/intake table and demegraphic table for query
        List lst = intakeDao.getClientIntakeFamily(intakeId);
        
        if(lst.size()==0) return null;

        List relationships = lookupDao.LoadCodeList("FRA",true, null, null);
        Iterator it = lst.iterator();
        while(it.hasNext()){
        	Object element = (Object)it.next();
        
       	  QuatroIntakeFamily obj=(QuatroIntakeFamily)element;
       	  obj.setJoinFamilyDateTxt(MyDateFormat.getStandardDate(obj.getJoinFamilyDate()));
       	  Iterator it2 = relationships.iterator();
       	  while(it2.hasNext()){
        	Object element2 = (Object)it2.next();
          
        	LookupCodeValue obj2= (LookupCodeValue)element2;  
       	    if(obj2.getCode().equals(obj.getRelationship())){
        	  obj.setRelationshipDesc(obj2.getDescription());
       	      break;
       	    }
          }  
        }
        
        StringBuffer sb= new StringBuffer();
        Iterator it3 = lst.iterator();
        while(it3.hasNext()){
        	Object element = (Object)it3.next();
    	
    	  QuatroIntakeFamily obj=(QuatroIntakeFamily)element;
          sb.append("," +  obj.getClientId().toString());          
        }

    	List lst2 = clientDao.getClientFamilyByDemographicNo(sb.substring(1));
    	Iterator it4 = lst2.iterator();
        while(it4.hasNext()){
        	Object element2 = (Object)it4.next();
    	//for(Object element2 : lst2) {
    	  Demographic dmg= (Demographic)element2;
    	  
    	  Iterator it5 = lst.iterator();
          while(it5.hasNext()){
          	Object element = (Object)it5.next();
          //for(Object element : lst) {
       	    QuatroIntakeFamily obj=(QuatroIntakeFamily)element;
    		if(dmg.getDemographicNo().equals(obj.getClientId())){
       	      obj.setFirstName(dmg.getFirstName());
       	      obj.setLastName(dmg.getLastName());
       	      obj.setSex(dmg.getSex());
       	      obj.setSexDesc(dmg.getSexDesc());
       	      obj.setYearOfBirth(dmg.getYearOfBirth());
       	      obj.setMonthOfBirth(dmg.getMonthOfBirth());
       	      obj.setDateOfBirth(dmg.getDateOfBirth());
       	      obj.setAlias(dmg.getAlias());
      		  obj.setDob(obj.getYearOfBirth() + "/" + MyDateFormat.formatMonthOrDay(obj.getMonthOfBirth()) + "/" + MyDateFormat.formatMonthOrDay(obj.getDateOfBirth()));
      		  obj.setEffDate(MyDateFormat.getSysDateString(dmg.getEffDate()));
    		  break;
    		}  
          }
    	}
        return lst;	
    }

	public OptionList LoadOptionsList() {
        List lst=intakeDao.LoadOptionsList();
        OptionList lst2= new OptionList();
        HashMap map= IntakeConstant.getPrefixDefined();

        ArrayList[] lst3 = new ArrayList[12];
        for(int i=0;i<12;i++){
           lst3[i]= new ArrayList();
           if(i==IntakeConstant.SOURCEINCOME) continue;
           LabelValueBean obj2 = new LabelValueBean();
   	       obj2.setLabel("  ");
       	   obj2.setValue("");
           lst3[i].add(obj2);
        }
        
        boolean getRec;
        for(int i=0;i<12;i++){
          getRec=false;
          for(int j=0;j<lst.size();j++){
        	QuatroIntakeOptionValue obj= (QuatroIntakeOptionValue)lst.get(j);
       	    if(obj.getPrefix().equals(map.get(new Integer(i)))){
       	      LabelValueBean obj2 = new LabelValueBean();
       	      obj2.setLabel(obj.getDescription());
       	      obj2.setValue(obj.getCode());
        	  lst3[i].add(obj2);
        	  getRec=true;
        	}else{
              if(getRec==true){
            	  getRec=false;
            	  break;	
              }
            }
          }
      	  lst2.setValue(i, lst3[i]);            	
        }
        
        return lst2;
	}

	public List getQuatroIntakeHeaderListByFacility(Integer clientId, Integer shelterId, String providerNo) {
        List lst= programDao.getProgramIdsByProvider(providerNo, shelterId);
        if (lst.size()==0) return lst;
        StringBuffer sb = new StringBuffer();
        Iterator it = lst.iterator();
        while(it.hasNext()){
        	Object element = (Object)it.next();
        //for(Object element: lst){
          Object[] obj = (Object[])element;
          sb.append("," + ((Integer)obj[0]).toString());	
        }
		List lst2 = intakeDao.getQuatroIntakeHeaderList(clientId, sb.substring(1));
		Iterator it2 = lst2.iterator();
        while(it2.hasNext()){
        	Object element2 = (Object)it2.next();
        //for(Object element2: lst2){
          QuatroIntakeHeader obj2 = (QuatroIntakeHeader)element2;
          
          Iterator it3 = lst.iterator();
          while(it3.hasNext()){
          	Object element3 = (Object)it3.next();
          //for(Object element3: lst){
            Object[] obj3 = (Object[])element3;
            if(((Integer)obj3[0]).equals(obj2.getProgramId())){
              obj2.setProgramType((String)obj3[2]);
              break;
            }
          }
          
        }

        return lst2;
		
	}

	public QuatroIntake getQuatroIntake(Integer intakeId) {
		return intakeDao.getQuatroIntake(intakeId);
	}

	public QuatroIntakeDB getQuatroIntakeDBByQueueId(Integer queueId) {
		return intakeDao.getQuatroIntakeDBByQueueId(queueId);
	}
	public QuatroIntakeDB getQuatroIntakeDBByIntakeId(Integer intakeId) {
		return intakeDao.getQuatroIntakeDBByIntakeId(intakeId);
	}
	
	public ArrayList saveQuatroIntake(QuatroIntake intake) {
		historyDao.saveClientHistory(intake);
		return intakeDao.saveQuatroIntake(intake, false);
	}
	public void saveQuatroIntakeFamilyHead(QuatroIntakeFamily intakeFamily) {
		intakeDao.saveQuatroIntakeFamilyRelation(intakeFamily);
	}

	public ArrayList saveQuatroIntakeFamily(Demographic client, QuatroIntake intake, QuatroIntakeDB exist_intakeDB, QuatroIntakeFamily intakeFamily) {
		ArrayList lst = new ArrayList();
		clientDao.saveClient(client);
		intake.setClientId(client.getDemographicNo());
		if(intake.getId().intValue()==0){
		    List lst2 = intakeDao.saveQuatroIntake(intake, true);
		    intakeFamily.setIntakeId((Integer)lst2.get(0));
		}  
		intakeDao.saveQuatroIntakeFamilyRelation(intakeFamily);
		
		//remove referral# and queue# from old active intake.
		if(exist_intakeDB!=null)
		  intakeDao.deleteReferralIdQueueId(exist_intakeDB.getReferralId(), exist_intakeDB.getQueueId());
		
		lst.add(intakeFamily.getIntakeHeadId());
		lst.add(intakeFamily.getIntakeId());
		lst.add(client.getDemographicNo());
		return lst;
	}

	public ClientDao getClientDao() {
		return clientDao;
	}

	public void setClientDao(ClientDao clientDao) {
		this.clientDao = clientDao;
	}

	public ProgramDao getProgramDao() {
		return programDao;
	}

	public void setProgramDao(ProgramDao programDao) {
		this.programDao = programDao;
	}

	public void setLookupDao(LookupDao lookupDao) {
		this.lookupDao = lookupDao;
	}

	public void setClientReferralDAO(ClientReferralDAO clientReferralDAO) {
		this.clientReferralDAO = clientReferralDAO;
	}

	public void setProgramQueueDao(ProgramQueueDao programQueueDao) {
		this.programQueueDao = programQueueDao;
	}

}
