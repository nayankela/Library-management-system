package model;

public class Role {

	private String roleId;
	private String roleType;

	public Role() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Role(String roleId, String roleType) {
		super();
		this.roleId = roleId;
		this.roleType = roleType;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getRoleType() {
		return roleType;
	}

	public void setRoleType(String roleType) {
		this.roleType = roleType;
	}

	@Override
	public String toString() {
		return "Role [roleId=" + roleId + ", roleType=" + roleType + "]";
	}

}
