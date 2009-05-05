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

import java.util.List;

import net.sf.navigator.menu.MenuRepository;

import org.apache.struts.validator.ValidatorForm;

import com.quatro.model.Complaint;

public class QuatroClientComplaintForm extends ValidatorForm{
	
	Complaint complaint;
	
    List sources;
	List methods;
	List outcomes;
	List sections;
	List subsections;
	
	String isStandards;
	//String OutstandingChk;
	List programs;
	
	public List getPrograms() {
		return programs;
	}
	public void setPrograms(List programs) {
		this.programs = programs;
	}
	public Complaint getComplaint() {
		if(complaint == null)
			complaint = new Complaint();
			
		return complaint;
	}
	public void setComplaint(Complaint complaint) {
		this.complaint = complaint;
	}
	public List getMethods() {
		return methods;
	}
	public void setMethods(List methods) {
		this.methods = methods;
	}
	public List getOutcomes() {
		return outcomes;
	}
	public void setOutcomes(List outcomes) {
		this.outcomes = outcomes;
	}
	public List getSections() {
		return sections;
	}
	public void setSections(List sections) {
		this.sections = sections;
	}
	public List getSources() {
		return sources;
	}
	public void setSources(List sources) {
		this.sources = sources;
	}
	public List getSubsections() {
		return subsections;
	}
	public void setSubsections(List subsections) {
		this.subsections = subsections;
	}
	public String getIsStandards() {
		return isStandards;
	}
	public void setIsStandards(String isStandards) {
		this.isStandards = isStandards;
	}
	
}
