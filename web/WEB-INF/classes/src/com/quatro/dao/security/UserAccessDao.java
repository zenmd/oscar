package com.quatro.dao.security;

import java.util.ArrayList;
import java.util.List;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
public class UserAccessDao extends HibernateDaoSupport {
	  public List GetUserAccessList(String providerNo)
	  {
		  String sSQL="from UserAccessValue s where s.providerNo= ? order by s.functionCd, s.privilege desc, s.orgCd";		
	      return getHibernateTemplate().find(sSQL ,providerNo);
	  }
	  
	  public List GetUserOrgAccessList(String providerNo)
	  {
		  String sSQL="select distinct o.codecsv from UserAccessValue s, LstOrgcd o where s.providerNo= ? and s.privilege>='r' and s.orgCd=o.code order by o.codecsv";		
		  return getHibernateTemplate().find(sSQL ,providerNo);
	  }
}
