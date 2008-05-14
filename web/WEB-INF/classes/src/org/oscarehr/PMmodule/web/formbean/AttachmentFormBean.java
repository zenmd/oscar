package org.oscarehr.PMmodule.web.formbean;

import java.io.Serializable;
import java.util.Calendar;

import com.quatro.model.Attachment;
import com.quatro.model.AttachmentText;
import com.quatro.model.LookupCodeValue;

public class AttachmentFormBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private Attachment attachmentValue;

	private AttachmentText attachmentTextValue;

	public AttachmentText getAttachmentTextValue() {
		return attachmentTextValue;
	}

	public void setAttachmentTextValue(AttachmentText attachmentTextValue) {
		this.attachmentTextValue = attachmentTextValue;
	}

	public Attachment getAttachmentValue() {
		return attachmentValue;
	}

	public void setAttachmentValue(Attachment attachmentValue) {
		this.attachmentValue = attachmentValue;
	}

}
