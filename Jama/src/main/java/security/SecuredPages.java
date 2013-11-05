package security;

import org.apache.deltaspike.core.api.config.view.ViewConfig;
import org.apache.deltaspike.security.api.authorization.Secured;

@Secured(value = { OperatorAccessDecisionVoter.class }, errorView = Login.class)
class AgreementEdit implements ViewConfig {
}

@Secured(value = { OperatorAccessDecisionVoter.class }, errorView = Login.class)
class AgreementWiz implements ViewConfig {
}

@Secured(value = { OperatorAccessDecisionVoter.class }, errorView = Login.class)
class InstallmentEdit implements ViewConfig {
}

@Secured(value = { OperatorAccessDecisionVoter.class }, errorView = Login.class)
class AgreementList implements ViewConfig {
}

@Secured(value = { OperatorAccessDecisionVoter.class }, errorView = Login.class)
class InstallmentWiz implements ViewConfig {
}

@Secured(value = { HomeAccessDecisionVoter.class }, errorView = Login.class)
class Home implements ViewConfig {}

@Secured(value = { OperatorAccessDecisionVoter.class }, errorView = Login.class)
class AgreementSchedule implements ViewConfig {
}


class Login implements ViewConfig {}