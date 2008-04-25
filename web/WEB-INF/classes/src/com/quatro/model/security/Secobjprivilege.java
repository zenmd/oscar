package com.quatro.model.security;



public class Secobjprivilege implements java.io.Serializable {

	// Fields

	private SecobjprivilegeId id;
	private String roleusergroup;
	private String objectname_desc;
	private String objectname_code;
	private String privilege;
	private String privilege_code;
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

	public String getObjectname_code() {
		return objectname_code;
	}

	public void setObjectname_code(String objectname_code) {
		this.objectname_code = objectname_code;
	}

	public String getObjectname_desc() {
		return objectname_desc;
	}

	public void setObjectname_desc(String objectname_desc) {
		this.objectname_desc = objectname_desc;
	}

	public String getPrivilege_code() {
		return privilege_code;
	}

	public void setPrivilege_code(String privilege_code) {
		this.privilege_code = privilege_code;
	}

	public String getRoleusergroup() {
		return roleusergroup;
	}

	public void setRoleusergroup(String roleusergroup) {
		this.roleusergroup = roleusergroup;
	}

}