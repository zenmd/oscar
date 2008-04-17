package org.oscarehr.PMmodule.service;

import java.util.List;

import org.oscarehr.PMmodule.dao.FacilityDAO;
import org.oscarehr.PMmodule.model.Facility;
import org.springframework.beans.factory.annotation.Required;
import com.quatro.dao.LookupDao;

/**
 * Manager for facilities
 */
public class FacilityManager {

    private FacilityDAO facilityDAO;
    private LookupDao lookupDao;
    
    public Facility getFacility(Integer facilityId) {
        return facilityDAO.getFacility(facilityId);
    }

    public List<Facility> getFacilities() {
        return facilityDAO.getFacilities();
    }

    public void saveFacility(Facility facility) {
        facilityDAO.saveFacility(facility);
        try {
        	lookupDao.SaveAsOrgCode(facility);
        }
        catch (Exception e)
        {
    		System.out.println(e.getStackTrace());
        }
    }

    public FacilityDAO getFacilityDAO() {
        return facilityDAO;
    }

    @Required
    public void setFacilityDAO(FacilityDAO facilityDAO) {
        this.facilityDAO = facilityDAO;
    }
    @Required
    public void setLookupDao(LookupDao lookupDao) {
        this.lookupDao = lookupDao;
    }
    
}
