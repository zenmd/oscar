package org.oscarehr.PMmodule.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.action.ActionMessage;

import org.apache.struts.util.LabelValueBean;

import org.oscarehr.PMmodule.web.formbean.ClientSearchFormBean;
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
import org.oscarehr.PMmodule.model.QuatroIntakeFamily;
import com.quatro.model.LookupCodeValue;
import oscar.MyDateFormat;
import java.util.Calendar;

public class QuatroIntakeEditAction extends DispatchAction {
    private ClientManager clientManager;
    private LookupManager lookupManager;
    private IntakeManager intakeManager;
    private ProgramManager programManager;
    private ClientRestrictionManager clientRestrictionManager;
	
    public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
    	return update(mapping,form,request,response);
	}

    //for new client
    public ActionForward create(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
    	QuatroIntakeEditForm qform = (QuatroIntakeEditForm) form;

    	String clientId = qform.getClientId();
        Integer intakeId = Integer.valueOf(qform.getIntakeId());
        HashMap actionParam = (HashMap) request.getAttribute("actionParam");
        if(actionParam==null){
     	  actionParam = new HashMap();
           actionParam.put("clientId", clientId); 
           actionParam.put("intakeId", intakeId.toString()); 
        }
        request.setAttribute("actionParam", actionParam);

        Demographic client;
        if(Integer.parseInt(clientId)>0){
		  client= clientManager.getClientByDemographicNo(clientId);
		  qform.setDob(client.getYearOfBirth() + "/" + MyDateFormat.formatMonthOrDay(client.getMonthOfBirth()) + "/" + MyDateFormat.formatMonthOrDay(client.getDateOfBirth()));
        }else{
          client= new Demographic();
  		  qform.setDob("");
        } 	
		qform.setClient(client);
        
		com.quatro.web.intake.OptionList optionValues = intakeManager.LoadOptionsList();
  		qform.setOptionList(optionValues);

        Integer facilityId= (Integer)request.getSession().getAttribute(KeyConstants.SESSION_KEY_FACILITYID);
        ArrayList lst= (ArrayList)programManager.getProgramIdsByProvider( 
        		new Integer(facilityId.intValue()),(String)request.getSession().getAttribute(KeyConstants.SESSION_KEY_PROVIDERNO));
        ArrayList<LabelValueBean> lst2 = new ArrayList<LabelValueBean>();
        ArrayList<LabelValueBean> lst3 = new ArrayList<LabelValueBean>();
        for(int i=0;i<lst.size();i++){
           Object[] obj = (Object[])lst.get(i);
           lst2.add(new LabelValueBean((String)obj[1], ((Integer)obj[0]).toString()));
           lst3.add(new LabelValueBean((String)obj[2], ((Integer)obj[0]).toString()));
        }
        qform.setProgramList(lst2);
        qform.setProgramTypeList(lst3);

        QuatroIntake obj;
        if(intakeId.intValue()!=0){
            obj=intakeManager.getQuatroIntake(intakeId);
        }else{
        	obj= new QuatroIntake();
        	obj.setCreatedOn(Calendar.getInstance());
            obj.setId(0);
            obj.setClientId(Integer.valueOf(qform.getClientId()));
            obj.setReferralId(0);
            obj.setQueueId(0);
            obj.setIntakeStatus(KeyConstants.INTAKE_STATUS_ACTIVE);
    		obj.setStaffId((String)request.getSession().getAttribute(KeyConstants.SESSION_KEY_PROVIDERNO));
    		obj.setYouth(KeyConstants.CONSTANT_NO);
    		obj.setVAW(KeyConstants.CONSTANT_NO);
        }

        obj.setCurrentProgramId(obj.getProgramId());
		qform.setIntake(obj);

        LookupCodeValue language;
        LookupCodeValue originalCountry;
        if(intakeId.intValue()!=0){
          if(obj.getLanguage()!=null){
        	language = lookupManager.GetLookupCode("LNG", obj.getLanguage());
          }else{
          	language = new LookupCodeValue();
          }
          if(obj.getOriginalCountry()!=null){
            originalCountry = lookupManager.GetLookupCode("CNT", obj.getOriginalCountry());
          }else{
        	originalCountry = new LookupCodeValue();
          }
        }else{
            language = new LookupCodeValue();
            originalCountry = new LookupCodeValue();
        }
        
        qform.setLanguage(language);
        qform.setOriginalCountry(originalCountry);

        request.setAttribute("newClientFlag", "true");
        
		return mapping.findForward("edit");
    }
    
    //for existing client
    public ActionForward update(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
    	QuatroIntakeEditForm qform = (QuatroIntakeEditForm) form;

    	String clientId = qform.getClientId();
        Integer intakeId = Integer.valueOf(qform.getIntakeId());
        HashMap actionParam = (HashMap) request.getAttribute("actionParam");
        if(actionParam==null){
     	  actionParam = new HashMap();
          actionParam.put("clientId", clientId);
          actionParam.put("intakeId", intakeId.toString()); 
        }
        request.setAttribute("actionParam", actionParam);
        Integer intakeHeadId = intakeManager.getIntakeFamilyHeadId(intakeId.toString());
        if(intakeHeadId!=null){
          request.setAttribute("intakeHeadId", intakeHeadId.toString()); 
          Integer intakeHeadClientId = intakeManager.getQuatroIntakeDBByIntakeId(intakeHeadId).getClientId();
          request.setAttribute("clientId", intakeHeadClientId); 
        }else{
          request.setAttribute("intakeHeadId", intakeId.toString()); 
          request.setAttribute("clientId", clientId); 
        }

        Demographic client;
        if(Integer.parseInt(clientId)>0){
		  client= clientManager.getClientByDemographicNo(clientId);
		  qform.setDob(client.getYearOfBirth() + "/" + MyDateFormat.formatMonthOrDay(client.getMonthOfBirth()) + "/" + MyDateFormat.formatMonthOrDay(client.getDateOfBirth()));
        }else{
          client= new Demographic();
  		  qform.setDob("");
        } 	
		qform.setClient(client);
		request.setAttribute("client", client);
		com.quatro.web.intake.OptionList optionValues = intakeManager.LoadOptionsList();
  		qform.setOptionList(optionValues);

        Integer facilityId= (Integer)request.getSession().getAttribute(KeyConstants.SESSION_KEY_FACILITYID);
        ArrayList lst= (ArrayList)programManager.getProgramIdsByProvider( 
        		new Integer(facilityId.intValue()),(String)request.getSession().getAttribute(KeyConstants.SESSION_KEY_PROVIDERNO));
        ArrayList<LabelValueBean> lst2 = new ArrayList<LabelValueBean>();
        ArrayList<LabelValueBean> lst3 = new ArrayList<LabelValueBean>();
        for(int i=0;i<lst.size();i++){
           Object[] obj = (Object[])lst.get(i);
           lst2.add(new LabelValueBean((String)obj[1], ((Integer)obj[0]).toString()));
           lst3.add(new LabelValueBean((String)obj[2], ((Integer)obj[0]).toString()));
        }
        qform.setProgramList(lst2);
        qform.setProgramTypeList(lst3);

        QuatroIntake obj;
        if(intakeId.intValue()!=0){
            obj=intakeManager.getQuatroIntake(intakeId);
        }else{
        	obj= new QuatroIntake();
        	obj.setCreatedOn(Calendar.getInstance());
            obj.setId(0);
            obj.setClientId(Integer.valueOf(qform.getClientId()));
            obj.setReferralId(0);
            obj.setQueueId(0);
            obj.setIntakeStatus(KeyConstants.INTAKE_STATUS_ACTIVE);
    		obj.setStaffId((String)request.getSession().getAttribute(KeyConstants.SESSION_KEY_PROVIDERNO));
    		obj.setYouth(KeyConstants.CONSTANT_NO);
    		obj.setVAW(KeyConstants.CONSTANT_NO);
        }

        obj.setCurrentProgramId(obj.getProgramId());
		qform.setIntake(obj);
		
        LookupCodeValue language = null;
        LookupCodeValue originalCountry = null;
        if(intakeId.intValue()!=0){
        	language = lookupManager.GetLookupCode("LNG", obj.getLanguage());
            originalCountry = lookupManager.GetLookupCode("CNT", obj.getOriginalCountry());
        }
        if (language == null) language = new LookupCodeValue();
        if (originalCountry == null) originalCountry = new LookupCodeValue();
        
        qform.setLanguage(language);
        qform.setOriginalCountry(originalCountry);
        
        request.setAttribute("PROGRAM_TYPE_Bed", KeyConstants.PROGRAM_TYPE_Bed);
        return mapping.findForward("edit");
	}

    public ActionForward save(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        ActionMessages messages = new ActionMessages();
        boolean isError = false;
        boolean isWarning = false;
    	QuatroIntakeEditForm qform = (QuatroIntakeEditForm) form;

    	String clientId = qform.getClientId();
    	
    	Demographic client= qform.getClient();
    	QuatroIntake obj= qform.getIntake();
    	
    	//check for new client duplication
    	if(obj.getClientId().intValue()==0 && request.getParameter("newClientChecked").equals("N")){
    	   ClientSearchFormBean criteria = new ClientSearchFormBean();
    	   criteria.setActive("");
    	   criteria.setAssignedToProviderNo("");
    	   criteria.setLastName(request.getParameter("client.lastName"));
    	   criteria.setFirstName(request.getParameter("client.firstName"));
    	   criteria.setDob(request.getParameter("dob"));
    	   criteria.setGender(request.getParameter("client.sex"));
    	   List lst = clientManager.search(criteria, false);
 		   if(lst.size()>0){
    	     messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("error.intake.duplicated_client",
          			request.getContextPath()));
             isError = true;
             saveMessages(request,messages);
             request.setAttribute("newClientFlag", "true");
       	     HashMap actionParam2 = new HashMap();
    	     actionParam2.put("clientId", obj.getClientId()); 
             actionParam2.put("intakeId", obj.getId().toString()); 
             request.setAttribute("actionParam", actionParam2);
		     return mapping.findForward("edit");
 		   }  
		}
    	
    	String[] split = qform.getDob().split("/");
		client.setYearOfBirth(MyDateFormat.formatMonthDay(split[0]));
		client.setMonthOfBirth(MyDateFormat.formatMonthDay(split[1]));
		client.setDateOfBirth(MyDateFormat.formatMonthDay(split[2]));
    	clientManager.saveClient(client);

    	HashMap actionParam = new HashMap();
    	actionParam.put("clientId", client.getDemographicNo()); 
        actionParam.put("intakeId", obj.getId().toString()); 
        Integer intakeHeadId = intakeManager.getIntakeFamilyHeadId(obj.getId().toString());
        if(intakeHeadId!=null){
          request.setAttribute("intakeHeadId", intakeHeadId.toString()); 
          Integer intakeHeadClientId = intakeManager.getQuatroIntakeDBByIntakeId(intakeHeadId).getClientId();
          request.setAttribute("clientId", intakeHeadClientId); 
        }else{
          request.setAttribute("intakeHeadId", obj.getId().toString()); 
          request.setAttribute("clientId", client.getDemographicNo()); 
        }
        request.setAttribute("actionParam", actionParam);
        request.setAttribute("client", client);
    	obj.setClientId(client.getDemographicNo());
		
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
            
    		if(isError){
                saveMessages(request,messages);
    			return mapping.findForward("edit");
    		}
          }
		}

		if(obj.getCreatedOnTxt().equals("")==false){
		  obj.setCreatedOn(MyDateFormat.getCalendar(obj.getCreatedOnTxt()));
		}else{
	  	  Calendar cal= Calendar.getInstance();
		  obj.setCreatedOn(cal);
	      obj.setCreatedOnTxt(String.valueOf(cal.get(Calendar.YEAR)) + "/" + 
				  String.valueOf(cal.get(Calendar.MONTH)+1) + "/" +  
				  String.valueOf(cal.get(Calendar.DATE)));
		}
 
		if(obj.getId().intValue()==0)obj.setIntakeStatus(KeyConstants.INTAKE_STATUS_ACTIVE);		
		obj.setLanguage(request.getParameter("language_code"));
		obj.setOriginalCountry(request.getParameter("originalCountry_code"));
//		obj.setStaffId((String)request.getSession().getAttribute(KeyConstants.SESSION_KEY_PROVIDERNO));
         
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
		
        Integer facilityId= (Integer)request.getSession().getAttribute(KeyConstants.SESSION_KEY_FACILITYID);
		if(obj.getId().intValue()==0 && intakeManager.checkExistBedIntakeByFacility(obj.getClientId(), facilityId).size()>0){
  			messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("error.intake.duplicate_bedprogram_intake",
          			request.getContextPath()));
        	isError = true;
		}else{
			ArrayList lst2 = intakeManager.saveQuatroIntake(obj);
			Integer intakeId = (Integer)lst2.get(0);
			Integer referralId = (Integer)lst2.get(1);
			Integer queueId = (Integer)lst2.get(2);
			obj.setId(intakeId);
			obj.setReferralId(referralId);
			obj.setQueueId(queueId);
	        obj.setCurrentProgramId(obj.getProgramId());
			qform.setIntake(obj);
		}
		
		if(!(isWarning || isError)) messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("message.save.success", request.getContextPath()));
        saveMessages(request,messages);

        request.setAttribute("PROGRAM_TYPE_Bed", KeyConstants.PROGRAM_TYPE_Bed);
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
