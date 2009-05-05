/*******************************************************************************
 * Copyright (c) 2008, 2009 Quatro Group Inc. and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU General Public License
 * which accompanies this distribution, and is available at
 * Free Software Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA 02111-1307, USA.
 *
 * Contributors:
 *     <Quatro Group Software Systems inc.>  <OSCAR Team>
 *******************************************************************************/
package com.quatro.model;

import java.io.Serializable;
import java.util.Calendar;

public class ProgramOccupancy implements Serializable {
	private static final long serialVersionUID = 1L;

	private int hashCode = Integer.MIN_VALUE;
	private Integer recordId;
	private Calendar occDate;
	private Integer programId;
	private Integer occupancy;
	private Integer capacityActual;
	private Integer capacityFunding;
	private Integer queue;
	private String lastUpdateUser;
	private Integer admissionOccupancy;
	private Integer programQueueCount;
	
	
	public Integer getCapacityActual() {
		return capacityActual;
	}
	public void setCapacityActual(Integer capacityActual) {
		this.capacityActual = capacityActual;
	}
	public Integer getCapacityFunding() {
		return capacityFunding;
	}
	public void setCapacityFunding(Integer capacityFunding) {
		this.capacityFunding = capacityFunding;
	}
	public String getLastUpdateUser() {
		return lastUpdateUser;
	}
	public void setLastUpdateUser(String lastUpdateUser) {
		this.lastUpdateUser = lastUpdateUser;
	}
	public Calendar getOccDate() {
		return occDate;
	}
	public void setOccDate(Calendar occDate) {
		this.occDate = occDate;
	}
	public Integer getOccupancy() {
		return occupancy;
	}
	public void setOccupancy(Integer occupancy) {
		this.occupancy = occupancy;
	}
	public Integer getProgramId() {
		return programId;
	}
	public void setProgramId(Integer programId) {
		this.programId = programId;
	}
	public Integer getQueue() {
		return queue;
	}
	public void setQueue(Integer queue) {
		this.queue = queue;
	}
	public Integer getRecordId() {
		return recordId;
	}
	public void setRecordId(Integer recordId) {
		this.recordId = recordId;
	}
	public Integer getAdmissionOccupancy() {
		return admissionOccupancy;
	}
	public void setAdmissionOccupancy(Integer admissionOccupancy) {
		this.admissionOccupancy = admissionOccupancy;
	}
	public Integer getProgramQueueCount() {
		return programQueueCount;
	}
	public void setProgramQueueCount(Integer programQueueCount) {
		this.programQueueCount = programQueueCount;
	}

}
