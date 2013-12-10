package usersManagement;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;

import businessLayer.Department;

@Entity
public class BusinessRole extends Role {

	
	@ManyToOne
	private Department department;


	public BusinessRole() {}


	public BusinessRole(RolePermission rolePermission, Department department) {
		super();
		this.rolePermission = rolePermission;
		this.department = department;
	}


	public Department getDepartment() {
		return department;
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
		result = prime * result + ((department == null) ? 0 : department.hashCode());
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
		BusinessRole other = (BusinessRole) obj;
		if (department == null) {
			if (other.department != null)
				return false;
		}
		else if (!department.equals(other.department))
			return false;
		if (rolePermission != other.rolePermission)
			return false;
		return true;
	}


	@Override
	public String toString() {
		return "BusinessRole [rolePermission=" + rolePermission + ", department=" + department + "]";
	}
	
	

}
