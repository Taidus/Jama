package security.accessDecisionVoters;

import java.util.HashSet;
import java.util.Set;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import org.apache.deltaspike.security.api.authorization.AccessDecisionVoter;
import org.apache.deltaspike.security.api.authorization.SecurityViolation;
import security.Authorizer;
import usersManagement.Permission;
import util.Messages;

@ApplicationScoped
public abstract class CustomAccessDecisionVoter implements AccessDecisionVoter {
	private static final long serialVersionUID = 1L;

	private Set<SecurityViolation> violationsHappened;
	private Set<SecurityViolation> noViolations = new HashSet<>();

	@Inject
	protected Authorizer authorizer;

	public CustomAccessDecisionVoter() {
		violationsHappened = new HashSet<>();
		violationsHappened.add(new SecurityViolation() {
			private static final long serialVersionUID = 1L;

			@Override
			public String getReason() {
				return Messages.getString("notAuthorized");
			}
		});
	}

	protected Set<SecurityViolation> _checkPermission(Permission toCheck) {
		return (authorizer.canUserDo(toCheck)) ? noViolations : violationsHappened;
	}
}
