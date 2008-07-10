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

import org.oscarehr.PMmodule.model.BedDemographic;
import org.oscarehr.PMmodule.model.BedDemographicHistorical;

public interface BedDemographicManager {

	public boolean demographicExists(Integer bedId);

	public BedDemographic getBedDemographicByBed(Integer bedId);

	public BedDemographic getBedDemographicByAdmissionId(Integer admissionId);

	public BedDemographic getBedDemographicByDemographic(Integer demographicNo);

	public BedDemographicHistorical[] getExpiredReservations();

	public void saveBedDemographic(BedDemographic bedDemographic);

	public void deleteBedDemographic(BedDemographic bedDemographic);
	public void deleteBedDemographic(String cIds);

}