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
