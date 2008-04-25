package com.quatro.model.security;

/**
 * Secprivilege entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class Secprivilege implements java.io.Serializable {

	// Fields

	private Long id;
	private String privilege;
	private String description;

	// Constructors

	/** default constructor */
	public Secprivilege() {
	}

	/** minimal constructor */
	public Secprivilege(Long id, String privilege) {
		this.id = id;
		this.privilege = privilege;
	}

	/** full constructor */
	public Secprivilege(Long id, String privilege, String description) {
		this.id = id;
		this.privilege = privilege;
		this.description = description;
	}

	// Property accessors

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPrivilege() {
		return this.privilege;
	}

	public void setPrivilege(String privilege) {
		this.privilege = privilege;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}