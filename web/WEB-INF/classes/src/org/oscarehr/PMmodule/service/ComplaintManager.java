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
import org.oscarehr.PMmodule.dao.ClientReferralDAO;
import org.oscarehr.PMmodule.dao.JointAdmissionDAO;
import org.oscarehr.PMmodule.exception.AlreadyAdmittedException;
import org.oscarehr.PMmodule.exception.AlreadyQueuedException;
import org.oscarehr.PMmodule.exception.ServiceRestrictionException;
import org.oscarehr.PMmodule.model.Admission;
import org.oscarehr.PMmodule.model.ClientReferral;
import org.oscarehr.PMmodule.model.Demographic;
import org.oscarehr.PMmodule.model.DemographicExt;
import org.oscarehr.PMmodule.model.JointAdmission;
import org.oscarehr.PMmodule.model.ProgramClientRestriction;
import org.oscarehr.PMmodule.model.ProgramQueue;
import org.oscarehr.PMmodule.web.formbean.ClientSearchFormBean;
import org.springframework.beans.factory.annotation.Required;

import com.quatro.dao.ComplaintDao;
import com.quatro.model.Complaint;

public class ComplaintManager {

	private static Log log = LogFactory.getLog(ComplaintManager.class);

	private ClientDao dao;

	private ClientReferralDAO referralDAO;

	private JointAdmissionDAO jointAdmissionDAO;

	private ProgramQueueManager queueManager;

	private AdmissionManager admissionManager;

	private ClientRestrictionManager clientRestrictionManager;

	private ComplaintDao complaintDao;

	
	public List getComplaintsByClientId(Integer clientId) {
		return complaintDao.findByClientId(clientId);
	}

	public Complaint getComplaint(Integer id) {

		return complaintDao.findById(id);
	}
	public void save(Complaint complaint) {
		complaintDao.save(complaint);
	}
	
	public List getSources() {
		return complaintDao.getSources();
	}

	public List getMethods() {
		return complaintDao.getMethods();
	}

	public List getOutcomes() {
		return complaintDao.getOutcomes();
	}

	public List getSections() {
		return complaintDao.getSections();
	}

	public List getSubsections() {
		return complaintDao.getSubsections();
	}

	public void setComplaintDao(ComplaintDao complaintDao) {
		this.complaintDao = complaintDao;
	}

}
