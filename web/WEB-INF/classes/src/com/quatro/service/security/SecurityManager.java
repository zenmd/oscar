package com.quatro.service.security;
import java.util.*;

import org.oscarehr.PMmodule.service.RoomDemographicManager;

import com.quatro.dao.LookupDao;
import com.quatro.model.LookupCodeValue;
import com.quatro.model.security.UserAccessValue;
import com.quatro.service.LookupManager;
import com.quatro.util.Utility;
public class SecurityManager {
	public static final String ACCESS_NONE = "o";
	public static final String ACCESS_READ = "r";
	public static final String ACCESS_UPDATE = "u";
	public static final String ACCESS_WRITE = "w";
	public static final String ACCESS_ALL = "x";
	private LookupManager lookupManager;
	Hashtable _userFunctionAccessList;	
	public void set_userFunctionAccessList(Hashtable functionAccessList) {
		_userFunctionAccessList = functionAccessList;
	}
	public void setLookupManager(LookupManager lkManager) {
		lookupManager=lkManager;
		}
	
    public String GetAccess(String functioncd, String orgcd)
    {
        String privilege = this.ACCESS_NONE;
        LookupCodeValue lcv=lookupManager.GetLookupCode("ORG", orgcd);
        String fullcode="";
        if(lcv !=null)   fullcode= lcv.getBuf1();
        
        try
        {
            List  orgList =  (List ) _userFunctionAccessList.get(functioncd);
            if(orgList!=null){ 
	            Iterator it = orgList.iterator();
	            
	            while(it.hasNext())
	            {
	            	UserAccessValue uav = (UserAccessValue)it.next();            
	            	if (uav.isOrgApplicable()) 
	            	{
	            		if ("".equals(orgcd) || Utility.IsEmpty(uav.getOrgCd()) || fullcode.startsWith(uav.getOrgCd()))
		            	{
		            		privilege = uav.getPrivilege();
		            		break;
		            	} 
	            	}else{
	            		privilege = uav.getPrivilege();
	            		break;
	            	}
	            }
            }
        }  
        catch (Exception ex)
        {
        	;
        }
        
        return privilege;
    }
    public String GetAccess(String function)
    {
        return GetAccess(function, "");
    }

}
