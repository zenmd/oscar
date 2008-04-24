package com.quatro.model.security;


public class SecobjprivilegeId implements java.io.Serializable {

	// Fields

	private String roleusergroup;
	private String objectname;

	// Constructors

	/** default constructor */
	public SecobjprivilegeId() {
	}

	/** full constructor */
	public SecobjprivilegeId(String roleusergroup, String objectname) {
		this.roleusergroup = roleusergroup;
		this.objectname = objectname;
	}

	// Property accessors

	public String getRoleusergroup() {
		return this.roleusergroup;
	}

	public void setRoleusergroup(String roleusergroup) {
		this.roleusergroup = roleusergroup;
	}

	public String getObjectname() {
		return this.objectname;
	}

	public void setObjectname(String objectname) {
		this.objectname = objectname;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof SecobjprivilegeId))
			return false;
		SecobjprivilegeId castOther = (SecobjprivilegeId) other;

		return ((this.getRoleusergroup() == castOther.getRoleusergroup()) || (this
				.getRoleusergroup() != null
				&& castOther.getRoleusergroup() != null && this
				.getRoleusergroup().equals(castOther.getRoleusergroup())))
				&& ((this.getObjectname() == castOther.getObjectname()) || (this
						.getObjectname() != null
						&& castOther.getObjectname() != null && this
						.getObjectname().equals(castOther.getObjectname())));
	}

	public int hashCode() {
		int result = 17;

		result = 37
				* result
				+ (getRoleusergroup() == null ? 0 : this.getRoleusergroup()
						.hashCode());
		result = 37
				* result
				+ (getObjectname() == null ? 0 : this.getObjectname()
						.hashCode());
		return result;
	}

}