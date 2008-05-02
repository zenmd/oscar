package com.quatro.model.security;


public class Secuserrole implements java.io.Serializable {

	// Fields

	private Long id;
	private String providerNo;
	private String roleName;
	private String orgcd;
	private Long activeyn;
	// added extra 
	private String roleName_desc;
	private String orgcd_desc;
	
	// added more
	private String fullName;
	private String userName;
	
	
	// Constructors

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	/** default constructor */
	public Secuserrole() {
	}

	/** full constructor */
	public Secuserrole(String providerNo, String roleName, String orgcd,
			Long activeyn) {
		this.providerNo = providerNo;
		this.roleName = roleName;
		this.orgcd = orgcd;
		this.activeyn = activeyn;
	}

	// Property accessors

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getProviderNo() {
		return this.providerNo;
	}

	public void setProviderNo(String providerNo) {
		this.providerNo = providerNo;
	}

	public String getRoleName() {
		return this.roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getOrgcd() {
		return this.orgcd;
	}

	public void setOrgcd(String orgcd) {
		this.orgcd = orgcd;
	}

	public Long getActiveyn() {
		return this.activeyn;
	}

	public void setActiveyn(Long activeyn) {
		this.activeyn = activeyn;
	}

	public String getOrgcd_desc() {
		return orgcd_desc;
	}

	public void setOrgcd_desc(String orgcd_desc) {
		this.orgcd_desc = orgcd_desc;
	}

	public String getRoleName_desc() {
		return roleName_desc;
	}

	public void setRoleName_desc(String roleName_desc) {
		this.roleName_desc = roleName_desc;
	}

}