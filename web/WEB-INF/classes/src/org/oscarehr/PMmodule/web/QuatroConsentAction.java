package org.oscarehr.PMmodule.web;

import java.sql.Date;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.action.DynaActionForm;
import org.oscarehr.PMmodule.model.ConsentDetail;

import org.oscarehr.PMmodule.model.Demographic;
import org.oscarehr.PMmodule.model.ProgramClientRestriction;
import org.oscarehr.PMmodule.model.Provider;
import org.oscarehr.PMmodule.service.ClientManager;
import org.oscarehr.PMmodule.service.ConsentManager;
import org.oscarehr.PMmodule.service.ProviderManager;
import org.oscarehr.PMmodule.web.formbean.ClientManagerFormBean;
import org.oscarehr.util.SessionConstants;

import oscar.MyDateFormat;

import com.quatro.common.KeyConstants;
import com.quatro.service.LookupManager;
import com.quatro.util.Utility;

public class QuatroConsentAction extends BaseClientAction {
	private static Log log = LogFactory.getLog(ConsentAction.class);
    private ClientManager clientManager;
    private ConsentManager consentManager;
    private LookupManager lookupManager;
    private ProviderManager providerManager;

	public void setLookupManager(LookupManager lookupManager) {
		this.lookupManager = lookupManager;
	}

	public ActionForward unspecified(ActionMapping mapping,	ActionForm form, HttpServletRequest request, HttpServletResponse response) {		
		return list(mapping,form,request,response);
	}
	
	public ActionForward list(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response){
		setListAttributes(form,request);
		super.setScreenMode(request, KeyConstants.TAB_CLIENT_CONSENT);
		return mapping.findForward("list");
	}
	public ActionForward edit(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response){
		setEditAttributes(form,request);
		super.setScreenMode(request, KeyConstants.TAB_CLIENT_CONSENT);
		return mapping.findForward("edit");
	}
	 private void setListAttributes(ActionForm form, HttpServletRequest request) {
		   DynaActionForm clientForm = (DynaActionForm) form;

	       HashMap actionParam = (HashMap) request.getAttribute("actionParam");
	       if(actionParam==null){
	    	  actionParam = new HashMap();
	          actionParam.put("clientId", request.getParameter("clientId")); 
	       }
	       request.setAttribute("actionParam", actionParam);
	       String demographicNo= (String)actionParam.get("clientId");
	       
	      // ClientManagerFormBean tabBean = (ClientManagerFormBean) clientForm.get("view");

	      // Integer facilityId=(Integer)request.getSession().getAttribute(SessionConstants.CURRENT_FACILITY_ID);
	       
	       request.setAttribute("clientId", demographicNo);
	       request.setAttribute("client", clientManager.getClientByDemographicNo(demographicNo));


	       String providerNo = (String)request.getSession().getAttribute(KeyConstants.SESSION_KEY_PROVIDERNO);
	           
	       List lstConsents = consentManager.getConsentDetailByClient(Integer.valueOf(demographicNo), providerNo);
	       //.getAdmissionList(Integer.valueOf(demographicNo), facilityId, providerNo2);           
	       request.setAttribute("lstConsents", lstConsents);
	   }
	 public ActionForward withdraw(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
			   DynaActionForm clientForm = (DynaActionForm) form;
		       ConsentDetail conObj = (ConsentDetail) clientForm.get("consentValue");
			   String recId=request.getParameter("rId");
			   if(Utility.IsEmpty(recId)) recId=conObj.getId().toString();
		       Integer rId=Integer.valueOf(recId);
			   String providerNo=(String)request.getSession().getAttribute("user");
		       consentManager.withdraw(rId, providerNo);		       
		       return edit(mapping, form, request, response);
		   
	   }
	 private void setEditAttributes(ActionForm form, HttpServletRequest request) {
		   DynaActionForm dForm = (DynaActionForm) form;
		   ConsentDetail cdObj =(ConsentDetail)dForm.get("consentValue");
	       HashMap actionParam = (HashMap) request.getAttribute("actionParam");
	       String cId =request.getParameter("clientId");
	       if(Utility.IsEmpty(cId))cId =cdObj.getDemographicNo().toString();
	       if(actionParam==null){
	    	  actionParam = new HashMap();
	          actionParam.put("clientId", cId); 
	       }
	       request.setAttribute("actionParam", actionParam);
	       String demographicNo= (String)actionParam.get("clientId");
	       String rId=request.getParameter("rId");
	       if(Utility.IsEmpty(rId)) rId=cdObj.getId().toString();
	      // ClientManagerFormBean tabBean = (ClientManagerFormBean) clientForm.get("view");
	       
	       Integer facilityId=(Integer)request.getSession().getAttribute(SessionConstants.CURRENT_FACILITY_ID);
	       request.setAttribute("facilityDesc",lookupManager.GetLookupCode("FAC", facilityId.toString()).getDescription());
	       request.setAttribute("clientId", demographicNo);
	       request.setAttribute("client", clientManager.getClientByDemographicNo(demographicNo));


	       String providerNo = (String)request.getSession().getAttribute(KeyConstants.SESSION_KEY_PROVIDERNO);
	       Provider proObj =providerManager.getProvider(providerNo);
	       ConsentDetail consentObj = (ConsentDetail)dForm.get("consentValue");
	       List programIds =clientManager.getRecentProgramIds(Integer.valueOf(demographicNo),providerNo,facilityId);
	       List programs = null;
	       if (programIds.size() > 0) {
		       String progs = ((Integer)programIds.get(0)).toString();
		       for (int i=1; i<programIds.size(); i++)
		       {
		    	   progs += "," + ((Integer)programIds.get(i)).toString();
		       }
		       programs =  lookupManager.LoadCodeList("PRO", true, progs, null);
	       }
	       else
	       {
	    	   programs = new ArrayList();
	       }
	       request.setAttribute("programs", programs);
	       if("0".equals(rId))
	       {
	    	   consentObj = new ConsentDetail();
	    	   consentObj.setDemographicNo(Integer.valueOf(demographicNo));	  
	    	   consentObj.setProviderNo(providerNo);
	    	   consentObj.setProviderFirstName(proObj.getFirstName());
	    	   consentObj.setProviderLastName(proObj.getLastName());
	       }
	       else if(rId!=null && rId!="0") consentObj= consentManager.getConsentDetail(Integer.valueOf(rId));
	       dForm.set("consentValue", consentObj);
	       request.setAttribute("recordId",rId);
	   }
	public ActionForward form(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		DynaActionForm consentForm = (DynaActionForm)form;
		
		String id = request.getParameter("id");
		String formName = request.getParameter("formName");
		String formMapping = getRandomForm();
		super.setScreenMode(request, KeyConstants.TAB_CLIENT_CONSENT);
		String gotoStr = request.getParameter("goto");
		
		if(id == null) {
			return mapping.findForward("error");
		}
				
		Demographic demographic = clientManager.getClientByDemographicNo(id);
		
		if(demographic == null) {
			return mapping.findForward("error");
		}
		
		ConsentDetail consent = consentManager.getConsentDetail(Integer.valueOf(id));
		if(consent != null) {
			ConsentDetail newConsent = new ConsentDetail();
			try {
				BeanUtils.copyProperties(newConsent,consent);
				newConsent.setId(null);
			}catch(Exception e) {log.warn(e);}
			consentForm.set("consentValue", consent);
			formMapping = consent.getFormName();
		}
		request.setAttribute("id",id);
		request.setAttribute("clientName", demographic.getFirstName() + " " + demographic.getLastName());
		
		if(gotoStr != null && !gotoStr.equals("")) {
			return new ActionForward(gotoStr);
		}
		
		if(formName != null) {
			return mapping.findForward("form" + formName.toUpperCase());
		}
		
//        return mapping.findForward(formMapping);
		return mapping.findForward("formA");
	}
	
	public ActionForward update(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response){
		log.debug("Saving Consent");
		ActionMessages messages = new ActionMessages();
		 boolean isError = false;
	     boolean isWarning = false;
		DynaActionForm consentForm = (DynaActionForm)form;
		super.setScreenMode(request, KeyConstants.TAB_CLIENT_CONSENT);
		ConsentDetail consent= (ConsentDetail)consentForm.get("consentValue");
		String rId=request.getParameter("recordId");
		if(Utility.IsEmpty(rId)) rId=(String)request.getAttribute("recordId");
		if(rId!=null && Integer.parseInt(rId)>0)
		consent.setId(Integer.valueOf(rId));
		else consent.setId(null);
		  HashMap actionParam = (HashMap) request.getAttribute("actionParam");
	       if(actionParam==null){
	    	  actionParam = new HashMap();
	          actionParam.put("clientId", consent.getDemographicNo().toString()); 
	       }
	       request.setAttribute("actionParam", actionParam);
	       request.setAttribute("clientId", consent.getDemographicNo().toString());
	       
		Provider p =  (Provider)request.getSession().getAttribute("provider");
		consent.setProviderNo(p.getProviderNo());
		//consent.setProviderName(p.getFormattedName());		
		
		consent.setDateSigned(new GregorianCalendar());
		consent.setStartDate(new GregorianCalendar());
		String eDt = consent.getEndDateStr();
		consent.setEndDate(MyDateFormat.getCalendar(consent.getEndDateStr()));
		consent.setHardCopy(true);
		consent.setStatus("active");		
		consentManager.saveConsentDetail(consent);	
		
		//String gotoStr = request.getParameter("goto");			
		if(!(isWarning || isError)) messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("message.save.success", request.getContextPath()));
        saveMessages(request,messages);
        setEditAttributes(form, request);
		return mapping.findForward("edit");	

	}
	public ActionForward save(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		super.setScreenMode(request, KeyConstants.TAB_CLIENT_CONSENT);
		log.debug("Saving Consent");
		ActionMessages messages = new ActionMessages();
		 boolean isError = false;
	     boolean isWarning = false;
		DynaActionForm consentForm = (DynaActionForm)form;
		
		ConsentDetail consent= (ConsentDetail)consentForm.get("consentValue");
		
		String id = (String)request.getParameter("clientId");
		consent.setDemographicNo(Integer.valueOf(id));
		
		Provider p =  (Provider)request.getSession().getAttribute("provider");
		consent.setProviderNo(p.getProviderNo());
		//consent.setProviderName(p.getFormattedName());		
		
		consent.setDateSigned(new GregorianCalendar());
		consent.setHardCopy(true);
		consent.setStatus("active");
		consent.setStartDate(new GregorianCalendar());
		consent.setEndDate(MyDateFormat.getCalendar(consent.getEndDateStr()));
		consentManager.saveConsentDetail(consent);	
		
		//String gotoStr = request.getParameter("goto");			
		if(!(isWarning || isError)) messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("message.save.success", request.getContextPath()));
        saveMessages(request,messages);
        setEditAttributes(form, request);
		return mapping.findForward("edit");	
	}
	
	private String getRandomForm() {
		int d = (int)(Math.random()*2);
		if(d == 0) {
			return "formA";
		}
//		return "formB";
        return "formA";
	}

    public void setClientManager(ClientManager mgr) {
    	this.clientManager = mgr;
    }


    public void setConsentManager(ConsentManager mgr) {
    	this.consentManager = mgr;
    }

	public void setProviderManager(ProviderManager providerManager) {
		this.providerManager = providerManager;
	}
}



