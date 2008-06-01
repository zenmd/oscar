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

package org.oscarehr.PMmodule.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.util.LabelValueBean;
import org.oscarehr.PMmodule.dao.AdmissionDao;
import org.oscarehr.PMmodule.dao.DefaultRoleAccessDAO;
import org.oscarehr.PMmodule.dao.ProgramAccessDAO;
import org.oscarehr.PMmodule.dao.ProgramClientStatusDAO;
import org.oscarehr.PMmodule.dao.ProgramDao;
import org.oscarehr.PMmodule.dao.ProgramFunctionalUserDAO;
import org.oscarehr.PMmodule.dao.ProgramProviderDAO;
import org.oscarehr.PMmodule.dao.ProgramSignatureDao;
import org.oscarehr.PMmodule.dao.ProgramTeamDAO;
import org.oscarehr.PMmodule.model.AccessType;
import org.oscarehr.PMmodule.model.Agency;
import org.oscarehr.PMmodule.model.DefaultRoleAccess;
import org.oscarehr.PMmodule.model.FunctionalUserType;
import org.oscarehr.PMmodule.model.Program;
import org.oscarehr.PMmodule.model.ProgramAccess;
import org.oscarehr.PMmodule.model.ProgramClientStatus;
import org.oscarehr.PMmodule.model.ProgramFunctionalUser;
import org.oscarehr.PMmodule.model.ProgramProvider;
import org.oscarehr.PMmodule.model.ProgramSignature;
import org.oscarehr.PMmodule.model.ProgramTeam;
import org.oscarehr.PMmodule.web.formbean.StaffForm;

import oscar.OscarProperties;

import com.quatro.dao.LookupDao;
import com.quatro.dao.ORGDao;
import com.quatro.dao.security.SecuserroleDao;

public class ProgramManager {

    private static Log log = LogFactory.getLog(ProgramManager.class);

    private ProgramDao programDao;
    private ProgramProviderDAO programProviderDAO;
    private ProgramFunctionalUserDAO programFunctionalUserDAO;
    private ProgramTeamDAO programTeamDAO;
    private ProgramAccessDAO programAccessDAO;
    private AdmissionDao admissionDao;
    private DefaultRoleAccessDAO defaultRoleAccessDAO;
    private ProgramClientStatusDAO clientStatusDAO;
    private ProgramSignatureDao programSignatureDao;
    private LookupDao lookupDao;
    private SecuserroleDao secuserroleDao;
    private ORGDao orgDao;

    private boolean enabled;

    public boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public ProgramSignatureDao getProgramSignatureDao() {
        return programSignatureDao;
    }

    public void setProgramSignatureDao(ProgramSignatureDao programSignatureDao) {
        this.programSignatureDao = programSignatureDao;
    }

    public void setProgramDao(ProgramDao dao) {
        this.programDao = dao;
    }

    public void setProgramProviderDAO(ProgramProviderDAO dao) {
        this.programProviderDAO = dao;
    }

    public void setProgramFunctionalUserDAO(ProgramFunctionalUserDAO dao) {
        this.programFunctionalUserDAO = dao;
    }

    public void setProgramTeamDAO(ProgramTeamDAO dao) {
        this.programTeamDAO = dao;
    }

    public void setProgramAccessDAO(ProgramAccessDAO dao) {
        this.programAccessDAO = dao;
    }

    public void setAdmissionDao(AdmissionDao dao) {
        this.admissionDao = dao;
    }

    public void setDefaultRoleAccessDAO(DefaultRoleAccessDAO dao) {
        this.defaultRoleAccessDAO = dao;
    }

    public void setProgramClientStatusDAO(ProgramClientStatusDAO dao) {
        this.clientStatusDAO = dao;
    }

    public void setLookupDao(LookupDao dao) {
        this.lookupDao = dao;
    }

    public Program getProgram(String programId) {
        return programDao.getProgram(Integer.valueOf(programId));
    }

    public Program getProgram(Integer programId) {
        return programDao.getProgram(programId);
    }

    public String getProgramName(String programId) {
        return programDao.getProgramName(Integer.valueOf(programId));
    }

    public List getAllPrograms() {
        return programDao.getAllPrograms();
    }

    public List getAllPrograms(String programStatus, String type, Integer facilityId) {
        return programDao.getAllPrograms(programStatus, type, facilityId);
    }

    /**
      * facilityId can be null, it will return all community programs optionally filtering by facility id if filtering is enabled.
     */
    public List getCommunityPrograms(Integer facilityId) {
        if (OscarProperties.getInstance().getBooleanProperty("FILTER_ON_FACILITY", "true")) {
            return programDao.getCommunityProgramsByFacilityId(facilityId);
        }
        else {
            return programDao.getAllPrograms();
        }
    }

    /**
      * facilityId can be null, it will return all programs optionally filtering by facility id if filtering is enabled.
     */
    public List getPrograms(Integer facilityId) {
        if (OscarProperties.getInstance().getBooleanProperty("FILTER_ON_FACILITY", "true")) {
            return programDao.getProgramsByFacilityId(facilityId);
        }
        else {
            return programDao.getAllPrograms();
        }
    }
    public List getProgramsByProvider(Integer facilityId,String providerNo) {
    	return programDao.getProgramByProvider(providerNo, facilityId);
    }
    public List getProgramIdsByProvider(Integer facilityId,String providerNo) {
    	return programDao.getProgramIdsByProvider(providerNo, facilityId);
    }

    public List getPrograms() {
        return programDao.getAllPrograms();
    }

    public Program[] getBedPrograms() {
        return programDao.getBedPrograms();
    }

    public Program[] getBedPrograms(Integer facilityId) {
        return programDao.getBedPrograms(facilityId);
    }

    public List getServicePrograms() {
        return programDao.getServicePrograms();
    }

    public Program[] getExternalPrograms() {
        return programDao.getExternalPrograms();
    }

    public boolean isBedProgram(String programId) {
        return programDao.isBedProgram(Integer.valueOf(programId));
    }

    public boolean isServiceProgram(String programId) {
        return programDao.isServiceProgram(Integer.valueOf(programId));
    }

    public boolean isCommunityProgram(String programId) {
        return programDao.isCommunityProgram(Integer.valueOf(programId));
    }

    public void saveProgram(Program program) {
//        if (program.getHoldingTank()) {
//            programDao.resetHoldingTank();
//        }
//        boolean isNew = (program.getId() == 0);
        programDao.saveProgram(program);
        try {
        	lookupDao.SaveAsOrgCode(program);
        }
        catch (Exception e)
        {
    		System.out.println(e.getStackTrace());
        }
    }

    public void removeProgram(String programId) {
        programDao.removeProgram(Integer.valueOf(programId));
        programSignatureDao.removeProgramSignature(Integer.valueOf(programId));
        orgDao.delete("P"+programId);
    }

    // TODO: Implement this method for real
    public Agency getAgencyByProgram(String programId) {
        return new Agency(new Integer(0), new Integer(1), "HS", "HS", "", true, false);
    }

    public List getProgramProviders(String orgcd) {
        //return programProviderDAO.getProgramProviders(Integer.valueOf(programId));
        return secuserroleDao.findByOrgcd(orgcd);
    }

    public List getProgramProvidersByProvider(String providerNo) {
        return programProviderDAO.getProgramProvidersByProvider(providerNo);
    }

    public ProgramProvider getProgramProvider(String id) {
        return programProviderDAO.getProgramProvider(Integer.valueOf(id));
    }

    public ProgramProvider getProgramProvider(String providerNo, String programId) {
        return programProviderDAO.getProgramProvider(providerNo, Integer.valueOf(programId));
    }

    public void saveProgramProvider(ProgramProvider pp) {
        programProviderDAO.saveProgramProvider(pp);
    }

    public void deleteProgramProvider(String id) {
        programProviderDAO.deleteProgramProvider(Integer.valueOf(id));
    }
    
    public void deleteProgramProvider(List lst) {
        for(int i = 0; i < lst.size(); i++){
        	Integer id = (Integer)lst.get(i);
        	secuserroleDao.deleteById(id);
        }
    }
    
    public List getProgramByProvider(String providerNo, Integer facilityId) {
        return programDao.getProgramByProvider(providerNo, facilityId);
    }
    public void deleteProgramProviderByProgramId(Integer programId) {
        programProviderDAO.deleteProgramProviderByProgramId(programId);
    }

    public List getFunctionalUserTypes() {
        return programFunctionalUserDAO.getFunctionalUserTypes();
    }

    public FunctionalUserType getFunctionalUserType(String id) {
        return programFunctionalUserDAO.getFunctionalUserType(Integer.valueOf(id));
    }

    public void saveFunctionalUserType(FunctionalUserType fut) {
        programFunctionalUserDAO.saveFunctionalUserType(fut);
    }

    public void deleteFunctionalUserType(String id) {
        programFunctionalUserDAO.deleteFunctionalUserType(Integer.valueOf(id));
    }

    public List getFunctionalUsers(String programId) {
        return programFunctionalUserDAO.getFunctionalUsers(Integer.valueOf(programId));
    }

    public ProgramFunctionalUser getFunctionalUser(String id) {
        return programFunctionalUserDAO.getFunctionalUser(Integer.valueOf(id));
    }

    public void saveFunctionalUser(ProgramFunctionalUser pfu) {
        programFunctionalUserDAO.saveFunctionalUser(pfu);
    }

    public void deleteFunctionalUser(String id) {
        programFunctionalUserDAO.deleteFunctionalUser(Integer.valueOf(id));
    }

    public Integer getFunctionalUserByUserType(Integer programId, Integer userTypeId) {
        return programFunctionalUserDAO.getFunctionalUserByUserType(programId, userTypeId);
    }

    public List getProgramTeams(String programId) {
        return programTeamDAO.getProgramTeams(Integer.valueOf(programId));
    }

    public ProgramTeam getProgramTeam(String id) {
        return programTeamDAO.getProgramTeam(Integer.valueOf(id));
    }

    public void saveProgramTeam(ProgramTeam team) {
        programTeamDAO.saveProgramTeam(team);
    }

    public void deleteProgramTeam(String id) {
        programTeamDAO.deleteProgramTeam(Integer.valueOf(id));
    }

    public boolean teamNameExists(Integer programId, String teamName) {
        return programTeamDAO.teamNameExists(programId, teamName);
    }

    public List getProgramAccesses(String programId) {
        return programAccessDAO.getProgramAccesses(Integer.valueOf(programId));
    }

    public ProgramAccess getProgramAccess(String id) {
        return programAccessDAO.getProgramAccess(Integer.valueOf(id));
    }

    public void saveProgramAccess(ProgramAccess pa) {
        programAccessDAO.saveProgramAccess(pa);
    }

    public void deleteProgramAccess(String id) {
        programAccessDAO.deleteProgramAccess(Integer.valueOf(id));
    }

    public List getAccessTypes() {
        return programAccessDAO.getAccessTypes();
    }

    public AccessType getAccessType(Integer id) {
        return programAccessDAO.getAccessType(id);
    }
    
/*
    public List getAllProvidersInTeam(Integer programId, Integer teamId) {
        return this.programProviderDAO.getProgramProvidersInTeam(programId, teamId);
    }
*/    
/*
    public List getAllClientsInTeam(Integer programId, Integer teamId) {
        return admissionDao.getAdmissionsInTeam(programId, teamId);
    }
*/
    public List search(Program criteria) {
        return this.programDao.search(criteria);
    }

    public List searchByFacility(Program criteria, Integer facilityId){
        return this.programDao.searchByFacility(criteria, facilityId);
    }
    
    public List searchStaff(StaffForm staffForm) {
        return this.secuserroleDao.searchByCriteria(staffForm);
    }
    
    public Program getHoldingTankProgram() {
        return this.programDao.getHoldingTankProgram();
    }

    public ProgramAccess getProgramAccess(String programId, String accessTypeId) {
        return this.programAccessDAO.getProgramAccess(Integer.valueOf(programId), Integer.valueOf(accessTypeId));
    }

    public List getProgramDomain(String providerNo) {
        List programDomain = new ArrayList();

        for (Iterator i = programProviderDAO.getProgramDomain(providerNo).iterator(); i.hasNext();) {
            ProgramProvider programProvider = (ProgramProvider) i.next();
            programDomain.add(getProgram(programProvider.getProgramId()));
        }

        return programDomain;
    }

    public List getProgramDomainInFacility(String providerNo, Integer facilityId) {
    	List programs = getProgramDomain(providerNo);
    	List results = new ArrayList();
    	if(facilityId==null) 
    		return null;
    	for(Iterator itr =programs.iterator(); itr.hasNext();) {
    		Program p = (Program)itr.next();
    		if(p.getFacilityId()==facilityId)
    			results.add(p);
    	}
    	return results;
    }
    public Program[] getCommunityPrograms() {
        return programDao.getCommunityPrograms();
    }

    public List getProgramBeans(String providerNo) {
        if (providerNo == null || "".equalsIgnoreCase(providerNo.trim())) return new ArrayList();
        ArrayList pList = new ArrayList();
        Program[] program = programDao.getCommunityPrograms();
        for (int i = 0; i < program.length; i++) {
            pList.add(new LabelValueBean(program[i].getName(), program[i].getId().toString()));
        }
        return pList;
        /*
         * Iterator iter = programProviderDAOT.getProgramProvidersByProvider(new Long(providerNo)).iterator(); ArrayList pList = new ArrayList(); while (iter.hasNext()) { ProgramProvider p = (ProgramProvider) iter.next(); if (p!=null && p.getProgramId() !=
         * null && p.getProgramId().longValue()>0){ //logger.debug("programName="+p.getProgram().getName()+"::"+"programId="+p.getProgram().getId().toString()); Program program = programDao.getProgram(new Integer(p.getProgramId().intValue()));
         * pList.add(new LabelValueBean(program.getName(),program.getId().toString())); } } return pList;
         */
    }

    public List getDefaultRoleAccesses() {
        return defaultRoleAccessDAO.getDefaultRoleAccesses();
    }

    public DefaultRoleAccess getDefaultRoleAccess(String id) {
        return defaultRoleAccessDAO.getDefaultRoleAccess(Integer.valueOf(id));
    }

    public void saveDefaultRoleAccess(DefaultRoleAccess dra) {
        defaultRoleAccessDAO.saveDefaultRoleAccess(dra);
    }

    public void deleteDefaultRoleAccess(String id) {
        defaultRoleAccessDAO.deleteDefaultRoleAccess(Integer.valueOf(id));
    }

    public DefaultRoleAccess findDefaultRoleAccess(Integer roleId, Integer accessTypeId) {
        return defaultRoleAccessDAO.find(roleId,accessTypeId);
    }

    public List getProgramClientStatuses(Integer programId) {
        return clientStatusDAO.getProgramClientStatuses(programId);
    }

    public void saveProgramClientStatus(ProgramClientStatus status) {
        clientStatusDAO.saveProgramClientStatus(status);
    }

    public void deleteProgramClientStatus(String id) {
        clientStatusDAO.deleteProgramClientStatus(id);
    }

    public boolean clientStatusNameExists(Integer programId, String statusName) {
        return clientStatusDAO.clientStatusNameExists(programId, statusName);
    }

    public List getAllClientsInStatus(Integer programId, Integer statusId) {
        return clientStatusDAO.getAllClientsInStatus(programId, statusId);
    }

    public ProgramClientStatus getProgramClientStatus(String statusId) {
        return clientStatusDAO.getProgramClientStatus(statusId);
    }

    public ProgramSignature getProgramFirstSignature(Integer programId) {
        return programSignatureDao.getProgramFirstSignature(programId);
    }

    public List getProgramSignatures(Integer programId) {
        return programSignatureDao.getProgramSignatures(programId);
    }

    public void saveProgramSignature(ProgramSignature programSignature) {
        programSignatureDao.saveProgramSignature(programSignature);
    }

    /**
     * This method returns true if the provider is authorised to see items of this programId based on facility filtering.
     * 
     * @param programId
     *            is allowed to be null.
     */
    public boolean hasAccessBasedOnFacility(Integer currentFacilityId, Integer programId) {
        // if no program restrictions are defined.
    	boolean hasAccess = false;
        if (programId == null) return(true);
        if (currentFacilityId == null && programId == null) return(true);

        // check the providers facilities against the programs facilities
        Program program = getProgram(programId);        
        return(program.getFacilityId().intValue() == currentFacilityId.intValue());
    }

	public void setSecuserroleDao(SecuserroleDao secuserroleDao) {
		this.secuserroleDao = secuserroleDao;
	}

	public void setOrgDao(ORGDao orgDao) {
		this.orgDao = orgDao;
	}
}
