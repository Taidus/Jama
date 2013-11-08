package security;

import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.apache.deltaspike.security.api.authorization.Secures;

import security.annotations.AlterContractsAllowed;
import security.annotations.AlterUserPermissionAllowed;
import security.annotations.CreateUserAllowed;
import security.annotations.DeleteContractsAllowed;
import security.annotations.ViewContractsAllowed;
import security.annotations.ViewHomeAllowed;
import security.annotations.ViewOwnContractsAllowed;
import usersManagement.Permission;
import annotations.Logged;

/**
 * This Authorizer class implements behavior for our security binding types.
 * This class is simply a CDI bean which declares a @Secures method, qualified
 * with the security binding annotation.
 * 
 * 
 */
// FIXME: provo Application Scoped; se non funziona rimettere SessionScoped
@ApplicationScoped
public class Authorizer implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	@Logged
	private Principal loggedUser;

	@Secures
	@AlterContractsAllowed
	public boolean canAlterContracts() {
		return canDo(Permission.ALTER_CONTRACTS);
	}

	@Secures
	@AlterUserPermissionAllowed
	public boolean canAlterUserPermission() {
		return canDo(Permission.ALTER_USER_PERMISSIONS);
	}

	@Secures
	@CreateUserAllowed
	public boolean canCreateUser() {
		return canDo(Permission.CREATE_USER);
	}

	@Secures
	@DeleteContractsAllowed
	public boolean canDeleteContracts() {
		return canDo(Permission.DELETE_CONTRACTS);
	}

	@Secures
	@ViewContractsAllowed
	public boolean canViewContracts() {
		return canDo(Permission.VIEW_CONTRACTS);
	}

	@Secures
	@ViewHomeAllowed
	public boolean canViewHome() {
		return canDo(Permission.VIEW_HOME);
	}

	@Secures
	@ViewOwnContractsAllowed
	public boolean canViewOwnContracts() {
		return canDo(Permission.VIEW_OWN_CONTRACTS);
	}
	
	public boolean canDo(Permission toCheck) {
		return loggedUser.hasPermission(toCheck);
	}

}
