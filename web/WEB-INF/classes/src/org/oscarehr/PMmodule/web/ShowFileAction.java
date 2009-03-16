package org.oscarehr.PMmodule.web;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.actions.DispatchAction;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;

import com.quatro.common.KeyConstants;
import com.quatro.model.Attachment;
import com.quatro.model.AttachmentText;
import com.quatro.model.LookupCodeValue;
import com.quatro.service.LookupManager;
import com.quatro.service.UploadFileManager;

public class ShowFileAction extends BaseClientAction {
	private static Log log = LogFactory.getLog(UploadFileAction.class);

	private UploadFileManager uploadFileManager;

	private LookupManager lookupManager;

	public void setUploadFileManager(UploadFileManager uploadFileManager) {
		this.uploadFileManager = uploadFileManager;
	}

	public ActionForward unspecified(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Integer recordId = Integer.valueOf((String) request.getParameter("id"));
		Attachment attObj = uploadFileManager.getAttachmentDetail(recordId);
		super.getAccess(request, KeyConstants.FUN_CLIENTDOCUMENT, attObj.getRefProgramId());
		
		AttachmentText attTextObj = uploadFileManager.getAttachmentText(recordId);
		LookupCodeValue lcv = lookupManager.GetLookupCode("DTT", attObj.getFileType());
		String contentType = "Unknown";
		if (lcv != null)
			contentType = lcv.getDescription();

		response.setContentType(contentType);
		Integer len = new Integer(attTextObj.getAttData().length);
		response.addHeader("Content-Length", len.toString());
		response.addHeader("Content-Disposition", "filename=\""
				+ attObj.getFileName() + "\"; size=" + len.toString());
		OutputStream o = response.getOutputStream();
		o.write(attTextObj.getAttData());
		o.flush();
		o.close();
		return null;
	}

	public void setLookupManager(LookupManager lookupManager) {
		this.lookupManager = lookupManager;
	}
}
