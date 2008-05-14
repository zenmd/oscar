package com.quatro.model.security;

import java.util.Date;


public class Security implements java.io.Serializable {

	// Fields

	private Integer securityNo;
	private String userName;
	private String password;
	private String providerNo;
	private String pin;
	private Integer BRemotelockset;
	private Integer BLocallockset;
	private Date dateExpiredate;
	private Integer BExpireset;

	// Constructors

	/** default constructor */
	public Security() {
	}

	/** full constructor */
	public Security(String userName, String password, String providerNo,
			String pin, Integer BRemotelockset, Integer BLocallockset,
			Date dateExpiredate, Integer BExpireset) {
		this.userName = userName;
		this.password = password;
		this.providerNo = providerNo;
		this.pin = pin;
		this.BRemotelockset = BRemotelockset;
		this.BLocallockset = BLocallockset;
		this.dateExpiredate = dateExpiredate;
		this.BExpireset = BExpireset;
	}

	// Property accessors

	public Integer getSecurityNo() {
		return this.securityNo;
	}

	public void setSecurityNo(Integer securityNo) {
		this.securityNo = securityNo;
	}

	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getProviderNo() {
		return this.providerNo;
	}

	public void setProviderNo(String providerNo) {
		this.providerNo = providerNo;
	}

	public String getPin() {
		return this.pin;
	}

	public void setPin(String pin) {
		this.pin = pin;
	}

	public Integer getBRemotelockset() {
		return this.BRemotelockset;
	}

	public void setBRemotelockset(Integer BRemotelockset) {
		this.BRemotelockset = BRemotelockset;
	}

	public Integer getBLocallockset() {
		return this.BLocallockset;
	}

	public void setBLocallockset(Integer BLocallockset) {
		this.BLocallockset = BLocallockset;
	}

	public Date getDateExpiredate() {
		return this.dateExpiredate;
	}

	public void setDateExpiredate(Date dateExpiredate) {
		this.dateExpiredate = dateExpiredate;
	}

	public Integer getBExpireset() {
		return this.BExpireset;
	}

	public void setBExpireset(Integer BExpireset) {
		this.BExpireset = BExpireset;
	}

}