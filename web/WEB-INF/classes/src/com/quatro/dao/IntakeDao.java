package com.quatro.dao;

import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.HashSet;
import java.util.HashMap;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import java.util.ArrayList;

import com.quatro.common.KeyConstants;
import com.quatro.dao.LookupDao;
import com.quatro.web.intake.IntakeConstant;

import org.oscarehr.PMmodule.dao.MergeClientDao;
import org.oscarehr.PMmodule.model.ClientReferral;
import org.oscarehr.PMmodule.model.ProgramQueue;
import org.oscarehr.PMmodule.model.QuatroIntakeAnswer;
import org.oscarehr.PMmodule.model.QuatroIntakeDB;
import org.oscarehr.PMmodule.model.QuatroIntake;
import org.oscarehr.PMmodule.model.QuatroIntakeFamily;
import org.oscarehr.PMmodule.model.Program;

import oscar.MyDateFormat;


public class IntakeDao extends HibernateDaoSupport {

	private MergeClientDao mergeClientDao;
	public List LoadOptionsList() {
		String sSQL="from QuatroIntakeOptionValue s order by s.prefix, s.displayOrder";		
        return getHibernateTemplate().find(sSQL);
	}

    public List checkExistBedIntakeByPrograms(Integer clientId, List programs){
        StringBuffer sb = new StringBuffer();
        Object[] obj= new Object[programs.size()];
        obj[0]=((Program)programs.get(0)).getId();
        sb.append("?");
        for(int i=1;i<programs.size();i++){
           sb.append(",?");
           obj[i]=((Program)programs.get(i)).getId();
        }
        //client Merge
        String clientIds=mergeClientDao.getMergedClientIds(clientId);
    	String sSQL="from QuatroIntakeDB i where i.clientId in " +clientIds+ 
		        " and i.programId in (" + sb.toString() + ") and i.intakeStatus='" + 
		          KeyConstants.INTAKE_STATUS_ACTIVE + "'";
		
    	List result = getHibernateTemplate().find(sSQL, obj);
	    return result;
    }
		
	public QuatroIntakeDB findQuatroIntakeDB(Integer clientId, Integer programId) {
		String clientIds=mergeClientDao.getMergedClientIds(clientId);
		List result = getHibernateTemplate().find("from QuatroIntakeDB i where i.clientId in "+clientIds +
					" and i.programId=? and i.intakeStatus='" + 
					KeyConstants.INTAKE_STATUS_ACTIVE + "'", programId);
		if(result.size()>0)
		  return (QuatroIntakeDB)result.get(0);
		else
		  return null;
		
	}

    //from queueId of family intake, you may get any family memebr's intakeId, then get family head's intakeId and return it.
	public QuatroIntakeDB getQuatroIntakeDBByQueueId(Integer queueId) {
		List result = getHibernateTemplate().find("from QuatroIntakeDB i where i.queueId = ?" +
					" and i.intakeStatus='" + 
					KeyConstants.INTAKE_STATUS_ACTIVE + "'", 
					new Object[] {queueId});
		if(result.size()>0){
			QuatroIntakeDB qdb = (QuatroIntakeDB)result.get(0);
			Integer intakeId = qdb.getId(); 
			String sSQL="select a.intakeHeadId from QuatroIntakeFamily a WHERE a.intakeId = ?";
			List lst = getHibernateTemplate().find(sSQL, new Object[] {intakeId});
			
			if(lst.size()==0){
			   return qdb;  //not family intake
			}else{
			   Integer intakeHeadId=(Integer)lst.get(0);
			   List lst2 = getHibernateTemplate().find("from QuatroIntakeDB i where i.id = ?",
						new Object[] {intakeHeadId});
			   return (QuatroIntakeDB)lst2.get(0);
			}
		}else{
		  return null;
		}
		
	}
    
    public List getQuatroIntakeDBByIntakeIds(String intakeIds) {
        String[] split= intakeIds.split(",");
        StringBuffer sb = new StringBuffer();
        Object[] obj= new Integer[split.length];
        for(int i=0;i<split.length;i++){
           sb.append(",?");
           obj[i]=Integer.valueOf(split[i]);
        }

        List result = getHibernateTemplate().find("from QuatroIntakeDB i where i.id in (" +
				      sb.substring(1) + ")", obj);

		return result;
		
	}

    public QuatroIntakeDB getQuatroIntakeDBByIntakeId(Integer intakeId) {
		List result = getHibernateTemplate().find("from QuatroIntakeDB i where i.id = ?",
					new Object[] {intakeId});
		if(result.size()>0)
		  return (QuatroIntakeDB)result.get(0);
		else
		  return null;
		
	}
    
	public QuatroIntake getQuatroIntake(Integer intakeId) {
/*
		List result = getHibernateTemplate().find("select i.id, i.clientId, " +
			"i.staffId, i.createdOn, i.intakeStatus, p.type, i.programId," +
			"i.referralId, i.queueId, i.answers " + 
			" from QuatroIntakeDB i, Program p where i.id = ?" +
			" and p.id=i.programId",
		  new Object[] {intakeId});
*/		
		List result = getHibernateTemplate().find("from QuatroIntakeDB i where i.id = ?",
			  new Object[] {intakeId});

		List result2 = getHibernateTemplate().find("select p.type, p.name " +
				" from QuatroIntakeDB i, Program p where i.id = ?" +
				" and p.id=i.programId",
				  new Object[] {intakeId});

		if(result.size()==0){
		  return null;
		}else{
/*			
			Object oo[] = (Object[]) result.get(0);
			QuatroIntakeDB intakeDb = new QuatroIntakeDB(); 
			intakeDb.setId((Integer)oo[0]);
			intakeDb.setClientId((Integer)oo[1]);
			intakeDb.setStaffId((String)oo[2]);
			intakeDb.setCreatedOn((Calendar)oo[3]);
			intakeDb.setIntakeStatus((String)oo[4]);
			intakeDb.setProgramType((String)oo[5]);
			intakeDb.setProgramId((Integer)oo[6]);
			intakeDb.setReferralId((Integer)oo[7]);
			intakeDb.setQueueId((Integer)oo[8]);
			intakeDb.setAnswers((Set)oo[9]);
*/
			QuatroIntakeDB intakeDb= (QuatroIntakeDB)result.get(0);
			Object oo[] = (Object[]) result2.get(0);
			intakeDb.setProgramType((String)oo[0]);
			
			QuatroIntake intake= new QuatroIntake();
		    intake.setId(intakeDb.getId());
		    intake.setClientId(intakeDb.getClientId());
		    intake.setProgramId(intakeDb.getProgramId());
		    intake.setIntakeStatus(intakeDb.getIntakeStatus());
		    intake.setStaffId(intakeDb.getStaffId());
		    intake.setProgramType(intakeDb.getProgramType());
		    intake.setProgramName((String)oo[1]);
		    Calendar edt = intakeDb.getEndDate();
		    intake.setEndDate(edt);
		    if (null != edt) {
		    	intake.setEndDateTxt(String.valueOf(edt.get(Calendar.YEAR)) + "/" +
  				  String.valueOf(edt.get(Calendar.MONTH)+1) + "/" +  
				  String.valueOf(edt.get(Calendar.DATE)));
		    }
		    intake.setCreatedOn(intakeDb.getCreatedOn());
    	    Calendar cal= intakeDb.getCreatedOn();
    	    intake.setCreatedOnTxt(String.valueOf(cal.get(Calendar.YEAR)) + "/" + 
    				  String.valueOf(cal.get(Calendar.MONTH)+1) + "/" +  
    				  String.valueOf(cal.get(Calendar.DATE)));
            
    	    //intake for bed program, add/update referral and queue records.
    	    intake.setReferralId(intakeDb.getReferralId());
    	    intake.setQueueId(intakeDb.getQueueId());

			Iterator it = intakeDb.getAnswers().iterator();
    	    while(it.hasNext()){
    	    	QuatroIntakeAnswer obj = (QuatroIntakeAnswer)it.next();
    	    
	        	switch(obj.getIntakeNodeId().intValue()){
	        	   //Referered by
	        	   case IntakeConstant.REFERREDBY:
		        	 intake.setReferredBy(obj.getValue());  
		          	 break;
	        	   case IntakeConstant.CONTACTNAME:
		        	 intake.setContactName(obj.getValue());  
		          	 break;
	        	   case IntakeConstant.CONTACTNUMBER:
		        	 intake.setContactNumber(obj.getValue());  
		          	 break;
	        	   case IntakeConstant.CONTACTEMAIL:
		        	 intake.setContactEmail(obj.getValue());  
		          	 break;

	        	   //Other information
	        	   case IntakeConstant.LANGUAGE:
		        	 intake.setLanguage(obj.getValue());  
		          	 break;
	        	   case IntakeConstant.YOUTH:
		        	 intake.setYouth(obj.getValue());  
		          	 break;
	        	   case IntakeConstant.ABORIGINAL:
	        		 intake.setAboriginal(obj.getValue());  
	        		 break;
	        	   case IntakeConstant.ABORIGINALOTHER:
		        	 intake.setAboriginalOther(obj.getValue());  
		          	 break;
	        	   case IntakeConstant.VAW:
		        	 intake.setVAW(obj.getValue());  
		          	 break;
	        	   case IntakeConstant.CURSLEEPARRANGEMENT:
		        	 intake.setCurSleepArrangement(obj.getValue());  
		          	 break;
	        	   case IntakeConstant.INSHELTERBEFORE:
		        	 intake.setInShelterBefore(obj.getValue());  
		          	 break;
	        	   case IntakeConstant.LENGTHOFHOMELESS:
			         intake.setLengthOfHomeless(obj.getValue());  
			         break;
	        	   case IntakeConstant.REASONFORHOMELESS:
			         intake.setReasonForHomeless(obj.getValue());  
			         break;
	        	   case IntakeConstant.REASONFORSERVICE:
				     intake.setReasonForService(obj.getValue());  
				     break;
	          		 
	          	   //Presenting issues
	        	   case IntakeConstant.PREGNANT:
			         intake.setPregnant(obj.getValue());  
			         break;
	        	   case IntakeConstant.DISCLOSEDABUSE:
			         intake.setDisclosedAbuse(obj.getValue());  
			         break;
	        	   case IntakeConstant.DISABILITY:
			         intake.setDisability(obj.getValue());  
			         break;
	        	   case IntakeConstant.OBSERVEDABUSE:
			         intake.setObservedAbuse(obj.getValue());  
			         break;
	        	   case IntakeConstant.DISCLOSEDMENTALISSUE:
			         intake.setDisclosedMentalIssue(obj.getValue());  
			         break;
	        	   case IntakeConstant.POORHYGIENE:
			         intake.setPoorHygiene(obj.getValue());  
			         break;
	        	   case IntakeConstant.OBSERVEDMENTALISSUE:
			         intake.setObservedMentalIssue(obj.getValue());  
			         break;
	        	   case IntakeConstant.DISCLOSEDALCOHOLABUSE:
			         intake.setDisclosedAlcoholAbuse(obj.getValue());  
			         break;
	        	   case IntakeConstant.OBSERVEDALCOHOLABUSE:
			         intake.setObservedAlcoholAbuse(obj.getValue());  
			         break;

                   //Identification
	        	   case IntakeConstant.BIRTHCERTIFICATE:
			         intake.setBirthCertificate(obj.getValue());  
			         break;
	        	   case IntakeConstant.BIRTHCERTIFICATEYN:
			         intake.setBirthCertificateYN(obj.getValue());  
			         break;
	        	   case IntakeConstant.SIN:
			         intake.setSIN(obj.getValue());  
			         break;
	        	   case IntakeConstant.SINYN:
			         intake.setSINYN(obj.getValue());  
			         break;
	        	   case IntakeConstant.HEALTHCARDNO:
			         intake.setHealthCardNo(obj.getValue());  
			         break;
	        	   case IntakeConstant.HEALTHCARDNOYN:
			         intake.setHealthCardNoYN(obj.getValue());  
			         break;
	        	   case IntakeConstant.DRIVERLICENSENO:
			         intake.setDriverLicenseNo(obj.getValue());  
			         break;
	        	   case IntakeConstant.DRIVERLICENSENOYN:
			         intake.setDriverLicenseNoYN(obj.getValue());  
			         break;
	        	   case IntakeConstant.CITIZENCARDNO:
			         intake.setCitizenCardNo(obj.getValue());  
			         break;
	        	   case IntakeConstant.CITIZENCARDNOYN:
			         intake.setCitizenCardNoYN(obj.getValue());  
			         break;
	        	   case IntakeConstant.NATIVERESERVENO:
			         intake.setNativeReserveNo(obj.getValue());  
			         break;
	        	   case IntakeConstant.NATIVERESERVENOYN:
			         intake.setNativeReserveNoYN(obj.getValue());  
			         break;

	        	   case IntakeConstant.VETERANNO:
			         intake.setVeteranNo(obj.getValue());  
			         break;
	        	   case IntakeConstant.VETERANNOYN:
			         intake.setVeteranNoYN(obj.getValue());  
			         break;
	        	   case IntakeConstant.RECORDLANDING:
			         intake.setRecordLanding(obj.getValue());  
			         break;
	        	   case IntakeConstant.RECORDLANDINGYN:
			         intake.setRecordLandingYN(obj.getValue());  
			         break;
	        	   case IntakeConstant.LIBRARYCARD:
			         intake.setLibraryCard(obj.getValue());  
			         break;
	        	   case IntakeConstant.LIBRARYCARDYN:
			         intake.setLibraryCardYN(obj.getValue());  
			         break;
	        	   case IntakeConstant.IDOTHER:
			         intake.setIdOther(obj.getValue());  
			         break;
	        	   
	        	   //Additional information
			       case IntakeConstant.SOURCEINCOME:
			    	 if(obj.getValue()!=null){
			           intake.setSourceIncome(obj.getValue().split(","));
			    	 }else{
				       intake.setSourceIncome(new String[0]);
			    	 }
			         break;
	        	   case IntakeConstant.INCOME:
			         intake.setIncome(obj.getValue());  
			         break;

	        	   case IntakeConstant.INCOMEWORKERNAME1:
			         intake.setIncomeWorkerName1(obj.getValue());  
			         break;
	        	   case IntakeConstant.INCOMEWORKERPHONE1:
			         intake.setIncomeWorkerPhone1(obj.getValue());  
			         break;
	        	   case IntakeConstant.INCOMEWORKEREMAIL1:
			         intake.setIncomeWorkerEmail1(obj.getValue());  
			         break;
	        	   case IntakeConstant.INCOMEWORKERNAME2:
			         intake.setIncomeWorkerName2(obj.getValue());  
			         break;
	        	   case IntakeConstant.INCOMEWORKERPHONE2:
			         intake.setIncomeWorkerPhone2(obj.getValue());  
			         break;
	        	   case IntakeConstant.INCOMEWORKEREMAIL2:
			         intake.setIncomeWorkerEmail2(obj.getValue());  
			         break;
	        	   case IntakeConstant.INCOMEWORKERNAME3:
			         intake.setIncomeWorkerName3(obj.getValue());  
			         break;
	        	   case IntakeConstant.INCOMEWORKERPHONE3:
			         intake.setIncomeWorkerPhone3(obj.getValue());  
			         break;
	        	   case IntakeConstant.INCOMEWORKEREMAIL3:
			         intake.setIncomeWorkerEmail3(obj.getValue());  
			         break;
	        	   case IntakeConstant.LIVEDBEFORE:
			         intake.setLivedBefore(obj.getValue());  
			         break;
	        	   case IntakeConstant.LIVEDBEFOREOTHER:
			         intake.setLivedBeforeOther(obj.getValue());  
			         break;
	        	   case IntakeConstant.STATUSINCANADA:
			         intake.setStatusInCanada(obj.getValue());  
			         break;
	        	   case IntakeConstant.ORIGINALCOUNTRY:
			         intake.setOriginalCountry(obj.getValue());  
			         break;
	        	   case IntakeConstant.REFERREDTO:
			         intake.setReferredTo(obj.getValue());  
			         break;
	        	   case IntakeConstant.REASONNOADMIT:
			         intake.setReasonNoAdmit(obj.getValue());  
			         break;

	        	   //Program
//	        	   case IntakeConstant.PROGRAM:
//			         intake.setProgramId(Integer.valueOf(obj.getValue()));  
//			         break;
			         
			       //Comments
	        	   case IntakeConstant.COMMENTS:
			         intake.setComments(obj.getValue());  
			         break;
			         
	        	}
	        }
	        
	        return intake;
			
		}
		
	}
	
	public List getQuatroIntakeHeaderList(Integer clientId, String programIds) {
// Quatro Shelter doesn't use program_provider table any more, use secuserrole table.		
//		List results = getHibernateTemplate().find("from QuatroIntakeHeader i where i.clientId = ? and i.programId in " +
//			"(select p.id from Program p, ProgramProvider q where p.facilityId =?" + 
//			" and p.id= q.ProgramId and q.ProviderNo=?) order by i.createdOn desc",
//			new Object[] {clientId, facilityId, providerNo });
        String[] split= programIds.split(",");
        StringBuffer sb = new StringBuffer();
        Object[] obj= new Object[split.length];
        obj[0]=Integer.valueOf(split[0]);
        sb.append("?");
        for(int i=1;i<split.length;i++){
           sb.append(",?");           
           obj[i]=Integer.valueOf(split[i]);
        }
        String clientIds =mergeClientDao.getMergedClientIds(clientId);
        String sSQL="from QuatroIntakeHeader i where i.clientId in " + clientIds+
        		" and i.programId in (" +
          sb.toString() + ") order by i.createdOn desc";
        
		List results = getHibernateTemplate().find(sSQL, obj);
		
		return results;
	}
		
	public List getQuatroIntakeHeaderListByFacility(Integer clientId, Integer shelterId, String providerNo) {

		List results = null;
		String clientIds=mergeClientDao.getMergedClientIds(clientId);
	    String progSQL = com.quatro.util.Utility.getUserOrgQueryString(providerNo, shelterId);

	    results = getHibernateTemplate().find("from QuatroIntakeHeader i where i.clientId in " + clientIds+" and i.programId in " + progSQL +
	    		"  order by i.createdOn desc");
		return results;
	}
	
	public Integer getIntakeFamilyHeadId(String intakeId){
		String sSQL="select a.intakeHeadId from QuatroIntakeFamily a " +
		  " WHERE a.intakeId = ?";

		List lst = getHibernateTemplate().find(sSQL, new Object[] {Integer.valueOf(intakeId)});
		if(lst.size()==0) return new Integer(0);
		
		return (Integer)lst.get(0);
	}
	
	public List getClientIntakeFamily(String intakeId){
		String sSQL="select a.intakeHeadId from QuatroIntakeFamily a " +
		  " WHERE a.intakeId = ?";

		List lst = getHibernateTemplate().find(sSQL, new Object[] {Integer.valueOf(intakeId)});
		if(lst.size()==0) return new ArrayList();
		
		Integer intakeHeadId = (Integer)lst.get(0);
		
		String sSQL2="select new org.oscarehr.PMmodule.model.QuatroIntakeFamily(" +
		  " b.clientId, a.intakeHeadId, a.intakeId, a.relationship," +
		  "a.joinFamilyDate) " +
		  " from QuatroIntakeFamily a, QuatroIntakeHeader b "+
		  " WHERE a.intakeHeadId = ? and a.intakeId = b.id order by a.relationship";

		List result = getHibernateTemplate().find(sSQL2, new Object[] {intakeHeadId});
		return result;
	}
	
	//bFamilyMember=true for family member intake
	//bFamilyMember=false for individual person or family head intake
	public ArrayList saveQuatroIntake(QuatroIntake intake, boolean bFamilyMember){
	    QuatroIntakeDB intakeDb= null;
	    
		Set obj= new HashSet();//TreeSet();

	    HashMap hData= new HashMap();

		//Referred by
		hData.put(new Integer(IntakeConstant.REFERREDBY), intake.getReferredBy());
		hData.put(new Integer(IntakeConstant.CONTACTNAME), intake.getContactName());
		hData.put(new Integer(IntakeConstant.CONTACTNUMBER), intake.getContactNumber());
		hData.put(new Integer(IntakeConstant.CONTACTEMAIL), intake.getContactEmail());

  		//Other information
		hData.put(new Integer(IntakeConstant.LANGUAGE), intake.getLanguage());
		hData.put(new Integer(IntakeConstant.YOUTH), intake.getYouth());
		hData.put(new Integer(IntakeConstant.ABORIGINAL), intake.getAboriginal());
		hData.put(new Integer(IntakeConstant.ABORIGINALOTHER), intake.getAboriginalOther());
		hData.put(new Integer(IntakeConstant.VAW), intake.getVAW());
		hData.put(new Integer(IntakeConstant.CURSLEEPARRANGEMENT), intake.getCurSleepArrangement());
		hData.put(new Integer(IntakeConstant.INSHELTERBEFORE), intake.getInShelterBefore());
		hData.put(new Integer(IntakeConstant.LENGTHOFHOMELESS), intake.getLengthOfHomeless());
		hData.put(new Integer(IntakeConstant.REASONFORHOMELESS), intake.getReasonForHomeless());
		hData.put(new Integer(IntakeConstant.REASONFORSERVICE), intake.getReasonForService());

        //Presenting issues
		hData.put(new Integer(IntakeConstant.PREGNANT), intake.getPregnant());
		hData.put(new Integer(IntakeConstant.DISCLOSEDABUSE), intake.getDisclosedAbuse());
		hData.put(new Integer(IntakeConstant.DISABILITY), intake.getDisability());
		hData.put(new Integer(IntakeConstant.OBSERVEDABUSE), intake.getObservedAbuse());
		hData.put(new Integer(IntakeConstant.DISCLOSEDMENTALISSUE), intake.getDisclosedMentalIssue());
		hData.put(new Integer(IntakeConstant.POORHYGIENE), intake.getPoorHygiene());
		hData.put(new Integer(IntakeConstant.OBSERVEDMENTALISSUE), intake.getObservedMentalIssue());
		hData.put(new Integer(IntakeConstant.DISCLOSEDALCOHOLABUSE), intake.getDisclosedAlcoholAbuse());
		hData.put(new Integer(IntakeConstant.OBSERVEDALCOHOLABUSE), intake.getObservedAlcoholAbuse());
		
		//Identification
		hData.put(new Integer(IntakeConstant.BIRTHCERTIFICATE), intake.getBirthCertificate());
		hData.put(new Integer(IntakeConstant.BIRTHCERTIFICATEYN), intake.getBirthCertificateYN());
		hData.put(new Integer(IntakeConstant.SIN), intake.getSIN());
		hData.put(new Integer(IntakeConstant.SINYN), intake.getSINYN());
		hData.put(new Integer(IntakeConstant.HEALTHCARDNO), intake.getHealthCardNo());
		hData.put(new Integer(IntakeConstant.HEALTHCARDNOYN), intake.getHealthCardNoYN());
		hData.put(new Integer(IntakeConstant.DRIVERLICENSENO), intake.getDriverLicenseNo());
		hData.put(new Integer(IntakeConstant.DRIVERLICENSENOYN), intake.getDriverLicenseNoYN());
		hData.put(new Integer(IntakeConstant.CITIZENCARDNO), intake.getCitizenCardNo());
		hData.put(new Integer(IntakeConstant.CITIZENCARDNOYN), intake.getCitizenCardNoYN());
		hData.put(new Integer(IntakeConstant.NATIVERESERVENO), intake.getNativeReserveNo());
		hData.put(new Integer(IntakeConstant.NATIVERESERVENOYN), intake.getNativeReserveNoYN());
		hData.put(new Integer(IntakeConstant.VETERANNO), intake.getVeteranNo());
		hData.put(new Integer(IntakeConstant.VETERANNOYN), intake.getVeteranNoYN());
		hData.put(new Integer(IntakeConstant.RECORDLANDING), intake.getRecordLanding());
		hData.put(new Integer(IntakeConstant.RECORDLANDINGYN), intake.getRecordLandingYN());
		hData.put(new Integer(IntakeConstant.LIBRARYCARD), intake.getLibraryCard());
		hData.put(new Integer(IntakeConstant.LIBRARYCARDYN), intake.getLibraryCardYN());
		hData.put(new Integer(IntakeConstant.IDOTHER), intake.getIdOther());
		
		//Additional information
		String sSourceIncome="";
		for(int i=0;i<intake.getSourceIncome().length;i++){
			sSourceIncome = sSourceIncome + "," + intake.getSourceIncome()[i];
		}
		if(sSourceIncome.length()>0){
		   hData.put(new Integer(IntakeConstant.SOURCEINCOME), sSourceIncome.substring(1));
		}else{
		   hData.put(new Integer(IntakeConstant.SOURCEINCOME), "");
		}
		
		hData.put(new Integer(IntakeConstant.INCOME), intake.getIncome());
		hData.put(new Integer(IntakeConstant.INCOMEWORKERNAME1), intake.getIncomeWorkerName1());
		hData.put(new Integer(IntakeConstant.INCOMEWORKERPHONE1), intake.getIncomeWorkerPhone1());
		hData.put(new Integer(IntakeConstant.INCOMEWORKEREMAIL1), intake.getIncomeWorkerEmail1());
		hData.put(new Integer(IntakeConstant.INCOMEWORKERNAME2), intake.getIncomeWorkerName2());
		hData.put(new Integer(IntakeConstant.INCOMEWORKERPHONE2), intake.getIncomeWorkerPhone2());
		hData.put(new Integer(IntakeConstant.INCOMEWORKEREMAIL2), intake.getIncomeWorkerEmail2());
		hData.put(new Integer(IntakeConstant.INCOMEWORKERNAME3), intake.getIncomeWorkerName3());
		hData.put(new Integer(IntakeConstant.INCOMEWORKERPHONE3), intake.getIncomeWorkerPhone3());
		hData.put(new Integer(IntakeConstant.INCOMEWORKEREMAIL3), intake.getIncomeWorkerEmail3());
		hData.put(new Integer(IntakeConstant.LIVEDBEFORE), intake.getLivedBefore());
		hData.put(new Integer(IntakeConstant.LIVEDBEFOREOTHER), intake.getLivedBeforeOther());
		hData.put(new Integer(IntakeConstant.STATUSINCANADA), intake.getStatusInCanada());
		hData.put(new Integer(IntakeConstant.ORIGINALCOUNTRY), intake.getOriginalCountry());
		hData.put(new Integer(IntakeConstant.REFERREDTO), intake.getReferredTo());
		hData.put(new Integer(IntakeConstant.REASONNOADMIT), intake.getReasonNoAdmit());

		//Comments
		hData.put(new Integer(IntakeConstant.COMMENTS), intake.getComments());
		
        if(intake.getId().intValue()>0){
		  List result = getHibernateTemplate().find("from QuatroIntakeAnswer i where i.intake2.id = ?",
				  new Object[] {intake.getId()});
		  
		  for(int i=0;i<result.size();i++){
			  QuatroIntakeAnswer obj2=(QuatroIntakeAnswer)result.get(i);
		      obj2.setValue((String)hData.get(obj2.getIntakeNodeId()));
		      if (i==0){
		    	intakeDb = obj2.getIntake2();
			    intakeDb.setProgramId(intake.getProgramId());
			    intakeDb.setProgramType(intake.getProgramType());
			    intakeDb.setEndDate(intake.getEndDate());
			    intakeDb.setLastUpdateDate(intake.getLastUpdateDate());
		      }
		      obj.add(obj2);
	      }

		}else{
			intakeDb = new QuatroIntakeDB();
		    intakeDb.setId(intake.getId());
		    intakeDb.setIntakeStatus(intake.getIntakeStatus());
		    intakeDb.setClientId(intake.getClientId());
		    intakeDb.setCreatedOn(intake.getCreatedOn());
		    intakeDb.setEndDate(intake.getEndDate());
		    intakeDb.setLastUpdateDate(intake.getLastUpdateDate());
		    intakeDb.setProgramId(intake.getProgramId());
		    intakeDb.setStaffId(intake.getStaffId());

		    intakeDb.setProgramType(intake.getProgramType());

		    //intake for bed program, add/update referral and queue records.
		    intakeDb.setReferralId(intake.getReferralId());
		    if(bFamilyMember){
		       intakeDb.setQueueId(new Integer(0));
		    }else{
		       intakeDb.setQueueId(intake.getQueueId());
		    }
			
			
		   for(int i=1;i<IntakeConstant.TOTALITEMS;i++){
			 obj.add(new QuatroIntakeAnswer(i, (String)hData.get(new Integer(i))));
		   }
		}

        intakeDb.setAnswers(obj);
		
        ClientReferral referral = new ClientReferral();
        if(!bFamilyMember){
          if(intake.getClientId()!=null) referral.setClientId(intake.getClientId());
          referral.setNotes("Intake Automated referral");
          if(intake.getProgramId()!=null) referral.setProgramId(intake.getProgramId());
          referral.setProviderNo(intake.getStaffId());
          referral.setReferralDate(new Date());
          referral.setStatus(KeyConstants.STATUS_ACTIVE);
        }
        
        ProgramQueue queue = new ProgramQueue();
        if(!bFamilyMember){
          queue.setClientId(referral.getClientId());
          queue.setNotes(referral.getNotes());
          queue.setProgramId(referral.getProgramId());
          queue.setProviderNo(Integer.valueOf(referral.getProviderNo()));
          queue.setReferralDate(referral.getReferralDate());
          queue.setReferralId(referral.getId());
        }
        ArrayList lst = new ArrayList();
        try{
        //existing intake
        if(intakeDb.getId().intValue()>0){
		  getHibernateTemplate().update(intakeDb);
/*		  
	      if(!bFamilyMember){
            //delete old referral and queue records linked to this intake
		    if(intakeDb.getReferralId() != null &&  intakeDb.getReferralId().intValue()>0){
		      ClientReferral referralOld = new ClientReferral(Integer.valueOf(intakeDb.getReferralId().intValue()));
		      referralOld.setClientId(intakeDb.getClientId());
		      referralOld.setProgramId(Integer.valueOf(intakeDb.getProgramId().intValue()));
		      getHibernateTemplate().delete(referralOld);
            }  
            if(intakeDb.getQueueId() != null && intakeDb.getQueueId().intValue()>0){
		      ProgramQueue queueOld = new ProgramQueue(Integer.valueOf(intakeDb.getQueueId().intValue()));
		      queueOld.setClientId(intakeDb.getClientId());
		      queueOld.setProviderNo(Integer.getInteger(intakeDb.getStaffId()));
		      queueOld.setProgramId(Integer.valueOf(intakeDb.getProgramId().intValue()));
		      getHibernateTemplate().delete(queueOld);
            }
          
            //add referral and queue records linked to this intake for bed program
		    if(intakeDb.getProgramType().equals(KeyConstants.BED_PROGRAM_TYPE)){
		      getHibernateTemplate().save(referral);
		      queue.setReferralId(referral.getId());
	          getHibernateTemplate().save(queue);
		    }
	      }
*/	      
		//new intake
        }else{
		  getHibernateTemplate().save(intakeDb);

		  //do this for single person intake and family head only. 
		  if(!bFamilyMember){
	    	//from manual referral
	    	if(intake.getReferralId()!=null && intake.getReferralId().intValue()>0){
	          //delete manual referral's referral# and queue# if this new intake selects non-bed program.
	    	  //non-bed program intake don't have admission, so no referral# and queue#.	
	    	  if(!intakeDb.getProgramType().equals(KeyConstants.BED_PROGRAM_TYPE)){
	             getHibernateTemplate().bulkUpdate("delete ClientReferral r where r.Id=?", intake.getReferralId());
	             if(intake.getQueueId()!=null) getHibernateTemplate().bulkUpdate("delete ProgramQueue q where q.Id=?", intake.getQueueId());
	          }
	    	//new intake not from manual referral
	    	}else{
		       if(intakeDb.getProgramType().equals(KeyConstants.BED_PROGRAM_TYPE)){
		         getHibernateTemplate().save(referral);
		         queue.setReferralId(referral.getId());
                 getHibernateTemplate().save(queue);
		       }
	    	}
	      }
		  
		}
        
        
      
        if(!bFamilyMember){
          if(referral.getId()!=null) intakeDb.setReferralId(new Integer(referral.getId().intValue()));
          if(queue.getId()!=null) intakeDb.setQueueId(new Integer(queue.getId().intValue()));

          lst.add(intakeDb.getId());
          lst.add(intakeDb.getReferralId());
          lst.add(intakeDb.getQueueId());
        }else{
            lst.add(intakeDb.getId());
            lst.add(null);
            lst.add(null);
        }
        }catch(Exception e){
        	String err =e.getMessage();
        }
        return lst;
	}

	public void saveQuatroIntakeFamilyRelation(QuatroIntakeFamily intakeFamily){
        getHibernateTemplate().saveOrUpdate(intakeFamily);
	}

	//used for family intake add/remove family member
	public void updateReferralIdQueueId(QuatroIntakeDB intake, Integer referralId, Integer queueId){
        String sSQL="update QuatroIntakeDB q set q.referralId=?, q.queueId=? where q.id=?";
		getHibernateTemplate().bulkUpdate(sSQL, new Object[]{referralId, queueId, intake.getId()});
	}
	public void deleteReferralIdQueueId(Integer referralId, Integer queueId){
        String sSQL="delete ClientReferral r where r.Id=?";
		getHibernateTemplate().bulkUpdate(sSQL, new Object[]{referralId});
        sSQL="delete ProgramQueue q where q.Id=?";
		getHibernateTemplate().bulkUpdate(sSQL, new Object[]{queueId});
	}
	
	public void removeInactiveIntakeFamilyMember(String sInactiveDependents){
        String[] split= sInactiveDependents.split(",");
        StringBuffer sb = new StringBuffer();
        Object[] obj= new Integer[split.length];
        for(int i=0;i<split.length;i++){
           sb.append(",?");
           obj[i]=Integer.valueOf(split[i]);
        }
        String sSQL="delete QuatroIntakeFamily p where p.intakeId in (" + sb.substring(1) + ")";
		getHibernateTemplate().bulkUpdate(sSQL, obj);
	}

	public void setIntakeStatusAdmitted(Integer intakeId){
        String sSQL="update QuatroIntakeDB q set q.intakeStatus='" + 
        KeyConstants.INTAKE_STATUS_ADMITTED + "' where q.id=?";
		getHibernateTemplate().bulkUpdate(sSQL, new Object[]{intakeId});
	}

	public void setMergeClientDao(MergeClientDao mergeClientDao) {
		this.mergeClientDao = mergeClientDao;
	}
	private LookupDao lookupDao; 
	public void setLookupDao(LookupDao lookupDao) {
		this.lookupDao = lookupDao;
	}
	
}
