package com.quatro.model;

import java.io.Serializable;

public class QuatroIntakeAnswer implements Serializable {
	private static final long serialVersionUID = 1L;
	private int hashCode = Integer.MIN_VALUE;

	private Integer id;
	private Integer intakeId;
	private Integer intakeNodeId;
    private String val;

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
	public String getVal() {
		return val;
	}
	public void setVal(String val) {
		this.val = val;
	}

    public boolean equals(Object obj) {
        if (null == obj)
            return false;
        if (!(obj instanceof QuatroIntakeAnswer))
            return false;
        else {
            QuatroIntakeAnswer intakeAnswer = (QuatroIntakeAnswer) obj;
            if (null == this.getId() || null == intakeAnswer.getId())
                return false;
            else
                return (this.getId().equals(intakeAnswer.getId()));
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
}
