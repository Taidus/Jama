package pageControllerLayer;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ConversationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Named;

import annotations.TransferObj;
import businessLayer.Agreement;

@Named("agreementWizardPCB")
@ConversationScoped
public class AgreementWizardPageControllerBean  implements Serializable {
	
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
		shareTableController = new ShareTableController(agreement.getShareTable());
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

		
	private float computePercent(float percent){
		return agreement.getWholeAmount()*percent/100;
	}
	
	public float getPercentAtheneumCapitalBalance() {
		return computePercent(agreement.getShareTable().getAtheneumCapitalBalance());
	}



	public float getPercentAtheneumCommonBalance() {
		return computePercent(agreement.getShareTable().getAtheneumCommonBalance());
	}

	

	public float getPercentStructures() {
		return computePercent(agreement.getShareTable().getStructures());
	}



	public float getPercentPersonnel() {
		return computePercent(agreement.getShareTable().getPersonnel());
	}

	

//	public Map<ChiefScientist, Float> getSharePerPersonnel() {
//		return sharePerPersonnel;
//	}

	

	public float getPercentGoodsAndServices() {
		return computePercent(agreement.getShareTable().getGoodsAndServices());
	}

	
	public float getPercentBusinessTrip() {
		return computePercent(agreement.getShareTable().getBusinessTrip());
	}

	

	public float getPercentConsumerMaterials() {
		return computePercent(agreement.getShareTable().getConsumerMaterials());
	}

	

	public float getPercentInventoryMaterials() {
		return computePercent(agreement.getShareTable().getInventoryMaterials());
	}



	public float getPercentRentals() {
		return computePercent(agreement.getShareTable().getRentals());
	}

	

	public float getPercentPersonnelOnContract() {
		return computePercent(agreement.getShareTable().getPersonnelOnContract());
	}

	

	public float getPercentOtherCost() {
		return computePercent(agreement.getShareTable().getOtherCost());
	}

	

	
	

}
