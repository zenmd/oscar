package org.oscarehr.PMmodule.web.formbean;

import java.util.Calendar;
import java.util.List;

import org.apache.struts.validator.ValidatorForm;

import com.quatro.model.Complaint;
import com.quatro.model.IncidentValue;

public class IncidentForm extends ValidatorForm{
	
	private IncidentValue incident;
	
    private List othersLst;
	private List natureLst;
	private List clientIssuesLst;
	private List dispositionLst;
	
	private String createdDateStr;
	private String incidentDateStr;
	private String investigationDateStr;
	private String followupDateStr;
	
	private String[] othersArr = {};
	private String[] naturesArr = {};
	private String[] issuesArr = {};
	private String[] dispositionArr = {};
	
	public List getClientIssuesLst() {
		return clientIssuesLst;
	}
	public void setClientIssuesLst(List clientIssuesLst) {
		this.clientIssuesLst = clientIssuesLst;
	}
	public List getDispositionLst() {
		return dispositionLst;
	}
	public void setDispositionLst(List dispositionLst) {
		this.dispositionLst = dispositionLst;
	}
	public IncidentValue getIncident() {
		if(incident == null)
			incident = new IncidentValue();
		return incident;
	}
	public void setIncident(IncidentValue incident) {
		this.incident = incident;
	}
	public List getNatureLst() {
		return natureLst;
	}
	public void setNatureLst(List natureLst) {
		this.natureLst = natureLst;
	}
	public List getOthersLst() {
		return othersLst;
	}
	public void setOthersLst(List othersLst) {
		this.othersLst = othersLst;
	}
	public String getCreatedDateStr() {
		return createdDateStr;
	}
	public void setCreatedDateStr(String createdDateStr) {
		this.createdDateStr = createdDateStr;
	}
	public String getFollowupDateStr() {
		return followupDateStr;
	}
	public void setFollowupDateStr(String followupDateStr) {
		this.followupDateStr = followupDateStr;
	}
	public String getIncidentDateStr() {
		return incidentDateStr;
	}
	public void setIncidentDateStr(String incidentDateStr) {
		this.incidentDateStr = incidentDateStr;
	}
	public String getInvestigationDateStr() {
		return investigationDateStr;
	}
	public void setInvestigationDateStr(String investigationDateStr) {
		this.investigationDateStr = investigationDateStr;
	}
	public String[] getDispositionArr() {
		return dispositionArr;
	}
	public void setDispositionArr(String[] dispositionArr) {
		this.dispositionArr = dispositionArr;
	}
	public String[] getIssuesArr() {
		return issuesArr;
	}
	public void setIssuesArr(String[] issuesArr) {
		this.issuesArr = issuesArr;
	}
	public String[] getNaturesArr() {
		return naturesArr;
	}
	public void setNaturesArr(String[] naturesArr) {
		this.naturesArr = naturesArr;
	}
	public String[] getOthersArr() {
		return othersArr;
	}
	public void setOthersArr(String[] othersArr) {
		this.othersArr = othersArr;
	}

	
}
