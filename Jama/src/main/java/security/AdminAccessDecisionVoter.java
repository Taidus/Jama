package security;

import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;

import javax.enterprise.context.ApplicationScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import org.apache.deltaspike.security.api.authorization.AccessDecisionVoter;
import org.apache.deltaspike.security.api.authorization.AccessDecisionVoterContext;
import org.apache.deltaspike.security.api.authorization.SecurityViolation;

@ApplicationScoped
public class AdminAccessDecisionVoter implements AccessDecisionVoter {
	private static final long serialVersionUID = 1L;

	@Inject
	private Authorizer authorizer;

	private ResourceBundle getBundle() {
		FacesContext context = FacesContext.getCurrentInstance();
		return context.getApplication().getResourceBundle(context, "msgs");
	}

	@Override
	public Set<SecurityViolation> checkPermission(
			AccessDecisionVoterContext arg0) {
		Set<SecurityViolation> violations = new HashSet<>();
		if (!authorizer.doAdminCheck()) {
			violations.add(new SecurityViolation() {
				private static final long serialVersionUID = 1L;

				@Override
				public String getReason() {
					return getBundle().getString("notAuthorized");
				}
			});
		}
		System.out.println(violations);
		return violations;
	}
}
