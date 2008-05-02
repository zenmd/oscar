package com.quatro.model;

/**
 * LstOrgcd entity.
 * 
 * @author JZhang
 */

public class LstOrgcd implements java.io.Serializable {

	// Fields

	private String code;
	private String description;
	private Long activeyn;
	private Long orderbyindex;
	private String codetree;
	private String fullcode;

	// Constructors

	/** default constructor */
	public LstOrgcd() {
	}

	/** minimal constructor */
	public LstOrgcd(String code) {
		this.code = code;
	}

	/** full constructor */
	public LstOrgcd(String code, String description, Long activeyn,
			Long orderbyindex, String codetree, String fullcode) {
		this.code = code;
		this.description = description;
		this.activeyn = activeyn;
		this.orderbyindex = orderbyindex;
		this.codetree = codetree;
		this.fullcode = fullcode;
	}

	// Property accessors

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Long getActiveyn() {
		return this.activeyn;
	}

	public void setActiveyn(Long activeyn) {
		this.activeyn = activeyn;
	}

	public Long getOrderbyindex() {
		return this.orderbyindex;
	}

	public void setOrderbyindex(Long orderbyindex) {
		this.orderbyindex = orderbyindex;
	}

	public String getCodetree() {
		return this.codetree;
	}

	public void setCodetree(String codetree) {
		this.codetree = codetree;
	}

	public String getFullcode() {
		return this.fullcode;
	}

	public void setFullcode(String fullcode) {
		this.fullcode = fullcode;
	}

}