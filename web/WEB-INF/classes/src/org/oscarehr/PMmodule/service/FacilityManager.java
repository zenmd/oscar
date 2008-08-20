package org.oscarehr.PMmodule.service;

import java.util.List;

import org.oscarehr.PMmodule.dao.FacilityDAO;
import org.oscarehr.PMmodule.dao.ProgramDao;
import org.oscarehr.PMmodule.model.Facility;
import org.springframework.beans.factory.annotation.Required;
import com.quatro.dao.LookupDao;
import org.oscarehr.PMmodule.model.Program;

/**
 * Manager for facilities
 */
public class FacilityManager {

    private FacilityDAO facilityDAO;
    private ProgramDao programDao;
    private LookupDao lookupDao;
    
    public Facility getFacility(Integer facilityId) {
        return facilityDAO.getFacility(facilityId);
    }

    public List getFacilities() {
        return facilityDAO.getFacilities();
    }
    public List getFacilities(Integer shelterId) {
        return facilityDAO.getFacilities(shelterId);
    }
    

    public void saveFacility(Facility facility) {
        facilityDAO.saveFacility(facility);
        try {
        	lookupDao.SaveAsOrgCode(facility);
        	if(!facility.getActive()){
              List programs = programDao.getProgramsInFacilityForInactive(facility.getId());
              StringBuilder sb = new StringBuilder();
              for(int i=0;i<programs.size();i++){
            	Program program = (Program)programs.get(i);
            	if(i==0) 
            	 sb.append(program.getId());
            	else
               	 sb.append("," + program.getId());
              }
              programDao.inactivePrograms(sb.toString());
        	}
        }
        catch (Exception e)
        {
    		System.out.println(e.getStackTrace());
        }
    }

    public FacilityDAO getFacilityDAO() {
        return facilityDAO;
    }

    //@Required
    public void setFacilityDAO(FacilityDAO facilityDAO) {
        this.facilityDAO = facilityDAO;
    }
    //@Required
    public void setLookupDao(LookupDao lookupDao) {
        this.lookupDao = lookupDao;
    }

	public void setProgramDao(ProgramDao programDao) {
		this.programDao = programDao;
	}    
}
