package presentationLayer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.ConversationScoped;
import javax.faces.validator.ValidatorException;
import javax.inject.Inject;
import javax.inject.Named;

import util.Messages;
import annotations.TransferObj;
import businessLayer.AbstractShareTable;
import businessLayer.Agreement;
import businessLayer.Installment;

@Named("instShareTablePB")
@ConversationScoped
public class InstallmentShareTablePresentationBean extends ShareTablePresentationObj implements Serializable {
	private static final long serialVersionUID = 1L;

	@Inject @TransferObj private Installment installment;

	public InstallmentShareTablePresentationBean() {
		super();
	}

	@Override
	protected AbstractShareTable getTransferObjShareTable() {
		return installment.getShareTable();
	}

	@Override
	protected float getTransfetObjWholeAmount() {
		return installment.getWholeAmount();
	}

	public void validateWithOtherInstallments() {
		Agreement agr = installment.getAgreement();
		List<Installment> installments = agr.getInstallments();
		List<AbstractShareTable> instShareTables = new ArrayList<>();
		
		for(Installment i : installments){
			instShareTables.add(i.getShareTable());
		}
		
		validateFields(agr.getShareTable(), instShareTables);

		// float[] mainValuesAmounts = installment.getMainValuesAmounts();
		// for (Installment installment : installments) {
		// float[] installmentMainValuesAmount =
		// installment.getMainValuesAmounts();
		// for (int i = 0; i < mainValuesAmounts.length; i++) {
		// mainValuesAmounts[i] += installmentMainValuesAmount[i];
		// }
		// }
		// float[] agreementMainValuesAmounts =
		// installment.getAgreement().getMainValuesAmounts();
		// for (int i = 0; i < mainValuesAmounts.length; i++) {
		// if (mainValuesAmounts[i] > agreementMainValuesAmounts[i]) {
		// throw new
		// ValidatorException(Messages.getErrorMessage("err_installmentShareTable"));
		// }
		// }
	}
	
	private void validateFields(AbstractShareTable agrST, List<AbstractShareTable> instSTs){
		List<List<Float>> shareTablesAttributes = new ArrayList<>();
		List<Float> agrAttributes = getAttributeList(agrST);
		
		for(AbstractShareTable st : instSTs){
			shareTablesAttributes.add(getAttributeList(st));
		}
		
		for(int i=0; i<agrAttributes.size(); i++){
			float sum = 0F;
			for(List<Float> l : shareTablesAttributes){
				sum +=  l.get(i);
			}
			if(sum > agrAttributes.get(i)){
				throw new ValidatorException(Messages.getErrorMessage("err_installmentShareTable"));
				//FIXME errore più specifico
			}
		}
	}

	private List<Float> getAttributeList(AbstractShareTable t) {
		List<Float> attributes = new ArrayList<>();

		attributes.add(t.getAtheneumCapitalBalance());
		attributes.add(t.getAtheneumCommonBalance());
		attributes.add(t.getStructures());
		attributes.add(t.getPersonnel());
		attributes.add(t.getGoodsAndServices());

		attributes.add(t.getBusinessTrip());
		attributes.add(t.getInventoryMaterials());
		attributes.add(t.getConsumerMaterials());
		attributes.add(t.getRentals());
		attributes.add(t.getPersonnelOnContract());
		attributes.add(t.getOtherCost());

		return attributes;
	}
}
