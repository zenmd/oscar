package com.quatro.service;

import com.quatro.dao.UploadFileDao;
import com.quatro.model.AttachmentText;
import com.quatro.model.Attachment;
import java.util.List;
public class UploadFileManager {
	 private UploadFileDao uploadFileDao;

    public void setUploadFileDao(UploadFileDao uploadFileDao) {
        this.uploadFileDao = uploadFileDao;
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
    public List<Attachment> getAttachment(Integer moduleId,String refNo,Integer programId){
    	List<Attachment> lst =uploadFileDao.getAttach(moduleId, refNo,programId);
    	return lst;
    }
    public void deleteAttachment(Integer docId){
    	uploadFileDao.deleteAttachment(docId);
    }
}
