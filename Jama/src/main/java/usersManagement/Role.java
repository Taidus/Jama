package usersManagement;

import java.util.List;

public interface Role {
	public boolean hasRolePermission(RolePermission toCheck);
	public RolePermission getRolePermission();
	public List<Permission> getPermissions();
}
