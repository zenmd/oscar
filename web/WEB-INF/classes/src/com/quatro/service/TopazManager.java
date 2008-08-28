package com.quatro.service;

import com.quatro.dao.TopazDao;
import com.quatro.model.signaturePad.TopazValue;

public class TopazManager {
    private TopazDao topazDao;

    public void setTopazDao(TopazDao topazDao) {
        this.topazDao = topazDao;
    }

    public TopazValue getTopazValue(Integer recordId,String moduleCd){
        return topazDao.getTopazValue(recordId,moduleCd);
    }

    public boolean isSignatureExist(Integer recordId){
        return topazDao.isSignatureExist(recordId);
    }
    
    public void saveTopazValue(TopazValue tv){
        topazDao.saveTopazValue(tv);
    }
    
    public void deleteSignature(Integer recordId){
        topazDao.deleteSignature(recordId);
    }

}
