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

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import org.oscarehr.PMmodule.dao.ClientDao;
import org.oscarehr.PMmodule.dao.ConsentDAO;
//import org.oscarehr.PMmodule.model.Agency;
import org.oscarehr.PMmodule.model.Consent;
import org.oscarehr.PMmodule.model.ConsentDetail;
import org.oscarehr.PMmodule.model.ConsentInterview;
import org.oscarehr.PMmodule.model.Demographic;

import com.quatro.common.KeyConstants;

public class ConsentManager {
    private ConsentDAO dao;
    private ClientDao clientDao;

    public void setConsentDao(ConsentDAO dao) {
        this.dao = dao;
    }

    public void setClientDao(ClientDao dao) {
        this.clientDao = dao;
    }

    public Consent getConsentByDemographic(Integer demographicNo) {
        return dao.getConsentByDemographic(demographicNo);
    }
    
    public List getConsentDetailByClient(Integer clientNo,String providerNo, Integer shelterId){
    	return dao.getConsentsDetailList(clientNo,providerNo,shelterId);
    }
    public ConsentDetail getConsentDetail(Integer rId){
    	return dao.getConsentDetail(rId);
    }
    public void saveConsentDetail(ConsentDetail conObj){
    	dao.saveConsentDetail(conObj);
    }
    public void saveConsent(Consent consent) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd kk:mm");

        clientDao.saveDemographicExt(consent.getDemographicNo(), "consent_st", consent.getStatus());

        clientDao.saveDemographicExt(consent.getDemographicNo(), Demographic.CONSENT_GIVEN_KEY ,consent.getStatus());
        clientDao.saveDemographicExt(consent.getDemographicNo(), Demographic.METHOD_OBTAINED_KEY ,Demographic.MethodObtained_EXPLICIT);

        clientDao.saveDemographicExt(consent.getDemographicNo(), "consent_ex", consent.getExclusionString());
//        clientDao.saveDemographicExt(consent.getDemographicNo(), "consent_ag", String.valueOf(Agency.getLocalAgency().getId()));
        clientDao.saveDemographicExt(consent.getDemographicNo(), "consent_dt", formatter.format(new Date()));

        dao.saveConsent(consent);
    }
    public void withdraw(Integer rId, String providerNo) {
        ConsentDetail x = dao.getConsentDetail(rId);

        if (x != null) {
            x.setProviderNo(providerNo);
            x.setStatus(KeyConstants.STATUS_WITHDRAW);
            x.setEndDate(new GregorianCalendar());
            x.setLastUpdateDate(new GregorianCalendar());
            dao.saveConsentDetail(x);
        }
    }
    public Consent getMostRecentConsent(Integer demographicNo) {
        return dao.getMostRecentConsent(demographicNo);
    }

    public void saveConsentInterview(ConsentInterview consent) {
        dao.saveConsentInterview(consent);
    }

    public List getConsentInterviews() {
        return dao.getConsentInterviews();
    }

    public ConsentInterview getConsentInterview(String id) {
        return dao.getConsentInterview(Integer.valueOf(id));
    }

    public ConsentInterview getConsentInterviewByDemographicNo(String demographicNo) {
        return dao.getConsentInterviewByDemographicNo(new Integer(demographicNo));
    }
}
