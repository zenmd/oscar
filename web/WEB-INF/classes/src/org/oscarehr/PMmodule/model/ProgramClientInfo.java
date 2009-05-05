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

public class ProgramClientInfo {
	private Integer admissionId;
	private String firstName;
	private String lastName;
	private String formattedName;
	private String admissionDate;
	private String admissionNote;
	private String room;
	private String bed;
	private String isDischargeable;
	private String isLatepassHolder;
	private String clientId;
	private boolean isHead;
	
	public boolean getIsHead() {
		return isHead;
	}
	public void setIsHead(boolean isHead) {
		this.isHead = isHead;
	}
	public String getClientId() {
		return clientId;
	}
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	public String getIsDischargeable() {
		return isDischargeable;
	}
	public void setIsDischargeable(String isDischargeable) {
		this.isDischargeable = isDischargeable;
	}
	public String getAdmissionDate() {
		return admissionDate;
	}
	public void setAdmissionDate(String admissionDate) {
		this.admissionDate = admissionDate;
	}
	public String getAdmissionNote() {
		return admissionNote;
	}
	public void setAdmissionNote(String admissionNote) {
		this.admissionNote = admissionNote;
	}
	public String getBed() {
		return bed;
	}
	public void setBed(String bed) {
		this.bed = bed;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getFormattedName() {
		formattedName = lastName + ", " + firstName;
		return formattedName;
	}
	public void setFormattedName(String formattedName) {
		this.formattedName = formattedName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getRoom() {
		return room;
	}
	public void setRoom(String room) {
		this.room = room;
	}
	public Integer getAdmissionId() {
		return admissionId;
	}
	public void setAdmissionId(Integer admissionId) {
		this.admissionId = admissionId;
	}
	public String getIsLatepassHolder() {
		return isLatepassHolder;
	}
	public void setIsLatepassHolder(String isLatepassHolder) {
		this.isLatepassHolder = isLatepassHolder;
	}
	

}
