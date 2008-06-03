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

import java.io.Serializable;

/**
 * This is the object class that relates to the client_referral table. Any customizations belong here.
 */
public class ClientReferral implements Serializable {

    private int hashCode = Integer.MIN_VALUE;// primary key

    private Integer id;// fields
    private Integer clientId;
    private java.util.Date referralDate;
    private String providerNo;
    private Integer facilityId;
    private String notes;
    private String presentProblems;
    private String radioRejectionReason;
    private String completionNotes;
    private Integer programId;
    private String status;
//    private boolean temporaryAdmission;
    private java.util.Date completionDate;
    private String providerLastName;
    private String providerFirstName;   
    private String programName;
    private String programType;

    // constructors
    public ClientReferral() {
        initialize();
    }

    /**
     * Constructor for primary key
     */
    public ClientReferral(Integer _id) {
        this.setId(_id);
        initialize();
    }

    /**
     * Constructor for required fields
     */
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

    /**
     * Return the unique identifier of this class
     * 
     * @hibernate.id generator-class="native" column="referral_id"
     */
    public Integer getId() {
        return id;
    }

    /**
     * Set the unique identifier of this class
     * 
     * @param _id
     *            the new ID
     */
    public void setId(Integer _id) {
        this.id = _id;
        this.hashCode = Integer.MIN_VALUE;
    }

    /**
     * Return the value associated with the column: client_id
     */
    public Integer getClientId() {
        return clientId;
    }

    /**
     * Set the value related to the column: client_id
     * 
     * @param _clientId
     *            the client_id value
     */
    public void setClientId(Integer _clientId) {
        this.clientId = _clientId;
    }

    /**
     * Return the value associated with the column: referral_date
     */
    public java.util.Date getReferralDate() {
        return referralDate;
    }

    /**
     * Set the value related to the column: referral_date
     * 
     * @param _referralDate
     *            the referral_date value
     */
    public void setReferralDate(java.util.Date _referralDate) {
        this.referralDate = _referralDate;
    }

    /**
     * Return the value associated with the column: provider_no
     */
    public String getProviderNo() {
        return providerNo;
    }

    /**
     * Set the value related to the column: provider_no
     * 
     * @param _providerNo
     *            the provider_no value
     */
    public void setProviderNo(String _providerNo) {
        this.providerNo = _providerNo;
    }

    /**
     * Return the value associated with the column: notes
     */
    public String getNotes() {
        return notes;
    }

    /**
     * Set the value related to the column: notes
     * 
     * @param _notes
     *            the notes value
     */
    public void setNotes(String _notes) {
        this.notes = _notes;
    }

    public String getPresentProblems() {
        return presentProblems;
    }

    public void setPresentProblems(String presentProblems) {
        this.presentProblems = presentProblems;
    }

    public String getRadioRejectionReason() {
        return radioRejectionReason;
    }

    public void setRadioRejectionReason(String radioRejectionReason) {
        this.radioRejectionReason = radioRejectionReason;
    }

    /**
     * Return the value associated with the column: completion_notes
     */
    public String getCompletionNotes() {
        return completionNotes;
    }

    /**
     * Set the value related to the column: completion_notes
     * 
     * @param _completionNotes
     *            the completion_notes value
     */
    public void setCompletionNotes(String _completionNotes) {
        this.completionNotes = _completionNotes;
    }

    /**
     * Return the value associated with the column: program_id
     */
    public Integer getProgramId() {
        return programId;
    }

    /**
     * Set the value related to the column: program_id
     * 
     * @param _programId
     *            the program_id value
     */
    public void setProgramId(Integer _programId) {
        this.programId = _programId;
    }

    /**
     * Return the value associated with the column: status
     */
    public String getStatus() {
        return status;
    }

    /**
     * Set the value related to the column: status
     * 
     * @param _status
     *            the status value
     */
    public void setStatus(String _status) {
        this.status = _status;
    }

    /**
     * Return the value associated with the column: temporary_admission_flag
     */
/*    
    public boolean isTemporaryAdmission() {
        return temporaryAdmission;
    }
*/
    /**
     * Set the value related to the column: temporary_admission_flag
     * 
     * @param _temporaryAdmission
     *            the temporary_admission_flag value
     */
/*    
    public void setTemporaryAdmission(boolean _temporaryAdmission) {
        this.temporaryAdmission = _temporaryAdmission;
    }
*/
    /**
     * Return the value associated with the column: completion_date
     */
    public java.util.Date getCompletionDate() {
        return completionDate;
    }

    /**
     * Set the value related to the column: completion_date
     * 
     * @param _completionDate
     *            the completion_date value
     */
    public void setCompletionDate(java.util.Date _completionDate) {
        this.completionDate = _completionDate;
    }

    /**
     * Return the value associated with the column: ProviderLastName
     */
    public String getProviderLastName() {
        return providerLastName;
    }

    /**
     * Set the value related to the column: ProviderLastName
     * 
     * @param _providerLastName
     *            the ProviderLastName value
     */
    public void setProviderLastName(String _providerLastName) {
        this.providerLastName = _providerLastName;
    }

    /**
     * Return the value associated with the column: ProviderFirstName
     */
    public String getProviderFirstName() {
        return providerFirstName;
    }

    /**
     * Set the value related to the column: ProviderFirstName
     * 
     * @param _providerFirstName
     *            the ProviderFirstName value
     */
    public void setProviderFirstName(String _providerFirstName) {
        this.providerFirstName = _providerFirstName;
    }

    /**
     * Return the value associated with the column: ProgramName
     */
    public String getProgramName() {
        return programName;
    }

    /**
     * Set the value related to the column: ProgramName
     * 
     * @param _programName
     *            the ProgramName value
     */
    public void setProgramName(String _programName) {
        this.programName = _programName;
    }

    /**
     * Return the value associated with the column: programType
     */
    public String getProgramType() {
        return programType;
    }

    /**
     * Set the value related to the column: programType
     * 
     * @param _programType
     *            the programType value
     */
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

    public Integer getFacilityId() {
        return facilityId;
    }

    public void setFacilityId(Integer facilityId) {
        this.facilityId = facilityId;
    }

}