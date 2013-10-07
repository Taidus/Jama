package pageControllerLayer;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import annotations.TransferObj;
import businessLayer.AbstractShareTable;
import businessLayer.Agreement;

@Named("agreementShareTablePCB")
@RequestScoped
public class AgreementShareTablePageControllerBean extends
		ShareTablePageControllerBean {
	
	@Inject @TransferObj
	private Agreement agreement;

	public AgreementShareTablePageControllerBean() {
	}
	
	@PostConstruct
	public void init(){
		initShares(getShareTable().getSharePerPersonnel());

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
