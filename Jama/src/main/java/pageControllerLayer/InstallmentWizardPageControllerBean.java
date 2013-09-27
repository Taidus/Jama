package pageControllerLayer;

import java.io.Serializable;

import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;

import annotations.Current;
import businessLayer.Installment;

@Named("installmentWizardPCB")
@ConversationScoped
public class InstallmentWizardPageControllerBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	
	@Inject @Current
	private Installment installment;

	public InstallmentWizardPageControllerBean() {

	}
	

	public Installment getInstallment() {
		return installment;
	}



}
