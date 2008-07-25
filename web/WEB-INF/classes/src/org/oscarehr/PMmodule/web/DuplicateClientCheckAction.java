package org.oscarehr.PMmodule.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;
import org.apache.struts.actions.DispatchAction;
import org.oscarehr.PMmodule.service.ClientManager;
import com.quatro.service.LookupManager;
import com.quatro.util.Utility;

import org.oscarehr.PMmodule.web.formbean.ClientSearchFormBean;
import org.oscarehr.PMmodule.model.Demographic;
import com.quatro.model.LookupCodeValue;
import oscar.MyDateFormat;

public class DuplicateClientCheckAction extends DispatchAction {
   private ClientManager clientManager;
   private LookupManager lookupManager;

   public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
       DynaActionForm qform = (DynaActionForm) form;

       ClientSearchFormBean criteria = new ClientSearchFormBean();
	   criteria.setActive("");
	   criteria.setAssignedToProviderNo("");
       criteria.setLastName(request.getParameter("lastName"));
       criteria.setFirstName(request.getParameter("firstName"));
       criteria.setDob(request.getParameter("dob"));
       criteria.setGender(request.getParameter("sex"));
	   List lst = clientManager.search(criteria, false,false);

	   Demographic obj= new Demographic();
	   obj.setDemographicNo(new Integer(0));
	   obj.setFirstName(request.getParameter("firstName"));
	   obj.setLastName(request.getParameter("lastName"));
	   if(request.getParameter("dob")!=null && !request.getParameter("dob").equals("")){
		 obj.setDateOfBirth(MyDateFormat.getCalendar(request.getParameter("dob")));  
//	     obj.setYearOfBirth(String.valueOf(MyDateFormat.getYearFromStandardDate(request.getParameter("dob"))));
//	     obj.setMonthOfBirth(String.valueOf(MyDateFormat.getMonthFromStandardDate(request.getParameter("dob"))));
//	     obj.setDateOfBirth(String.valueOf(MyDateFormat.getDayFromStandardDate(request.getParameter("dob"))));
	   }
	   if(request.getParameter("sex")!=null && !request.getParameter("sex").equals("")){
	     obj.setSex(request.getParameter("sex"));
	     obj.setSexDesc(lookupManager.GetLookupCode("GEN", obj.getSex()).getDescription());
	   }  
	   obj.setAlias(request.getParameter("alias"));
       qform.set("client", obj);

	   if(request.getParameter("firstName") !=null && !(request.getParameter("firstName").equals("") &&
		 request.getParameter("lastName").equals(""))){
	     lst.add(0, obj);
	   }
	   
	   request.setAttribute("clients", lst);
	   String pageFrom=request.getParameter("pageFrom");
	   if(Utility.IsEmpty(pageFrom) && request.getAttribute("pageForm")!=null) pageFrom=(String)request.getAttribute("pageForm");		   
	   request.setAttribute("pageForm", pageFrom);
	   request.setAttribute("var", request.getParameter("var"));
	   request.setAttribute("shortFlag", request.getParameter("shortFlag"));
	   if(!Utility.IsEmpty(request.getParameter("var"))){
		   String[] split= request.getParameter("var").split(",");
		   request.setAttribute("formName", split[0]);
		   request.setAttribute("firstName", split[1]);
		   request.setAttribute("lastName", split[2]);
		   request.setAttribute("sex", split[3]);
		   request.setAttribute("dob", split[4]);
		   request.setAttribute("alias", split[5]);
		   request.setAttribute("clientNo", split[6]);
		   request.setAttribute("statusMsg", split[7]);
		   request.setAttribute("newClientChecked", split[8]);
	   }
	   List genders = lookupManager.LoadCodeList("GEN", true, null, null);
	   LookupCodeValue obj3= new LookupCodeValue();
	   obj3.setCode("");
	   obj3.setDescription("");
	   genders.add(0, obj3);
	   qform.set("genders",genders);

	   Demographic obj2= new Demographic();
 	   
	   return mapping.findForward("edit");
   }

   public void setClientManager(ClientManager clientManager) {
	 this.clientManager = clientManager;
   }

   public void setLookupManager(LookupManager lookupManager) {
	 this.lookupManager = lookupManager;
  }

}
