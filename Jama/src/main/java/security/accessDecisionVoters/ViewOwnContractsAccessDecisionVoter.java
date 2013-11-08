package security.accessDecisionVoters;

import java.util.Set;

import org.apache.deltaspike.security.api.authorization.AccessDecisionVoterContext;
import org.apache.deltaspike.security.api.authorization.SecurityViolation;

import usersManagement.Permission;

public class ViewOwnContractsAccessDecisionVoter extends
		CustomAccessDecisionVoter {
	private static final long serialVersionUID = 1L;

	@Override
	public Set<SecurityViolation> checkPermission(
			AccessDecisionVoterContext accessDecisionVoterContext) {
		return _checkPermission(Permission.VIEW_OWN_CONTRACTS);
	}

}
