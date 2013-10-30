package security;

import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import org.apache.deltaspike.security.api.authorization.Secures;
import usersManagement.Permission;
import annotations.Logged;

/**
 * This Authorizer class implements behavior for our security binding types.
 * This class is simply a CDI bean which declares a @Secures method, qualified
 * with the security binding annotation.
 * 
 * 
 */
@SessionScoped
public class Authorizer implements Serializable {

	private static final long serialVersionUID = 1L;
	@Inject
	@Logged
	private Principal loggedUser;

	@Secures
	@AlterContractsAllowed
	public boolean canAlterContracts() {
		System.out
				.println("Controllo di sicurezza in corso, prego depositare le armi nella vaschetta");
		return (loggedUser.hasPermission(Permission.ALTER_CONTRACTS)) ? true
				: false;
	}

	@Secures
	@ChiefScientistAllowed
	//FIXME: per ora è finto
	public boolean doChiefScientistCheck() {
		System.out
				.println("Controllo di sicurezza in corso, prego depositare le armi nella vaschetta");
		return true;
	}

}
