/*
 * 
 * Copyright (c) 2001-2002. Centre for Research on Inner City Health, St. Michael's Hospital, Toronto. All Rights Reserved. *
 * This software is published under the GPL GNU General Public License. 
 * This program is free software; you can redistribute it and/or 
 * modify it under the terms of the GNU General Public License 
 * as published by the Free Software Foundation; either version 2 
 * of the License, or (at your option) any later version. * 
 * This program is distributed in the hope that it will be useful, 
 * but WITHOUT ANY WARRANTY; without even the implied warranty of 
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the 
 * GNU General Public License for more details. * * You should have received a copy of the GNU General Public License 
 * along with this program; if not, write to the Free Software 
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA 02111-1307, USA. * 
 * 
 * <OSCAR TEAM>
 * 
 * This software was written for 
 * Centre for Research on Inner City Health, St. Michael's Hospital, 
* Toronto, Ontario, Canada  - UPDATED: Quatro Group 2008/2009
 */

package org.oscarehr.PMmodule.dao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.LogicalExpression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.oscarehr.PMmodule.model.Demographic;
import org.oscarehr.PMmodule.model.DemographicExt;
//import org.oscarehr.PMmodule.model.caisi_ProgramProvider;
import org.oscarehr.PMmodule.web.formbean.ClientSearchFormBean;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import oscar.MyDateFormat;

import com.quatro.common.KeyConstants;
import com.quatro.util.Utility;

public class ClientDao extends HibernateDaoSupport {

	private Logger log = LogManager.getLogger(ClientDao.class);
//	private static final int LIST_PROCESSING_CHUNK_SIZE=64;

	public boolean clientExists(Integer demographicNo) {
		boolean exists = getHibernateTemplate().get(Demographic.class, demographicNo) != null;
		log.debug("exists: " + exists);

		return exists;
	}

	public Demographic getClientByDemographicNo(Integer demographicNo) {
		if (demographicNo == null || demographicNo.intValue() <= 0) {
			throw new IllegalArgumentException();
		}

		Demographic result = (Demographic)getHibernateTemplate().get(Demographic.class, demographicNo);

		if (log.isDebugEnabled()) {
			log.debug("getClientByDemographicNo: id=" + demographicNo + ", found=" + (result != null));
		}
        result.setEffDateTxt(MyDateFormat.getSysDateString(result.getEffDate()));
		return result;
	}
	public List getClientSubRecords(Integer demographicNo)
	{
		String sSQL="select a.merged_to from v_demographic_merged a where a.demographic_no = " + demographicNo.toString();
		SQLQuery q = getSession().createSQLQuery(sSQL);
		List lst =  q.list();
		return lst;
	}
	
	public List getClientFamilyByDemographicNo(String demographicNos) {
		if (demographicNos == null || demographicNos.equals("")) {
			throw new IllegalArgumentException();
		}
		
        String[] split= demographicNos.split(",");
        StringBuffer sb = new StringBuffer();
        Object[] obj= new Object[split.length];
        for(int i=0;i<split.length;i++){
           sb.append(",?");
           obj[i]=Integer.valueOf(split[i]);
        }
//        String sSQL="from QuatroIntakeHeader i where i.clientId = ? and i.programId in (" +
//          sb.substring(1) + ") order by i.createdOn desc";
		String sSQL = "from Demographic d where d.DemographicNo in (" +
		  sb.substring(1) + ") order by d.LastName, d.FirstName";
		List result = getHibernateTemplate().find(sSQL, obj);
		
		return result;
	}

    public List getClients() {
		String queryStr = " FROM Demographic";
	    //@SuppressWarnings("unchecked")
		List rs = getHibernateTemplate().find(queryStr);

		if (log.isDebugEnabled()) {
			log.debug("getClients: # of results=" + rs.size());
		}

		return rs;
	}

	public List search(ClientSearchFormBean bean, boolean returnOptinsOnly,boolean excludeMerged) {
		Criteria criteria = getSession().createCriteria(Demographic.class);
		String firstName = "";
		String lastName = "";
		String firstNameL = "";
		String lastNameL = "";
		String bedProgramId = "";
		String assignedToProviderNo = "";
		String admitDateFromCond = "";
		String admitDateToCond = "";
		String active = "";
		String gender = "";
		String AND = " and ";
		String WHERE = " where ";
		String sql = "";
		String sql2 = "";
		
		//@SuppressWarnings("unchecked")
		List results = null;
		if (bean.getFirstName() != null && bean.getFirstName().length() > 0) {
			firstName = bean.getFirstName();
			// firstName = StringEscapeUtils.escapeSql(firstName);
			firstNameL = firstName + "%"; 
		}
		
		if (bean.getLastName() != null && bean.getLastName().length() > 0) {
			lastName = bean.getLastName();
//			lastName = StringEscapeUtils.escapeSql(lastName);
			lastNameL =  lastName + "%";
		}
		
		String clientNo = bean.getDemographicNo(); 
		//exclude merged client 
		if(excludeMerged)criteria.add(Expression.eq("merged", Boolean.FALSE));
		if (clientNo != null && !"".equals(clientNo))
		{
			if (com.quatro.util.Utility.IsInt(clientNo) ) {
				criteria.add(Expression.eq("DemographicNo", Integer.valueOf(clientNo)));
				results = criteria.list();
			} 
			else 
			{
				/* invalid client no generates a empty search results */
				results = new ArrayList();
			}
		    return results;
		}
		LogicalExpression condAlias1 = null;
		LogicalExpression condAlias2 = null;
		LogicalExpression condFirstName = null;
		LogicalExpression condLastName = null;
		if (firstName.length() > 0) {
//			sql = "(LEFT(SOUNDEX(first_name),4) = LEFT(SOUNDEX('" + firstName + "'),4))";
//			sql2 = "(LEFT(SOUNDEX(alias),4) = LEFT(SOUNDEX('" + firstName + "'),4))";
//			condFirstName = Restrictions.or(Restrictions.ilike("FirstName", firstNameL), Restrictions.sqlRestriction(sql));
//			condAlias1 = Restrictions.or(Restrictions.ilike("Alias", firstNameL),Restrictions.sqlRestriction(sql2));
			criteria.add(Restrictions.or(Restrictions.or(Restrictions.ilike("LastName", firstNameL), Restrictions.ilike("Alias", firstNameL)),Restrictions.ilike("FirstName", firstNameL)));
		}
		if (lastName.length() > 0) {
//				sql = "(LEFT(SOUNDEX(last_name),4) = LEFT(SOUNDEX('" + lastName + "'),4))";
//				sql2 = "(LEFT(SOUNDEX(alias),4) = LEFT(SOUNDEX('" + lastName + "'),4))";
//				condLastName = Restrictions.or(Restrictions.ilike("LastName", lastNameL), Restrictions.sqlRestriction(sql));
//				condAlias2 = Restrictions.or(Restrictions.ilike("Alias", lastNameL),Restrictions.sqlRestriction(sql2));
				criteria.add(Restrictions.or(Restrictions.or(Restrictions.ilike("FirstName", lastNameL), Restrictions.ilike("Alias", lastNameL)),Restrictions.ilike("LastName", lastNameL)));
		}
/*
		if (firstName.length() > 0 && lastName.length()>0)
		{
			criteria.add(Restrictions.or(Restrictions.and(condFirstName, condLastName),  Restrictions.or(condAlias1, condAlias2)));
		}
		else if (firstName.length() > 0)
		{
			criteria.add(Restrictions.or(condFirstName,condAlias1));
		}
		else if (lastName.length()>0)
		{
			criteria.add(Restrictions.or(condLastName,condAlias2));
		}
*/		
		if (bean.getDob() != null && bean.getDob().length() > 0) {
			criteria.add(Expression.eq("DateOfBirth", MyDateFormat.getCalendar(bean.getDob())));
		}

		if (bean.getHealthCardNumber() != null && bean.getHealthCardNumber().length() > 0) {
			criteria.add(Expression.eq("Hin", bean.getHealthCardNumber()));
		}

		if (bean.getHealthCardVersion() != null && bean.getHealthCardVersion().length() > 0) {
			criteria.add(Expression.eq("Ver", bean.getHealthCardVersion()));
		}
		
		if(bean.getBedProgramId() != null && bean.getBedProgramId().length() > 0) {
			bedProgramId = bean.getBedProgramId(); 
			sql = " demographic_no in (select decode(dm.merged_to,null,i.client_id,dm.merged_to) from intake i,v_demographic_merged dm where i.client_id=dm.demographic_no(+) and i.program_id in (" + bedProgramId + "))";		
			criteria.add(Restrictions.sqlRestriction(sql));
		}
		if(bean.getAssignedToProviderNo() != null && bean.getAssignedToProviderNo().length() > 0) {
			assignedToProviderNo = bean.getAssignedToProviderNo();
			sql = " demographic_no in (select decode(dm.merged_to,null,a.client_id,dm.merged_to) from admission a,v_demographic_merged dm where a.client_id=dm.demographic_no(+)and a.primaryWorker='" + assignedToProviderNo + "')"; 
			criteria.add(Restrictions.sqlRestriction(sql));
		}
		
		active = bean.getActive();
		if("1".equals(active)) {
			criteria.add(Expression.ge("activeCount", new Integer(1)));
		}else if ("0".equals(active)){
			criteria.add(Expression.eq("activeCount", new Integer(0)));
		}
		if (bean.isMerged()) {
			criteria.add(Expression.eq("merged", Boolean.TRUE));
		}
			
		gender = bean.getGender();
		if (gender != null && !"".equals(gender)){
			criteria.add(Expression.eq("Sex", gender));
		}
		criteria.addOrder(Order.asc("LastName"));
		criteria.addOrder(Order.asc("FirstName"));
		results = criteria.list();

		if (log.isDebugEnabled()){
			log.debug("search: # of results=" + results.size());
		}
		return results;
	}

	public void saveClient(Demographic client) {
		if (client == null) {
			throw new IllegalArgumentException();
		}

		Calendar cal= client.getDateOfBirth();
		if(cal==null){
		  cal= Calendar.getInstance();
		  cal.set(1,1,1);
		  client.setDateOfBirth(cal);
		}

		this.getHibernateTemplate().saveOrUpdate(client);

		if (log.isDebugEnabled()) {
			log.debug("saveClient: id=" + client.getDemographicNo());
		}
	}
	
	 public List getRecentProgramIds(Integer clientId, String providerNo, Integer shelterId){
		 String progSql = Utility.getUserOrgQueryString(providerNo, shelterId);
	    	String sql = "select p.programId  from QuatroIntakeHeader p ,Program c ";
	    	sql+=" where p.programId = c.id and p.clientId=? and  p.programId in " + progSql;
	    	sql+=" order by p.createdOn desc " ;    	
	    	List lst = this.getHibernateTemplate().find(sql, new Object[] {clientId});
	    	return lst;
	 }
	   
    public Integer getRecentProgramId(Integer clientId, String providerNo, Integer shelterId){
    	String progSql = Utility.getUserOrgQueryString(providerNo, shelterId);
    	String sql = "select p.programId  from QuatroIntakeHeader p ,Program c ";
    	sql+=" where p.programId = c.id and p.clientId=? and p.programId in " + progSql;
    	sql+=" order by p.createdOn desc " ;    	
    	List lst = this.getHibernateTemplate().find(sql, new Object[] {clientId});
    	
    	if (lst.size() > 0)
    		return (Integer) lst.get(0);
    	else
    		return null;
    }

	public DemographicExt getDemographicExt(Integer id) {
		if (id == null || id.intValue() <= 0) {
			throw new IllegalArgumentException();
		}
		DemographicExt result = (DemographicExt)this.getHibernateTemplate().get(DemographicExt.class, id);
		if (log.isDebugEnabled()) {
			log.debug("getDemographicExt: id=" + id + ",found=" + (result != null));
		}
		return result;
	}

    public List getDemographicExtByDemographicNo(Integer demographicNo) {
		if (demographicNo == null || demographicNo.intValue() <= 0) {
			throw new IllegalArgumentException();
		}
	    //@SuppressWarnings("unchecked")
		List results = this.getHibernateTemplate().find("from DemographicExt d where d.demographicNo = ?", demographicNo);
		if (log.isDebugEnabled()) {
			log.debug("getDemographicExtByDemographicNo: demographicNo=" + demographicNo + ",# of results=" + results.size());
		}
		return results;
	}

	public DemographicExt getDemographicExt(Integer demographicNo, String key) {
		if (demographicNo == null || demographicNo.intValue() <= 0) {
			throw new IllegalArgumentException();
		}
		if (key == null || key.length() <= 0) {
			throw new IllegalArgumentException();
		}
		List results = this.getHibernateTemplate().find("from DemographicExt d where d.demographicNo = ? and d.key = ?", new Object[] {demographicNo, key});
		if (results.isEmpty()) return null;
		DemographicExt result = (DemographicExt)results.get(0);
		if (log.isDebugEnabled()) {
			log.debug("getDemographicExt: demographicNo=" + demographicNo + ",key=" + key + ",found=" + (result != null));
		}
		return result;
	}
	public void updateClientBenUnitStatus(Demographic client){
		if (client == null) {
			throw new IllegalArgumentException();
		}
		 getHibernateTemplate().bulkUpdate("update Demographic r set r.benefitUnitStatus='" + client.getBenefitUnitStatus() + "', " +
				 "r.Pin='" + client.getPin() + "'," +
				 "r.Hin='" + client.getHin() + "'" +
				 " where r.DemographicNo=? ", client.getDemographicNo());
	}
	public void updateDemographicExt(DemographicExt de) {
		if (de == null) {
			throw new IllegalArgumentException();
		}
		this.getHibernateTemplate().saveOrUpdate(de);
		if (log.isDebugEnabled()) {
			log.debug("updateDemographicExt: id=" + de.getId());
		}
	}

	public void saveDemographicExt(Integer demographicNo, String key, String value) {
		if (demographicNo == null || demographicNo.intValue() <= 0) {
			throw new IllegalArgumentException();
		}
		if (key == null || key.length() <= 0) {
			throw new IllegalArgumentException();
		}
		if (value == null) {
			return;
		}
		DemographicExt existingDe = this.getDemographicExt(demographicNo, key);
		if (existingDe != null) {
			existingDe.setDateCreated(new Date());
			existingDe.setValue(value);
			this.getHibernateTemplate().update(existingDe);
		}else {
			DemographicExt de = new DemographicExt();
			de.setDateCreated(new Date());
			de.setDemographicNo(demographicNo);
			de.setHidden(false);
			de.setKey(key);
			de.setValue(value);
			this.getHibernateTemplate().save(de);
		}
		if (log.isDebugEnabled()) {
			log.debug("saveDemographicExt: demographicNo=" + demographicNo + ",key=" + key + ",value=" + value);
		}
	}

	public void removeDemographicExt(Integer id) {
		if (id == null || id.intValue() <= 0) {
			throw new IllegalArgumentException();
		}
		this.getHibernateTemplate().delete(getDemographicExt(id));
		if (log.isDebugEnabled()) {
			log.debug("removeDemographicExt: id=" + id);
		}
	}

	public void removeDemographicExt(Integer demographicNo, String key) {
		if (demographicNo == null || demographicNo.intValue() <= 0) {
			throw new IllegalArgumentException();
		}
		if (key == null || key.length() <= 0) {
			throw new IllegalArgumentException();
		}
		this.getHibernateTemplate().delete(getDemographicExt(demographicNo, key));
		if (log.isDebugEnabled()) {
			log.debug("removeDemographicExt: demographicNo=" + demographicNo + ",key=" + key);
		}
	}

	public List getIntakeByShelter(Integer demographicNo, Integer shelterId) {
		  if (demographicNo == null || demographicNo.intValue() <= 0) {
		    throw new IllegalArgumentException();
		  }
		  String queryStr = "";
		  List rs=null;
		  if(shelterId.intValue()>0){
			  queryStr = "FROM QuatroIntakeHeader a WHERE a.clientId=? and a.programId in " +
			        "(select s.id from Program s where s.shelterId=? or s.shelterId is null) ORDER BY a.createdOn DESC";
			  rs = getHibernateTemplate().find(queryStr, new Object[] { demographicNo,  shelterId });
		  }
		  else{
			  queryStr = "FROM QuatroIntakeHeader a WHERE a.clientId=? ORDER BY a.createdOn DESC";
			  rs = getHibernateTemplate().find(queryStr, new Object[] { demographicNo});
		  }
        return rs;
	}

	public List getProgramIdByDemoNo(String demoNo) {
		String q = "Select a.ProgramId From Admission a " + "Where a.ClientId=? and a.AdmissionDate<=? and " + "(a.DischargeDate>=? or (a.DischargeDate is null) or a.DischargeDate=?)";
		/* default time is Oscar default null time 0001-01-01. */
		Date defdt = new GregorianCalendar(1, 0, 1).getTime();
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		Date dt = cal.getTime();
		List rs = getHibernateTemplate().find(q, new Object[] {demoNo, dt, dt, defdt});
		return rs;
	}
/*
	public static class ClientListsReportResults
	{
		public int demographicId;
		public String firstName;
		public String lastName;
		public Calendar dateOfBirth;
		public int admissionId;
		public int programId;
		public String programName;
	}
*/
}
