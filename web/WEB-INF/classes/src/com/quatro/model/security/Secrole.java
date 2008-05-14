package com.quatro.model.security;


public class Secrole implements java.io.Serializable {

	// Fields

	private Integer roleNo;
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

	public Integer getRoleNo() {
		return this.roleNo;
	}

	public void setRoleNo(Integer roleNo) {
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