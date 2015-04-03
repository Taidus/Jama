package usersManagement;

import java.util.List;

import javax.persistence.Entity;

@Entity
public class SystemRole extends Role {


	public SystemRole() {}


	public SystemRole(RolePermission rolePermission) {
		super();
		this.rolePermission = rolePermission;
	}

	
	@Override
	public RolePermission getRolePermission() {
		return rolePermission;
	}


	@Override
	public List<Permission> getPermissions() {
		return rolePermission.getPermissions();
	}


	@Override
	public boolean hasRolePermission(RolePermission toCheck) {
		return rolePermission.equals(toCheck);
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((rolePermission == null) ? 0 : rolePermission.hashCode());
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SystemRole other = (SystemRole) obj;
		if (rolePermission != other.rolePermission)
			return false;
		return true;
	}


	@Override
	public String toString() {
		return "SystemRole [rolePermission=" + rolePermission + "]";
	}
	
	
}
