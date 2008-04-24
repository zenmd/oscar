package com.quatro.model.security;



public class Secobjprivilege implements java.io.Serializable {

	// Fields

	private SecobjprivilegeId id;
	private String privilege;
	private Long priority;
	private String providerNo;

	// Constructors

	/** default constructor */
	public Secobjprivilege() {
	}

	/** minimal constructor */
	public Secobjprivilege(SecobjprivilegeId id) {
		this.id = id;
	}

	/** full constructor */
	public Secobjprivilege(SecobjprivilegeId id, String privilege,
			Long priority, String providerNo) {
		this.id = id;
		this.privilege = privilege;
		this.priority = priority;
		this.providerNo = providerNo;
	}

	// Property accessors

	public SecobjprivilegeId getId() {
		return this.id;
	}

	public void setId(SecobjprivilegeId id) {
		this.id = id;
	}

	public String getPrivilege() {
		return this.privilege;
	}

	public void setPrivilege(String privilege) {
		this.privilege = privilege;
	}

	public Long getPriority() {
		return this.priority;
	}

	public void setPriority(Long priority) {
		this.priority = priority;
	}

	public String getProviderNo() {
		return this.providerNo;
	}

	public void setProviderNo(String providerNo) {
		this.providerNo = providerNo;
	}

}