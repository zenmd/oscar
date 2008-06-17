package org.oscarehr.PMmodule.web.formbean;

import org.apache.struts.validator.ValidatorForm;
import org.oscarehr.PMmodule.model.Admission;
import org.oscarehr.PMmodule.model.Program;

public class QuatroClientDischargeForm extends ValidatorForm{
    private Admission admission = new Admission();

	public Admission getAdmission() {
		return admission;
	}

	public void setAdmission(Admission admission) {
		this.admission = admission;
	}
}
