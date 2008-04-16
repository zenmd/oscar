package org.oscarehr.PMmodule.model;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class QuatroIntake implements Serializable {
	private static final long serialVersionUID = 1L;
    private int hashCode = Integer.MIN_VALUE;

    private Integer id;// fields
    private Integer clientId;
    private String staffId;
    private Calendar createdOn;// many to one    
    private java.util.Set<QuatroIntakeAnswer> answers;
    private String intakeStatus;
//    private Integer intakeLocation;
    private Integer programId;

    public boolean equals(Object obj) {
        if (null == obj)
            return false;
        if (!(obj instanceof QuatroIntake))
            return false;
        else {
        	QuatroIntake intake = (QuatroIntake) obj;
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

	public java.util.Set<QuatroIntakeAnswer> getAnswers() {
		return answers;
	}

	public void setAnswers(java.util.Set<QuatroIntakeAnswer> answers) {
		this.answers = answers;
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


}
