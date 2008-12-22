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
 * This is the object class that relates to the program_queue table.
 * Any customizations belong here.
 */
public class ProgramQueue implements Serializable {

    private int hashCode = Integer.MIN_VALUE;// primary key

    private Integer _id;// fields
    private Integer _clientId;
    private java.util.Date _referralDate;
    private Integer _providerNo;
    private String _notes;
    private Integer _programId;
//    private Integer intakeId;
    private Integer _referralId;
    private String _programName;
    private String _providerLastName;
    private String _providerFirstName;
    private String _clientLastName;
    private String _clientFirstName;
    private boolean _clientActive;
    private boolean clientFamilyHead = false;
    private String presentProblems;
	private Integer fromIntakeId;


    // constructors
    public ProgramQueue () {
        initialize();
    }

    /**
     * Constructor for primary key
     */
    public ProgramQueue (Integer _id) {
        this.setId(_id);
        initialize();
    }

    /**
     * Constructor for required fields
     */
    public ProgramQueue (
            Integer _id,
            Integer _clientId,
            Integer _providerNo,
            Integer _programId) {

        this.setId(_id);
        this.setClientId(_clientId);
        this.setProviderNo(_providerNo);
        this.setProgramId(_programId);
        initialize();
    }

    public String getProviderFormattedName() {
        return getProviderLastName() + ", " + getProviderFirstName();
    }

    public String getClientFormattedName() {
        return getClientLastName() + ", " + getClientFirstName();
    }

    protected void initialize () {}

    /**
     * Return the unique identifier of this class
     * @hibernate.id
     *  generator-class="native"
     *  column="queue_id"
     */
    public Integer getId () {
        return _id;
    }

    /**
     * Set the unique identifier of this class
     * @param _id the new ID
     */
    public void setId (Integer _id) {
        this._id = _id;
        this.hashCode = Integer.MIN_VALUE;
    }

    /**
     * Return the value associated with the column: client_id
     */
    public Integer getClientId () {
        return _clientId;
    }

    /**
     * Set the value related to the column: client_id
     * @param _clientId the client_id value
     */
    public void setClientId (Integer _clientId) {
        this._clientId = _clientId;
    }

    /**
     * Return the value associated with the column: referral_date
     */
    public java.util.Date getReferralDate () {
        return _referralDate;
    }

    /**
     * Set the value related to the column: referral_date
     * @param _referralDate the referral_date value
     */
    public void setReferralDate (java.util.Date _referralDate) {
        this._referralDate = _referralDate;
    }

    /**
     * Return the value associated with the column: provider_no
     */
    public Integer getProviderNo () {
        return _providerNo;
    }

    /**
     * Set the value related to the column: provider_no
     * @param _providerNo the provider_no value
     */
    public void setProviderNo (Integer _providerNo) {
        this._providerNo = _providerNo;
    }

    /**
     * Return the value associated with the column: notes
     */
    public String getNotes () {
        return _notes;
    }

    /**
     * Set the value related to the column: notes
     * @param _notes the notes value
     */
    public void setNotes (String _notes) {
        this._notes = _notes;
    }

    /**
     * Return the value associated with the column: program_id
     */
    public Integer getProgramId () {
        return _programId;
    }

    /**
     * Set the value related to the column: program_id
     * @param _programId the program_id value
     */
    public void setProgramId (Integer _programId) {
        this._programId = _programId;
    }

    public Integer getReferralId () {
        return _referralId;
    }

    public void setReferralId (Integer _referralId) {
        this._referralId = _referralId;
    }

    public String getProgramName () {
        return _programName;
    }

    public void setProgramName (String _programName) {
        this._programName = _programName;
    }

    public String getProviderLastName () {
        return _providerLastName;
    }

    public void setProviderLastName (String _providerLastName) {
        this._providerLastName = _providerLastName;
    }

    public String getProviderFirstName () {
        return _providerFirstName;
    }

    public void setProviderFirstName (String _providerFirstName) {
        this._providerFirstName = _providerFirstName;
    }

    public String getClientLastName () {
        return _clientLastName;
    }

    public void setClientLastName (String _clientLastName) {
        this._clientLastName = _clientLastName;
    }

    public String getClientFirstName () {
        return _clientFirstName;
    }

    public void setClientFirstName (String _clientFirstName) {
        this._clientFirstName = _clientFirstName;
    }

    public String getPresentProblems() {
        return presentProblems;
    }

    public void setPresentProblems(String presentProblems) {
        this.presentProblems = presentProblems;
    }

    public boolean equals (Object obj) {
        if (null == obj) return false;
        if (!(obj instanceof ProgramQueue)) return false;
        else {
            ProgramQueue mObj = (ProgramQueue) obj;
            if (null == this.getId() || null == mObj.getId()) return false;
            else return (this.getId().equals(mObj.getId()));
        }
    }

    public int hashCode () {
        if (Integer.MIN_VALUE == this.hashCode) {
            if (null == this.getId()) return super.hashCode();
            else {
                String hashStr = this.getClass().getName() + ":" + this.getId().hashCode();
                this.hashCode = hashStr.hashCode();
            }
        }
        return this.hashCode;
    }

    public String toString () {
        return super.toString();
    }
/*
	public Integer getIntakeId() {
		return intakeId;
	}

	public void setIntakeId(Integer intakeId) {
		this.intakeId = intakeId;
	}
*/
	public Integer getFromIntakeId() {
		return fromIntakeId;
	}

	public void setFromIntakeId(Integer fromIntakeId) {
		this.fromIntakeId = fromIntakeId;
	}

	public boolean isClientActive() {
		return _clientActive;
	}

	public void setClientActive(boolean clientActive) {
		this._clientActive = clientActive;
	}

	public boolean isClientFamilyHead() {
		return clientFamilyHead;
	}

	public void setClientFamilyHead(boolean clientFamilyHead) {
		this.clientFamilyHead = clientFamilyHead;
	}
    
}