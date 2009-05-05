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
package org.oscarehr.PMmodule.web.formbean;

import org.apache.struts.validator.ValidatorForm;
import org.oscarehr.PMmodule.model.Demographic;
//import org.oscarehr.PMmodule.model.QuatroDemographic;
import java.util.List;
//import java.util.ArrayList;
//import org.oscarehr.PMmodule.model.QuatroIntakeFamily;

public class QuatroClientFamilyIntakeForm extends ValidatorForm{
	Demographic familyHead;
    List dependents;
    String intakeId;
    String intakeStatus;
    String dob;
    int dependentsSize;
    List genders;
    List relationships;
    
    public List getDependents() {
    	return dependents;
	}
	public void setDependents(List dependents) {
		this.dependents = dependents;
	}
	public Demographic getFamilyHead() {
		if(familyHead==null) familyHead= new Demographic(); 
		return familyHead;
	}
	public void setFamilyHead(Demographic familyHead) {
		this.familyHead = familyHead;
	}
	public String getIntakeId() {
		return intakeId;
	}
	public void setIntakeId(String intakeId) {
		this.intakeId = intakeId;
	}
	public String getDob() {
		return dob;
	}
	public void setDob(String dob) {
		this.dob = dob;
	}
/*
	public QuatroDemographic getQuatroFamilyHead() {
		if(quatroFamilyHead==null) quatroFamilyHead= new QuatroDemographic(); 
		return quatroFamilyHead;
	}
	public void setQuatroFamilyHead(QuatroDemographic quatroFamilyHead) {
		this.quatroFamilyHead = quatroFamilyHead;
	}
*/	
	public int getDependentsSize() {
	    return dependentsSize;
	}
	public void setDependentsSize(int dependentsSize) {
		this.dependentsSize = dependentsSize;
	}
	public List getGenders() {
		return genders;
	}
	public void setGenders(List genders) {
		this.genders = genders;
	}
	public List getRelationships() {
		return relationships;
	}
	public void setRelationships(List relationships) {
		this.relationships = relationships;
	}
	public String getIntakeStatus() {
		return intakeStatus;
	}
	public void setIntakeStatus(String intakeStatus) {
		this.intakeStatus = intakeStatus;
	}

}
