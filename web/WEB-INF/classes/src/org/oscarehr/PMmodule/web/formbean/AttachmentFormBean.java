/*******************************************************************************
 * Copyright (c) 2008, 2009 Quatro Group Inc. and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU General Public License
 * which accompanies this distribution, and is available at
 * Free Software Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA 02111-1307, USA.
 *
 * Contributors:
 *     <Quatro Group Software Systems inc.>  <OSCAR Team>
 *******************************************************************************/
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
