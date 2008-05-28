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

public class ProgramDao extends HibernateDaoSupport {
	private final String PROGRAM_TYPE_Bed = "Bed";
	private final String PROGRAM_TYPE_Service = "Service";
	private final String PROGRAM_TYPE_External = "external";
	private final String PROGRAM_TYPE_Community = "community";

    private static final Log log = LogFactory.getLog(ProgramDao.class);

    private boolean isTypeOf(Integer programId, String programType) {
        boolean result = false;

        if (programId == null || programId <= 0) {
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

    public boolean isBedProgram(Integer programId) {
        return isTypeOf(programId,PROGRAM_TYPE_Bed);
    }

    public boolean isServiceProgram(Integer programId) {
    	return isTypeOf(programId,PROGRAM_TYPE_Service);
    }

    public boolean isCommunityProgram(Integer programId) {
    	return isTypeOf(programId,PROGRAM_TYPE_Community);
    }

    public boolean isExternalProgram(Integer programId) {
    	return isTypeOf(programId,PROGRAM_TYPE_External);
    }

    public Program getProgram(Integer programId) {
        if (programId == null || programId <= 0) {
            return null;
        }

        Program program = (Program) getHibernateTemplate().get(Program.class, programId);

        if (log.isDebugEnabled()) {
            log.debug("getProgram: " + ((program != null) ? String.valueOf(program.getId()) : "null"));
        }

        return program;
    }

    public String getProgramName(Integer programId) {
        String name = null;

        if (programId == null || programId <= 0) {
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

    public List<Program> getAllPrograms() {
        List<Program> rs = getHibernateTemplate().find("FROM Program p WHERE p.type != ? ORDER BY p.name ", "community");

        if (log.isDebugEnabled()) {
            log.debug("getAllPrograms: # of programs: " + rs.size());
        }
        return rs;
    }

    public List<Program> getAllActivePrograms() {
    	return getAllPrograms("active", "", 0);
    }

    public List<Program> getAllActiveBedPrograms() {
    	return getAllPrograms("active",PROGRAM_TYPE_Bed,0);
    }
    
    public List <Program> getAllPrograms(String programStatus, String type, Integer facilityId)
    {
    	Criteria c = getSession().createCriteria(Program.class);
    	if (!("Any".equals(programStatus) || "".equals(programStatus))) {
    		c.add(Restrictions.eq("programStatus", programStatus));
    	}
    	if (!("Any".equalsIgnoreCase(type) || "".equals(type))) {
    		c.add(Restrictions.eq("type", type));
    	}
    	if (facilityId > 0) {
    		c.add(Restrictions.eq("facilityId", facilityId));
    	}
    	return 	c.list();
    }
    public List<Program> getActiveUserDefinedPrograms() {
        List<Program> rs = getHibernateTemplate().find("FROM Program p WHERE p.userDefined = ? and p.programStatus = 'active'", new Boolean[] { true });
        return rs;
    }
    /**
     * @param facilityId is allowed to be null
     * @return a list of community programs in the facility and any programs with no facility associated
     */
    public List<Program> getServiceProgramsByFacilityId(Integer facilityId) {
        return getAllPrograms("", PROGRAM_TYPE_Service, facilityId);
    }
    /**
     * @param facilityId is allowed to be null
     * @return a list of community programs in the facility and any programs with no facility associated
     */
    public List<Program> getProgramsByFacilityId(Integer facilityId) {
        return getAllPrograms("", "", facilityId);
    }

    public List<Program> getCommunityProgramsByFacilityId(Integer facilityId) {
        return getAllPrograms("", PROGRAM_TYPE_Community, facilityId);
    }

    public Program[] getBedPrograms(Integer facilityId) {
    	List list = getAllPrograms("", PROGRAM_TYPE_Bed, facilityId);
        return (Program[]) list.toArray(new Program[list.size()]);
    }
    public Program[] getBedPrograms() {
        List list = getAllPrograms("", PROGRAM_TYPE_Bed, 0);
        return (Program[]) list.toArray(new Program[list.size()]);
    }

    public List<Program> getServicePrograms() {
        return getAllPrograms("", PROGRAM_TYPE_Service, 0);
    }

    public Program[] getExternalPrograms() {
        List list = getAllPrograms("", PROGRAM_TYPE_External, 0);
        return (Program[]) list.toArray(new Program[list.size()]);
    }
    public Program[] getCommunityPrograms() {
        List list = getAllPrograms("", PROGRAM_TYPE_Community, 0);
        return (Program[]) list.toArray(new Program[list.size()]);
    }

    public List<Program> getProgramByGenderType(String genderType) {
        // yeah I know, it's not safe to insert random strings and it's also inefficient, but unless I fix all the hibernate calls I'm following convention of
        // using the hibernate templates and just inserting random strings for now.
        @SuppressWarnings("unchecked")
        List<Program> rs = getHibernateTemplate().find("FROM Program p WHERE p.manOrWoman = '" + genderType + "'");
        return rs;
    }
    public List getProgramByProvider(String providerNo, Integer facilityId)
    {
        Criteria criteria = getSession().createCriteria(Program.class);
        String sql = "'P' || program_id in (select a.code from lst_orgcd a, secuserrole b where a.fullcode like '%' || b.orgcd || '%' and b.provider_no='" + providerNo + "')";

        criteria.add(Restrictions.sqlRestriction(sql));
        criteria.add(Restrictions.eq("facilityId", facilityId));
        return criteria.list();
    }

    public List getProgramIdsByProvider(String providerNo, Integer facilityId){
    	String sql = "select p.program_id, p.name, p.type  from program p where p.facility_id=? and 'P' || p.program_id in (select a.code from lst_orgcd a, secuserrole b where a.fullcode like '%' || b.orgcd || '%' and b.provider_no=?)";
    	Query query = getSession().createSQLQuery(sql);
    	((SQLQuery) query).addScalar("program_id", Hibernate.INTEGER); 
    	((SQLQuery) query).addScalar("name", Hibernate.STRING); 
    	((SQLQuery) query).addScalar("type", Hibernate.STRING); 
    	query.setInteger(0, facilityId);
    	query.setString(1, providerNo);
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
        if (programId == null || programId <= 0) {
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

        criteria.add(Expression.eq("programStatus", "active"));

        if (program.getManOrWoman() != null && program.getManOrWoman().length() > 0) {
            criteria.add(Expression.eq("manOrWoman", program.getManOrWoman()));
        }

        if (program.getFacilityId()!=null && program.getFacilityId().intValue()>0) {
            criteria.add(Expression.eq("facilityId", program.getFacilityId()));
        }

        if (program.getAgeMin()>0) {
            criteria.add(Expression.le("ageMin", program.getAgeMin()));
        }

        if (program.getAgeMax()>=0) {
            criteria.add(Expression.ge("ageMax", program.getAgeMax()));
        }

         criteria.addOrder(Order.asc("name"));

        List results = criteria.list();

        if (log.isDebugEnabled()) {
            log.debug("search: # results: " + results.size());
        }

        return results;
    }

    public List searchByFacility(Program program, Integer facilityId) {
        if (program == null) {
            throw new IllegalArgumentException();
        }
        if (facilityId == null) {
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

        criteria.add(Expression.eq("programStatus", "active"));

        if (program.getManOrWoman() != null && program.getManOrWoman().length() > 0) {
            criteria.add(Expression.eq("manOrWoman", program.getManOrWoman()));
        }

        if (program.isTransgender()) {
            criteria.add(Expression.eq("transgender", true));
        }

        if (program.isFirstNation()) {
            criteria.add(Expression.eq("firstNation", true));
        }

        if (program.isBedProgramAffiliated()) {
            criteria.add(Expression.eq("bedProgramAffiliated", true));
        }

        if (program.isAlcohol()) {
            criteria.add(Expression.eq("alcohol", true));
        }

        if (program.getAbstinenceSupport() != null && program.getAbstinenceSupport().length() > 0) {
            criteria.add(Expression.eq("abstinenceSupport", program.getAbstinenceSupport()));
        }

        if (program.isPhysicalHealth()) {
            criteria.add(Expression.eq("physicalHealth", true));
        }

        if (program.isHousing()) {
            criteria.add(Expression.eq("housing", true));
        }

        if (program.isMentalHealth()) {
            criteria.add(Expression.eq("mentalHealth", true));
        }

        criteria.add(Expression.eq("facilityId", facilityId));
        
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

        @SuppressWarnings("unchecked")
        List<Program> results = getHibernateTemplate().find("from Program p where p.holdingTank = true");

        if (!results.isEmpty()) {
            result = results.get(0);
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

    @SuppressWarnings("unchecked")
    public List<Program> getLinkedServicePrograms(Integer bedProgramId, Integer clientId) {
        List results = this.getHibernateTemplate().find(
                "select p from Admission a,Program p where a.ProgramId = p.id and p.type='Service' and  p.bedProgramLinkId = ? and a.ClientId=?",
                new Object[] { bedProgramId, clientId });
        return results;
    }
    
    public boolean isInSameFacility(Integer programId1, Integer programId2) {
    	if (programId1 == null || programId1 <= 0) {
            throw new IllegalArgumentException();
        }
    	
    	if (programId2 == null || programId2 <= 0) {
            throw new IllegalArgumentException();
        }
    	
    	Program p1 = getProgram(programId1);
    	Program p2 = getProgram(programId2);
    	return(p1.getFacilityId()==p2.getFacilityId());
    }
}