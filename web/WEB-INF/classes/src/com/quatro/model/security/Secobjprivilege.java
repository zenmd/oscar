package com.quatro.model.security;



public class Secobjprivilege implements java.io.Serializable {

	// Fields

	//private SecobjprivilegeId id;
	private String roleusergroup;
	private String objectname;

	private String privilege;
	private Long priority;
	private String providerNo;
	
	private String privilege_code;
	private String objectname_desc;
	private String objectname_code;
	// Constructors

	/** default constructor */
	public Secobjprivilege() {
	}



	/** full constructor */
	public Secobjprivilege(String roleusergroup, String objectname, String privilege,
			Long priority, String providerNo) {
		this.roleusergroup = roleusergroup;
		this.objectname = objectname;
		this.privilege = privilege;
		this.priority = priority;
		this.providerNo = providerNo;
	}

	// Property accessors


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

	public String getObjectname() {
		return objectname;
	}

	public void setObjectname(String objectname) {
		this.objectname = objectname;
	}

}