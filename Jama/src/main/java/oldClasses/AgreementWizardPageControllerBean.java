package oldClasses;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ConversationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Named;

import annotations.TransferObj;
import businessLayer.AbstractShareTable;
import businessLayer.Agreement;

@Named("agreementWizardPCBOLD")
@ConversationScoped
public class AgreementWizardPageControllerBean  implements Serializable,ShareTableHolder {
	
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
	private void init(){
		shareTableController = new ShareTableController(this);
	}
	
		
	
	private void onAgreementUpdate(@Observes AgreementEvent event){
	
		init();
		
	}
	
	

	
	public ShareTableController getShareTableController() {
		return shareTableController;
	}

	public Agreement getAgreement() {
		return agreement;
	}

	public void setAgreement(Agreement agreement) {
		this.agreement = agreement;
	}

		
	

	@Override
	public AbstractShareTable getShareTable() {
		return agreement.getShareTable();
	}

	@Override
	public float getWholeAmount() {
		return agreement.getWholeAmount();
	}

	

	
	

}
