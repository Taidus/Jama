package usersManagement;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class Role {
	public abstract boolean hasRolePermission(RolePermission toCheck);
	public abstract List<Permission> getPermissions();
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
}
