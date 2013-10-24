package security;

import org.apache.deltaspike.core.api.config.view.ViewConfig;
import org.apache.deltaspike.security.api.authorization.Secured;

@Secured(value = { AdminAccessDecisionVoter.class })
class AgreementEdit implements ViewConfig {
}

@Secured(value = { AdminAccessDecisionVoter.class })
class AgreementWiz implements ViewConfig {
}

@Secured(value = { AdminAccessDecisionVoter.class })
class InstallmentEdit implements ViewConfig {
}

@Secured(value = { AdminAccessDecisionVoter.class })
class InstallmentWiz implements ViewConfig {
}