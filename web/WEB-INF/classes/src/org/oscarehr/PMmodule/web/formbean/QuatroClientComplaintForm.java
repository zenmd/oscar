package org.oscarehr.PMmodule.web.formbean;

import org.apache.struts.validator.ValidatorForm;
import org.oscarehr.PMmodule.model.QuatroComplaint;
import org.oscarehr.PMmodule.model.QuatroComplaintOutcome;
import java.util.List;

public class QuatroClientComplaintForm extends ValidatorForm{
	QuatroComplaint complaint;
	QuatroComplaintOutcome outcome;
    List titleList;
    List radioStatuses;
    List radioYNs;
    List outcomeSatisfiedList;
    List sourceComplaintList;
    List methodContactList;

    public QuatroComplaint getComplaint() {
		return complaint;
	}
	public void setComplaint(QuatroComplaint complaint) {
		this.complaint = complaint;
	}
	public List getMethodContactList() {
		return methodContactList;
	}
	public void setMethodContactList(List methodContactList) {
		this.methodContactList = methodContactList;
	}
	public QuatroComplaintOutcome getOutcome() {
		return outcome;
	}
	public void setOutcome(QuatroComplaintOutcome outcome) {
		this.outcome = outcome;
	}
	public List getOutcomeSatisfiedList() {
		return outcomeSatisfiedList;
	}
	public void setOutcomeSatisfiedList(List outcomeSatisfiedList) {
		this.outcomeSatisfiedList = outcomeSatisfiedList;
	}
	public List getRadioStatuses() {
		return radioStatuses;
	}
	public void setRadioStatuses(List radioStatuses) {
		this.radioStatuses = radioStatuses;
	}
	public List getRadioYNs() {
		return radioYNs;
	}
	public void setRadioYNs(List radioYNs) {
		this.radioYNs = radioYNs;
	}
	public List getSourceComplaintList() {
		return sourceComplaintList;
	}
	public void setSourceComplaintList(List sourceComplaintList) {
		this.sourceComplaintList = sourceComplaintList;
	}
	public List getTitleList() {
		return titleList;
	}
	public void setTitleList(List titleList) {
		this.titleList = titleList;
	}
}
