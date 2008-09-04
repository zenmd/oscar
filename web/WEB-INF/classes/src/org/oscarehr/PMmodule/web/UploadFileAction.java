package org.oscarehr.PMmodule.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.action.DynaActionForm;
import org.apache.struts.upload.FormFile;
import org.oscarehr.PMmodule.model.QuatroIntakeHeader;
import org.oscarehr.PMmodule.service.AdmissionManager;
import org.oscarehr.PMmodule.service.ClientManager;
import org.oscarehr.PMmodule.service.ProgramManager;

import com.quatro.common.KeyConstants;
import com.quatro.service.IntakeManager;
import com.quatro.service.LookupManager;
import com.quatro.service.UploadFileManager;
import com.quatro.util.Utility;

import java.util.HashMap;
import java.util.List;
import java.util.GregorianCalendar;
import com.quatro.model.Attachment;
import com.quatro.model.AttachmentText;
import com.quatro.model.LookupCodeValue;

public class UploadFileAction extends BaseClientAction {
	
	 private static Log log = LogFactory.getLog(UploadFileAction.class);
	 private UploadFileManager uploadFileManager;
	 private LookupManager lookupManager;
	 protected AdmissionManager admissionManager;	
	 protected ClientManager clientManager;
	 protected ProgramManager programManager;
     private IntakeManager intakeManager;

	public void setClientManager(ClientManager clientManager) {
		this.clientManager = clientManager;
	}
	
	public void setAdmissionManager(AdmissionManager admMgr){
		this.admissionManager = admMgr;
	}
	
	public void setUploadFileManager(UploadFileManager uploadFileManager) {
		this.uploadFileManager = uploadFileManager;
	}

	 public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		String mthd=request.getParameter("method");
		if("save".equals(mthd)) return save(mapping, form, request, response);
		 return list(mapping, form, request, response);
	    }
	 public ActionForward list(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
			//DynaActionForm accessForm = (DynaActionForm)form;
		 List atts=null;
		
		 HashMap actionParam = (HashMap) request.getAttribute("actionParam");
	       if(actionParam==null){
	    	  actionParam = new HashMap();
	          actionParam.put("clientId", request.getParameter("clientId")); 
	       }
	       request.setAttribute("actionParam", actionParam);
	       String demographicNo= (String)actionParam.get("clientId");
	       if(Utility.IsEmpty(demographicNo)){
	    	   ActionMessages messages= new ActionMessages();
	    	   
	    	   messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("error.save.attachment", request.getContextPath()));
		        saveMessages(request,messages);
				//return mapping.findForward("edit");		        
	    	   return edit(mapping,form,request,response);
	       }
	       request.setAttribute("client", clientManager.getClientByDemographicNo(demographicNo));
		   super.setScreenMode(request, KeyConstants.TAB_CLIENT_ATTCHMENT);
		   Integer currentFacilityId=(Integer)request.getSession().getAttribute(KeyConstants.SESSION_KEY_SHELTERID);
			String providerNo=(String) request.getSession().getAttribute("user");

			Integer shelterId =(Integer)request.getSession(true).getAttribute(KeyConstants.SESSION_KEY_SHELTERID);
		    
			List lstIntakeHeader = intakeManager.getActiveQuatroIntakeHeaderListByFacility(Integer.valueOf(demographicNo), shelterId, providerNo);
		    if(lstIntakeHeader.size()>0) {
		       QuatroIntakeHeader obj0= (QuatroIntakeHeader)lstIntakeHeader.get(0);
	           request.setAttribute("currentIntakeProgramId", obj0.getProgramId());
		    }else{
	           request.setAttribute("currentIntakeProgramId", new Integer(0));
		    }
			
	       try {
		    	// attachment only for client 
			    Integer moduleId = KeyConstants.MODULE_ID_CLIENT;
			    String refNo = demographicNo;			   
			   // List lstProgram = programManager.getPrograms(providerNo, currentFacilityId);
				atts=uploadFileManager.getAttachment(moduleId, refNo,providerNo,currentFacilityId);
			    request.setAttribute("att_files", atts);
		    }catch(Exception ex){
		    	request.setAttribute("att_files", atts);
		    }
			return mapping.findForward("list");
		}
	 public ActionForward addNew(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		 DynaActionForm attForm = (DynaActionForm) form;
		 Attachment attObj =(Attachment)attForm.get("attachmentValue");
		 
		 HashMap actionParam = (HashMap) request.getAttribute("actionParam");
	       if(actionParam==null){
	    	  actionParam = new HashMap();
	          actionParam.put("clientId", request.getParameter("clientId")); 
	       }
	       request.setAttribute("actionParam", actionParam);
	      String demoNo = (String)actionParam.get("clientId");      
		 super.setScreenMode(request, KeyConstants.TAB_CLIENT_ATTCHMENT);
		 List lst = lookupManager.LoadCodeList("DCT", true, null, null);
		 request.setAttribute("lstDocType", lst);
		 ActionMessages messages= new ActionMessages();
		 request.setAttribute("client", clientManager.getClientByDemographicNo(demoNo));
		 Integer cId=Integer.valueOf(demoNo) ;   
		 Integer moduleId = (Integer)request.getSession().getAttribute(KeyConstants.SESSION_KEY_CURRENT_MODULE);
		 if(null==moduleId) moduleId=KeyConstants.MODULE_ID_CLIENT; 
		 if(null==cId){
			 messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("message.attachment.errors",request.getContextPath()));		 
			 saveMessages(request,messages);
		 }
		 else{
			 attObj.setModuleId(moduleId);
			 attObj.setRefNo(cId.toString());
		 }
		 attForm.set("attachmentValue", attObj);
		 return mapping.findForward("edit");
	    }
	 public ActionForward edit(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		 DynaActionForm attForm = (DynaActionForm) form;
		 AttachmentText attTextObj =(AttachmentText)attForm.get("attachmentText");
		 Attachment attObj = (Attachment)attForm.get("attachmentValue");
		 String clientId =request.getParameter("clientId");
		 Integer aId = null;
		 if(null!=request.getParameter("id")) {
			 aId= new Integer(request.getParameter("id"));
			 if(aId.intValue()>0)
			 attObj = uploadFileManager.getAttachmentDetail(aId);
			 attForm.set("attachmentValue", attObj);
		 }
		 attForm.set("attachmentText",attTextObj);
		 
		 if(Utility.IsEmpty(clientId)) clientId =attObj.getRefNo();
		 HashMap actionParam = (HashMap) request.getAttribute("actionParam");
	       if(actionParam==null){
	    	  actionParam = new HashMap();
	          actionParam.put("clientId",clientId ); 
	       }
	       request.setAttribute("actionParam", actionParam);
		
		 super.setScreenMode(request, KeyConstants.TAB_CLIENT_ATTCHMENT);
		 ActionMessages messages= new ActionMessages();
		 String demoNo =(String)actionParam.get("clientId");
		 Integer cId=Integer.valueOf(demoNo) ;       
		 Integer moduleId =KeyConstants.MODULE_ID_CLIENT;  //(Integer)request.getSession().getAttribute(KeyConstants.SESSION_KEY_CURRENT_MODULE);
		 request.setAttribute("client", clientManager.getClientByDemographicNo(demoNo));
		 request.setAttribute("clientId", demoNo);
		 if(null==cId || null==moduleId) messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("message.attachment.errors",request.getContextPath()));
		 List lst = lookupManager.LoadCodeList("DCT", true, null, null);
		 request.setAttribute("lstDocType", lst);
		 return mapping.findForward("edit");
	    }
	 public ActionForward delete(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		 Integer aId = new Integer(request.getParameter("id"));
		 Attachment attObj=uploadFileManager.getAttachmentDetail(aId);
		 HashMap actionParam = (HashMap) request.getAttribute("actionParam");
	       if(actionParam==null){
	    	  actionParam = new HashMap();
	          actionParam.put("clientId",attObj.getRefNo() ); 
	       }
	       request.setAttribute("actionParam", actionParam);
	       
	       String demoNo =(String)actionParam.get("clientId");
	       request.setAttribute("client", clientManager.getClientByDemographicNo(demoNo));
		   request.setAttribute("clientId", demoNo);
		 if(null!=aId)uploadFileManager.deleteAttachment(aId);
		 return list(mapping, form, request, response);
	    }
	 public ActionForward save(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		 DynaActionForm attForm = (DynaActionForm) form;
		 Attachment attObj = (Attachment )attForm.get("attachmentValue");
		 AttachmentText attTextObj =(AttachmentText)attForm.get("attachmentText");		 
		 HashMap actionParam = (HashMap) request.getAttribute("actionParam");
	       if(actionParam==null){
	    	  actionParam = new HashMap();
	          actionParam.put("clientId",attObj.getRefNo() ); 
	       }
	       request.setAttribute("actionParam", actionParam);
	       String demoNo =(String)actionParam.get("clientId");
			 Integer cId=Integer.valueOf(demoNo) ;
		 	ActionMessages messages = new ActionMessages();
	        boolean isError = false;
	        boolean isWarning = false;
	        String dupMessage ="";
			HttpSession session = request.getSession(true);	
			//only for client module 
			Integer moduleId = KeyConstants.MODULE_ID_CLIENT;
			String programId = "";
			Integer shelterId=(Integer)request.getSession().getAttribute(KeyConstants.SESSION_KEY_SHELTERID);
			String providerNo=(String) session.getAttribute("user");
	        try{
	        	programId = this.clientManager.getRecentProgramId(cId, providerNo, shelterId).toString();
	        }catch(Exception e){;}
	       
			log.info("attachment client upload id: id="  + cId);
			FormFile formFile = attTextObj.getImagefile();			
			String type = formFile.getFileName().substring(formFile.getFileName().lastIndexOf(".")+1);
			int count=0;		
			log.info("extension = " + type);
			if(Utility.IsEmpty(formFile.getContentType()) || formFile.getFileSize()==0){
				messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("error.save.attachment", request.getContextPath()));
		        saveMessages(request,messages);
				//return mapping.findForward("edit");	
		        return edit(mapping,form,request,response);
			}
			try {
				byte[] imageData = formFile.getFileData();
				Byte[] imageData2 = new Byte[imageData.length];
				for(int x=0;x<imageData.length;x++) {
					imageData2[x] = new Byte(imageData[x]);
				}
				attTextObj.setAttData(imageData);
				attTextObj.setRevDate(new GregorianCalendar());
				attObj.setFileName(formFile.getFileName());
				attObj.setFileType(type);
				attObj.setModuleId(moduleId);			 
				attObj.setProviderNo((String) session.getAttribute("user"));
				attObj.setRefNo(cId.toString());
				attObj.setRefProgramId(new Integer(programId));
				attObj.setRevDate(new GregorianCalendar());
				attObj.setFileSize(new Integer(formFile.getFileSize()));
				attObj.setAttText(attTextObj);
				if(attObj.getId().intValue()==0) attObj.setId(null);
				uploadFileManager.saveAttachment(attObj);				
				attForm.set("attachmentValue",attObj);
				attForm.set("attachmentText", attObj.getAttText());
			}catch(Exception e) {
				log.error(e);
				//post error to page
			}
			count=uploadFileManager.getAttachment(cId.toString(), formFile.getFileName(), formFile.getFileSize());
			if(count>1) dupMessage="Duplicate file name detected";
			messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("message.save.attachment.success", request.getContextPath(),dupMessage));
	        saveMessages(request,messages);
			//return mapping.findForward("edit");	
	        return edit(mapping,form,request,response);

	    }
	 public ActionForward showFile(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response){
		 return list(mapping, form, request, response); 
	 }
	 	public void setLookupManager(LookupManager lookupManager) {
		this.lookupManager = lookupManager;
	}
		public void setProgramManager(ProgramManager programManager) {
			this.programManager = programManager;
		}

		public void setIntakeManager(IntakeManager intakeManager) {
			this.intakeManager = intakeManager;
		}

}
