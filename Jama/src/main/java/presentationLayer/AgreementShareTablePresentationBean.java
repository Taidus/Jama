package presentationLayer;

import java.io.Serializable;

import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.joda.money.Money;

import annotations.TransferObj;
import businessLayer.AbstractShareTable;
import businessLayer.Contract;
import businessLayer.ContractShareTable;

@Named("agrShareTablePB")
@ConversationScoped
public class AgreementShareTablePresentationBean extends ShareTablePresentationObj implements Serializable {
	private static final long serialVersionUID = 1L;

	@Inject @TransferObj private Contract c;

	public AgreementShareTablePresentationBean() {
		super();
	}
	
	@Override
	protected AbstractShareTable getTransferObjShareTable(){
		return c.getShareTable();
	}
	
	@Override
	protected Money getTransfetObjWholeAmount() {
		return c.getWholeAmount();
	}
	
	@Override
	protected Money getTransfetObjWholeTaxableAmount() {
		return c.getWholeTaxableAmount();
	}
	
	@Override
	protected ContractShareTable getContractShareTable() {
		return c.getShareTable();
	}

}
