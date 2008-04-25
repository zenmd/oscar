package com.quatro.model.security;


public class Secuserrole implements java.io.Serializable {

	// Fields

	private Long id;
	private String providerNo;
	private String roleName;
	private String orgcd;
	private Long activeyn;

	// Constructors

	/** default constructor */
	public Secuserrole() {
	}

	/** minimal constructor */
	public Secuserrole(String providerNo, String roleName) {
		this.providerNo = providerNo;
		this.roleName = roleName;
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

}