package security;

import java.util.Set;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.apache.deltaspike.security.api.authorization.AccessDecisionVoter;
import org.apache.deltaspike.security.api.authorization.AccessDecisionVoterContext;
import org.apache.deltaspike.security.api.authorization.AccessDeniedException;
import org.apache.deltaspike.security.api.authorization.SecurityViolation;

@ApplicationScoped
public class AdminAccessDecisionVoter implements AccessDecisionVoter {
	private static final long serialVersionUID = 1L;

	@Inject
	private Authorizer authorizer;

	@Override
	public Set<SecurityViolation> checkPermission(
			AccessDecisionVoterContext arg0) {
		if (!authorizer.doAdminCheck()) {
			throw new AccessDeniedException(null);
		}
		return null;
	}
}
