package com.quatro.model.security;


public class Secrole implements java.io.Serializable {

	// Fields

	private Long roleNo;
	private String roleName;
	private String description;

	// Constructors

	/** default constructor */
	public Secrole() {
	}

	/** full constructor */
	public Secrole(String roleName, String description) {
		this.roleName = roleName;
		this.description = description;
	}

	// Property accessors

	public Long getRoleNo() {
		return this.roleNo;
	}

	public void setRoleNo(Long roleNo) {
		this.roleNo = roleNo;
	}

	public String getRoleName() {
		return this.roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}