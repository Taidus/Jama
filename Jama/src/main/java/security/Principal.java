package security;

import java.util.ArrayList;
import java.util.List;

import usersManagement.Permission;
import usersManagement.Role;

public class Principal {

	private String name;
	private String surname;
	private String email;
	private String serialNumber;
	private Role role;
	private List<String> permissions = new ArrayList<>();

	public Principal() {
		// TODO: di serialNumber che ci metto?
		serialNumber = "Guest";
		role = Role.GUEST;
		// FIXME: rimettere GUEST quando sarà tutto montato
		setPermissions(role.getPermissions());
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
			String serialNumber, Role role) {
		super();
		this.name = name;
		this.surname = surname;
		this.email = email;
		this.serialNumber = serialNumber;
		this.role = role;
		setPermissions(role.getPermissions());

	}

	// public Principal(String serialNumber, Role role) {
	// this.serialNumber = serialNumber;
	// this.role = role;
	// setPermissions(role.getPermissions());
	// }

	public String getSerialNumber() {
		return serialNumber;
	}

	public boolean hasPermission(Permission toCheck) {
		return permissions.contains(toCheck.toString());
	}

	public boolean hasRole(Role toCheck) {
		System.out.println("Check Role : " + toCheck + " , answer ="
				+ role.equals(toCheck));
		return role.equals(toCheck);
	}

	public boolean hasRole(String toCheck) {
		Role r = Role.valueOf(toCheck);
		System.out.println("Check Role String : " + r + " , answer ="
				+ r.equals(r));
		return role.equals(r);
	}

	private void setPermissions(List<Permission> permissions) {
		for (Permission toAdd : permissions)
			this.permissions.add(toAdd.toString());
	}

}
