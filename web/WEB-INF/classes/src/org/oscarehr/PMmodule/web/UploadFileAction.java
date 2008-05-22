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
import org.oscarehr.PMmodule.service.AdmissionManager;
import org.oscarehr.PMmodule.service.ClientManager;

import com.quatro.common.KeyConstants;
import com.quatro.service.LookupManager;
import com.quatro.service.UploadFileManager;
import com.quatro.util.Utility;

import java.util.HashMap;
import java.util.List;
import java.util.GregorianCalendar;
import com.quatro.model.Attachment;
import com.quatro.model.AttachmentText;
import com.quatro.model.LookupCodeValue;

public class UploadFileAction extends BaseAction {
	
	 private static Log log = LogFactory.getLog(UploadFileAction.class);
	 private UploadFileManager uploadFileManager;
	 private LookupManager lookupManager;
	 protected AdmissionManager admissionManager;	
	 protected ClientManager clientManager;

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
		 List<Attachment> atts=null;
		 super.setScreenMode(request, KeyConstants.TAB_ATTCHMENT);
		 HashMap actionParam = (HashMap) request.getAttribute("actionParam");
	       if(actionParam==null){
	    	  actionParam = new HashMap();
	          actionParam.put("clientId", request.getParameter("clientId")); 
	       }
	       request.setAttribute("actionParam", actionParam);
	       String demographicNo= (String)actionParam.get("clientId");
	       request.setAttribute("client", clientManager.getClientByDemographicNo(demographicNo));
		   super.setScreenMode(request, KeyConstants.TAB_ATTCHMENT);
	       try {
		    	// attachment only for client 
			    Integer moduleId = KeyConstants.MODULE_ID_CLIENT;
			    String refNo = demographicNo;
			    Integer programId =new Integer((String)request.getSession().getAttribute("case_program_id"));
				atts=uploadFileManager.getAttachment(moduleId, refNo,programId);
			    request.setAttribute("att_files", atts);
		    }catch(Exception ex){
		    	request.setAttribute("att_files", atts);
		    }
			return mapping.findForward("list");
		}
	 public ActionForward addNew(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		 DynaActionForm attForm = (DynaActionForm) form;
		 super.setScreenMode(request, KeyConstants.TAB_ATTCHMENT);
		 List lst = lookupManager.LoadCodeList("DCT", true, null, null);
		 request.setAttribute("lstDocType", lst);
		 ActionMessages messages= new ActionMessages();
		 
		 Integer cId=(Integer)(request.getSession().getAttribute(KeyConstants.SESSION_KEY_CLIENTID));    
		 Integer moduleId = (Integer)request.getSession().getAttribute(KeyConstants.SESSION_KEY_CURRENT_MODULE);
		 if(null==cId || null==moduleId) messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("message.attachment.errors",request.getContextPath()));		 
		 saveMessages(request,messages);
		 
		 return mapping.findForward("edit");
	    }
	 public ActionForward edit(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		 DynaActionForm attForm = (DynaActionForm) form;
		 Attachment attObj = null;
		 super.setScreenMode(request, KeyConstants.TAB_ATTCHMENT);
		 ActionMessages messages= new ActionMessages();
		 
		 Integer cId=(Integer)(request.getSession().getAttribute(KeyConstants.SESSION_KEY_CLIENTID));    
		 Integer moduleId = (Integer)request.getSession().getAttribute(KeyConstants.SESSION_KEY_CURRENT_MODULE);
		 if(null==cId || null==moduleId) messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("message.attachment.errors",request.getContextPath()));
		 
		 Integer aId = null;
		 if(null!=request.getParameter("id")) aId= new Integer(request.getParameter("id"));
		 else aId= (Integer)request.getAttribute("id");
		 attObj = uploadFileManager.getAttachmentDetail(aId);
		 AttachmentText attTextObj =(AttachmentText)attForm.get("attachmentText");
		// attTextObj.getImagefile().setFileName(attObj.getFileName());
		 attForm.set("attachmentValue", attObj);
		 attForm.set("attachmentText",attTextObj);
		// attForm.set("attachmentText",attTextObj);
		 List lst = lookupManager.LoadCodeList("DCT", true, null, null);
		 request.setAttribute("lstDocType", lst);
		 return mapping.findForward("edit");
	    }
	 public ActionForward delete(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		 Integer aId = new Integer(request.getParameter("id"));
		 if(null!=aId)uploadFileManager.deleteAttachment(aId);
		 return list(mapping, form, request, response);
	    }
	 public ActionForward save(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
			
		 	ActionMessages messages = new ActionMessages();
	        boolean isError = false;
	        boolean isWarning = false;
		    DynaActionForm attForm = (DynaActionForm)form;
		 	Attachment attObj = (Attachment )attForm.get("attachmentValue");
		 	AttachmentText attTextObj =(AttachmentText )attForm.get("attachmentText");
			HttpSession session = request.getSession(true);
			Integer cId=(Integer)(session.getAttribute(KeyConstants.SESSION_KEY_CLIENTID));    
			Integer moduleId = (Integer)session.getAttribute(KeyConstants.SESSION_KEY_CURRENT_MODULE);
			String programId = (String) request.getSession().getAttribute("case_program_id");
	        if(Utility.IsEmpty(programId)){
	        	//Integer demoInt = new Integer(cId);
	        	programId = this.admissionManager.getCurrentBedProgramAdmission(cId).getProgramId().toString();
	        }
			log.info("attachment client upload id: id="  + cId);

			FormFile formFile = attTextObj.getImagefile();			
			String type = formFile.getFileName().substring(formFile.getFileName().lastIndexOf(".")+1);
						
			log.info("extension = " + type);
			
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
				attObj.setAttText(attTextObj);
				uploadFileManager.saveAttachment(attObj);
				request.setAttribute("id", attObj.getId());
				messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("message.attachment.saved", request.getContextPath()));
		        saveMessages(request,messages);
				
			}catch(Exception e) {
				log.error(e);
				//post error to page
			}
			
		//	request.setAttribute("success",new Boolean(true));
			
			return edit(mapping,form,request,response);

	    }
	 public ActionForward showFile(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response){
		 return list(mapping, form, request, response); 
	 }
	 	public void setLookupManager(LookupManager lookupManager) {
		this.lookupManager = lookupManager;
	}

}
