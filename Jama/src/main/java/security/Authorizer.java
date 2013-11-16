package security;

import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.apache.deltaspike.security.api.authorization.Secures;

import security.annotations.AlterCompaniesAllowed;
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
	@AlterCompaniesAllowed
	public boolean canUserAlterCompanies() {
		return canUserDo(Permission.ALTER_COMPANIES);
	}
	
	@Secures
	@AlterContractsAllowed
	public boolean canUserAlterContracts() {
		return canUserDo(Permission.ALTER_CONTRACTS);
	}

	@Secures
	@AlterUserPermissionAllowed
	public boolean canUserAlterUserPermission() {
		return canUserDo(Permission.ALTER_USER_PERMISSIONS);
	}

	@Secures
	@CreateUserAllowed
	public boolean canUserCreateUser() {
		return canUserDo(Permission.CREATE_USER);
	}

	@Secures
	@DeleteContractsAllowed
	public boolean canUserDeleteContracts() {
		return canUserDo(Permission.DELETE_CONTRACTS);
	}

	@Secures
	@ViewContractsAllowed
	public boolean canUserViewContracts() {
		return canUserDo(Permission.VIEW_CONTRACTS);
	}

	@Secures
	@ViewHomeAllowed
	public boolean canUserViewHome() {
		return canUserDo(Permission.VIEW_HOME);
	}

	@Secures
	@ViewOwnContractsAllowed
	public boolean canUserViewOwnContracts() {
		return canUserDo(Permission.VIEW_OWN_CONTRACTS);
	}
	
	public boolean canUserDo(Permission toCheck) {
		return loggedUser.hasPermission(toCheck);
	}

}
