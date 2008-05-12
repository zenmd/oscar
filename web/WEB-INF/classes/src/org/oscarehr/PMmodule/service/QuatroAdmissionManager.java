package org.oscarehr.PMmodule.service;

import org.oscarehr.PMmodule.dao.QuatroAdmissionDao;
import org.oscarehr.PMmodule.model.QuatroAdmission;

public class QuatroAdmissionManager {
	private QuatroAdmissionDao admissionDao;

    public void saveAdmission(QuatroAdmission admission) {
       admissionDao.saveAdmission(admission);	
    }

	public void setAdmissionDao(QuatroAdmissionDao admissionDao) {
		this.admissionDao = admissionDao;
	}
	
}
