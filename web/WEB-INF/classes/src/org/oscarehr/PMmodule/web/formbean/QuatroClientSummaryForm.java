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
import org.oscarehr.PMmodule.model.Admission;
import org.oscarehr.PMmodule.model.Demographic;
import org.oscarehr.PMmodule.model.Program;
import org.oscarehr.PMmodule.model.ClientReferral;
import org.oscarehr.PMmodule.model.Bed;
import org.oscarehr.PMmodule.model.Provider;            


public class QuatroClientSummaryForm extends ValidatorForm{
	private Admission admission;
    private Demographic client;
    private Program program;
    private ClientReferral referral;
    private Bed[] unreservedBeds;
    private Provider provider;            
//    private String programWithIntakeId;

    public Admission getAdmission() {
		return admission;
	}
	public void setAdmission(Admission admission) {
		this.admission = admission;
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
/*	
	public String getProgramWithIntakeId() {
		return programWithIntakeId;
	}
	public void setProgramWithIntakeId(String programWithIntakeId) {
		this.programWithIntakeId = programWithIntakeId;
	}
*/	
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
