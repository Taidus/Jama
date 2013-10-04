package pageControllerLayer;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;

import annotations.TransferObj;
import businessLayer.AbstractShareTable;
import businessLayer.Installment;

@Named("installmentWizardPCB")
@ConversationScoped
public class InstallmentWizardPageControllerBean extends WizardPageController implements Serializable {

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
		initShares(getShareTable().getSharePerPersonnel());

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

	@Override
	public AbstractShareTable getShareTable() {
		return installment.getShareTable();
	}

}
