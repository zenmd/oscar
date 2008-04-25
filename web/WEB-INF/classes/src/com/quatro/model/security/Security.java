package com.quatro.model.security;

import java.util.Date;


public class Security implements java.io.Serializable {

	// Fields

	private Long securityNo;
	private String userName;
	private String password;
	private String providerNo;
	private String pin;
	private Long BRemotelockset;
	private Long BLocallockset;
	private Date dateExpiredate;
	private Long BExpireset;

	// Constructors

	/** default constructor */
	public Security() {
	}

	/** full constructor */
	public Security(String userName, String password, String providerNo,
			String pin, Long BRemotelockset, Long BLocallockset,
			Date dateExpiredate, Long BExpireset) {
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

	public Long getSecurityNo() {
		return this.securityNo;
	}

	public void setSecurityNo(Long securityNo) {
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

	public Long getBRemotelockset() {
		return this.BRemotelockset;
	}

	public void setBRemotelockset(Long BRemotelockset) {
		this.BRemotelockset = BRemotelockset;
	}

	public Long getBLocallockset() {
		return this.BLocallockset;
	}

	public void setBLocallockset(Long BLocallockset) {
		this.BLocallockset = BLocallockset;
	}

	public Date getDateExpiredate() {
		return this.dateExpiredate;
	}

	public void setDateExpiredate(Date dateExpiredate) {
		this.dateExpiredate = dateExpiredate;
	}

	public Long getBExpireset() {
		return this.BExpireset;
	}

	public void setBExpireset(Long BExpireset) {
		this.BExpireset = BExpireset;
	}

}