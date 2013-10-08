package pageControllerLayer;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;

import annotations.TransferObj;

import javax.inject.Named;

import businessLayer.AbstractShareTable;
import businessLayer.Installment;

@Named("installmentShareTablePCB")
@ConversationScoped
public class InstallmentShareTablePageControllerBean extends
		ShareTablePageControllerBean {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
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
