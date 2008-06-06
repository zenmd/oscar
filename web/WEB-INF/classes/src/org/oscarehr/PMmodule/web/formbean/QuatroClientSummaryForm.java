package org.oscarehr.PMmodule.web.formbean;

import org.apache.struts.validator.ValidatorForm;
import org.oscarehr.PMmodule.model.Admission;
import org.oscarehr.PMmodule.model.BedDemographic;
import org.oscarehr.PMmodule.model.Demographic;
import org.oscarehr.PMmodule.model.Program;
import org.oscarehr.PMmodule.model.ClientReferral;
import org.oscarehr.PMmodule.model.Bed;
import org.oscarehr.PMmodule.model.BedDemographicStatus;
import org.oscarehr.PMmodule.model.Provider;            


public class QuatroClientSummaryForm extends ValidatorForm{
	private Admission admission;
    private BedDemographic bedDemographic;
    private Demographic client;
    private Program program;
    private ClientReferral referral;
    private Bed[] unreservedBeds;
    private BedDemographicStatus[] bedDemographicStatuses;
    private Provider provider;            
    private String programWithIntakeId;

    public Admission getAdmission() {
		return admission;
	}
	public void setAdmission(Admission admission) {
		this.admission = admission;
	}
	public BedDemographic getBedDemographic() {
		return bedDemographic;
	}
	public void setBedDemographic(BedDemographic bedDemographic) {
		this.bedDemographic = bedDemographic;
	}
	public BedDemographicStatus[] getBedDemographicStatuses() {
		return bedDemographicStatuses;
	}
	public void setBedDemographicStatuses(
			BedDemographicStatus[] bedDemographicStatuses) {
		this.bedDemographicStatuses = bedDemographicStatuses;
	}
	public Demographic getClient() {
		return client;
	}
	public void setClient(Demographic client) {
		this.client = client;
	}
	public Program getProgram() {
		return program;
	}
	public void setProgram(Program program) {
		this.program = program;
	}
	public String getProgramWithIntakeId() {
		return programWithIntakeId;
	}
	public void setProgramWithIntakeId(String programWithIntakeId) {
		this.programWithIntakeId = programWithIntakeId;
	}
	public Provider getProvider() {
		return provider;
	}
	public void setProvider(Provider provider) {
		this.provider = provider;
	}
	public ClientReferral getReferral() {
		return referral;
	}
	public void setReferral(ClientReferral referral) {
		this.referral = referral;
	}
	public Bed[] getUnreservedBeds() {
		return unreservedBeds;
	}
	public void setUnreservedBeds(Bed[] unreservedBeds) {
		this.unreservedBeds = unreservedBeds;
	}

}
