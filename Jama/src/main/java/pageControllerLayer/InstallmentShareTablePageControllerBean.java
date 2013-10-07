package pageControllerLayer;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import annotations.TransferObj;

import javax.inject.Named;

import businessLayer.AbstractShareTable;
import businessLayer.Installment;

@Named("installmentShareTablePCB")
@RequestScoped
public class InstallmentShareTablePageControllerBean extends
		ShareTablePageControllerBean {
	
	@Inject @TransferObj
	private Installment installment;

	public InstallmentShareTablePageControllerBean() {

	}
	
	@PostConstruct
	public void init(){
		initShares(getShareTable().getSharePerPersonnel());

	}

	@Override
	public AbstractShareTable getShareTable() {
		return installment.getShareTable();
	}

	@Override
	public float getWholeAmount() {
		return installment.getAmount();
	}

}
