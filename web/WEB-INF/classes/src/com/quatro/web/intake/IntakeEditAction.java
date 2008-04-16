package com.quatro.web.intake;

import java.util.Calendar;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;
import java.util.TreeSet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import org.apache.struts.util.LabelValueBean;

import org.oscarehr.PMmodule.web.formbean.QuatroIntakeEditForm;
import org.oscarehr.PMmodule.model.Demographic;

import org.oscarehr.PMmodule.service.ClientManager;
import com.quatro.service.LookupManager;
import com.quatro.service.IntakeManager;
import org.oscarehr.PMmodule.service.ProgramManager;
import com.quatro.common.LookupTagValue;
import com.quatro.common.KeyConstants;
import org.oscarehr.PMmodule.model.Program;
import org.oscarehr.PMmodule.model.QuatroIntake;
import org.oscarehr.PMmodule.model.QuatroIntakeAnswer;

public class IntakeEditAction extends DispatchAction {
    private ClientManager clientManager;
    private LookupManager lookupManager;
    private IntakeManager intakeManager;
    private ProgramManager programManager;
	
    public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
    	return edit(mapping,form,request,response);
	}

    public ActionForward update(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
    	QuatroIntakeEditForm qform = (QuatroIntakeEditForm) form;
        String clientId = qform.getClientId();
        
		Demographic client= clientManager.getClientByDemographicNo(clientId);
		qform.setClient(client);
        qform.setDob(client.getYearOfBirth() + "-" + client.getMonthOfBirth() + "-" + client.getDateOfBirth());
        
        qform.setLanguage(new LookupTagValue());
        qform.setOriginalCountry(new LookupTagValue());
        
        setOptionList(qform);

        Integer facilityId= (Integer)request.getSession().getAttribute(KeyConstants.SESSION_KEY_FACILITYID);
        ArrayList lst= (ArrayList)programManager.getProgramDomainInFacility(
        		(String)request.getSession().getAttribute(KeyConstants.SESSION_KEY_PROVIDERNO), 
        		new Long(facilityId.longValue()));
        ArrayList<LabelValueBean> lst2 = new ArrayList<LabelValueBean>();
        for(int i=0;i<lst.size();i++){
           Program obj = (Program)lst.get(i);
           lst2.add(new LabelValueBean(obj.getName(), obj.getId().toString()));
        }
        qform.setProgramList(lst2);
        setIntake(qform);
//        qform.setProgramList(new ArrayList());
        
		return mapping.findForward("edit");
	}
    
    public void setIntake(QuatroIntakeEditForm qform){
        Integer intakeId = Integer.valueOf(qform.getIntakeId());
        QuatroIntake obj=intakeManager.getQuatroIntake(intakeId);
        if(obj==null) return;
        for(QuatroIntakeAnswer obj2: obj.getAnswers()){
        	switch(obj2.getIntakeNodeId()){
        	   case IntakeConstant.ABORIGINAL:
        		 qform.setAboriginal(obj2.getValue());  
        		 break;
        	   case IntakeConstant.ABORIGINALOTHER:
          		 qform.setAboriginalOther(obj2.getValue());  
          		 break;
        	}
        }
    }
    
	public ActionForward edit(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
    	QuatroIntakeEditForm qform = (QuatroIntakeEditForm) form;
        String clientId = qform.getClientId();
		Demographic client= clientManager.getClientByDemographicNo(clientId);
		qform.setClient(client);
        qform.setDob(client.getYearOfBirth() + "-" + client.getMonthOfBirth() + "-" + client.getDateOfBirth());
        
        setOptionList(qform);
//        com.quatro.web.intake.OptionList optionValues = intakeManager.LoadOptionsList();
//		qform.setOptionList(optionValues);

		qform.setProgramList(new ArrayList());
        
		return mapping.findForward("edit");
	}

	private void setOptionList(QuatroIntakeEditForm qform){
        if(qform.getOptionList()==null){
		  com.quatro.web.intake.OptionList optionValues = intakeManager.LoadOptionsList();
		  qform.setOptionList(optionValues);
        }  
	}
	
    public ActionForward save(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
    	QuatroIntakeEditForm qform = (QuatroIntakeEditForm) form;
        String clientId = qform.getClientId();
		Demographic client= clientManager.getClientByDemographicNo(clientId);
		
		QuatroIntake obj= new QuatroIntake();
		obj.setId(0);
		obj.setClientId(Integer.valueOf(clientId));
		obj.setCreatedOn(Calendar.getInstance());
		obj.setProgramId(Integer.valueOf(qform.getProgram()));
		obj.setStaffId((String)request.getSession().getAttribute(KeyConstants.SESSION_KEY_PROVIDERNO));

		Set<QuatroIntakeAnswer> obj2= new TreeSet<QuatroIntakeAnswer>();

		//Referred by
		obj2.add(new QuatroIntakeAnswer(IntakeConstant.REFERREDBY, qform.getReferredBy()));
		obj2.add(new QuatroIntakeAnswer(IntakeConstant.CONTACTNAME, qform.getContactName()));
		obj2.add(new QuatroIntakeAnswer(IntakeConstant.CONTACTNUMBER, qform.getContactNumber()));
		obj2.add(new QuatroIntakeAnswer(IntakeConstant.CONTACTEMAIL, qform.getContactEmail()));

		//Other information
		obj2.add(new QuatroIntakeAnswer(IntakeConstant.LANGUAGE, request.getParameter("language_val")));
		obj2.add(new QuatroIntakeAnswer(IntakeConstant.YOUTH, qform.getYouth()));
		obj2.add(new QuatroIntakeAnswer(IntakeConstant.ABORIGINAL, qform.getAboriginal()));
		obj2.add(new QuatroIntakeAnswer(IntakeConstant.ABORIGINALOTHER, qform.getAboriginalOther()));
		obj2.add(new QuatroIntakeAnswer(IntakeConstant.VAW, qform.getVAW()));
		obj2.add(new QuatroIntakeAnswer(IntakeConstant.CURSLEEPARRANGEMENT, qform.getCurSleepArrangement()));
		obj2.add(new QuatroIntakeAnswer(IntakeConstant.INSHELTERBEFORE, qform.getInShelterBefore()));
		obj2.add(new QuatroIntakeAnswer(IntakeConstant.LENGTHOFHOMELESS, qform.getLengthOfHomeless()));
		obj2.add(new QuatroIntakeAnswer(IntakeConstant.REASONFORHOMELESS, qform.getReasonForHomeless()));

        //Presenting issues
		obj2.add(new QuatroIntakeAnswer(IntakeConstant.PREGNANT, qform.getPregnant()));
		obj2.add(new QuatroIntakeAnswer(IntakeConstant.DISCLOSEDABUSE, qform.getDisclosedAbuse()));
		obj2.add(new QuatroIntakeAnswer(IntakeConstant.DISABILITY, qform.getDisability()));
		obj2.add(new QuatroIntakeAnswer(IntakeConstant.OBSERVEDABUSE, qform.getObservedAbuse()));
		obj2.add(new QuatroIntakeAnswer(IntakeConstant.DISCLOSEDMENTALISSUE, qform.getDisclosedMentalIssue()));
		obj2.add(new QuatroIntakeAnswer(IntakeConstant.POORHYGIENE, qform.getPoorHygiene()));
		obj2.add(new QuatroIntakeAnswer(IntakeConstant.OBSERVEDMENTALISSUE, qform.getObservedMentalIssue()));
		obj2.add(new QuatroIntakeAnswer(IntakeConstant.DISCLOSEDALCOHOLABUSE, qform.getDisclosedAlcoholAbuse()));
		obj2.add(new QuatroIntakeAnswer(IntakeConstant.OBSERVEDALCOHOLABUSE, qform.getObservedAlcoholAbuse()));
		
		//Identification
		obj2.add(new QuatroIntakeAnswer(IntakeConstant.BIRTHCERTIFICATE, qform.getBirthCertificate()));
		obj2.add(new QuatroIntakeAnswer(IntakeConstant.BIRTHCERTIFICATEYN, qform.getBirthCertificateYN()));
		obj2.add(new QuatroIntakeAnswer(IntakeConstant.SIN, qform.getSIN()));
		obj2.add(new QuatroIntakeAnswer(IntakeConstant.SINYN, qform.getSINYN()));
		obj2.add(new QuatroIntakeAnswer(IntakeConstant.HEALTHCARDNO, qform.getHealthCardNo()));
		obj2.add(new QuatroIntakeAnswer(IntakeConstant.HEALTHCARDNOYN, qform.getHealthCardNoYN()));
		obj2.add(new QuatroIntakeAnswer(IntakeConstant.DRIVERLICENSENO, qform.getDriverLicenseNo()));
		obj2.add(new QuatroIntakeAnswer(IntakeConstant.DRIVERLICENSENOYN, qform.getDriverLicenseNoYN()));
		obj2.add(new QuatroIntakeAnswer(IntakeConstant.CITIZENCARDNO, qform.getCitizenCardNo()));
		obj2.add(new QuatroIntakeAnswer(IntakeConstant.CITIZENCARDNOYN, qform.getCitizenCardNoYN()));
		obj2.add(new QuatroIntakeAnswer(IntakeConstant.NATIVERESERVENO, qform.getNativeReserveNo()));
		obj2.add(new QuatroIntakeAnswer(IntakeConstant.NATIVERESERVENOYN, qform.getNativeReserveNoYN()));
		obj2.add(new QuatroIntakeAnswer(IntakeConstant.VETERANNO, qform.getVeteranNo()));
		obj2.add(new QuatroIntakeAnswer(IntakeConstant.VETERANNOYN, qform.getVeteranNoYN()));
		obj2.add(new QuatroIntakeAnswer(IntakeConstant.RECORDLANDING, qform.getRecordLanding()));
		obj2.add(new QuatroIntakeAnswer(IntakeConstant.RECORDLANDINGYN, qform.getRecordLandingYN()));
		obj2.add(new QuatroIntakeAnswer(IntakeConstant.LIBRARYCARD, qform.getLibraryCard()));
		obj2.add(new QuatroIntakeAnswer(IntakeConstant.LIBRARYCARDYN, qform.getLibraryCardYN()));
		obj2.add(new QuatroIntakeAnswer(IntakeConstant.IDOTHER, qform.getIdOther()));
		
		//Additional information
		obj2.add(new QuatroIntakeAnswer(IntakeConstant.SOURCEINCOME, qform.getSourceIncome()));
		obj2.add(new QuatroIntakeAnswer(IntakeConstant.INCOME, qform.getIncome()));
		obj2.add(new QuatroIntakeAnswer(IntakeConstant.INCOMEWORKERNAME1, qform.getIncomeWorkerName1()));
		obj2.add(new QuatroIntakeAnswer(IntakeConstant.INCOMEWORKERPHONE1, qform.getIncomeWorkerPhone1()));
		obj2.add(new QuatroIntakeAnswer(IntakeConstant.INCOMEWORKEREMAIL1, qform.getIncomeWorkerEmail1()));
		obj2.add(new QuatroIntakeAnswer(IntakeConstant.INCOMEWORKERNAME2, qform.getIncomeWorkerName2()));
		obj2.add(new QuatroIntakeAnswer(IntakeConstant.INCOMEWORKERPHONE2, qform.getIncomeWorkerPhone2()));
		obj2.add(new QuatroIntakeAnswer(IntakeConstant.INCOMEWORKEREMAIL2, qform.getIncomeWorkerEmail2()));
		obj2.add(new QuatroIntakeAnswer(IntakeConstant.INCOMEWORKERNAME3, qform.getIncomeWorkerName3()));
		obj2.add(new QuatroIntakeAnswer(IntakeConstant.INCOMEWORKERPHONE3, qform.getIncomeWorkerPhone3()));
		obj2.add(new QuatroIntakeAnswer(IntakeConstant.INCOMEWORKEREMAIL3, qform.getIncomeWorkerEmail3()));
		obj2.add(new QuatroIntakeAnswer(IntakeConstant.LIVEDBEFORE, qform.getLivedBefore()));
		obj2.add(new QuatroIntakeAnswer(IntakeConstant.LIVEDBEFOREOTHER, qform.getLivedBeforeOther()));
		obj2.add(new QuatroIntakeAnswer(IntakeConstant.STATUSINCANADA, qform.getStatusInCanada()));
		obj2.add(new QuatroIntakeAnswer(IntakeConstant.ORIGINALCOUNTRY, request.getParameter("originalCountry_val")));
		obj2.add(new QuatroIntakeAnswer(IntakeConstant.REFERREDTO, qform.getReferredTo()));
		obj2.add(new QuatroIntakeAnswer(IntakeConstant.REASONNOADMIT, qform.getReasonNoAdmit()));

		//Program
		obj2.add(new QuatroIntakeAnswer(IntakeConstant.PROGRAM, qform.getProgram()));
		
		//Comments
		obj2.add(new QuatroIntakeAnswer(IntakeConstant.COMMENTS, qform.getComments()));

		
		obj.setAnswers(obj2);
		intakeManager.saveQuatroIntake(obj);
		
		return mapping.findForward("edit");
	}
	
	public ClientManager getClientManager() {
		return clientManager;
	}

	public void setClientManager(ClientManager clientManager) {
		this.clientManager = clientManager;
	}

	public LookupManager getLookupManager() {
		return lookupManager;
	}

	public void setLookupManager(LookupManager lookupManager) {
		this.lookupManager = lookupManager;
	}

	public IntakeManager getIntakeManager() {
		return intakeManager;
	}

	public void setIntakeManager(IntakeManager intakeManager) {
		this.intakeManager = intakeManager;
	}

	public void setProgramManager(ProgramManager programManager) {
		this.programManager = programManager;
	}

}
