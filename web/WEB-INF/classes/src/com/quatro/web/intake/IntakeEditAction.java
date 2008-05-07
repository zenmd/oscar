package com.quatro.web.intake;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.action.ActionMessage;

import org.apache.struts.util.LabelValueBean;

import org.oscarehr.PMmodule.web.formbean.QuatroIntakeEditForm;
import org.oscarehr.PMmodule.exception.ServiceRestrictionException;
import org.oscarehr.PMmodule.model.Demographic;

import org.oscarehr.PMmodule.service.ClientManager;
import com.quatro.service.LookupManager;
import com.quatro.service.IntakeManager;
import org.oscarehr.PMmodule.service.ProgramManager;
import org.oscarehr.PMmodule.service.ClientRestrictionManager;
import com.quatro.common.KeyConstants;

import org.oscarehr.PMmodule.model.ClientReferral;
import org.oscarehr.PMmodule.model.Program;
import org.oscarehr.PMmodule.model.ProgramClientRestriction;
import org.oscarehr.PMmodule.model.QuatroIntake;
import com.quatro.model.LookupCodeValue;
import oscar.MyDateFormat;
import java.util.Calendar;

public class IntakeEditAction extends DispatchAction {
    private ClientManager clientManager;
    private LookupManager lookupManager;
    private IntakeManager intakeManager;
    private ProgramManager programManager;
    private ClientRestrictionManager clientRestrictionManager;
	
    public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
    	return update(mapping,form,request,response);
	}

    public ActionForward update(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
    	QuatroIntakeEditForm qform = (QuatroIntakeEditForm) form;

    	String clientId = qform.getClientId();
    	setClientIdParam(request,clientId);
        Demographic client = null;
        if ("0".equals(clientId))
        {
        	client= new Demographic();
        }
        else
        {
        	client= clientManager.getClientByDemographicNo(clientId);
        }
       
		qform.setClient(client);

		qform.setDob(client.getYearOfBirth() + "/" + MyDateFormat.formatMonthOrDay(client.getMonthOfBirth()) + "/" + MyDateFormat.formatMonthOrDay(client.getDateOfBirth()));
        
		com.quatro.web.intake.OptionList optionValues = intakeManager.LoadOptionsList();
  		qform.setOptionList(optionValues);

        Integer facilityId= (Integer)request.getSession().getAttribute(KeyConstants.SESSION_KEY_FACILITYID);
        ArrayList lst= (ArrayList)programManager.getProgramsByProvider( 
        		new Integer(facilityId.intValue()),(String)request.getSession().getAttribute(KeyConstants.SESSION_KEY_PROVIDERNO));
        ArrayList<LabelValueBean> lst2 = new ArrayList<LabelValueBean>();
        ArrayList<LabelValueBean> lst3 = new ArrayList<LabelValueBean>();
        for(int i=0;i<lst.size();i++){
           Program obj = (Program)lst.get(i);
           lst2.add(new LabelValueBean(obj.getName(), obj.getId().toString()));
           lst3.add(new LabelValueBean(obj.getType(), obj.getId().toString()));
        }
        qform.setProgramList(lst2);
        qform.setProgramTypeList(lst3);

        Integer intakeId = Integer.valueOf(qform.getIntakeId());
        
        QuatroIntake obj;
        if(intakeId.intValue()!=0){
            obj=intakeManager.getQuatroIntake(intakeId);
        }else{
        	obj= new QuatroIntake();
        	obj.setCreatedOn(Calendar.getInstance());
            obj.setId(intakeId);
            obj.setClientId(Integer.valueOf(qform.getClientId()));
            obj.setReferralId(0);
            obj.setQueueId(0);
    		obj.setYouth(KeyConstants.CONSTANT_NO);
    		obj.setVAW(KeyConstants.CONSTANT_NO);
        }

        obj.setCurrentProgramId(obj.getProgramId());
		qform.setIntake(obj);

        LookupCodeValue language;
        LookupCodeValue originalCountry;
        if(intakeId.intValue()!=0){
          language = lookupManager.GetLookupCode("LNG", obj.getLanguage());
          originalCountry = lookupManager.GetLookupCode("CNT", obj.getOriginalCountry());
        }else{
            language = new LookupCodeValue();
            originalCountry = new LookupCodeValue();
        }
        
        qform.setLanguage(language);
        qform.setOriginalCountry(originalCountry);
        
		return mapping.findForward("edit");
	}
    private void setClientIdParam(HttpServletRequest request, String clientId)
    {
        HashMap actionParam = (HashMap) request.getAttribute("actionParam");
        if(actionParam==null){
     	  actionParam = new HashMap();
           actionParam.put("id", clientId); 
        }
        request.setAttribute("actionParam", actionParam);
    }
/*    
    public ActionForward close(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
    	QuatroIntakeEditForm qform = (QuatroIntakeEditForm) form;
		QuatroIntake intake= qform.getIntake();
		Integer clientId = intake.getClientId();
		request.getSession().setAttribute("clientId", clientId);
    	return mapping.findForward("close");
	}
*/
    public ActionForward edit(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
    	return update(mapping,form,request,response);
	}

    public ActionForward save(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        ActionMessages messages = new ActionMessages();
        boolean isError = false;
        boolean isWarning = false;
    	QuatroIntakeEditForm qform = (QuatroIntakeEditForm) form;

    	Demographic client= qform.getClient();
    	String clientId = String.valueOf(client.getDemographicNo());
       	setClientIdParam(request,clientId);
        
		client.setYearOfBirth(String.valueOf(MyDateFormat.getYearFromStandardDate(qform.getDob())));
		client.setMonthOfBirth(String.valueOf(MyDateFormat.getMonthFromStandardDate(qform.getDob())));
		client.setDateOfBirth(String.valueOf(MyDateFormat.getDayFromStandardDate(qform.getDob())));
    	clientManager.saveClient(client);
    	
    	QuatroIntake obj= qform.getIntake();
		
		//get program type
    	ArrayList<LabelValueBean> lst= (ArrayList<LabelValueBean>)qform.getProgramTypeList();
		for(int i=0;i<lst.size();i++){
			LabelValueBean obj2= (LabelValueBean)lst.get(i);
			if(Integer.valueOf(obj2.getValue()).equals(obj.getProgramId())){
			  obj.setProgramType(obj2.getLabel());
			  break;
			}
		}
		
		//check service restriction
		if(!obj.getProgramId().equals(obj.getCurrentProgramId())){
          ProgramClientRestriction restrInPlace = clientRestrictionManager.checkClientRestriction(
        		obj.getProgramId().intValue(), obj.getClientId().intValue(), new Date());
          if (restrInPlace != null && request.getParameter("skipError")==null) {
        	for(Object element : qform.getProgramList()) {
        		LabelValueBean obj3 = (LabelValueBean) element;
            	if(obj3.getValue().equals(obj.getProgramId().toString())){
          		  if(obj.getProgramType().equals(KeyConstants.BED_PROGRAM_TYPE)){
               		messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("warning.intake.service_restriction",
               			request.getContextPath(), obj3.getLabel()));
               		isWarning = true;
                    break;
          		  }else{
          			messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("error.intake.service_restriction",
              			request.getContextPath(), obj3.getLabel()));
              		isError = true;
                    break;
          		  }
            	}
            }
            saveMessages(request,messages);
            
    		if(isError) return mapping.findForward("edit");
          }
		}

		if(obj.getCreatedOnTxt().equals("")==false){
		  obj.setCreatedOn(MyDateFormat.getCalendar(obj.getCreatedOnTxt()));
		}else{
	  	  Calendar cal= Calendar.getInstance();
		  obj.setCreatedOn(cal);
	      obj.setCreatedOnTxt(String.valueOf(cal.get(Calendar.YEAR)) + "-" + 
				  String.valueOf(cal.get(Calendar.MONTH)+1) + "-" +  
				  String.valueOf(cal.get(Calendar.DATE)));
		}

		obj.setLanguage(request.getParameter("language_code"));
		obj.setOriginalCountry(request.getParameter("originalCountry_code"));
		obj.setStaffId((String)request.getSession().getAttribute(KeyConstants.SESSION_KEY_PROVIDERNO));
         
		obj.setPregnant(request.getParameter("intake.pregnant"));
		obj.setDisclosedAbuse(request.getParameter("intake.disclosedAbuse"));
		obj.setObservedAbuse(request.getParameter("intake.observedAbuse"));
		obj.setDisclosedMentalIssue(request.getParameter("intake.disclosedMentalIssue"));
		obj.setPoorHygiene(request.getParameter("intake.poorHygiene"));
		obj.setObservedMentalIssue(request.getParameter("intake.observedMentalIssue"));
		obj.setDisclosedAlcoholAbuse(request.getParameter("intake.disclosedAlcoholAbuse"));
		obj.setObservedAlcoholAbuse(request.getParameter("intake.observedAlcoholAbuse"));
		obj.setBirthCertificateYN(request.getParameter("intake.birthCertificateYN"));
		obj.setSINYN(request.getParameter("intake.SINYN"));
		obj.setHealthCardNoYN(request.getParameter("intake.healthCardNoYN"));
		obj.setDriverLicenseNoYN(request.getParameter("intake.driverLicenseNoYN"));
		obj.setCitizenCardNoYN(request.getParameter("intake.citizenCardNoYN"));
		obj.setNativeReserveNoYN(request.getParameter("intake.nativeReserveNoYN"));
		obj.setVeteranNoYN(request.getParameter("intake.veteranNoYN"));
		obj.setRecordLandingYN(request.getParameter("intake.recordLandingYN"));
		obj.setLibraryCardYN(request.getParameter("intake.libraryCardYN"));
        
        LookupCodeValue language = new LookupCodeValue();
        language.setCode(request.getParameter("language_code"));
        language.setDescription(request.getParameter("language_description"));
        LookupCodeValue originalCountry = new LookupCodeValue();
        originalCountry.setCode(request.getParameter("originalCountry_code"));
        originalCountry.setDescription(request.getParameter("originalCountry_description"));
		qform.setLanguage(language);        
		qform.setOriginalCountry(originalCountry);
		

		ArrayList lst2 = intakeManager.saveQuatroIntake(obj);
		Integer intakeId = (Integer)lst2.get(0);
		Integer referralId = (Integer)lst2.get(1);
		Integer queueId = (Integer)lst2.get(2);
		obj.setId(intakeId);
		obj.setReferralId(referralId);
		obj.setQueueId(queueId);
        obj.setCurrentProgramId(obj.getProgramId());
		qform.setIntake(obj);

		if(!(isWarning || isError)) messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("message.intake.service_restriction", request.getContextPath()));
        saveMessages(request,messages);
		
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

	public void setClientRestrictionManager(
			ClientRestrictionManager clientRestrictionManager) {
		this.clientRestrictionManager = clientRestrictionManager;
	}

}
