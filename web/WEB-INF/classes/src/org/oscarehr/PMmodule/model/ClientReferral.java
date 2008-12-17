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
package org.oscarehr.PMmodule.model;

import java.awt.GradientPaint;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

import oscar.MyDateFormat;
/**
 * This is the object class that relates to the client_referral table. Any customizations belong here.
 */
public class ClientReferral implements Serializable {

    private int hashCode = Integer.MIN_VALUE;

    private Integer id;
    private Integer clientId;
    private java.util.Calendar referralDate;
    private String providerNo;
//    private Integer facilityId;
    private String notes;
    private String presentProblems;
    private String rejectionReason;
    private String rejectionReasonDesc;
    private String completionNotes;
    private Integer programId;
    private String status;
    private java.util.Calendar completionDate;
    private String providerLastName;
    private String providerFirstName;   
    private String programName;
    private String programType;
	private String autoManual;
	private Integer fromProgramId;
	private Integer fromIntakeId;    

	public Integer getFromProgramId() {
		return fromProgramId;
	}

	public void setFromProgramId(Integer fromProgramId) {
		this.fromProgramId = fromProgramId;
	}

	public ClientReferral() {
        initialize();
    }

    public ClientReferral(Integer _id) {
        this.setId(_id);
        initialize();
    }

    public ClientReferral(Integer _id, Integer _clientId, String _providerNo, Integer _programId) {

        this.setId(_id);
        this.setClientId(_clientId);
        this.setProviderNo(_providerNo);
        this.setProgramId(_programId);
        initialize();
    }

    public String getProviderFormattedName() {
        return getProviderLastName() + "," + getProviderFirstName();
    }

    protected void initialize() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer _id) {
        this.id = _id;
        this.hashCode = Integer.MIN_VALUE;
    }

    public Integer getClientId() {
        return clientId;
    }

    public void setClientId(Integer _clientId) {
        this.clientId = _clientId;
    }

    public Calendar getReferralDate() {
        return referralDate;
    }

    public void setReferralDate(Calendar _referralDate) {
        this.referralDate = _referralDate;
    }
    public String getReferralDateTxt() {
        return MyDateFormat.getStandardDateTime(referralDate);
    }

    public void setReferralDateTxt(String _referralDate) {
        this.referralDate = MyDateFormat.getCalendarwithTime(_referralDate);
    }

    public String getProviderNo() {
        return providerNo;
    }

    public void setProviderNo(String _providerNo) {
        this.providerNo = _providerNo;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String _notes) {
        this.notes = _notes;
    }

    public String getPresentProblems() {
        return presentProblems;
    }

    public void setPresentProblems(String presentProblems) {
        this.presentProblems = presentProblems;
    }

    public String getRejectionReason() {
        return rejectionReason;
    }

    public void setRejectionReason(String rejectionReason) {
        this.rejectionReason = rejectionReason;
    }

    public String getCompletionNotes() {
        return completionNotes;
    }

    public void setCompletionNotes(String _completionNotes) {
        this.completionNotes = _completionNotes;
    }

    public Integer getProgramId() {
        return programId;
    }

    public void setProgramId(Integer _programId) {
        this.programId = _programId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String _status) {
        this.status = _status;
    }

    public java.util.Calendar getCompletionDate() {
        return completionDate;
    }

    public void setCompletionDate(java.util.Calendar _completionDate) {
        this.completionDate = _completionDate;
    }
    
    public String getCompletionDateTxt() {
        return MyDateFormat.getStandardDateTime(completionDate);
    }

    public void setCompletionDateTxt(String _completionDate) {
        this.completionDate = MyDateFormat.getCalendarwithTime(_completionDate);
    }

    public String getProviderLastName() {
        return providerLastName;
    }

    public void setProviderLastName(String _providerLastName) {
        this.providerLastName = _providerLastName;
    }

    public String getProviderFirstName() {
        return providerFirstName;
    }

    public void setProviderFirstName(String _providerFirstName) {
        this.providerFirstName = _providerFirstName;
    }

    public String getProgramName() {
        return programName;
    }

    public void setProgramName(String _programName) {
        this.programName = _programName;
    }

    public String getProgramType() {
        return programType;
    }

    public void setProgramType(String _programType) {
        this.programType = _programType;
    }

    public boolean equals(Object obj) {
        if (null == obj) return false;
        if (!(obj instanceof ClientReferral)) return false;
        else {
            ClientReferral mObj = (ClientReferral) obj;
            if (null == this.getId() || null == mObj.getId()) return false;
            else return(this.getId().equals(mObj.getId()));
        }
    }

    public int hashCode() {
        if (Integer.MIN_VALUE == this.hashCode) {
            if (null == this.getId()) return super.hashCode();
            else {
                String hashStr = this.getClass().getName() + ":" + this.getId().hashCode();
                this.hashCode = hashStr.hashCode();
            }
        }
        return this.hashCode;
    }

    public String toString() {
        return super.toString();
    }
/*
    public Integer getFacilityId() {
        return facilityId;
    }

    public void setFacilityId(Integer shelterId) {
        this.facilityId = facilityId;
    }
*/
    public Integer getDaysCreated(){
		Date referralDate = getReferralDate().getTime();
		Date currentDate = new Date();
			
		long referralDiff = currentDate.getTime() - referralDate.getTime();
		referralDiff = referralDiff / 1000; // seconds
		referralDiff = referralDiff / 60; // minutes
		referralDiff = referralDiff / 60; // hours
		referralDiff = referralDiff / 24; // days

		return new Integer((int)referralDiff);
    }
   
    

    public String getAutoManual() {
		return autoManual;
	}

	public void setAutoManual(String autoManual) {
		this.autoManual = autoManual;
	}

	public Integer getFromIntakeId() {
		return fromIntakeId;
	}

	public void setFromIntakeId(Integer fromIntakeId) {
		this.fromIntakeId = fromIntakeId;
	}

	public String getRejectionReasonDesc() {
		return rejectionReasonDesc;
	}

	public void setRejectionReasonDesc(String rejectionReasonDesc) {
		this.rejectionReasonDesc = rejectionReasonDesc;
	}

}