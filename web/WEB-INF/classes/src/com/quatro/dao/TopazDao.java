package com.quatro.dao;

import java.util.ArrayList;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.quatro.model.TopazValue;

public class TopazDao extends HibernateDaoSupport{
  public void saveTopazValue(TopazValue rtv){
     getHibernateTemplate().saveOrUpdate(rtv);
  }
  
  public TopazValue getTopazValue(Integer recordId){
	  return (TopazValue)getHibernateTemplate().get(TopazValue.class, recordId);
  }
}
