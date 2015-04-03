package security.accessDecisionVoters;

import java.util.HashSet;
import java.util.Set;
import org.apache.deltaspike.security.api.authorization.AccessDecisionVoterContext;
import org.apache.deltaspike.security.api.authorization.SecurityViolation;
import usersManagement.Permission;

public class ViewHomeAccessDecisionVoter extends CustomAccessDecisionVoter {
	private static final long serialVersionUID = 1L;

	@Override
	public Set<SecurityViolation> checkPermission(
			AccessDecisionVoterContext accessDecisionVoterContext) {
		Set<SecurityViolation> violations = new HashSet<>();
		if (!authorizer.canUserDo(Permission.VIEW_HOME))
			violations.add(new SecurityViolation() {
				private static final long serialVersionUID = 1L;

				@Override
				public String getReason() {
					return "Per visualizzare la home si prega di effettuare il login";
				}
			});
		return violations;
	}
}
