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
import org.caisi.model.Tickler;
import java.util.List;
import java.util.ArrayList;
import org.caisi.model.CustomFilter;

public class TicklerForm extends ValidatorForm{
	private Tickler tickler = new Tickler();
	private List ampmLst = new ArrayList();
	private List serviceHourLst = new ArrayList();
	private List serviceMinuteLst = new ArrayList();
	private List priorityLst = new ArrayList();
	private List programLst = new ArrayList();
	private List providerLst = new ArrayList();
	private CustomFilter filter = new CustomFilter();

	public Tickler getTickler() {
		return tickler;
	}

	public void setTickler(Tickler tickler) {
		this.tickler = tickler;
	}

	public List getAmpmLst() {
		return ampmLst;
	}

	public void setAmpmLst(List ampmLst) {
		this.ampmLst = ampmLst;
	}

	public List getServiceHourLst() {
		return serviceHourLst;
	}

	public void setServiceHourLst(List serviceHourLst) {
		this.serviceHourLst = serviceHourLst;
	}

	public List getServiceMinuteLst() {
		return serviceMinuteLst;
	}

	public void setServiceMinuteLst(List serviceMinuteLst) {
		this.serviceMinuteLst = serviceMinuteLst;
	}

	public List getPriorityLst() {
		return priorityLst;
	}

	public void setPriorityLst(List priorityLst) {
		this.priorityLst = priorityLst;
	}

	public List getProgramLst() {
		return programLst;
	}

	public void setProgramLst(List programLst) {
		this.programLst = programLst;
	}

	public List getProviderLst() {
		return providerLst;
	}

	public void setProviderLst(List providerLst) {
		this.providerLst = providerLst;
	}

	public CustomFilter getFilter() {
		return filter;
	}

	public void setFilter(CustomFilter filter) {
		this.filter = filter;
	}

}
