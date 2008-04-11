package com.quatro.web.intake;

import java.util.List;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import org.oscarehr.PMmodule.web.formbean.GenericIntakeConstants;
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
//        qform.setProgramList(new ArrayList());
        
		return mapping.findForward("edit");
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
		qform.setClient(client);
        qform.setDob(client.getYearOfBirth() + "-" + client.getMonthOfBirth() + "-" + client.getDateOfBirth());
        
        qform.setLanguage(new LookupTagValue());
        qform.setOriginalCountry(new LookupTagValue());
        
        setOptionList(qform);

		qform.setProgramList(new ArrayList());
        
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
