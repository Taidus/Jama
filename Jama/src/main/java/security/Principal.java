package security;

import java.util.ArrayList;
import java.util.List;

import usersManagement.Permission;
import usersManagement.Role;

public class Principal {
	private String serialNumber;
	private Role role;
	private List<String> permissions = new ArrayList<>();

	public Principal() {
		// TODO: di serialNumber che ci metto?
		serialNumber = "Guest";
		role = Role.GUEST;
		// FIXME: rimettere GUEST quando sarà tutto montato
		setPermissions(Role.ADMIN.getPermissions());
	}

	public Principal(String serialNumber, Role role) {
		this.serialNumber = serialNumber;
		this.role = role;
		setPermissions(role.getPermissions());
	}

	public String getSerialNumber() {
		return serialNumber;
	}

	public boolean hasPermission(Permission toCheck) {
		return permissions.contains(toCheck.toString());
	}

	public boolean hasRole(Role toCheck) {
		return role.equals(toCheck);
	}

	private void setPermissions(List<Permission> permissions) {
		for (Permission toAdd : permissions)
			this.permissions.add(toAdd.toString());
	}

}
