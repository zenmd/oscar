package org.oscarehr.PMmodule.web.formbean;

import org.apache.struts.validator.ValidatorForm;
import org.caisi.model.Tickler;

public class TicklerForm extends ValidatorForm{
	private Tickler tickler = new Tickler();

	public Tickler getTickler() {
		return tickler;
	}

	public void setTickler(Tickler tickler) {
		this.tickler = tickler;
	}

}
