package usersManagement;

import java.util.List;

public interface Role {
	public boolean hasRolePermission(RolePermission toCheck);
	public List<Permission> getPermissions();
}
