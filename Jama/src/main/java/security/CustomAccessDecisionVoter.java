package security;

import java.util.ResourceBundle;

import javax.faces.context.FacesContext;
import javax.inject.Inject;

import org.apache.deltaspike.security.api.authorization.AccessDecisionVoter;

public abstract class CustomAccessDecisionVoter implements AccessDecisionVoter {

	private static final long serialVersionUID = 1L;

	@Inject
	protected Authorizer authorizer;

	protected ResourceBundle getBundle() {
		FacesContext context = FacesContext.getCurrentInstance();
		return context.getApplication().getResourceBundle(context, "msgs");
	}
}
