package security;

import org.apache.deltaspike.core.api.config.view.ViewConfig;
import org.apache.deltaspike.security.api.authorization.Secured;

@Secured(value = { OperatorAccessDecisionVoter.class }, errorView = NotAuthorized.class)
class AgreementEdit implements ViewConfig {
}

@Secured(value = { OperatorAccessDecisionVoter.class }, errorView = NotAuthorized.class)
class AgreementWiz implements ViewConfig {
}

@Secured(value = { OperatorAccessDecisionVoter.class }, errorView = NotAuthorized.class)
class InstallmentEdit implements ViewConfig {
}

@Secured(value = { OperatorAccessDecisionVoter.class }, errorView = NotAuthorized.class)
class AgreementList implements ViewConfig {
}

@Secured(value = { OperatorAccessDecisionVoter.class }, errorView = NotAuthorized.class)
class InstallmentWiz implements ViewConfig {
}

@Secured(value = { HomeAccessDecisionVoter.class }, errorView = Login.class)
class Home implements ViewConfig {
}

@Secured(value = { OperatorAccessDecisionVoter.class }, errorView = NotAuthorized.class)
class AgreementSchedule implements ViewConfig {
}

class NotAuthorized implements ViewConfig {
}

class Login implements ViewConfig {
}