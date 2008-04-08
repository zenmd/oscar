package com.quatro.service;

import com.quatro.dao.TopazDao;
import com.quatro.model.TopazValue;

public class TopazManager {
    private TopazDao topazDao;

    public void setTopazDao(TopazDao topazDao) {
        this.topazDao = topazDao;
    }

    public TopazValue getTopazValue(Integer recordId){
        return topazDao.getTopazValue(recordId);
    }

    public void saveTopazValue(TopazValue tv){
        topazDao.saveTopazValue(tv);
    }
}
