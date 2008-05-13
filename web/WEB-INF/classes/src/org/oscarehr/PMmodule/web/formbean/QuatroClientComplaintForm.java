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
