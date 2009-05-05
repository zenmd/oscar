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
package com.quatro.service;

import com.quatro.dao.LookupDao;
import com.quatro.dao.UploadFileDao;
import com.quatro.model.AttachmentText;
import com.quatro.model.Attachment;

import java.util.ArrayList;
import java.util.List;

import org.oscarehr.PMmodule.model.Program;
public class UploadFileManager {
	 private UploadFileDao uploadFileDao;
	 private LookupDao lookupDao;
    public void setLookupDao(LookupDao lookupDao) {
		this.lookupDao = lookupDao;
	}

	public void setUploadFileDao(UploadFileDao uploadFileDao) {
        this.uploadFileDao = uploadFileDao;
    }
	public int getAttachment(String refNo, String fileName, int fileSize){
		return uploadFileDao.getAttachment(refNo, fileName, fileSize);
	}
    public AttachmentText getAttachmentText(Integer docId){
        return uploadFileDao.getAttachmentText(docId);
    }
    public Attachment getAttachmentDetail(Integer docId){
    	return uploadFileDao.getAttachment(docId);
    }
    
    public void saveAttachment(Attachment attDoc){    	
        uploadFileDao.saveAttachement(attDoc);
    }
    public List getAttachment(Integer moduleId,String refNo,String providerNo,Integer shelterId){
    	List lst =uploadFileDao.getAttach(moduleId, refNo, providerNo, shelterId);
    	return lst;
    }
    public void deleteAttachment(Integer docId){
    	uploadFileDao.deleteAttachment(docId);
    }
}
