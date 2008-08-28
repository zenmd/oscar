package com.quatro.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.quatro.model.signaturePad.TopazValue;

public class TopazDao extends HibernateDaoSupport{
  public void saveTopazValue(TopazValue rtv){
     getHibernateTemplate().saveOrUpdate(rtv);
  }
  
  public TopazValue getTopazValue(Integer recordId,String moduleCd){
	  
	  String sql = "From TopazValue where recordId=? and moduleName=?";
	  List lst = getHibernateTemplate().find(sql,new Object[]{recordId,moduleCd});
	  if(lst.size()>0){
		 return (TopazValue)lst.get(0);
	  }else{
		 return null;
	  }
  }
  
  public boolean isSignatureExist(Integer recordId){
	  String sql = "From TopazValue where recordId=?";
	  List lst = getHibernateTemplate().find(sql,new Object[]{recordId});
	  if(lst.size()>0){
		 return true;
	  }else{
		 return false;
	  }
  }
  
  public void deleteSignature(Integer recordId){
      getHibernateTemplate().bulkUpdate("delete TopazValue t where t.recordId=?", recordId);
  }

}
