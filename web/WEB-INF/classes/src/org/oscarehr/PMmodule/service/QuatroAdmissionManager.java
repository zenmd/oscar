package org.oscarehr.PMmodule.service;

import java.util.Date;
import java.util.List;

import org.oscarehr.PMmodule.dao.QuatroAdmissionDao;
import org.oscarehr.PMmodule.model.Admission;
import org.oscarehr.PMmodule.model.QuatroAdmission;

import com.quatro.common.KeyConstants;

public class QuatroAdmissionManager {
	private QuatroAdmissionDao quatroAdmissionDao;

    public void saveAdmission(QuatroAdmission admission, Integer intakeId, Integer queueId, Integer referralId) {
    	quatroAdmissionDao.saveAdmission(admission, intakeId, queueId, referralId);
    }

    public void dischargeAdmission(String admissionIds) {
    	quatroAdmissionDao.dischargeAdmission(admissionIds);
    }
    
    public void updateAdmission(QuatroAdmission admission) {
    	quatroAdmissionDao.updateAdmission(admission);
    }

    public void setQuatroAdmissionDao(QuatroAdmissionDao quatroAdmissionDao) {
		this.quatroAdmissionDao = quatroAdmissionDao;
	}

	public List getAdmissionList(Integer clientId, Integer facilityId) {
		return quatroAdmissionDao.getAdmissionList(clientId, facilityId);
	}

	public List getAdmissionList(Integer clientId, Integer facilityId, String providerNo) {
		return quatroAdmissionDao.getAdmissionList(clientId, facilityId, providerNo);
	}

    public QuatroAdmission getAdmission(Integer intakeId){
		return quatroAdmissionDao.getAdmission(intakeId);
    }

    public List<QuatroAdmission> getCurrentAdmissionsByFacility(Integer demographicNo, Integer facilityId) {
		return quatroAdmissionDao.getCurrentAdmissionsByFacility(demographicNo, facilityId);
    }

}
