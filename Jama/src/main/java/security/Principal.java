package security;

import java.util.ArrayList;
import java.util.List;

import usersManagement.Role;

public class Principal {
	private String serialNumber;

	private List<String> roles;

	public Principal() {
		// TODO: di serialNumber che ci metto?
		serialNumber = "Guest";
		roles = new ArrayList<String>();
		roles.add(Role.GUEST.toString());
	}

	public Principal(String serialNumber) {
		this.serialNumber = serialNumber;
		this.roles = new ArrayList<String>();
	}
	
	public String getSerialNumber() {
		return serialNumber;
	}

	public void addRole(Role role) {
		roles.add(role.toString());
	}

	public boolean hasRole(Role role) {
		return roles.contains(role.toString());
	}

}
