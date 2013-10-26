package presentationLayer;

import java.io.Serializable;

import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.joda.money.Money;

import annotations.TransferObj;
import businessLayer.AbstractShareTable;
import businessLayer.Agreement;

@Named("agrShareTablePB")
@ConversationScoped
public class AgreementShareTablePresentationBean extends ShareTablePresentationObj implements Serializable {
	private static final long serialVersionUID = 1L;

	@Inject @TransferObj private Agreement agreement;

	public AgreementShareTablePresentationBean() {
		super();
	}
	
	@Override
	protected AbstractShareTable getTransferObjShareTable(){
		return agreement.getShareTable();
	}
	
	@Override
	protected Money getTransfetObjWholeAmount() {
		return agreement.getWholeAmount();
	}

}
