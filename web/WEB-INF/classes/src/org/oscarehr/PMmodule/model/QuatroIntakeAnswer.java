package org.oscarehr.PMmodule.model;

import java.io.Serializable;

import org.apache.commons.lang.builder.CompareToBuilder;

public class QuatroIntakeAnswer implements Comparable<QuatroIntakeAnswer>, Serializable {
	private static final long serialVersionUID = 1L;
    private int hashCode = Integer.MIN_VALUE;

    private Integer id;// fields
    private Integer intakeId;
    private Integer intakeNodeId;
    private String value;
    private QuatroIntakeDB intake2;

    public QuatroIntakeAnswer(){
    }
    
    public QuatroIntakeAnswer(int intakeNodeId, String value, int id){
       	this.intakeNodeId=new Integer(intakeNodeId);
       	this.value=value;
       	this.id=id;
    }
    
    public boolean equals(Object obj) {
        if (null == obj)
            return false;
        if (!(obj instanceof QuatroIntakeAnswer))
            return false;
        else {
        	QuatroIntakeAnswer intakeAnswer = (QuatroIntakeAnswer) obj;
            if (null == this.getId() || null == intakeAnswer.getId() ||
           		null == this.getIntakeNodeId() || null == intakeAnswer.getIntakeNodeId())
                return false;
            else
                return (this.getId().equals(intakeAnswer.getId()) && 
                		this.getIntakeNodeId().equals(intakeAnswer.getIntakeNodeId()) );
        }
    }

    public int hashCode() {
        if (Integer.MIN_VALUE == this.hashCode) {
            if (null == this.getId())
                return super.hashCode();
            else {
                String hashStr = this.getClass().getName() + ":" + this.getId().hashCode() +
                                 ":" + this.getIntakeNodeId().hashCode();
                this.hashCode = hashStr.hashCode();
            }
        }
        return this.hashCode;
    }

	public int compareTo(QuatroIntakeAnswer answer) {
		CompareToBuilder compareToBuilder = new CompareToBuilder();
		compareToBuilder.append(getId(), answer.getId());
		compareToBuilder.append(getIntakeNodeId(), answer.getIntakeNodeId());
		
		return compareToBuilder.toComparison();
	}
    
	public String toString() {
		return new StringBuilder("QuatroIntakeAnswer").append("(").append(getId()).append(", ").append(getValue()).append(")").toString();
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getIntakeId() {
		return intakeId;
	}

	public void setIntakeId(Integer intakeId) {
		this.intakeId = intakeId;
	}

	public Integer getIntakeNodeId() {
		return intakeNodeId;
	}

	public void setIntakeNodeId(Integer intakeNodeId) {
		this.intakeNodeId = intakeNodeId;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public QuatroIntakeDB getIntake2() {
		return intake2;
	}

	public void setIntake2(QuatroIntakeDB intake2) {
		this.intake2 = intake2;
	}
    
}
