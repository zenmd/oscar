package com.quatro.dao;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

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

import oscar.MyDateFormat;


public class IntakeDao extends HibernateDaoSupport {

	public List LoadOptionsList() {
		String sSQL="from QuatroIntakeOptionValue s order by s.prefix, s.displayOrder";		
        return getHibernateTemplate().find(sSQL);
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

		List result2 = getHibernateTemplate().find("select p.type " +
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
			intakeDb.setProgramType((String)result2.get(0));
			
			QuatroIntake intake= new QuatroIntake();
		    intake.setId(intakeDb.getId());
		    intake.setClientId(intakeDb.getClientId());
		    intake.setProgramId(intakeDb.getProgramId());
		    intake.setStaffId(intakeDb.getStaffId());
		    intake.setProgramType(intakeDb.getProgramType());

		    intake.setCreatedOn(intakeDb.getCreatedOn());
    	    Calendar cal= intakeDb.getCreatedOn();
    	    intake.setCreatedOnTxt(String.valueOf(cal.get(Calendar.YEAR)) + "-" + 
    				  String.valueOf(cal.get(Calendar.MONTH)+1) + "-" +  
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
		        	 intake.setAboriginalOther(obj.getValue());  
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

//	        	   case IntakeConstant.CREATEDON:
//			         intake.setCreatedOnTxt(obj.getValue());  
//			 	     intake.setCreatedOn(MyDateFormat.getCalendar(intake.getCreatedOnTxt())); 
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

	public List getQuatroIntakeHeaderListByFacility(Integer clientId, Integer facilityId, String providerNo) {
//		select * from Intake i where i.client_id = 200492 and i.program_id in 
//		(select p.program_id from program p, program_provider q where p.facility_id =200058 
//and p.program_id=q.program_id and q.provider_no=999998) order by i.creation_date desc
		List results = getHibernateTemplate().find("from QuatroIntakeHeader i where i.clientId = ? and i.programId in " +
			"(select p.id from Program p, ProgramProvider q where p.facilityId =?" + 
			" and p.id= q.ProgramId and q.ProviderNo=?) order by i.createdOn desc",
			new Object[] {clientId, new Long(facilityId.longValue()), providerNo });

		return results;
	}

	public ArrayList saveQuatroIntake(QuatroIntake intake){
	    QuatroIntakeDB intakeDb= new QuatroIntakeDB();
	    
	    intakeDb.setId(intake.getId());
	    
	    intakeDb.setClientId(intake.getClientId());
	    intakeDb.setCreatedOn(intake.getCreatedOn());
	    intakeDb.setProgramId(intake.getProgramId());
	    intakeDb.setStaffId(intake.getStaffId());

	    intakeDb.setProgramType(intake.getProgramType());

	    //intake for bed program, add/update referral and queue records.
	    intakeDb.setReferralId(intake.getReferralId());
	    intakeDb.setQueueId(intake.getQueueId());
	    
	    
		Set<QuatroIntakeAnswer> obj= new TreeSet<QuatroIntakeAnswer>();

		int i=1;

		obj.add(new QuatroIntakeAnswer(IntakeConstant.CREATEDON, intake.getCreatedOnTxt(), i++));

		//Referred by
		obj.add(new QuatroIntakeAnswer(IntakeConstant.REFERREDBY, intake.getReferredBy(), i++));
		obj.add(new QuatroIntakeAnswer(IntakeConstant.CONTACTNAME, intake.getContactName(), i++));
		obj.add(new QuatroIntakeAnswer(IntakeConstant.CONTACTNUMBER, intake.getContactNumber(), i++));
		obj.add(new QuatroIntakeAnswer(IntakeConstant.CONTACTEMAIL, intake.getContactEmail(), i++));

		//Other information
		obj.add(new QuatroIntakeAnswer(IntakeConstant.LANGUAGE, intake.getLanguage(), i++));
		obj.add(new QuatroIntakeAnswer(IntakeConstant.YOUTH, intake.getYouth(), i++));
		obj.add(new QuatroIntakeAnswer(IntakeConstant.ABORIGINAL, intake.getAboriginal(), i++));
		obj.add(new QuatroIntakeAnswer(IntakeConstant.ABORIGINALOTHER, intake.getAboriginalOther(), i++));
		obj.add(new QuatroIntakeAnswer(IntakeConstant.VAW, intake.getVAW(), i++));
		obj.add(new QuatroIntakeAnswer(IntakeConstant.CURSLEEPARRANGEMENT, intake.getCurSleepArrangement(), i++));
		obj.add(new QuatroIntakeAnswer(IntakeConstant.INSHELTERBEFORE, intake.getInShelterBefore(), i++));
		obj.add(new QuatroIntakeAnswer(IntakeConstant.LENGTHOFHOMELESS, intake.getLengthOfHomeless(), i++));
		obj.add(new QuatroIntakeAnswer(IntakeConstant.REASONFORHOMELESS, intake.getReasonForHomeless(), i++));

        //Presenting issues
		obj.add(new QuatroIntakeAnswer(IntakeConstant.PREGNANT, intake.getPregnant(), i++));
		obj.add(new QuatroIntakeAnswer(IntakeConstant.DISCLOSEDABUSE, intake.getDisclosedAbuse(), i++));
		obj.add(new QuatroIntakeAnswer(IntakeConstant.DISABILITY, intake.getDisability(), i++));
		obj.add(new QuatroIntakeAnswer(IntakeConstant.OBSERVEDABUSE, intake.getObservedAbuse(), i++));
		obj.add(new QuatroIntakeAnswer(IntakeConstant.DISCLOSEDMENTALISSUE, intake.getDisclosedMentalIssue(), i++));
		obj.add(new QuatroIntakeAnswer(IntakeConstant.POORHYGIENE, intake.getPoorHygiene(), i++));
		obj.add(new QuatroIntakeAnswer(IntakeConstant.OBSERVEDMENTALISSUE, intake.getObservedMentalIssue(), i++));
		obj.add(new QuatroIntakeAnswer(IntakeConstant.DISCLOSEDALCOHOLABUSE, intake.getDisclosedAlcoholAbuse(), i++));
		obj.add(new QuatroIntakeAnswer(IntakeConstant.OBSERVEDALCOHOLABUSE, intake.getObservedAlcoholAbuse(), i++));
		
		//Identification
		obj.add(new QuatroIntakeAnswer(IntakeConstant.BIRTHCERTIFICATE, intake.getBirthCertificate(), i++));
		obj.add(new QuatroIntakeAnswer(IntakeConstant.BIRTHCERTIFICATEYN, intake.getBirthCertificateYN(), i++));
		obj.add(new QuatroIntakeAnswer(IntakeConstant.SIN, intake.getSIN(), i++));
		obj.add(new QuatroIntakeAnswer(IntakeConstant.SINYN, intake.getSINYN(), i++));
		obj.add(new QuatroIntakeAnswer(IntakeConstant.HEALTHCARDNO, intake.getHealthCardNo(), i++));
		obj.add(new QuatroIntakeAnswer(IntakeConstant.HEALTHCARDNOYN, intake.getHealthCardNoYN(), i++));
		obj.add(new QuatroIntakeAnswer(IntakeConstant.DRIVERLICENSENO, intake.getDriverLicenseNo(), i++));
		obj.add(new QuatroIntakeAnswer(IntakeConstant.DRIVERLICENSENOYN, intake.getDriverLicenseNoYN(), i++));
		obj.add(new QuatroIntakeAnswer(IntakeConstant.CITIZENCARDNO, intake.getCitizenCardNo(), i++));
		obj.add(new QuatroIntakeAnswer(IntakeConstant.CITIZENCARDNOYN, intake.getCitizenCardNoYN(), i++));
		obj.add(new QuatroIntakeAnswer(IntakeConstant.NATIVERESERVENO, intake.getNativeReserveNo(), i++));
		obj.add(new QuatroIntakeAnswer(IntakeConstant.NATIVERESERVENOYN, intake.getNativeReserveNoYN(), i++));
		obj.add(new QuatroIntakeAnswer(IntakeConstant.VETERANNO, intake.getVeteranNo(), i++));
		obj.add(new QuatroIntakeAnswer(IntakeConstant.VETERANNOYN, intake.getVeteranNoYN(), i++));
		obj.add(new QuatroIntakeAnswer(IntakeConstant.RECORDLANDING, intake.getRecordLanding(), i++));
		obj.add(new QuatroIntakeAnswer(IntakeConstant.RECORDLANDINGYN, intake.getRecordLandingYN(), i++));
		obj.add(new QuatroIntakeAnswer(IntakeConstant.LIBRARYCARD, intake.getLibraryCard(), i++));
		obj.add(new QuatroIntakeAnswer(IntakeConstant.LIBRARYCARDYN, intake.getLibraryCardYN(), i++));
		obj.add(new QuatroIntakeAnswer(IntakeConstant.IDOTHER, intake.getIdOther(), i++));
		
		//Additional information
		obj.add(new QuatroIntakeAnswer(IntakeConstant.SOURCEINCOME, intake.getSourceIncome(), i++));
		obj.add(new QuatroIntakeAnswer(IntakeConstant.INCOME, intake.getIncome(), i++));
		obj.add(new QuatroIntakeAnswer(IntakeConstant.INCOMEWORKERNAME1, intake.getIncomeWorkerName1(), i++));
		obj.add(new QuatroIntakeAnswer(IntakeConstant.INCOMEWORKERPHONE1, intake.getIncomeWorkerPhone1(), i++));
		obj.add(new QuatroIntakeAnswer(IntakeConstant.INCOMEWORKEREMAIL1, intake.getIncomeWorkerEmail1(), i++));
		obj.add(new QuatroIntakeAnswer(IntakeConstant.INCOMEWORKERNAME2, intake.getIncomeWorkerName2(), i++));
		obj.add(new QuatroIntakeAnswer(IntakeConstant.INCOMEWORKERPHONE2, intake.getIncomeWorkerPhone2(), i++));
		obj.add(new QuatroIntakeAnswer(IntakeConstant.INCOMEWORKEREMAIL2, intake.getIncomeWorkerEmail2(), i++));
		obj.add(new QuatroIntakeAnswer(IntakeConstant.INCOMEWORKERNAME3, intake.getIncomeWorkerName3(), i++));
		obj.add(new QuatroIntakeAnswer(IntakeConstant.INCOMEWORKERPHONE3, intake.getIncomeWorkerPhone3(), i++));
		obj.add(new QuatroIntakeAnswer(IntakeConstant.INCOMEWORKEREMAIL3, intake.getIncomeWorkerEmail3(), i++));
		obj.add(new QuatroIntakeAnswer(IntakeConstant.LIVEDBEFORE, intake.getLivedBefore(), i++));
		obj.add(new QuatroIntakeAnswer(IntakeConstant.LIVEDBEFOREOTHER, intake.getLivedBeforeOther(), i++));
		obj.add(new QuatroIntakeAnswer(IntakeConstant.STATUSINCANADA, intake.getStatusInCanada(), i++));
		obj.add(new QuatroIntakeAnswer(IntakeConstant.ORIGINALCOUNTRY, intake.getOriginalCountry(), i++));
		obj.add(new QuatroIntakeAnswer(IntakeConstant.REFERREDTO, intake.getReferredTo(), i++));
		obj.add(new QuatroIntakeAnswer(IntakeConstant.REASONNOADMIT, intake.getReasonNoAdmit(), i++));

		//Program
//		obj.add(new QuatroIntakeAnswer(IntakeConstant.PROGRAM, intake.get.getProgram()));
		
		//Comments
		obj.add(new QuatroIntakeAnswer(IntakeConstant.COMMENTS, intake.getComments(), i++));
		
		intakeDb.setAnswers(obj);
		
        ClientReferral referral = new ClientReferral();
        if(intake.getClientId()!=null) referral.setClientId(intake.getClientId().longValue());
        referral.setNotes("Intake Automated referral");
        if(intake.getProgramId()!=null) referral.setProgramId(intake.getProgramId().longValue());
        referral.setProviderNo(intake.getStaffId());
        referral.setReferralDate(new Date());
        referral.setStatus(ClientReferral.STATUS_ACTIVE);
        
        ProgramQueue queue = new ProgramQueue();
        queue.setClientId(referral.getClientId());
        queue.setNotes(referral.getNotes());
        queue.setProgramId(referral.getProgramId());
        queue.setProviderNo(Long.parseLong(referral.getProviderNo()));
        queue.setReferralDate(referral.getReferralDate());
        queue.setStatus(ProgramQueue.STATUS_ACTIVE);
        queue.setReferralId(referral.getId());
        queue.setTemporaryAdmission(referral.isTemporaryAdmission());
        queue.setPresentProblems(referral.getPresentProblems());

//save referral		
/*
            ClientReferral referral = new ClientReferral();
            referral.setClientId(new Long(demographicNo));
            referral.setNotes("ER Automated referral\nConsent Type: " + consentFormBean.getConsentType() + "\nReason: " + consentFormBean.getConsentReason());
            referral.setProgramId(program.getProgramId().longValue());
            referral.setProviderNo(getProviderNo(request));
            referral.setReferralDate(new Date());
            referral.setStatus(ClientReferral.STATUS_ACTIVE);

 
        referralDAO.saveClientReferral(referral);

        if (referral.getStatus().equalsIgnoreCase(ClientReferral.STATUS_ACTIVE)) {
            ProgramQueue queue = new ProgramQueue();
            queue.setClientId(referral.getClientId());
            queue.setNotes(referral.getNotes());
            queue.setProgramId(referral.getProgramId());
            queue.setProviderNo(Long.parseLong(referral.getProviderNo()));
            queue.setReferralDate(referral.getReferralDate());
            queue.setStatus(ProgramQueue.STATUS_ACTIVE);
            queue.setReferralId(referral.getId());
            queue.setTemporaryAdmission(referral.isTemporaryAdmission());
            queue.setPresentProblems(referral.getPresentProblems());

            queueManager.saveProgramQueue(queue);
        }
*/	    

        if(intakeDb.getId().intValue()>0){
		  getHibernateTemplate().update(intakeDb);
		  
          //delete old referral and queue records linked to this intake
		  if(intakeDb.getReferralId().intValue()>0){
		    ClientReferral referralOld = new ClientReferral(Long.valueOf(intakeDb.getReferralId().longValue()));
		    referralOld.setClientId(Long.valueOf(intakeDb.getClientId().longValue()));
		    referralOld.setProgramId(Long.valueOf(intakeDb.getProgramId().longValue()));
		    getHibernateTemplate().delete(referralOld);
          }  
          if(intakeDb.getQueueId().intValue()>0){
		    ProgramQueue queueOld = new ProgramQueue(Long.valueOf(intakeDb.getQueueId().longValue()));
		    queueOld.setClientId(Long.valueOf(intakeDb.getClientId().longValue()));
		    queueOld.setProviderNo(Long.valueOf(intakeDb.getStaffId()));
		    queueOld.setProgramId(Long.valueOf(intakeDb.getProgramId().longValue()));
		    getHibernateTemplate().delete(queueOld);
          }
          
          //add referral and queue records linked to this intake for bed program
		  if(intakeDb.getProgramType().equals(KeyConstants.BED_PROGRAM_TYPE)){
//		    referral.setIntakeId(intakeDb.getId());
		    getHibernateTemplate().save(referral);
		    queue.setReferralId(referral.getId());
//		    queue.setIntakeId(intakeDb.getId());
	        getHibernateTemplate().save(queue);
		  }
		  
		}else{
		  getHibernateTemplate().save(intakeDb);
		  if(intakeDb.getProgramType().equals(KeyConstants.BED_PROGRAM_TYPE)){
//		    referral.setIntakeId(intakeDb.getId());
		    getHibernateTemplate().save(referral);
		    queue.setReferralId(referral.getId());
//		    queue.setIntakeId(intakeDb.getId());
            getHibernateTemplate().save(queue);
		  }
		}

        intakeDb.setReferralId(Integer.valueOf(referral.getId().intValue()));
        intakeDb.setQueueId(Integer.valueOf(queue.getId().intValue()));

        ArrayList<Integer> lst = new ArrayList<Integer>();
        lst.add(intakeDb.getId());
        lst.add(intakeDb.getReferralId());
        lst.add(intakeDb.getQueueId());
        
	    return lst;
	}
	
}
