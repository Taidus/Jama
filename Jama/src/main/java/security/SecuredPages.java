package security;

import org.apache.deltaspike.core.api.config.view.ViewConfig;
import org.apache.deltaspike.security.api.authorization.Secured;

import security.accessDecisionVoters.AlterUserPermissionAccessDecisionVoter;
import security.accessDecisionVoters.ViewHomeAccessDecisionVoter;
import security.accessDecisionVoters.AlterContractsAccessDecisionVoter;


@Secured(value = { AlterContractsAccessDecisionVoter.class }, errorView = Login.class)
class AgreementEdit implements ViewConfig {
}

@Secured(value = { AlterContractsAccessDecisionVoter.class }, errorView = Login.class)
class AgreementWiz implements ViewConfig {
}

@Secured(value = { AlterContractsAccessDecisionVoter.class }, errorView = Login.class)
class InstallmentEdit implements ViewConfig {
}

@Secured(value = { AlterContractsAccessDecisionVoter.class }, errorView = Login.class)
class AgreementList implements ViewConfig {
}

@Secured(value = { AlterContractsAccessDecisionVoter.class }, errorView = Login.class)
class InstallmentWiz implements ViewConfig {
}

@Secured(value = { ViewHomeAccessDecisionVoter.class }, errorView = Login.class)
class Home implements ViewConfig {
}

@Secured(value = { AlterContractsAccessDecisionVoter.class }, errorView = Login.class)
class AgreementSchedule implements ViewConfig {
}

@Secured(value = { AlterUserPermissionAccessDecisionVoter.class}, errorView = Login.class)
class userWiz implements ViewConfig {
}

class Login implements ViewConfig {
}