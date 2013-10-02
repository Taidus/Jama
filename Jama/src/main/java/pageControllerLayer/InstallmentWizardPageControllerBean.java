package pageControllerLayer;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;

import annotations.TransferObj;
import businessLayer.Installment;

@Named("installmentWizardPCB")
@ConversationScoped
public class InstallmentWizardPageControllerBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	@TransferObj
	private Installment installment;
	private ShareTableController shareTableController;

	public InstallmentWizardPageControllerBean() {
	}

	@PostConstruct
	private void init() {
		shareTableController = new ShareTableController(
				installment.getShareTable());
	}

	public Installment getInstallment() {
		return installment;
	}

	public void setInstallment(Installment installment) {
		this.installment = installment;
	}

	public ShareTableController getShareTableController() {
		return shareTableController;
	}

}
