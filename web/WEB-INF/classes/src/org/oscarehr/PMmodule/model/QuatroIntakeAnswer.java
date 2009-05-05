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

import org.apache.commons.lang.builder.CompareToBuilder;

public class QuatroIntakeAnswer implements Comparable, Serializable {
	private static final long serialVersionUID = 1L;
    private int hashCode = Integer.MIN_VALUE;

    private Integer id;// fields
    private Integer intakeId;
    private Integer intakeNodeId;
    private String value;
    private QuatroIntakeDB intake2;
    private String lastUpdateUser;
    private Calendar lastUpdateDate;

    public Calendar getLastUpdateDate() {
		return lastUpdateDate;
	}
	public void setLastUpdateDate(Calendar lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

    public QuatroIntakeAnswer(){
    }
    
    public QuatroIntakeAnswer(Integer intakeId, int intakeNodeId, String value, Integer id){
       	this.intakeNodeId=new Integer(intakeNodeId);
       	this.value=value;
       	this.intakeId=intakeId;
       	this.id=id;
    }
    
    public QuatroIntakeAnswer(int intakeNodeId, String value){
       	this.intakeNodeId=new Integer(intakeNodeId);
       	this.value=value;
       	this.id=null;
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
                String hashStr = this.getClass().getName() + ":" + this.getId().hashCode();// +
//                                 ":" + this.getIntakeNodeId().hashCode();
                this.hashCode = hashStr.hashCode();
            }
        }
        return this.hashCode;
    }

//	public int compareTo(QuatroIntakeAnswer answer) {
    public int compareTo(Object obj) {
    	QuatroIntakeAnswer answer = (QuatroIntakeAnswer) obj; 
		CompareToBuilder compareToBuilder = new CompareToBuilder();
		compareToBuilder.append(getId(), answer.getId());
//		compareToBuilder.append(getIntakeNodeId(), answer.getIntakeNodeId());
		
		return compareToBuilder.toComparison();
	}
    
	public String toString() {
		return new StringBuffer("QuatroIntakeAnswer").append("(").append(getId()).append(", ").append(getValue()).append(")").toString();
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
		if(value!=null)
		  this.value = value.trim();
		else
		  this.value = value;	
	}

	public QuatroIntakeDB getIntake2() {
		return intake2;
	}

	public void setIntake2(QuatroIntakeDB intake2) {
		this.intake2 = intake2;
	}
	public String getLastUpdateUser() {
		return lastUpdateUser;
	}
	public void setLastUpdateUser(String lastUpdateUser) {
		this.lastUpdateUser = lastUpdateUser;
	}
    
}
