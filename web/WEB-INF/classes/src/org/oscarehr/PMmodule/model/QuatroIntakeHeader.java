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
package org.oscarehr.PMmodule.model;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class QuatroIntakeHeader implements Serializable {
	private static final long serialVersionUID = 1L;
    private int hashCode = Integer.MIN_VALUE;
	private static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy/MM/dd kk:mm");

    private Integer id;// fields
    private Integer clientId;
    private String staffId;
    private String staffName;
    private Calendar createdOn;// many to one    
    private Calendar endDate;    
    private String intakeStatus;
    private Integer programId;
    private String programType;
    private String programName;
    private boolean nerverExpiry;
    private String sdmtBenUnitStatus; 
    private String sdmtOffice; 
    private String sdmtLastBenMonth;

    public boolean equals(Object obj) {
        if (null == obj)
            return false;
        if (!(obj instanceof QuatroIntakeHeader))
            return false;
        else {
        	QuatroIntakeHeader intake = (QuatroIntakeHeader) obj;
            if (null == this.getId() || null == intake.getId())
                return false;
            else
                return (this.getId().equals(intake.getId()));
        }
    }

    public int hashCode() {
        if (Integer.MIN_VALUE == this.hashCode) {
            if (null == this.getId())
                return super.hashCode();
            else {
                String hashStr = this.getClass().getName() + ":" + this.getId().hashCode();
                this.hashCode = hashStr.hashCode();
            }
        }
        return this.hashCode;
    }

	public Integer getClientId() {
		return clientId;
	}

	public void setClientId(Integer clientId) {
		this.clientId = clientId;
	}

	public Calendar getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Calendar createdOn) {
		this.createdOn = createdOn;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getIntakeStatus() {
		return intakeStatus;
	}

	public void setIntakeStatus(String intakeStatus) {
		this.intakeStatus = intakeStatus;
	}

	public Integer getProgramId() {
		return programId;
	}

	public void setProgramId(Integer programId) {
		this.programId = programId;
	}

	public String getStaffId() {
		return staffId;
	}

	public void setStaffId(String staffId) {
		this.staffId = staffId;
	}
	
	public String getCreatedOnStr() {
		return DATE_FORMAT.format(getCreatedOn().getTime());
	}

	public String getProgramType() {
		return programType;
	}

	public void setProgramType(String programType) {
		this.programType = programType;
	}

	public Calendar getEndDate() {
		return endDate;
	}

	public void setEndDate(Calendar endDate) {
		this.endDate = endDate;
	}

	public String getStaffName() {
		return staffName;
	}

	public void setStaffName(String staffName) {
		this.staffName = staffName;
	}

	public String getProgramName() {
		return programName;
	}

	public void setProgramName(String programName) {
		this.programName = programName;
	}

	public boolean isNerverExpiry() {
		return nerverExpiry;
	}

	public void setNerverExpiry(boolean nerverExpiry) {
		this.nerverExpiry = nerverExpiry;
	}

	public String getSdmtBenUnitStatus() {
		return sdmtBenUnitStatus;
	}

	public void setSdmtBenUnitStatus(String sdmtBenUnitStatus) {
		this.sdmtBenUnitStatus = sdmtBenUnitStatus;
	}

	public String getSdmtLastBenMonth() {
		return sdmtLastBenMonth;
	}

	public void setSdmtLastBenMonth(String sdmtLastBenMonth) {
		this.sdmtLastBenMonth = sdmtLastBenMonth;
	}

	public String getSdmtOffice() {
		return sdmtOffice;
	}

	public void setSdmtOffice(String sdmtOffice) {
		this.sdmtOffice = sdmtOffice;
	}

}
