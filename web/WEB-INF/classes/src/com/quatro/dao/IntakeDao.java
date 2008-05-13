package com.quatro.dao;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.HashSet;
import java.util.HashMap;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import java.util.ArrayList;

import com.quatro.common.KeyConstants;
import com.quatro.model.QuatroIntakeOptionValue;
import com.quatro.web.intake.IntakeConstant;

import org.oscarehr.PMmodule.model.ClientReferral;
import org.oscarehr.PMmodule.model.ProgramQueue;
import org.oscarehr.PMmodule.model.QuatroIntakeAnswer;
import org.oscarehr.PMmodule.model.QuatroIntakeDB;
import org.oscarehr.PMmodule.model.QuatroIntake;
import org.oscarehr.PMmodule.model.QuatroIntakeFamily;
import org.oscarehr.PMmodule.model.Program;

import oscar.MyDateFormat;


public class IntakeDao extends HibernateDaoSupport {

	public List LoadOptionsList() {
		String sSQL="from QuatroIntakeOptionValue s order by s.prefix, s.displayOrder";		
        return getHibernateTemplate().find(sSQL);
		}

    public List checkExistBedIntakeByPrograms(Integer clientId, Program[] programs){
        StringBuilder sb = new StringBuilder();
        Object[] obj= new Object[programs.length+1];
        obj[0]=clientId;
        for(int i=1;i<=programs.length;i++){
           sb.append(",?");
           obj[i]=programs[i-1].getId();
        }

    	String sSQL="from QuatroIntakeDB i where i.clientId = ?" +
		        " and i.programId in (" + sb.substring(1) + ") and i.intakeStatus='" + 
		          KeyConstants.INTAKE_STATUS_ACTIVE + "'";
		
    	List result = getHibernateTemplate().find(sSQL, obj);
	    return result;
    }
		
	public Integer findQuatroIntake(Integer clientId, Integer programId) {
		List result = getHibernateTemplate().find("select i.id" +
					" from QuatroIntakeDB i where i.clientId = ?" +
					" and i.programId=? and i.intakeStatus='" + 
					KeyConstants.INTAKE_STATUS_ACTIVE + "'", 
					new Object[] {clientId, programId});
		if(result.size()>0)
		  return (Integer)result.get(0);
		else
		  return 0;
		
	}

    public QuatroIntakeDB getQuatroIntakeDBByQueueId(Integer queueId) {
		List result = getHibernateTemplate().find("from QuatroIntakeDB i where i.queueId = ?" +
					" and i.intakeStatus='" + 
					KeyConstants.INTAKE_STATUS_ACTIVE + "'", 
					new Object[] {queueId});
		if(result.size()>0)
		  return (QuatroIntakeDB)result.get(0);
		else
		  return null;
		
	}
    public QuatroIntakeDB getQuatroIntakeDBByIntakeId(Integer intakeId) {
		List result = getHibernateTemplate().find("from QuatroIntakeDB i where i.id = ?" +
					" and i.intakeStatus='" + 
					KeyConstants.INTAKE_STATUS_ACTIVE + "'", 
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

		    intake.setCreatedOn(intakeDb.getCreatedOn());
    	    Calendar cal= intakeDb.getCreatedOn();
    	    intake.setCreatedOnTxt(String.valueOf(cal.get(Calendar.YEAR)) + "/" + 
    				  String.valueOf(cal.get(Calendar.MONTH)+1) + "/" +  
    				  String.valueOf(cal.get(Calendar.DATE)));
            
    	    //intake for bed program, add/update referral and queue records.
    	    intake.setReferralId(intakeDb.getReferralId());
    	    intake.setQueueId(intakeDb.getQueueId());

			for(QuatroIntakeAnswer obj: intakeDb.getAnswers()){
	        	switch(obj.getIntakeNodeId()){
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
			         intake.setSourceIncome(obj.getValue());  
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
	        	   case IntakeConstant.PROGRAM:
			         intake.setProgramId(Integer.valueOf(obj.getValue()));  
			         break;
			         
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
        StringBuilder sb = new StringBuilder();
        Object[] obj= new Object[split.length+1];
        obj[0]=clientId;
        for(int i=1;i<=split.length;i++){
           sb.append(",?");
           obj[i]=Integer.valueOf(split[i-1]);
        }
        String sSQL="from QuatroIntakeHeader i where i.clientId = ? and i.programId in (" +
          sb.substring(1) + ") order by i.createdOn desc";
		List results = getHibernateTemplate().find(sSQL, obj);
		
		return results;
	}
		
	public List getQuatroIntakeHeaderListByFacility(Integer clientId, Integer facilityId, String providerNo) {
//		select * from Intake i where i.client_id = 200492 and i.program_id in 
//		(select p.program_id from program p, program_provider q where p.facility_id =200058 
//and p.program_id=q.program_id and q.provider_no=999998) order by i.creation_date desc
		List results = getHibernateTemplate().find("from QuatroIntakeHeader i where i.clientId = ? and i.programId in " +
			"(select p.id from Program p, ProgramProvider q where p.facilityId =?" + 
			" and p.id= q.ProgramId and q.ProviderNo=?) order by i.createdOn desc",
			new Object[] {clientId, facilityId, providerNo });

		return results;
	}
	
	public List getClientIntakeFamily(String intakeId){
		String sSQL="select a.intakeHeadId from QuatroIntakeFamily a " +
		  " WHERE a.intakeId = ? and a.memberStatus='" + 
		  KeyConstants.INTAKE_STATUS_ACTIVE + "'";
		
		List lst = getHibernateTemplate().find(sSQL, new Object[] {Integer.valueOf(intakeId)});
		if(lst.size()==0) return null;
		
		Integer intakeHeadId = (Integer)lst.get(0);
		
		String sSQL2="select new org.oscarehr.PMmodule.model.QuatroIntakeFamily(" +
		  " b.clientId, a.intakeHeadId, a.intakeId, a.relationship," +
		  "a.joinFamilyDate) " +
		  " from QuatroIntakeFamily a, QuatroIntakeHeader b "+
		  " WHERE a.intakeHeadId = ? and a.intakeId = b.id and a.memberStatus='" + 
		  KeyConstants.INTAKE_STATUS_ACTIVE + "' order by a.relationship";
		
		List result = getHibernateTemplate().find(sSQL2, new Object[] {Integer.valueOf(intakeHeadId)});
		return result;
	}
	
	public QuatroIntakeDB getQuatroIntakeDB (Integer intakeId){
		QuatroIntakeDB intakeDb =null;
		QuatroIntake intake = this.getQuatroIntake(intakeId);
		 if(intake.getId().intValue()>0){
			  List result = getHibernateTemplate().find("from QuatroIntakeAnswer i where i.intake2.id = ?",
					  new Object[] {intake.getId()});
			  
			  for(int i=0;i<result.size();i++){
				  QuatroIntakeAnswer obj2=(QuatroIntakeAnswer)result.get(i);			      
			      if (i==0){
			    	intakeDb = obj2.getIntake2();	
			    	intakeDb.setProgramType(intake.getProgramType());
			      }	      
		      }
		 }else{
			 	intakeDb = new QuatroIntakeDB();
			    intakeDb.setId(intake.getId());
			    
			    intakeDb.setClientId(intake.getClientId());
			    intakeDb.setCreatedOn(intake.getCreatedOn());
			    intakeDb.setProgramId(intake.getProgramId());
			    intakeDb.setStaffId(intake.getStaffId());

			    intakeDb.setProgramType(intake.getProgramType());

			    //intake for bed program, add/update referral and queue records.
			    intakeDb.setReferralId(intake.getReferralId());
			    intakeDb.setQueueId(intake.getQueueId());
		}

		return intakeDb;
	}
	
		//bFamilyMember=true for family member intake
	//bFamilyMember=false for individual person or family head intake
	public ArrayList saveQuatroIntake(QuatroIntake intake, boolean bFamilyMember){
	    QuatroIntakeDB intakeDb= null;
	    
		Set<QuatroIntakeAnswer> obj= new HashSet<QuatroIntakeAnswer>();//TreeSet<QuatroIntakeAnswer>();

	    HashMap<Integer, String> hData= new HashMap<Integer, String>();
		hData.put(IntakeConstant.CREATEDON, intake.getCreatedOnTxt());

		//Referred by
		hData.put(IntakeConstant.REFERREDBY, intake.getReferredBy());
		hData.put(IntakeConstant.CONTACTNAME, intake.getContactName());
		hData.put(IntakeConstant.CONTACTNUMBER, intake.getContactNumber());
		hData.put(IntakeConstant.CONTACTEMAIL, intake.getContactEmail());

  		//Other information
		hData.put(IntakeConstant.LANGUAGE, intake.getLanguage());
		hData.put(IntakeConstant.YOUTH, intake.getYouth());
		hData.put(IntakeConstant.ABORIGINAL, intake.getAboriginal());
		hData.put(IntakeConstant.ABORIGINALOTHER, intake.getAboriginalOther());
		hData.put(IntakeConstant.VAW, intake.getVAW());
		hData.put(IntakeConstant.CURSLEEPARRANGEMENT, intake.getCurSleepArrangement());
		hData.put(IntakeConstant.INSHELTERBEFORE, intake.getInShelterBefore());
		hData.put(IntakeConstant.LENGTHOFHOMELESS, intake.getLengthOfHomeless());
		hData.put(IntakeConstant.REASONFORHOMELESS, intake.getReasonForHomeless());

        //Presenting issues
		hData.put(IntakeConstant.PREGNANT, intake.getPregnant());
		hData.put(IntakeConstant.DISCLOSEDABUSE, intake.getDisclosedAbuse());
		hData.put(IntakeConstant.DISABILITY, intake.getDisability());
		hData.put(IntakeConstant.OBSERVEDABUSE, intake.getObservedAbuse());
		hData.put(IntakeConstant.DISCLOSEDMENTALISSUE, intake.getDisclosedMentalIssue());
		hData.put(IntakeConstant.POORHYGIENE, intake.getPoorHygiene());
		hData.put(IntakeConstant.OBSERVEDMENTALISSUE, intake.getObservedMentalIssue());
		hData.put(IntakeConstant.DISCLOSEDALCOHOLABUSE, intake.getDisclosedAlcoholAbuse());
		hData.put(IntakeConstant.OBSERVEDALCOHOLABUSE, intake.getObservedAlcoholAbuse());
		
		//Identification
		hData.put(IntakeConstant.BIRTHCERTIFICATE, intake.getBirthCertificate());
		hData.put(IntakeConstant.BIRTHCERTIFICATEYN, intake.getBirthCertificateYN());
		hData.put(IntakeConstant.SIN, intake.getSIN());
		hData.put(IntakeConstant.SINYN, intake.getSINYN());
		hData.put(IntakeConstant.HEALTHCARDNO, intake.getHealthCardNo());
		hData.put(IntakeConstant.HEALTHCARDNOYN, intake.getHealthCardNoYN());
		hData.put(IntakeConstant.DRIVERLICENSENO, intake.getDriverLicenseNo());
		hData.put(IntakeConstant.DRIVERLICENSENOYN, intake.getDriverLicenseNoYN());
		hData.put(IntakeConstant.CITIZENCARDNO, intake.getCitizenCardNo());
		hData.put(IntakeConstant.CITIZENCARDNOYN, intake.getCitizenCardNoYN());
		hData.put(IntakeConstant.NATIVERESERVENO, intake.getNativeReserveNo());
		hData.put(IntakeConstant.NATIVERESERVENOYN, intake.getNativeReserveNoYN());
		hData.put(IntakeConstant.VETERANNO, intake.getVeteranNo());
		hData.put(IntakeConstant.VETERANNOYN, intake.getVeteranNoYN());
		hData.put(IntakeConstant.RECORDLANDING, intake.getRecordLanding());
		hData.put(IntakeConstant.RECORDLANDINGYN, intake.getRecordLandingYN());
		hData.put(IntakeConstant.LIBRARYCARD, intake.getLibraryCard());
		hData.put(IntakeConstant.LIBRARYCARDYN, intake.getLibraryCardYN());
		hData.put(IntakeConstant.IDOTHER, intake.getIdOther());
		
		//Additional information
		hData.put(IntakeConstant.SOURCEINCOME, intake.getSourceIncome());
		hData.put(IntakeConstant.INCOME, intake.getIncome());
		hData.put(IntakeConstant.INCOMEWORKERNAME1, intake.getIncomeWorkerName1());
		hData.put(IntakeConstant.INCOMEWORKERPHONE1, intake.getIncomeWorkerPhone1());
		hData.put(IntakeConstant.INCOMEWORKEREMAIL1, intake.getIncomeWorkerEmail1());
		hData.put(IntakeConstant.INCOMEWORKERNAME2, intake.getIncomeWorkerName2());
		hData.put(IntakeConstant.INCOMEWORKERPHONE2, intake.getIncomeWorkerPhone2());
		hData.put(IntakeConstant.INCOMEWORKEREMAIL2, intake.getIncomeWorkerEmail2());
		hData.put(IntakeConstant.INCOMEWORKERNAME3, intake.getIncomeWorkerName3());
		hData.put(IntakeConstant.INCOMEWORKERPHONE3, intake.getIncomeWorkerPhone3());
		hData.put(IntakeConstant.INCOMEWORKEREMAIL3, intake.getIncomeWorkerEmail3());
		hData.put(IntakeConstant.LIVEDBEFORE, intake.getLivedBefore());
		hData.put(IntakeConstant.LIVEDBEFOREOTHER, intake.getLivedBeforeOther());
		hData.put(IntakeConstant.STATUSINCANADA, intake.getStatusInCanada());
		hData.put(IntakeConstant.ORIGINALCOUNTRY, intake.getOriginalCountry());
		hData.put(IntakeConstant.REFERREDTO, intake.getReferredTo());
		hData.put(IntakeConstant.REASONNOADMIT, intake.getReasonNoAdmit());

		//Program
		hData.put(IntakeConstant.PROGRAM, intake.getProgramId().toString());

		//Comments
		hData.put(IntakeConstant.COMMENTS, intake.getComments());
		
		
        if(intake.getId().intValue()>0){
		  List result = getHibernateTemplate().find("from QuatroIntakeAnswer i where i.intake2.id = ?",
				  new Object[] {intake.getId()});
		  
		  for(int i=0;i<result.size();i++){
			  QuatroIntakeAnswer obj2=(QuatroIntakeAnswer)result.get(i);
		      obj2.setValue(hData.get(obj2.getIntakeNodeId()));
		      if (i==0){
		    	intakeDb = obj2.getIntake2();
			    intakeDb.setProgramType(intake.getProgramType());
		      }
		      obj.add(obj2);
	      }

		}else{
			intakeDb = new QuatroIntakeDB();
		    intakeDb.setId(intake.getId());
		    intakeDb.setIntakeStatus(intake.getIntakeStatus());
		    intakeDb.setClientId(intake.getClientId());
		    intakeDb.setCreatedOn(intake.getCreatedOn());
		    intakeDb.setProgramId(intake.getProgramId());
		    intakeDb.setStaffId(intake.getStaffId());

		    intakeDb.setProgramType(intake.getProgramType());

		    //intake for bed program, add/update referral and queue records.
		    intakeDb.setReferralId(intake.getReferralId());
		    intakeDb.setQueueId(intake.getQueueId());
			
			
		   for(int i=1;i<IntakeConstant.TOTALITEMS-1;i++){
			 obj.add(new QuatroIntakeAnswer(i, hData.get(i)));
		   }
		}

        intakeDb.setAnswers(obj);
		
        ClientReferral referral = new ClientReferral();
        if(!bFamilyMember){
          if(intake.getClientId()!=null) referral.setClientId(intake.getClientId().longValue());
          referral.setNotes("Intake Automated referral");
          if(intake.getProgramId()!=null) referral.setProgramId(intake.getProgramId().longValue());
          referral.setProviderNo(intake.getStaffId());
          referral.setReferralDate(new Date());
          referral.setStatus(ClientReferral.STATUS_ACTIVE);
        }
        
        ProgramQueue queue = new ProgramQueue();
        if(!bFamilyMember){
          queue.setClientId(referral.getClientId());
          queue.setNotes(referral.getNotes());
          queue.setProgramId(referral.getProgramId());
          queue.setProviderNo(Long.parseLong(referral.getProviderNo()));
          queue.setReferralDate(referral.getReferralDate());
          queue.setStatus(ProgramQueue.STATUS_ACTIVE);
          queue.setReferralId(referral.getId());
          queue.setTemporaryAdmission(referral.isTemporaryAdmission());
          queue.setPresentProblems(referral.getPresentProblems());
        }

        if(intakeDb.getId().intValue()>0){
		  getHibernateTemplate().update(intakeDb);
	      if(!bFamilyMember){
            //delete old referral and queue records linked to this intake
		    if(intakeDb.getReferralId() != null &&  intakeDb.getReferralId().intValue()>0){
		      ClientReferral referralOld = new ClientReferral(Long.valueOf(intakeDb.getReferralId().longValue()));
		      referralOld.setClientId(Long.valueOf(intakeDb.getClientId().longValue()));
		      referralOld.setProgramId(Long.valueOf(intakeDb.getProgramId().longValue()));
		      getHibernateTemplate().delete(referralOld);
            }  
            if(intakeDb.getQueueId() != null && intakeDb.getQueueId().intValue()>0){
		      ProgramQueue queueOld = new ProgramQueue(Long.valueOf(intakeDb.getQueueId().longValue()));
		      queueOld.setClientId(Long.valueOf(intakeDb.getClientId().longValue()));
		      queueOld.setProviderNo(Long.valueOf(intakeDb.getStaffId()));
		      queueOld.setProgramId(Long.valueOf(intakeDb.getProgramId().longValue()));
		      getHibernateTemplate().delete(queueOld);
            }
          
            //add referral and queue records linked to this intake for bed program
		    if(intakeDb.getProgramType().equals(KeyConstants.BED_PROGRAM_TYPE)){
		      getHibernateTemplate().save(referral);
		      queue.setReferralId(referral.getId());
	          getHibernateTemplate().save(queue);
		    }
	      }
		}else{
		  getHibernateTemplate().save(intakeDb);
	      if(!bFamilyMember){
		    if(intakeDb.getProgramType().equals(KeyConstants.BED_PROGRAM_TYPE)){
		      getHibernateTemplate().save(referral);
		      queue.setReferralId(referral.getId());
              getHibernateTemplate().save(queue);
		    }
	      }
		}
        
        ArrayList<Integer> lst = new ArrayList<Integer>();
        if(!bFamilyMember){
          if(referral.getId()!=null) intakeDb.setReferralId(Integer.valueOf(referral.getId().intValue()));
          if(queue.getId()!=null) intakeDb.setQueueId(Integer.valueOf(queue.getId().intValue()));

          lst.add(intakeDb.getId());
          lst.add(intakeDb.getReferralId());
          lst.add(intakeDb.getQueueId());
        }else{
            lst.add(intakeDb.getId());
            lst.add(null);
            lst.add(null);
        }

        return lst;
	}

	public void saveQuatroIntakeFamilyRelation(QuatroIntakeFamily intakeFamily){
        getHibernateTemplate().saveOrUpdate(intakeFamily);
	}
	
}
