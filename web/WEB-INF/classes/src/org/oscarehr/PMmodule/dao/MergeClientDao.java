package org.oscarehr.PMmodule.dao;


import java.util.List;

import org.oscarehr.PMmodule.model.ClientMerge;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.quatro.common.KeyConstants;

public class MergeClientDao extends HibernateDaoSupport {
	
	 public void merge(ClientMerge cmObj) {
	        if (cmObj == null) {
	            throw new IllegalArgumentException();
	        }
	        this.getHibernateTemplate().saveOrUpdate(cmObj);
	    }
	
	    public void unMerge(ClientMerge cmObj){	    	
	    	  getHibernateTemplate().bulkUpdate("update ClientMerge c set c.deleted=1,c.lastUpdateUser='" +
	                 cmObj.getProviderNo() + "' c.lastUpdateDate="+cmObj.getLastUpdateDate() +" where c.demographic_no=?", cmObj.getClientId() );
	    }
	    
	    public Integer getHead(Integer demographic_no) {
	        String queryStr = "FROM ClientMerge a WHERE a.deleted=0 and a.demographic_no =?";
	        ClientMerge cmObj= (ClientMerge)getHibernateTemplate().find(queryStr, new Object[] {demographic_no });     
	        return cmObj.getClientId();
	    }
	    
	    public List getTail(Integer demographic_no){	        
	        String sql = "from ClientMerge where merged_to = ? and deleted = 0";	              
	        List  lst= getHibernateTemplate().find(sql, new Object[] {demographic_no});
	        return lst;
	        
	    }
}
