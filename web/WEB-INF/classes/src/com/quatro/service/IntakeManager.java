package com.quatro.service;

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
import org.oscarehr.PMmodule.model.Demographic;
import org.oscarehr.PMmodule.model.QuatroIntakeDB;
import org.oscarehr.PMmodule.model.QuatroIntake;
import org.oscarehr.PMmodule.model.QuatroIntakeFamily;
import org.oscarehr.PMmodule.model.QuatroIntakeHeader;
import org.oscarehr.PMmodule.dao.ClientHistoryDao;
import com.quatro.common.KeyConstants;
import com.quatro.model.LookupCodeValue;
import org.oscarehr.PMmodule.model.Program;

import oscar.MyDateFormat;

public class IntakeManager {
    private IntakeDao intakeDao;
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

    public Demographic getClientByDemographicNo(String demographicNo) {
        if (demographicNo == null || demographicNo.length() == 0) {
            return null;
        }
        return clientDao.getClientByDemographicNo(Integer.valueOf(demographicNo));
    }

    public List checkExistBedIntakeByFacility(Integer clientId, Integer facilityId){
    	Program[] programs =programDao.getBedPrograms(facilityId);
    	if(programs==null || programs.length==0) return new ArrayList();
    	return intakeDao.checkExistBedIntakeByPrograms(clientId, programs);
    }
    
    public List getClientFamilyByIntakeId(String intakeId) {
        if (intakeId == null || intakeId.length() == 0) {
            return null;
        }
        
        //avoid to join intake_family/intake table and demegraphic table for query
        List lst = intakeDao.getClientIntakeFamily(intakeId);
        
        if(lst==null) return null;

        List relationships = lookupDao.LoadCodeList("FRA",true, null, null);
        for(Object element : lst) {
       	  QuatroIntakeFamily obj=(QuatroIntakeFamily)element;
          for(Object element2 : relationships) {
        	LookupCodeValue obj2= (LookupCodeValue)element2;  
       	    if(obj2.getCode().equals(obj.getRelationship())){
        	  obj.setRelationshipDesc(obj2.getDescription());
       	      break;
       	    }
          }  
        }
        
        StringBuilder sb= new StringBuilder();
    	for(Object element : lst) {
    	  QuatroIntakeFamily obj=(QuatroIntakeFamily)element;
          sb.append("," +  obj.getClientId().toString());          
        }

    	List lst2 = clientDao.getClientFamilyByDemographicNo(sb.substring(1));
    	for(Object element2 : lst2) {
    	  Demographic dmg= (Demographic)element2;
          for(Object element : lst) {
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
      		  obj.setDupliDemographicNo(0);
    		  break;
    		}  
          }
    	}
        return lst;	
    }

	public OptionList LoadOptionsList() {
        List lst=intakeDao.LoadOptionsList();
        OptionList lst2= new OptionList();
        HashMap<Integer, String> map= IntakeConstant.getPrefixDefined();

        ArrayList[] lst3 = new ArrayList[11];
        for(int i=0;i<11;i++){
           lst3[i]= new ArrayList();
           LabelValueBean obj2 = new LabelValueBean();
   	       obj2.setLabel("  ");
       	   obj2.setValue("");
           lst3[i].add(obj2);
        }
        
        boolean getRec;
        for(int i=0;i<11;i++){
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

	public List getQuatroIntakeHeaderListByFacility(Integer clientId, Integer facilityId, String providerNo) {
        List lst= programDao.getProgramIdsByProvider(providerNo, facilityId);
        if (lst.size()==0) return lst;
        StringBuilder sb = new StringBuilder();
        for(Object element: lst){
          Object[] obj = (Object[])element;
          sb.append("," + ((Integer)obj[0]).toString());	
        }
		List lst2 = intakeDao.getQuatroIntakeHeaderList(clientId, sb.substring(1));
        for(Object element2: lst2){
          QuatroIntakeHeader obj2 = (QuatroIntakeHeader)element2;
          for(Object element3: lst){
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
		return intakeDao.saveQuatroIntake(intake, false);
	}

	public void saveQuatroIntakeFamilyHead(QuatroIntakeFamily intakeFamily) {
		intakeDao.saveQuatroIntakeFamilyRelation(intakeFamily);
	}

	public ArrayList saveQuatroIntakeFamily(Demographic client, QuatroIntake intake, QuatroIntakeFamily intakeFamily) {
		ArrayList lst = new ArrayList();
		clientDao.saveClient(client);
		if(intake.getId().intValue()==0){
		  Integer intekeId=intakeDao.findQuatroIntake(client.getDemographicNo(), intake.getProgramId());
		  if(intekeId.intValue()==0){
		    intake.setId(intekeId);
		    intake.setClientId(client.getDemographicNo());
		    intake.setIntakeStatus(KeyConstants.INTAKE_STATUS_ACTIVE);
		    intake.setCreatedOn(Calendar.getInstance());
		    List lst2 = intakeDao.saveQuatroIntake(intake, true);
		    intakeFamily.setIntakeId((Integer)lst2.get(0));
		  }else{
		    //find existing active intake with same clientId and program Id.
		    intakeFamily.setIntakeId(intekeId);
		  }
		}  
		intakeDao.saveQuatroIntakeFamilyRelation(intakeFamily);
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

}
