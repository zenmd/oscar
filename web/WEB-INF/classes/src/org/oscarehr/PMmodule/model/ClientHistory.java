package org.oscarehr.PMmodule.model;

import java.io.Serializable;

public class ClientHistory  implements Serializable {
    private Integer _id;// fields
    private Integer _clientId;
    private String _action;
    private java.util.Calendar _actionDate;
    private String _providerNo;
    private String _notes;
    private Integer _programId;
    private Integer _programId2;
    private java.util.Date _historyDate; //last update date
    private String _providerLastName;
    private String _providerFirstName;
    private String _programName;
    private String _programType;

    public java.util.Calendar getActionDate() {
		return _actionDate;
	}
	public void setActionDate(java.util.Calendar date) {
		_actionDate = date;
	}
	public Integer getClientId() {
		return _clientId;
	}
	public void setClientId(Integer id) {
		_clientId = id;
	}
	public java.util.Date getHistoryDate() {
		return _historyDate;
	}
	public void setHistoryDate(java.util.Date date) {
		_historyDate = date;
	}
	public Integer getId() {
		return _id;
	}
	public void setId(Integer _id) {
		this._id = _id;
	}
	public String getNotes() {
		return _notes;
	}
	public void setNotes(String _notes) {
		this._notes = _notes;
	}
	public Integer getProgramId() {
		return _programId;
	}
	public void setProgramId(Integer id) {
		_programId = id;
	}
	public Integer getProgramId2() {
		return _programId2;
	}
	public void setProgramId2(Integer id2) {
		_programId2 = id2;
	}
	public String getProgramName() {
		return _programName;
	}
	public void setProgramName(String name) {
		_programName = name;
	}
	public String getProgramType() {
		return _programType;
	}
	public void setProgramType(String type) {
		_programType = type;
	}
	public String getProviderFirstName() {
		return _providerFirstName;
	}
	public void setProviderFirstName(String firstName) {
		_providerFirstName = firstName;
	}
	public String getProviderLastName() {
		return _providerLastName;
	}
	public void setProviderLastName(String lastName) {
		_providerLastName = lastName;
	}
	public String getProviderName()
	{
		return _providerLastName + ", " + _providerFirstName;
	}
	public String getProviderNo() {
		return _providerNo;
	}
	public void setProviderNo(String no) {
		_providerNo = no;
	}
	public String getAction() {
		return _action;
	}
	public void setAction(String _action) {
		this._action = _action;
	}
}
