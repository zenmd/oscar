package org.oscarehr.PMmodule.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.HashMap;
import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.util.LabelValueBean;

import com.quatro.model.LookupCodeValue;
import com.quatro.service.IntakeManager;
import com.quatro.service.LookupManager;

import org.oscarehr.PMmodule.service.ClientManager;
import org.oscarehr.PMmodule.service.ClientRestrictionManager;
import org.oscarehr.PMmodule.model.ProgramClientRestriction;
import org.oscarehr.PMmodule.model.QuatroIntakeHeader;
import org.oscarehr.PMmodule.model.Demographic;

import com.quatro.common.KeyConstants;
import org.oscarehr.util.SessionConstants;
import org.oscarehr.PMmodule.web.formbean.ClientSearchFormBean;
import org.oscarehr.PMmodule.web.formbean.QuatroClientFamilyIntakeForm;
import org.oscarehr.PMmodule.model.QuatroIntakeFamily;
import org.oscarehr.PMmodule.model.QuatroIntake;

import oscar.MyDateFormat;

public class QuatroFamilyIntakeAction extends DispatchAction {

   private IntakeManager intakeManager;
   private LookupManager lookupManager;
   private ClientManager clientManager;
   private ClientRestrictionManager clientRestrictionManager;
   
   public static final String ID = "id";
   
   public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
       return edit(mapping, form, request, response);
   }

   public ActionForward edit(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
       QuatroClientFamilyIntakeForm clientForm = (QuatroClientFamilyIntakeForm)form; 
       
       String intakeId = (String)clientForm.getIntakeId();
       HashMap actionParam = (HashMap) request.getAttribute("actionParam");
       if(actionParam==null){
    	  actionParam = new HashMap();
          actionParam.put("clientId", request.getParameter("clientId")); 
          actionParam.put("intakeId", request.getParameter("intakeId")); 
       }
       request.setAttribute("actionParam", actionParam);
       
       String demographicNo= (String)actionParam.get("clientId");
       request.setAttribute("clientId", demographicNo);

       List genders = lookupManager.LoadCodeList("GEN",true, null, null);
       clientForm.setGenders(genders);

       List relationships = lookupManager.LoadCodeList("FRA",true, null, null);
       clientForm.setRelationships(relationships);
       
	   Demographic familyHead = intakeManager.getClientByDemographicNo(demographicNo);
	   for(Object element : genders){
           LookupCodeValue obj= (LookupCodeValue)element;
           if(obj.getCode().equals(familyHead.getSex())){
        	 familyHead.setSexDesc(obj.getDescription());
		     break;
           }  
	   }
	   
       List dependent = intakeManager.getClientFamilyByIntakeId(intakeId);
       if(dependent==null) dependent = new ArrayList(); 
       for(Object element: dependent){
    	  QuatroIntakeFamily obj= (QuatroIntakeFamily)element;
    	  if(obj.getIntakeHeadId().equals(obj.getIntakeId())){
    		 dependent.remove(obj);
    		 break;
    	  }
       }

       clientForm.setFamilyHead(familyHead);
       clientForm.setDob(familyHead.getYearOfBirth() + "/" + MyDateFormat.formatMonthOrDay(familyHead.getMonthOfBirth()) + "/" + MyDateFormat.formatMonthOrDay(familyHead.getDateOfBirth()));
       clientForm.setDependents(dependent);
       clientForm.setDependentsSize(dependent.size());
       
       return mapping.findForward("edit");
   }

   public ActionForward add(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
       QuatroClientFamilyIntakeForm clientForm = (QuatroClientFamilyIntakeForm)form; 
       
       String intakeId = (String)clientForm.getIntakeId();
       HashMap actionParam = (HashMap) request.getAttribute("actionParam");
       if(actionParam==null){
    	  actionParam = new HashMap();
          actionParam.put("clientId", request.getParameter("clientId")); 
          actionParam.put("intakeId", request.getParameter("intakeId")); 
       }
       request.setAttribute("actionParam", actionParam);
       
       String demographicNo= (String)actionParam.get("clientId");
       request.setAttribute("clientId", demographicNo);

       List genders = lookupManager.LoadCodeList("GEN",true, null, null);
       clientForm.setGenders(genders);

       List relationships = lookupManager.LoadCodeList("FRA",true, null, null);
       clientForm.setRelationships(relationships);

	   Demographic familyHead = clientForm.getFamilyHead();
	   for(Object element : genders){
           LookupCodeValue obj= (LookupCodeValue)element;
           if(obj.getCode().equals(familyHead.getSex())){
        	 familyHead.setSexDesc(obj.getDescription());
		     break;
           }  
	   }
       clientForm.setFamilyHead(familyHead);
       
       ArrayList dependents = new ArrayList();
	   int dependentsSize=clientForm.getDependentsSize();

	   for(int i=0;i<dependentsSize;i++){
	      QuatroIntakeFamily obj = new QuatroIntakeFamily();	
		  obj.setClientId(Integer.valueOf(request.getParameter("dependent[" + i +"].clientId")));
		  obj.setIntakeId(Integer.valueOf(request.getParameter("dependent[" + i +"].intakeId")));
		  obj.setLastName(request.getParameter("dependent[" + i +"].lastName"));
		  obj.setFirstName(request.getParameter("dependent[" + i +"].firstName"));
		  obj.setDob(request.getParameter("dependent[" + i +"].dob"));
		  obj.setSex(request.getParameter("dependent[" + i +"].sex"));
		  obj.setAlias(request.getParameter("dependent[" + i +"].alias"));
		  obj.setRelationship(request.getParameter("dependent[" + i +"].relationship"));
		  obj.setIntakeId(Integer.valueOf(request.getParameter("dependent[" + i +"].intakeId")));
		  obj.setSelect(request.getParameter("dependent[" + i +"].select"));
		  obj.setDupliDemographicNo(Integer.valueOf(request.getParameter("dependent[" + i +"].dupliDemographicNo")));
		  obj.setNewClientCheck(request.getParameter("dependent[" + i +"].newClientCheck"));
		  dependents.add(obj);
	    }
       
        QuatroIntakeFamily obj2 = new QuatroIntakeFamily();
        obj2.setClientId(0);
        obj2.setIntakeId(0);
    	obj2.setDupliDemographicNo(0);  
        dependents.add(obj2);
        clientForm.setDependents(dependents);
        clientForm.setDependentsSize(dependents.size());
       
       return mapping.findForward("edit");
   }

   public ActionForward delete(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
       QuatroClientFamilyIntakeForm clientForm = (QuatroClientFamilyIntakeForm)form; 
       
       String intakeId = (String)clientForm.getIntakeId();
       HashMap actionParam = (HashMap) request.getAttribute("actionParam");
       if(actionParam==null){
    	  actionParam = new HashMap();
          actionParam.put("clientId", request.getParameter("clientId")); 
          actionParam.put("intakeId", request.getParameter("intakeId")); 
       }
       request.setAttribute("actionParam", actionParam);
       
       String demographicNo= (String)actionParam.get("clientId");
       request.setAttribute("clientId", demographicNo);

       List genders = lookupManager.LoadCodeList("GEN",true, null, null);
       clientForm.setGenders(genders);

       List relationships = lookupManager.LoadCodeList("FRA",true, null, null);
       clientForm.setRelationships(relationships);
       
	   Demographic familyHead = clientForm.getFamilyHead();
	   for(Object element : genders){
           LookupCodeValue obj= (LookupCodeValue)element;
           if(obj.getCode().equals(familyHead.getSex())){
        	 familyHead.setSexDesc(obj.getDescription());
		     break;
           }  
	   }
       clientForm.setFamilyHead(familyHead);

       ArrayList dependents = new ArrayList();
	   int dependentsSize=clientForm.getDependentsSize();

	   for(int i=0;i<dependentsSize;i++){
	      if(request.getParameter("dependent[" + i +"].select")==null){
		  QuatroIntakeFamily obj = new QuatroIntakeFamily();	
		  obj.setClientId(Integer.valueOf(request.getParameter("dependent[" + i +"].clientId")));
		  obj.setIntakeId(Integer.valueOf(request.getParameter("dependent[" + i +"].intakeId")));
		  obj.setLastName(request.getParameter("dependent[" + i +"].lastName"));
		  obj.setFirstName(request.getParameter("dependent[" + i +"].firstName"));
		  obj.setDob(request.getParameter("dependent[" + i +"].dob"));
		  obj.setSex(request.getParameter("dependent[" + i +"].sex"));
		  obj.setAlias(request.getParameter("dependent[" + i +"].alias"));
		  obj.setRelationship(request.getParameter("dependent[" + i +"].relationship"));
		  obj.setIntakeId(Integer.valueOf(request.getParameter("dependent[" + i +"].intakeId")));
		  obj.setSelect(request.getParameter("dependent[" + i +"].select"));
		  obj.setDupliDemographicNo(Integer.valueOf(request.getParameter("dependent[" + i +"].dupliDemographicNo")));
		  obj.setNewClientCheck(request.getParameter("dependent[" + i +"].newClientCheck"));
		  dependents.add(obj);
	      }
	    }

       clientForm.setDependents(dependents);
       clientForm.setDependentsSize(dependents.size());
	   
       return mapping.findForward("edit");
   }

   public ActionForward save(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
       ActionMessages messages = new ActionMessages();
       boolean isError = false;
       boolean isWarning = false;

       QuatroClientFamilyIntakeForm clientForm = (QuatroClientFamilyIntakeForm)form; 

       String intakeId = (String)clientForm.getIntakeId();
	   HashMap actionParam = (HashMap) request.getAttribute("actionParam");
       if(actionParam==null){
    	  actionParam = new HashMap();
          actionParam.put("clientId", request.getParameter("clientId")); 
          actionParam.put("intakeId", intakeId); 
       }
       request.setAttribute("actionParam", actionParam);

       String demographicNo= (String)actionParam.get("clientId");
       request.setAttribute("clientId", demographicNo);

       List genders = lookupManager.LoadCodeList("GEN",true, null, null);
       clientForm.setGenders(genders);

       List relationships = lookupManager.LoadCodeList("FRA",true, null, null);
       clientForm.setRelationships(relationships);
       
	   Demographic familyHead = intakeManager.getClientByDemographicNo(demographicNo);
	   for(Object element : genders){
           LookupCodeValue obj= (LookupCodeValue)element;
           if(obj.getCode().equals(familyHead.getSex())){
        	 familyHead.setSexDesc(obj.getDescription());
		     break;
           }  
	   }
	   
       ArrayList dependents = new ArrayList();
	   int dependentsSize=clientForm.getDependentsSize();
       
	   Integer cur_dupliDemographicNo;
	   boolean bDupliDemographicNoApproved=true;
	   boolean bServiceRestriction=false;
	   
       for(int i=0;i<dependentsSize;i++){
		  QuatroIntakeFamily obj = new QuatroIntakeFamily();	
		  obj.setClientId(Integer.valueOf(request.getParameter("dependent[" + i +"].clientId")));
		  obj.setIntakeId(Integer.valueOf(request.getParameter("dependent[" + i +"].intakeId")));
		  obj.setLastName(request.getParameter("dependent[" + i +"].lastName"));
		  obj.setFirstName(request.getParameter("dependent[" + i +"].firstName"));
		  obj.setDob(request.getParameter("dependent[" + i +"].dob"));
		  obj.setSex(request.getParameter("dependent[" + i +"].sex"));
		  obj.setAlias(request.getParameter("dependent[" + i +"].alias"));
		  obj.setRelationship(request.getParameter("dependent[" + i +"].relationship"));
		  obj.setIntakeId(Integer.valueOf(request.getParameter("dependent[" + i +"].intakeId")));
		  obj.setSelect(request.getParameter("dependent[" + i +"].select"));
		  cur_dupliDemographicNo = Integer.valueOf(request.getParameter("dependent[" + i +"].dupliDemographicNo"));
		  obj.setNewClientCheck(request.getParameter("dependent[" + i +"].newClientCheck"));

    	  obj.setDupliDemographicNo(0);  
		  if(obj.getIntakeId().intValue()==0){
		    ClientSearchFormBean criteria = new ClientSearchFormBean();
	        criteria.setLastName(obj.getLastName());
	        criteria.setFirstName(obj.getFirstName());
	        criteria.setDob(obj.getDob());
	        criteria.setGender(obj.getSex());
		    List lst = clientManager.search(criteria, false);
	        if(lst.size()>0){
     	      Demographic obj2= (Demographic)lst.get(0);
     	      if(cur_dupliDemographicNo.equals(obj2.getDemographicNo())==false){
     	        obj.setNeedCheck(true);
     	        bDupliDemographicNoApproved=false;    	
     	      }else{
      	        obj.setNeedCheck(false);
     	      }
              obj.setDupliDemographicNo(obj2.getDemographicNo());	    	  
  	          obj.setClientId(obj2.getDemographicNo());
	        }else{
  	          obj.setClientId(0);
	        }
		  }
	      dependents.add(obj);
	   }
	   
       if(!bDupliDemographicNoApproved){
		 messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("error.intake.duplicated_client",
          			request.getContextPath()));
         isError = true;
         clientForm.setDependents(dependents);
         clientForm.setDependentsSize(dependents.size());
         return mapping.findForward("edit");
       }
       
       String providerNo = (String)request.getSession().getAttribute(KeyConstants.SESSION_KEY_PROVIDERNO);
   	   Integer headIntakeId = Integer.valueOf(clientForm.getIntakeId());//.getFamilyHead().getDemographicNo();
 	   QuatroIntake headIntake = intakeManager.getQuatroIntake(headIntakeId);
       if(bDupliDemographicNoApproved){
   		 //check service restriction
         for(int i=0;i<dependentsSize;i++){
           QuatroIntakeFamily obj3 = (QuatroIntakeFamily)dependents.get(i);
    	   obj3.setBServiceRestriction(false);
           if(obj3.getClientId().intValue()>0){
             ProgramClientRestriction restrInPlace = clientRestrictionManager.checkClientRestriction(
        		 headIntake.getProgramId().intValue(), obj3.getClientId().intValue(), new Date());
             if (restrInPlace != null) {
     	       obj3.setBServiceRestriction(true);
       		   messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("warning.intake.service_restriction",
                  			request.getContextPath(), headIntake.getProgramName()));
       		   isWarning = true;
               saveMessages(request,messages);
             }
           }
         } 
       }
       
       QuatroIntakeFamily intakeFamilyHead = new QuatroIntakeFamily();
       intakeFamilyHead.setIntakeHeadId(headIntake.getId());
       intakeFamilyHead.setIntakeId(headIntake.getId());
       intakeFamilyHead.setMemberStatus(KeyConstants.INTAKE_STATUS_ACTIVE);
       intakeFamilyHead.setRelationship(KeyConstants.FAMILY_HEAD_CODE);
       intakeFamilyHead.setJoinFamilyDate(Calendar.getInstance());
       intakeManager.saveQuatroIntakeFamilyHead(intakeFamilyHead);

       for(int i=0;i<dependentsSize;i++){
       	 Demographic client = new Demographic();
         QuatroIntakeFamily intakeFamily = (QuatroIntakeFamily)dependents.get(i);
         client.setDemographicNo(intakeFamily.getClientId());
         client.setLastName(intakeFamily.getLastName());
         client.setFirstName(intakeFamily.getFirstName());

     	 String[] split = intakeFamily.getDob().split("/");
		 client.setYearOfBirth(MyDateFormat.formatMonthDay(split[0]));
		 client.setMonthOfBirth(MyDateFormat.formatMonthDay(split[1]));
		 client.setDateOfBirth(MyDateFormat.formatMonthDay(split[2]));
         
         client.setSex(intakeFamily.getSex());
         client.setAlias(intakeFamily.getAlias());
         client.setProviderNo(providerNo);
         client.setEffDate(Calendar.getInstance().getTime());
         
     	 QuatroIntake intake = new QuatroIntake();
     	 intake.setClientId(client.getDemographicNo());
     	 intake.setId(intakeFamily.getIntakeId());
     	 intake.setProgramId(headIntake.getProgramId());
     	 intake.setReferralId(headIntake.getReferralId());
     	 intake.setQueueId(headIntake.getQueueId());
     	 intake.setLanguage(headIntake.getLanguage());
     	 intake.setOriginalCountry(headIntake.getOriginalCountry());
     	 intake.setStaffId((String)request.getSession().getAttribute(KeyConstants.SESSION_KEY_PROVIDERNO));

     	 intakeFamily.setJoinFamilyDate(Calendar.getInstance());
     	 intakeFamily.setMemberStatus(KeyConstants.INTAKE_STATUS_ACTIVE);
     	 intakeFamily.setIntakeHeadId(headIntake.getId());
     	 ArrayList lst2 = intakeManager.saveQuatroIntakeFamily(client, intake, intakeFamily);
     	 intakeFamily.setIntakeId((Integer)lst2.get(1));
     	 intakeFamily.setClientId((Integer)lst2.get(2));
       }

       clientForm.setDependents(dependents);
       clientForm.setDependentsSize(dependents.size());
	   
       if(!(isWarning || isError)) messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("message.intake.saved", request.getContextPath()));
       saveMessages(request,messages);
	   
	   return mapping.findForward("edit");
   }

   
   public void setIntakeManager(IntakeManager intakeManager) {
	 this.intakeManager = intakeManager;
   }

   public void setLookupManager(LookupManager lookupManager) {
	 this.lookupManager = lookupManager;
   }

   public void setClientManager(ClientManager clientManager) {
	 this.clientManager = clientManager;
   }

   public void setClientRestrictionManager(ClientRestrictionManager clientRestrictionManager) {
	 this.clientRestrictionManager = clientRestrictionManager;
   }
   
}
