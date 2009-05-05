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
package org.oscarehr.PMmodule.model;

import java.util.Calendar;

public class ClientCurrentProgram {
	private Integer programId;
	private String programName;
	private String programType;
	private Calendar admissionDate;
    private Integer daysInProgram;

    public Calendar getAdmissionDate() {
		return admissionDate;
	}
	public void setAdmissionDate(Calendar admissionDate) {
		this.admissionDate = admissionDate;
	}
	public Integer getDaysInProgram() {
		return daysInProgram;
	}
	public void setDaysInProgram(Integer daysInProgram) {
		this.daysInProgram = daysInProgram;
	}
	public Integer getProgramId() {
		return programId;
	}
	public void setProgramId(Integer programId) {
		this.programId = programId;
	}
	public String getProgramName() {
		return programName;
	}
	public void setProgramName(String programName) {
		this.programName = programName;
	}
	public String getProgramType() {
		return programType;
	}
	public void setProgramType(String programType) {
		this.programType = programType;
	}
}
