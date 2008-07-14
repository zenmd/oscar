package com.quatro.service.security;
import java.util.*;

import com.quatro.service.LookupManager;
import com.quatro.util.*;
import org.apache.batik.dom.util.HashTable;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import oscar.OscarProperties;

import com.quatro.dao.security.UserAccessDao;
import com.quatro.model.security.*;
public class UserAccessManager
{
    private UserAccessDao _dao=null;
    public SecurityManager getUserUserSecurityManager(String providerNo,LookupManager lkManager)
    {
    	// _list is ordered by Function, privilege (desc) and the org
    	SecurityManager secManager = new SecurityManager();
    	secManager.setLookupManager(lkManager);
    	Hashtable functionList = new Hashtable();
        List list = _dao.GetUserAccessList(providerNo);
    	if (list.size()>0) {
	    	int startIdx = 0;
	    	List orgList = getAccessListForFunction(list,startIdx);
	    	functionList.put(((UserAccessValue)list.get(startIdx)).getFunctionCd(), list);
	
	    	while(orgList != null && startIdx + orgList.size()<list.size())
	    	{
	    		startIdx += orgList.size();
	        	orgList = getAccessListForFunction(list,startIdx);
	        	functionList.put(((UserAccessValue)list.get(startIdx)).getFunctionCd(), orgList);
	    	}
    	}
    	secManager.setUserFunctionAccessList(functionList);
    	List orgs = _dao.GetUserOrgAccessList(providerNo);
    	if(orgs.size() > 0 && OscarProperties.getInstance().getProperty("ORGROOT").equals((String) orgs.get(0))) 
    	{
    		orgs.clear();
    	}
    	secManager.setUserOrgAccessList(orgs);
    	return secManager;
    }
    private List getAccessListForFunction(List  list, int startIdx)
    {
    	if (startIdx >= list.size()) return null;
    	List orgList = new ArrayList();
    	UserAccessValue uofv = (UserAccessValue) list.get(startIdx);
    	String functionCd = uofv.getFunctionCd();
    	orgList.add(uofv);
    	startIdx ++;
    	while (startIdx < list.size()) {
        	uofv = (UserAccessValue) list.get(startIdx);
    		if (uofv.getFunctionCd().equals(functionCd)) {
    			orgList.add(uofv);
        		startIdx ++;
    		}
    		else
    		{
    			break;
    		}
    	}
    	return orgList;
    }
    public void setUserAccessDao(UserAccessDao dao)
    {
    	_dao = dao;
    }
}
