package usersManagement;

import java.util.ArrayList;
import java.util.List;

public enum Role {
	ADMIN(new Permission[] { 
			Permission.ALTER_CONTRACTS,
			Permission.DELETE_CONTRACTS,
			Permission.VIEW_CONTRACTS }),
	
	CHIEF_SCIENTIST(new Permission[] { 
			Permission.VIEW_OWN_CONTRACTS }),
	
	GUEST(new Permission[] {});

	private List<Permission> permissions;

	private Role(Permission[] permissions) {
		this.permissions = new ArrayList<>();
		for (int i = 0; i < permissions.length; i++) {
			this.permissions.add(permissions[i]);
		}
	}

	public boolean hasPermission(Permission toCheck) {
		return permissions.contains(toCheck);
	}
	
	public List<Permission> getPermissions() {
		return permissions;
	}
}