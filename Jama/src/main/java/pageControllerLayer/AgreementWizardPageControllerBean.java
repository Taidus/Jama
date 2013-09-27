package pageControllerLayer;

import java.io.Serializable;

import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;

import annotations.Current;
import businessLayer.Agreement;



@Named("agreementWizardPCB")
@ConversationScoped
public class AgreementWizardPageControllerBean implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	
	@Inject @Current private Agreement agreement;

	public AgreementWizardPageControllerBean() {
	
	}
		
	public Agreement getAgreement() {
		return agreement;
	}
	
	

}
