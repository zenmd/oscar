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
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;

import org.oscarehr.PMmodule.dao.ProgramClientRestrictionDAO;
import org.oscarehr.PMmodule.exception.ClientAlreadyRestrictedException;
import org.oscarehr.PMmodule.model.ProgramClientRestriction;

/**
 * Manage client restrictions
 */
public class ClientRestrictionManager {

    private ProgramClientRestrictionDAO programClientRestrictionDAO;

    public List getActiveRestrictionsForProgram(Integer programId, Date asOfDate) {
        // check dao for restriction
        Collection pcrs = programClientRestrictionDAO.findForProgram(programId);
        List returnPcrs = new ArrayList();
        if (pcrs != null && !pcrs.isEmpty()) {
//            for (ProgramClientRestriction pcr : pcrs) {
           	Iterator iterator = pcrs.iterator(); 
            while (iterator.hasNext ()) {
            	ProgramClientRestriction pcr = (ProgramClientRestriction)iterator.next(); 
                if (pcr.getStartDate().getTime().getTime() <= asOfDate.getTime() && pcr.getEndDate().getTime().getTime() <= pcr.getEndDate().getTime().getTime()) returnPcrs.add(pcr);
            }
        }
        return returnPcrs;
    }

    public List getDisabledRestrictionsForProgram(Integer programId, Date asOfDate) {
        // check dao for restriction
        Collection pcrs = programClientRestrictionDAO.findDisabledForProgram(programId);
        List returnPcrs = new ArrayList();
        if (pcrs != null && !pcrs.isEmpty()) {
//            for (ProgramClientRestriction pcr : pcrs) {
          	Iterator iterator = pcrs.iterator(); 
             while (iterator.hasNext ()) {
               	ProgramClientRestriction pcr = (ProgramClientRestriction)iterator.next(); 
                if (pcr.getStartDate().getTime().getTime() <= asOfDate.getTime() && pcr.getEndDate().getTime().getTime() <= pcr.getEndDate().getTime().getTime()) returnPcrs.add(pcr);
            }
        }
        return returnPcrs;
    }

    public List getActiveRestrictionsForClient(Integer demographicNo, Date asOfDate) {
        // check dao for restriction
        Collection pcrs = programClientRestrictionDAO.findForClient(demographicNo);
        List returnPcrs = new ArrayList();
        if (pcrs != null && !pcrs.isEmpty()) {
//            for (ProgramClientRestriction pcr : pcrs) {
           	Iterator iterator = pcrs.iterator(); 
            while (iterator.hasNext ()) {
               	ProgramClientRestriction pcr = (ProgramClientRestriction)iterator.next(); 
                if (pcr.getStartDate().getTime().getTime() <= asOfDate.getTime() && pcr.getEndDate().getTime().getTime() <= pcr.getEndDate().getTime().getTime()) returnPcrs.add(pcr);
            }
        }
        return returnPcrs;
    }

    public List getAllRestrictionsForClient(Integer demographicNo,String providerNo,Integer shelterId) {
    	 List results=programClientRestrictionDAO.findAllForClient(demographicNo,providerNo,shelterId);    	 
         return  results;      
    }

    public List getActiveRestrictionsForClient(Integer demographicNo,String providerNo, Integer shelterId, Date asOfDate) {
        // check dao for restriction
        Collection pcrs = programClientRestrictionDAO.findForClient(demographicNo,providerNo, shelterId);
        List returnPcrs = new ArrayList();
        if (pcrs != null && !pcrs.isEmpty()) {
//            for (ProgramClientRestriction pcr : pcrs) {
          	Iterator iterator = pcrs.iterator(); 
            while (iterator.hasNext ()) {
              	ProgramClientRestriction pcr = (ProgramClientRestriction)iterator.next(); 
                if (pcr.getStartDate().getTime().getTime() <= asOfDate.getTime() && pcr.getEndDate().getTime().getTime() <= pcr.getEndDate().getTime().getTime()) returnPcrs.add(pcr);
            }
        }
        return returnPcrs;
    }

    public List getDisabledRestrictionsForClient(Integer demographicNo, Date asOfDate) {
        // check dao for restriction
        Collection pcrs = programClientRestrictionDAO.findDisabledForClient(demographicNo);
        List returnPcrs = new ArrayList();
        if (pcrs != null && !pcrs.isEmpty()) {
//            for (ProgramClientRestriction pcr : pcrs) {
          	Iterator iterator = pcrs.iterator(); 
            while (iterator.hasNext ()) {
              	ProgramClientRestriction pcr = (ProgramClientRestriction)iterator.next(); 
                if (pcr.getStartDate().getTime().getTime() <= asOfDate.getTime() && pcr.getEndDate().getTime().getTime() <= pcr.getEndDate().getTime().getTime()) returnPcrs.add(pcr);
            }
        }
        return returnPcrs;
    }

    public ProgramClientRestriction checkClientRestriction(Integer programId, Integer demographicNo, Date asOfDate) {
        // check dao for restriction
        Collection pcrs = programClientRestrictionDAO.find(programId, demographicNo);
        if (pcrs != null && !pcrs.isEmpty()) {
//            for (ProgramClientRestriction pcr : pcrs) {
          	Iterator iterator = pcrs.iterator(); 
            while (iterator.hasNext ()) {
              	ProgramClientRestriction pcr = (ProgramClientRestriction)iterator.next(); 
                if (pcr.getStartDate().getTime().getTime() <= asOfDate.getTime() && asOfDate.getTime() <= pcr.getEndDate().getTime().getTime()) return pcr;
            }
        }
        return null;
    }

    public void saveClientRestriction(ProgramClientRestriction restriction) throws ClientAlreadyRestrictedException {
        if (restriction.getId() == null) {
            ProgramClientRestriction result = checkClientRestriction(restriction.getProgramId(), restriction.getDemographicNo(), new Date());
            if (result != null) throw new ClientAlreadyRestrictedException("the client has already been service restricted in this program");
        }

        programClientRestrictionDAO.save(restriction);
    }

    public void terminateEarly(Integer programClientRestrictionId, String providerNo) {
        ProgramClientRestriction x = programClientRestrictionDAO.find(programClientRestrictionId);

        if (x != null) {
            x.setEarlyTerminationProvider(providerNo);
            x.setEndDate(new GregorianCalendar());
            programClientRestrictionDAO.save(x);
        }
    }

    public void disableClientRestriction(Integer restrictionId) {
        ProgramClientRestriction pcr = programClientRestrictionDAO.find(restrictionId);
        pcr.setEnabled(false);
        try {
            saveClientRestriction(pcr);
        }
        catch (ClientAlreadyRestrictedException e) {
            // this exception should not happen here, so toss it up as a runtime exception to be caught higher up
            throw new RuntimeException(e);
        }
    }

    public void enableClientRestriction(Integer restrictionId) {
        ProgramClientRestriction pcr = programClientRestrictionDAO.find(restrictionId);
        pcr.setEnabled(true);
        try {
            saveClientRestriction(pcr);
        }
        catch (ClientAlreadyRestrictedException e) {
            // this exception should not happen here, so toss it up as a runtime exception to be caught higher up
            throw new RuntimeException(e);
        }
    }
    
    public ProgramClientRestriction find(Integer restrictionId){
    	return programClientRestrictionDAO.find(restrictionId);
    }
    
    public ProgramClientRestrictionDAO getProgramClientRestrictionDAO() {
        return programClientRestrictionDAO;
    }

    //@Required
    public void setProgramClientRestrictionDAO(ProgramClientRestrictionDAO programClientRestrictionDAO) {
        this.programClientRestrictionDAO = programClientRestrictionDAO;
    }

}
