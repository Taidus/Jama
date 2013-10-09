package pageControllerLayer;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;

import annotations.TransferObj;
import businessLayer.AbstractShareTable;
import businessLayer.Agreement;

@Named("agreementShareTablePCB")
@ConversationScoped
public class AgreementShareTablePageControllerBean extends
		ShareTablePageControllerBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	@TransferObj
	private Agreement agreement;

	public AgreementShareTablePageControllerBean() {
	}

	@PostConstruct
	public void init() {
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
