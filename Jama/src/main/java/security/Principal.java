package security;

import java.util.ArrayList;
import java.util.List;

import usersManagement.Permission;
import usersManagement.RolePermission;
import usersManagement.User;

public class Principal {

	private String name;
	private String surname;
	private String email;
	private String serialNumber;
	//TODO cambiare in Stringa
	private RolePermission rolePermission;
	private List<String> permissions = new ArrayList<>();
	private List<String> belongingDepthsCodes;

	public Principal() {
		// TODO: di serialNumber che ci metto?
		serialNumber = "Guest";
		rolePermission = RolePermission.GUEST;
		setPermissions(rolePermission.getPermissions());
	}

	public String getName() {
		return name;
	}

	public String getSurname() {
		return surname;
	}

	public String getEmail() {
		return email;
	}

	public Principal(String name, String surname, String email,
			String serialNumber, RolePermission role, List<String> depthsCodes) {
		super();
		this.name = name;
		this.surname = surname;
		this.email = email;
		this.serialNumber = serialNumber;
		this.rolePermission = role;
		this.belongingDepthsCodes = depthsCodes;
		setPermissions(role.getPermissions());

	}
	
	public Principal(User u){
		super();
		this.name = u.getName();
		this.surname = u.getSurname();
		this.email = u.getEmail();
		this.serialNumber = u.getSerialNumber();
//		this.rolePermission = u.getRolePermission();
		this.belongingDepthsCodes = u.getBelongingDeptsCodes();
		setPermissions(rolePermission.getPermissions());
		
	}

	// public Principal(String serialNumber, RolePermissions role) {
	// this.serialNumber = serialNumber;
	// this.role = role;
	// setPermissions(role.getPermissions());
	// }

	public String getSerialNumber() {
		return serialNumber;
	}
	

	public List<String> getBelongingDepthsCodes() {
		return belongingDepthsCodes;
	}

	public boolean hasPermission(Permission toCheck) {
		return permissions.contains(toCheck.toString());
	}

	public boolean hasRolePermission(RolePermission toCheck) {
		return rolePermission.equals(toCheck);
	}

	public boolean hasRolePermission(String toCheck) {
		RolePermission r = RolePermission.valueOf(toCheck);
		return rolePermission.equals(r);
	}

	private void setPermissions(List<Permission> permissions) {
		for (Permission toAdd : permissions)
			this.permissions.add(toAdd.toString());
	}

}