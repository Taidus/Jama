package pageControllerLayer;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;

import annotations.TransferObj;
import businessLayer.Agreement;

@Named("agreementWizardPCB")
@ConversationScoped
public class AgreementWizardPageControllerBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	@TransferObj
	private Agreement agreement;
	private ShareTableController shareTableController;

	public AgreementWizardPageControllerBean() {
	}

	@PostConstruct
	private void init() {
		shareTableController = new ShareTableController(
				agreement.getShareTable());
	}

	public Agreement getAgreement() {
		return agreement;
	}

	public void setAgreement(Agreement agreement) {
		this.agreement = agreement;
	}

	public ShareTableController getShareTableController() {
		return shareTableController;
	}

}
