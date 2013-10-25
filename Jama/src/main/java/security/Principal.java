package security;

import java.util.ArrayList;
import java.util.List;

import usersManagement.Role;

public class Principal {
	private String username;
	private List<String> roles;

	public Principal() {
		username = "Guest";
		roles = new ArrayList<String>();
		roles.add(Role.GUEST.toString());
	}

	public Principal(String username) {
		this.username = username;
		this.roles = new ArrayList<String>();
	}

	public String getUsername() {
		return username;
	}

	public void addRole(Role role) {
		roles.add(role.toString());
	}

	public boolean hasRole(Role role) {
		return roles.contains(role.toString());
	}
}
