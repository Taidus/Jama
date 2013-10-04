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
	//TODO: modificare il getpercentuale per i sottocampi
	
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
	
	public float getPercentAtheneumCapitalBalance() {
		return agreement.getShareTable().getAtheneumCapitalBalance()*agreement.getWholeAmount()/100;
	}



	public float getPercentAtheneumCommonBalance() {
		return agreement.getShareTable().getAtheneumCommonBalance()*agreement.getWholeAmount()/100;
	}

	

	public float getPercentStructures() {
		return agreement.getShareTable().getStructures()*agreement.getWholeAmount()/100;
	}



	public float getPercentPersonnel() {
		return agreement.getShareTable().getPersonnel()*agreement.getWholeAmount()/100;
	}

	

//	public Map<ChiefScientist, Float> getSharePerPersonnel() {
//		return sharePerPersonnel;
//	}

	

	public float getPercentGoodsAndServices() {
		return agreement.getShareTable().getGoodsAndServices()*agreement.getWholeAmount()/100;
	}

	
	public float getPercentBusinessTrip() {
		return agreement.getShareTable().getBusinessTrip()*agreement.getWholeAmount()*agreement.getShareTable().getGoodsAndServices()/(100*100);
	}

	

	public float getPercentConsumerMaterials() {
		return agreement.getShareTable().getConsumerMaterials()*agreement.getWholeAmount()*agreement.getShareTable().getGoodsAndServices()/(100*100);
	}

	

	public float getPercentInventoryMaterials() {
		return agreement.getShareTable().getInventoryMaterials()*agreement.getWholeAmount()*agreement.getShareTable().getGoodsAndServices()/(100*100);
	}



	public float getPercentRentals() {
		return agreement.getShareTable().getRentals()*agreement.getWholeAmount()*agreement.getShareTable().getGoodsAndServices()/(100*100);
	}

	

	public float getPercentPersonnelOnContract() {
		return agreement.getShareTable().getPersonnelOnContract()*agreement.getWholeAmount()*agreement.getShareTable().getGoodsAndServices()/(100*100);
	}

	

	public float getPercentOtherCost() {
		return agreement.getShareTable().getOtherCost()*agreement.getWholeAmount()/100;
	}

	
	

}
