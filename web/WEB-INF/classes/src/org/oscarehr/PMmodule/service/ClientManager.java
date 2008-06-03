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
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.oscarehr.PMmodule.dao.ClientDao;
import org.oscarehr.PMmodule.dao.ClientHistoryDao;
import org.oscarehr.PMmodule.dao.ClientReferralDAO;
import org.oscarehr.PMmodule.exception.AlreadyAdmittedException;
import org.oscarehr.PMmodule.exception.AlreadyQueuedException;
import org.oscarehr.PMmodule.exception.ServiceRestrictionException;
import org.oscarehr.PMmodule.model.Admission;
import org.oscarehr.PMmodule.model.ClientReferral;
import org.oscarehr.PMmodule.model.Demographic;
import org.oscarehr.PMmodule.model.DemographicExt;
import org.oscarehr.PMmodule.model.ProgramClientRestriction;
import org.oscarehr.PMmodule.model.ProgramQueue;
import org.oscarehr.PMmodule.web.formbean.ClientSearchFormBean;
import org.springframework.beans.factory.annotation.Required;
import com.quatro.common.KeyConstants;

import com.quatro.common.KeyConstants;

public class ClientManager {

    private static Log log = LogFactory.getLog(ClientManager.class);

    private ClientDao dao;
    private ClientHistoryDao clientHistoryDao;
    private ClientReferralDAO referralDAO;
    private ProgramQueueManager queueManager;
   

    private boolean outsideOfDomainEnabled;

    public boolean isOutsideOfDomainEnabled() {
        return outsideOfDomainEnabled;
    }

    public Demographic getClientByDemographicNo(String demographicNo) {
        if (demographicNo == null || demographicNo.length() == 0) {
            return null;
        }
        return dao.getClientByDemographicNo(Integer.valueOf(demographicNo));
    }

    public List getClients() {
        return dao.getClients();
    }

    public List search(ClientSearchFormBean criteria, boolean returnOptinsOnly) {
        return dao.search(criteria, returnOptinsOnly);
    }
    
	public List getIntakeByFacility(Integer demographicNo, Integer facilityId){
		return dao.getIntakeByFacility(demographicNo, facilityId);
	}
    public java.util.Date getMostRecentIntakeADate(String demographicNo) {
        return dao.getMostRecentIntakeADate(Integer.valueOf(demographicNo));
    }
    
    public Integer getRecentProgramId(Integer clientId, String providerNo, Integer facilityId){
    	return dao.getRecentProgramId(clientId, providerNo, facilityId);
    }
    
    public java.util.Date getMostRecentIntakeCDate(String demographicNo) {
        return dao.getMostRecentIntakeCDate(Integer.valueOf(demographicNo));
    }

    public String getMostRecentIntakeAProvider(String demographicNo) {
        return dao.getMostRecentIntakeAProvider(Integer.valueOf(demographicNo));
    }

    public String getMostRecentIntakeCProvider(String demographicNo) {
        return dao.getMostRecentIntakeCProvider(Integer.valueOf(demographicNo));
    }

    public List getReferrals() {
        return referralDAO.getReferrals();
    }

    public List getReferrals(String clientId,String providerNo,Integer facilityId) {
        return referralDAO.getReferrals(Integer.valueOf(clientId),providerNo,facilityId);
    }

    public List getReferralsByFacility(String clientId, Integer facilityId) {
        return referralDAO.getReferralsByFacility(Integer.valueOf(clientId), facilityId);
    }

    public List getActiveReferrals(String clientId, String sourceFacilityId) {
        List results = referralDAO.getActiveReferrals(Integer.valueOf(clientId), Integer.valueOf(sourceFacilityId));

        return results;
    }

    public ClientReferral getClientReferral(String id) {
        return referralDAO.getClientReferral(Integer.valueOf(id));
    }

    /*
     * This should always be a new one. add the queue to the program.
     */
    public void saveClientReferral(ClientReferral referral) {

        referralDAO.saveClientReferral(referral);

        if (referral.getStatus().equalsIgnoreCase(KeyConstants.STATUS_ACTIVE)) {
            ProgramQueue queue = new ProgramQueue();
            queue.setClientId(referral.getClientId());
            queue.setNotes(referral.getNotes());
            queue.setProgramId(referral.getProgramId());
            queue.setProviderNo(Integer.valueOf(referral.getProviderNo()));
            queue.setReferralDate(referral.getReferralDate());
            queue.setReferralId(referral.getId());
            queue.setPresentProblems(referral.getPresentProblems());

            queueManager.saveProgramQueue(queue);
        }
        
        clientHistoryDao.saveClientHistory(referral);
    }

    public List searchReferrals(ClientReferral referral) {
        return referralDAO.search(referral);
    }
    
    

/*    
    public void processReferral(ClientReferral referral) throws AlreadyAdmittedException, AlreadyQueuedException, ServiceRestrictionException {
        processReferral(referral, false);
    }

    public void processReferral(ClientReferral referral, boolean override) throws AlreadyAdmittedException, AlreadyQueuedException, ServiceRestrictionException {
        if (!override) {
            // check if there's a service restriction in place on this individual for this program
            ProgramClientRestriction restrInPlace = clientRestrictionManager.checkClientRestriction(referral.getProgramId().intValue(), referral.getClientId().intValue(), new Date());
            if (restrInPlace != null) {
                throw new ServiceRestrictionException("service restriction in place", restrInPlace);
            }
        }

        Admission currentAdmission = admissionManager.getCurrentAdmission(String.valueOf(referral.getProgramId()), referral.getClientId().intValue());
        if (currentAdmission != null) {
            referral.setStatus(KeyConstants.STATUS_REJECTED);
            referral.setCompletionNotes("Client currently admitted");
            referral.setCompletionDate(new Date());

            saveClientReferral(referral);
            throw new AlreadyAdmittedException();
        }

        ProgramQueue queue = queueManager.getActiveProgramQueue(String.valueOf(referral.getProgramId()), String.valueOf(referral.getClientId()));
        if (queue != null) {
            referral.setStatus(KeyConstants.STATUS_REJECTED);
            referral.setCompletionNotes("Client already in queue");
            referral.setCompletionDate(new Date());

            saveClientReferral(referral);
            throw new AlreadyQueuedException();
        }

        saveClientReferral(referral);
        List<JointAdmission> dependents = getDependents(referral.getClientId());
        for (JointAdmission jadm : dependents) {
            referral.setClientId(jadm.getClientId());
            saveClientReferral(referral);
        }

    }
*/
    
    public void saveClient(Demographic client) {
        dao.saveClient(client);
    }

    public DemographicExt getDemographicExt(String id) {
        return dao.getDemographicExt(Integer.valueOf(id));
    }

    public List getDemographicExtByDemographicNo(int demographicNo) {
        return dao.getDemographicExtByDemographicNo(new Integer(demographicNo));
    }

    public DemographicExt getDemographicExt(int demographicNo, String key) {
        return dao.getDemographicExt(new Integer(demographicNo), key);
    }

    public void updateDemographicExt(DemographicExt de) {
        dao.updateDemographicExt(de);
    }

    public void saveDemographicExt(int demographicNo, String key, String value) {
        dao.saveDemographicExt(new Integer(demographicNo), key, value);
    }

    public void removeDemographicExt(String id) {
        dao.removeDemographicExt(Integer.valueOf(id));
    }

    public void removeDemographicExt(int demographicNo, String key) {
        dao.removeDemographicExt(new Integer(demographicNo), key);
    }

    //@Required
    public void setClientDao(ClientDao dao) {
        this.dao = dao;
    }

    //@Required
    public void setClientReferralDAO(ClientReferralDAO dao) {
        this.referralDAO = dao;
    }

    //@Required
    public void setProgramQueueManager(ProgramQueueManager mgr) {
        this.queueManager = mgr;
    }   

    //@Required
    public void setOutsideOfDomainEnabled(boolean outsideOfDomainEnabled) {
        this.outsideOfDomainEnabled = outsideOfDomainEnabled;
    }

	public void setClientHistoryDao(ClientHistoryDao clientHistoryDao) {
		this.clientHistoryDao = clientHistoryDao;
	}

}
