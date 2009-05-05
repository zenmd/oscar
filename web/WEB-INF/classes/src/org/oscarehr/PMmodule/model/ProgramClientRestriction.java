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
import java.util.Calendar;
import java.util.Date;

import com.quatro.common.KeyConstants;

import oscar.MyDateFormat;

/**
 * Service restriction
 */
public class ProgramClientRestriction implements Serializable {

	private static final long serialVersionUID = 1L;
    private int hashCode = Integer.MIN_VALUE;// primary key
    private Integer id;
    private Integer programId;
    private String programDesc;
    
    private Integer demographicNo;
    private String providerNo;
    private String providerFirstName;
    private String providerLastName;
    private String commentId;
    private String comments;
    private Calendar startDate;
    private Calendar endDate;
    private boolean enabled;
    private String earlyTerminationProvider;
    
    private Program program;
    private Demographic client;
    private Provider provider;
    private String startDateStr;
    private String notes;
    private Calendar lastUpdateDate;   
    
    public String getStatus(){
    	String status=KeyConstants.STATUS_ACTIVE;
    	if (getEarlyTerminationProvider() != null) {			
			status = KeyConstants.STATUS_TERMEARLY ;
		} else if (getEndDate().getTime().getTime() < System.currentTimeMillis()) {
			status = KeyConstants.STATUS_COMPLETED;
		} else if (getStartDate().getTime().getTime() <= System	.currentTimeMillis()
				&& getEndDate().getTime().getTime() >= System.currentTimeMillis()) {
			status = KeyConstants.STATUS_IN_PROGRESS;			
		}
    	return status;
    }
	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}
    public ProgramClientRestriction() {
    }

    public ProgramClientRestriction(Integer id, Integer programId, Integer demographicNo, String providerNo, String comments, Calendar startDate, Calendar endDate, boolean enabled, Program program, Demographic client) {
        this.id = id;
        this.programId = programId;
        this.demographicNo = demographicNo;
        this.providerNo = providerNo;
        this.comments = comments;
        this.startDate = startDate;
        this.endDate = endDate;
        this.enabled = enabled;
        this.program = program;
        this.client = client;
    }

    public String getProviderNo() {
        return providerNo;
    }

    public long getDaysRemaining() {
        return (this.getEndDate().getTime().getTime() - this.getStartDate().getTime().getTime()) / 1000 / 60 / 60 / 24;
    }

    public void setProviderNo(String providerNo) {
        this.providerNo = providerNo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getProgramId() {
        return programId;
    }

    public void setProgramId(Integer programId) {
        this.programId = programId;
    }

    public Integer getDemographicNo() {
        return demographicNo;
    }

    public void setDemographicNo(Integer demographicNo) {
        this.demographicNo = demographicNo;
    }

    public Calendar getStartDate() {
        return startDate;
    }

    public void setStartDate(Calendar startDate) {
        this.startDate = startDate;
    }
    public void setStartDateStr(String dateStr){
    	this.startDateStr= dateStr;
    }
    public String getStartDateDisplay(){
    	if(startDate==null) return "";
    	return MyDateFormat.getStandardDate(startDate);
    }
    public String getEndDateDisplay(){
    	if(endDate==null) return "";
    	return MyDateFormat.getStandardDate(endDate);
    }
     public String getStartDateStr(){    	
    	return startDateStr;
    }
    public Calendar  getEndDate() {
        return endDate;
    }

    public void setEndDate(Calendar endDate) {
        this.endDate = endDate;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public Program getProgram() {
        return program;
    }

    public void setProgram(Program program) {
        this.program = program;
    }

    public Demographic getClient() {
        return client;
    }

    public void setClient(Demographic client) {
        this.client = client;
    }

    public Provider getProvider() {
        return provider;
    }

    public void setProvider(Provider provider) {
        this.provider = provider;
    }

    public boolean equals(Object obj) {
        if (null == obj) return false;
        if (!(obj instanceof ProgramClientRestriction)) return false;
        else {
        	ProgramClientRestriction mObj = (ProgramClientRestriction) obj;
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

	public String getCommentId() {
		return commentId;
	}

	public void setCommentId(String commentId) {
		this.commentId = commentId;
	}

    public String getEarlyTerminationProvider() {
        return earlyTerminationProvider;
    }

    public void setEarlyTerminationProvider(String earlyTerminationProvider) {
        this.earlyTerminationProvider = earlyTerminationProvider;
    }

	public String getProgramDesc() {
		return programDesc;
	}

	public void setProgramDesc(String programDesc) {
		this.programDesc = programDesc;
	}
	public String getProviderFormattedName() {
	    return getProviderLastName() + "," + getProviderFirstName();
	}
	public String getProviderFirstName() {
		return providerFirstName;
	}

	public void setProviderFirstName(String providerFirstName) {
		this.providerFirstName = providerFirstName;
	}

	public String getProviderLastName() {
		return providerLastName;
	}

	public void setProviderLastName(String providerLastName) {
		this.providerLastName = providerLastName;
	}

	public Calendar getLastUpdateDate() {
		return lastUpdateDate;
	}

	public void setLastUpdateDate(Calendar lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

    

}
