package org.oscarehr.PMmodule.model;

import java.io.Serializable;
import java.util.Calendar;

public class QuatroIntakeFamily implements Serializable{
	private static final long serialVersionUID = 1L;
    private int hashCode = Integer.MIN_VALUE;

    private Integer intakeHeadId;
    private Integer intakeId;
    private String memberStatus;
    private String relationship;
    private String relationshipDesc;
    private Calendar joinFamilyDate;
    private Calendar leaveFamilyDate;
    private String dateOfBirth;
    private String monthOfBirth;
    private String yearOfBirth;
    private String firstName;
    private String lastName;
    private String sex;
    private String sexDesc;
    private String alias;    
    private Integer dupliDemographicNo;
    private Integer useDupliDemographicNo;
    private boolean needCheck;
    private String newClientCheck;    
    private Integer clientId;
    private String dob;
    private String select;
    private boolean bServiceRestriction;
    
    private String statusMsg;
    //hidden field in family intake page, to be compatible with single person intake.

    //a flag in each line of family intake page. 
    private String newClientChecked;

    public QuatroIntakeFamily(){
    	this.select="sel";
    }

    public QuatroIntakeFamily(Integer clientId, Integer intakeHeadId, 
    		Integer intakeId, String relationship, Calendar leaveFamilyDate)
    {
       this.clientId = clientId;
       this.intakeHeadId = intakeHeadId;
       this.intakeId = intakeId;
       this.relationship = relationship; 
   	   this.select="sel";
    }
    
    public Integer getIntakeHeadId() {
		return intakeHeadId;
	}
	public void setIntakeHeadId(Integer intakeHeadId) {
		this.intakeHeadId = intakeHeadId;
	}
	public Integer getIntakeId() {
		return intakeId;
	}
	public void setIntakeId(Integer intakeId) {
		this.intakeId = intakeId;
	}
	public Calendar getJoinFamilyDate() {
		return joinFamilyDate;
	}
	public void setJoinFamilyDate(Calendar joinFamilyDate) {
		this.joinFamilyDate = joinFamilyDate;
	}
	public Calendar getLeaveFamilyDate() {
		return leaveFamilyDate;
	}
	public void setLeaveFamilyDate(Calendar leaveFamilyDate) {
		this.leaveFamilyDate = leaveFamilyDate;
	}
	public String getMemberStatus() {
		return memberStatus;
	}
	public void setMemberStatus(String memberStatus) {
		this.memberStatus = memberStatus;
	}
	public String getRelationship() {
		return relationship;
	}
	public void setRelationship(String relationship) {
		this.relationship = relationship;
	}
	
    public boolean equals(Object obj) {
        if (null == obj)
            return false;
        if (!(obj instanceof QuatroIntakeFamily))
            return false;
        else {
        	QuatroIntakeFamily intakeFamily = (QuatroIntakeFamily) obj;
        	
           if (null == this.getIntakeHeadId() || null == intakeFamily.getIntakeHeadId() 
        	   || null == this.getIntakeId() || null == intakeFamily.getIntakeId())
              return false;
           else
              return (this.getIntakeHeadId().equals(intakeFamily.getIntakeHeadId()) &&
            		  this.getIntakeId().equals(intakeFamily.getIntakeId()));
              
        }
        
    }

    public int hashCode() {
        if (Integer.MIN_VALUE == this.hashCode) {
            if (null == this.getIntakeHeadId() || null == this.getIntakeId())
                return super.hashCode();
            else {
                String hashStr = this.getClass().getName() + ":" + this.getIntakeHeadId().hashCode() +
                                 ":" + this.getIntakeId().hashCode();
                this.hashCode = hashStr.hashCode();
            }
        }
        return this.hashCode;
    }
	public String getAlias() {
		return alias;
	}
	public void setAlias(String alias) {
		this.alias = alias;
	}
	public String getDateOfBirth() {
		return dateOfBirth;
	}
	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public int getHashCode() {
		return hashCode;
	}
	public void setHashCode(int hashCode) {
		this.hashCode = hashCode;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getMonthOfBirth() {
		return monthOfBirth;
	}
	public void setMonthOfBirth(String monthOfBirth) {
		this.monthOfBirth = monthOfBirth;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getYearOfBirth() {
		return yearOfBirth;
	}
	public void setYearOfBirth(String yearOfBirth) {
		this.yearOfBirth = yearOfBirth;
	}
	public Integer getClientId() {
		return clientId;
	}
	public void setClientId(Integer clientId) {
		this.clientId = clientId;
	}

	public String getDob() {
		return dob;
	}

	public void setDob(String dob) {
		this.dob = dob;
	}

	public String getSelect() {
		return select;
	}

	public void setSelect(String select) {
		this.select = select;
	}

	public String getSexDesc() {
		return sexDesc;
	}

	public void setSexDesc(String sexDesc) {
		this.sexDesc = sexDesc;
	}

	public Integer getDupliDemographicNo() {
		return dupliDemographicNo;
	}

	public void setDupliDemographicNo(Integer dupliDemographicNo) {
		this.dupliDemographicNo = dupliDemographicNo;
	}

	public String getNewClientCheck() {
		return newClientCheck;
	}

	public void setNewClientCheck(String newClientCheck) {
		this.newClientCheck = newClientCheck;
	}

	public Integer getUseDupliDemographicNo() {
		return useDupliDemographicNo;
	}

	public void setUseDupliDemographicNo(Integer useDupliDemographicNo) {
		this.useDupliDemographicNo = useDupliDemographicNo;
	}

	public boolean isNeedCheck() {
		return needCheck;
	}

	public void setNeedCheck(boolean needCheck) {
		this.needCheck = needCheck;
	}

	public boolean isBServiceRestriction() {
		return bServiceRestriction;
	}

	public void setBServiceRestriction(boolean serviceRestriction) {
		bServiceRestriction = serviceRestriction;
	}

	public String getRelationshipDesc() {
		return relationshipDesc;
	}

	public void setRelationshipDesc(String relationshipDesc) {
		this.relationshipDesc = relationshipDesc;
	}

	public String getStatusMsg() {
		return statusMsg;
	}

	public void setStatusMsg(String statusMsg) {
		this.statusMsg = statusMsg;
	}

	public String getNewClientChecked() {
		return newClientChecked;
	}

	public void setNewClientChecked(String newClientChecked) {
		this.newClientChecked = newClientChecked;
	}
	
}
