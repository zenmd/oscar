package com.quatro.web.intake;

import java.util.ArrayList;

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
import com.quatro.common.KeyConstants;
import org.oscarehr.PMmodule.model.Program;
import org.oscarehr.PMmodule.model.QuatroIntake;
import com.quatro.model.LookupCodeValue;
import oscar.MyDateFormat;

public class IntakeEditAction extends DispatchAction {
    private ClientManager clientManager;
    private LookupManager lookupManager;
    private IntakeManager intakeManager;
    private ProgramManager programManager;
	
    public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
    	return update(mapping,form,request,response);
	}

    public ActionForward update(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
    	QuatroIntakeEditForm qform = (QuatroIntakeEditForm) form;
        String clientId = qform.getClientId();
        
		Demographic client= clientManager.getClientByDemographicNo(clientId);
		qform.setClient(client);
        qform.setDob(client.getYearOfBirth() + "-" + client.getMonthOfBirth() + "-" + client.getDateOfBirth());
        
        if(qform.getOptionList()==null){
  		  com.quatro.web.intake.OptionList optionValues = intakeManager.LoadOptionsList();
  		  qform.setOptionList(optionValues);
        }  

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

        Integer intakeId = Integer.valueOf(qform.getIntakeId());
        	
        QuatroIntake obj;
        if(intakeId.intValue()!=0){
            obj=intakeManager.getQuatroIntake(intakeId);
        }else{
        	obj= new QuatroIntake();
        }
        obj.setId(intakeId);
        obj.setClientId(Integer.valueOf(qform.getClientId()));
        
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

    public ActionForward edit(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
    	return update(mapping,form,request,response);
	}

    public ActionForward save(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
    	QuatroIntakeEditForm qform = (QuatroIntakeEditForm) form;
		Demographic client= qform.getClient();
    	clientManager.saveClient(client);
    	
		QuatroIntake obj= qform.getIntake();
		obj.setCreatedOn(MyDateFormat.getCalendar(qform.getDob()));
		obj.setLanguage(request.getParameter("language_code"));
		obj.setOriginalCountry(request.getParameter("originalCountry_code"));
		obj.setStaffId((String)request.getSession().getAttribute(KeyConstants.SESSION_KEY_PROVIDERNO));
        
        LookupCodeValue language = new LookupCodeValue();
        language.setCode(request.getParameter("language_code"));
        language.setDescription(request.getParameter("language_description"));
        LookupCodeValue originalCountry = new LookupCodeValue();
        originalCountry.setCode(request.getParameter("originalCountry_code"));
        originalCountry.setDescription(request.getParameter("originalCountry_description"));
		qform.setLanguage(language);        
		qform.setOriginalCountry(originalCountry);        

		Integer intakeId= intakeManager.saveQuatroIntake(obj);
        qform.getIntake().setId(intakeId);

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
