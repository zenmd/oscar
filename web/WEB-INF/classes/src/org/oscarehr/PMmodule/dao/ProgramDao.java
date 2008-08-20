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
 * Toronto, Ontario, Canada 
 */

package org.oscarehr.PMmodule.dao;

import java.util.List;
import java.util.ArrayList;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Hibernate;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.oscarehr.PMmodule.model.Program;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.w3c.tidy.IStack;

import oscar.OscarProperties;

import com.quatro.common.KeyConstants;
import com.quatro.util.Utility;

public class ProgramDao extends HibernateDaoSupport {

    private static final Log log = LogFactory.getLog(ProgramDao.class);

    public boolean isTypeOf(Integer programId, String programType) {
        boolean result = false;

        if (programId == null || programId.intValue() <= 0) {
            throw new IllegalArgumentException();
        }

        String queryStr = "FROM Program p WHERE p.id = ? AND p.type =?";
        Object [] params = new Object [2];
        params[0] = programId;
        params[1] = programType;

        List rs = getHibernateTemplate().find(queryStr, params);

        if (!rs.isEmpty()) {
            result = true;
        }

        if (log.isDebugEnabled()) {
            log.debug("is" + programType + "Program: id=" + programId + " : " + result);
        }

        return result;
    }


    public Program getProgram(Integer programId) {
        if (programId == null || programId.intValue() <= 0) {
            return null;
        }

        Program program = (Program) getHibernateTemplate().get(Program.class, programId);

        if (log.isDebugEnabled()) {
            log.debug("getProgram: " + ((program != null) ? String.valueOf(program.getId()) : "null"));
        }
        if(program.getCapacity_actual() == null) program.setCapacity_actual(new Integer(0));
        return program;
    }

    public String getProgramName(Integer programId) {
        String name = null;

        if (programId == null || programId.intValue() <= 0) {
            return null;
        }

        Program program = (Program) getHibernateTemplate().get(Program.class, programId);

        if (program != null) {
            name = program.getName();
        }

        if (log.isDebugEnabled()) {
            if (program != null) {
                log.debug("getProgramName: " + program.getId());
            }
        }

        return name;
    }
    public List getAllPrograms(String programStatus, String type, Integer facilityId, String providerNo,Integer shelterId)
    {
    	return getAllPrograms(programStatus, type,facilityId,null,providerNo,shelterId);
    }
    public List getAllPrograms(String programStatus, String type, Integer facilityId, Integer clientId, String providerNo,Integer shelterId)
    {
    	Criteria c = getSession().createCriteria(Program.class);
    	if (!(Utility.IsEmpty(programStatus))) {
    		c.add(Restrictions.eq("programStatus", programStatus));
    	}
    	if (null != type && !("Any".equalsIgnoreCase(type) || "".equals(type))) {
    		c.add(Restrictions.eq("type", type));
    	}
    	if (null != facilityId && facilityId.intValue() > 0) {
    		c.add(Restrictions.eq("facilityId", facilityId));
    	}
    	if (null != clientId && clientId.intValue() > 0) {
    		String clientProgram = "program_id in (select itk.program_id from intake itk where itk.client_id=" + clientId.toString() + ")";
    		c.add(Restrictions.sqlRestriction(clientProgram));
    	}
    	c.add(Restrictions.sqlRestriction("program_id in " + Utility.getUserOrgSqlString(providerNo, shelterId)));
    	c.addOrder(Order.asc("name"));
    	return 	c.list();
    }
    
    public List getBedProgramsInFacility(String providerNo,Integer facilityId)
    {
    	Criteria c = getSession().createCriteria(Program.class);
    	c.add(Restrictions.eq("programStatus","1"));
    	c.add(Restrictions.eq("type",Program.BED_TYPE));
    	c.add(Restrictions.eq("facilityId",facilityId));
    	c.add(Restrictions.sqlRestriction("program_id in " + Utility.getUserOrgSqlString(providerNo, null)));
    	return 	c.list();
    }

    public List getProgramsInFacility(String providerNo,Integer facilityId)
    {
    	Criteria c = getSession().createCriteria(Program.class);
    	c.add(Restrictions.eq("programStatus","1"));
    	c.add(Restrictions.eq("facilityId",facilityId));
    	c.add(Restrictions.sqlRestriction("program_id in " + Utility.getUserOrgSqlString(providerNo, null)));
    	return 	c.list();
    }

    public List getActiveUserDefinedPrograms() {
        List rs = getHibernateTemplate().find("FROM Program p WHERE p.userDefined = ? and p.programStatus = '1'", new Boolean[] { Boolean.TRUE });
        return rs;
    }

    public List getProgramByGenderType(String providerNo, Integer shelterId,String genderType) {
        // yeah I know, it's not safe to insert random strings and it's also inefficient, but unless I fix all the hibernate calls I'm following convention of
        // using the hibernate templates and just inserting random strings for now.
        //@SuppressWarnings("unchecked")
        List rs = getHibernateTemplate().find("FROM Program p WHERE p.manOrWoman = '" + genderType + "' and p.id in " + Utility.getUserOrgQueryString(providerNo, shelterId));
        return rs;
    }
    public List getProgramIdsByProvider(String providerNo, Integer shelterId){
    	String sql = "select p.program_id, p.name, p.type  from program p where p.program_status='1' and p.program_Id in " + Utility.getUserOrgSqlString(providerNo, shelterId);
    	Query query = getSession().createSQLQuery(sql);
    	((SQLQuery) query).addScalar("program_id", Hibernate.INTEGER); 
    	((SQLQuery) query).addScalar("name", Hibernate.STRING); 
    	((SQLQuery) query).addScalar("type", Hibernate.STRING); 
    	List lst=query.list();
    	
    	//element in lst is Object[] type
    	return lst;
    }

    public void saveProgram(Program program) {
        if (program == null) {
            throw new IllegalArgumentException();
        }
        
        getHibernateTemplate().saveOrUpdate(program);

        if (log.isDebugEnabled()) {
            log.debug("saveProgram: " + program.getId());
        }
    }

    public void removeProgram(Integer programId) {
        if (programId == null || programId.intValue() <= 0) {
            throw new IllegalArgumentException();
        }

        Object program = getHibernateTemplate().load(Program.class, programId);
        getHibernateTemplate().delete(program);

        if (log.isDebugEnabled()) {
            log.debug("deleteProgram: " + programId);
        }
    }

    public List search(Program program) {
        if (program == null) {
            throw new IllegalArgumentException();
        }
        Criteria criteria = getSession().createCriteria(Program.class);

        if (program.getName() != null && program.getName().length() > 0) {
            String programName = StringEscapeUtils.escapeSql(program.getName());
            String sql = "";
        	sql = "((LEFT(SOUNDEX(name),4) = LEFT(SOUNDEX('" + programName + "'),4)))";
        	criteria.add(Restrictions.or(Restrictions.ilike("name", "%" + programName + "%"), Restrictions.sqlRestriction(sql)));
        }

        if (program.getType() != null && program.getType().length() > 0) {
            criteria.add(Expression.eq("type", program.getType()));
        }

        if (program.getType() == null || program.getType().equals("") || !program.getType().equals("community")) {
            criteria.add(Expression.ne("type", "community"));
        }

        criteria.add(Expression.eq("programStatus", "1"));

        if (program.getManOrWoman() != null && program.getManOrWoman().length() > 0) {
            criteria.add(Expression.eq("manOrWoman", program.getManOrWoman()));
        }

        if (program.getFacilityId()!=null && program.getFacilityId().intValue()>0) {
            criteria.add(Expression.eq("facilityId", program.getFacilityId()));
        }

        if (program.getAgeMin().intValue()>0) {
            criteria.add(Expression.le("ageMin", program.getAgeMin()));
        }

        if (program.getAgeMax().intValue()>=0) {
            criteria.add(Expression.le("ageMax", program.getAgeMax()));
        }
         criteria.addOrder(Order.asc("name"));

        List results = criteria.list();

        if (log.isDebugEnabled()) {
            log.debug("search: # results: " + results.size());
        }

        return results;
    }

    public List searchByShelter(String providerNo, Integer shelterId, Program program) {
        if (program == null) {
            throw new IllegalArgumentException();
        }
        boolean isOracle = OscarProperties.getInstance().getDbType().equals("oracle");
        Criteria criteria = getSession().createCriteria(Program.class);

        if (program.getName() != null && program.getName().length() > 0) {
            String programName = StringEscapeUtils.escapeSql(program.getName());
            String sql = "";
            if (isOracle) {
            	sql = "((LEFT(SOUNDEX(name),4) = LEFT(SOUNDEX('" + programName + "'),4)))";
            	criteria.add(Restrictions.or(Restrictions.ilike("name", "%" + programName + "%"), Restrictions.sqlRestriction(sql)));
            }
            else
            {
            	sql = "((LEFT(SOUNDEX(name),4) = LEFT(SOUNDEX('" + programName + "'),4))" + " " + "OR (name like '" + "%" + programName + "%'))";
                criteria.add(Restrictions.sqlRestriction(sql));
            }
        }

        if (program.getType() != null && program.getType().length() > 0) {
            criteria.add(Expression.eq("type", program.getType()));
        }

        if (program.getType() == null || program.getType().equals("") || !program.getType().equals("community")) {
            criteria.add(Expression.ne("type", "community"));
        }

        criteria.add(Expression.eq("programStatus", "1"));

        if (program.getManOrWoman() != null && program.getManOrWoman().length() > 0) {
            criteria.add(Expression.eq("manOrWoman", program.getManOrWoman()));
        }

        if (program.isTransgender()) {
            criteria.add(Expression.eq("transgender", Boolean.TRUE));
        }

        if (program.isFirstNation()) {
            criteria.add(Expression.eq("firstNation", Boolean.TRUE));
        }

        if (program.isBedProgramAffiliated()) {
            criteria.add(Expression.eq("bedProgramAffiliated", Boolean.TRUE));
        }

        if (program.isAlcohol()) {
            criteria.add(Expression.eq("alcohol", Boolean.TRUE));
        }

        if (program.getAbstinenceSupport() != null && program.getAbstinenceSupport().length() > 0) {
            criteria.add(Expression.eq("abstinenceSupport", program.getAbstinenceSupport()));
        }

        if (program.isPhysicalHealth()) {
            criteria.add(Expression.eq("physicalHealth", Boolean.TRUE));
        }

        if (program.isHousing()) {
            criteria.add(Expression.eq("housing", Boolean.TRUE));
        }

        if (program.isMentalHealth()) {
            criteria.add(Expression.eq("mentalHealth", Boolean.TRUE));
        }
        criteria.add(Restrictions.sqlRestriction("programId in " + Utility.getUserOrgQueryString(providerNo, shelterId)));
        
        criteria.addOrder(Order.asc("name"));

        List results = criteria.list();

        if (log.isDebugEnabled()) {
            log.debug("search: # results: " + results.size());
        }
        return results;
    }
    
    public void resetHoldingTank() {
        Query q = getSession().createQuery("update Program set holdingTank = false");
        q.executeUpdate();

        if (log.isDebugEnabled()) {
            log.debug("resetHoldingTank:");
        }
    }

    public Program getHoldingTankProgram() {
        Program result = null;

        //@SuppressWarnings("unchecked")
        List results = getHibernateTemplate().find("from Program p where p.holdingTank = true");

        if (!results.isEmpty()) {
            result = (Program)results.get(0);
        }

        if (log.isDebugEnabled()) {
            log.debug((result != null) ? "getHoldingTankProgram: program: " + result.getId() : "getHoldingTankProgram: none found");
        }

        return result;
    }

    public boolean programExists(Integer programId) {
        boolean exists = getHibernateTemplate().get(Program.class, programId) != null;
        log.debug("exists: " + exists);

        return exists;
    }

    //@SuppressWarnings("unchecked")
    public List getLinkedServicePrograms(Integer bedProgramId, Integer clientId) {
        List results = this.getHibernateTemplate().find(
                "select p from Admission a,Program p where a.ProgramId = p.id and p.type='Service' and  p.bedProgramLinkId = ? and a.ClientId=?",
                new Object[] { bedProgramId, clientId });
        return results;
    }
    
    public boolean isInSameFacility(Integer programId1, Integer programId2) {
    	if (programId1 == null || programId1.intValue() <= 0) {
            throw new IllegalArgumentException();
        }
    	
    	if (programId2 == null || programId2.intValue() <= 0) {
            throw new IllegalArgumentException();
        }
    	
    	Program p1 = getProgram(programId1);
    	Program p2 = getProgram(programId2);
    	return(p1.getFacilityId()==p2.getFacilityId());
    }
}