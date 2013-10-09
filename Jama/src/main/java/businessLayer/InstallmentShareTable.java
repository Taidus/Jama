package businessLayer;

import businessLayer.AbstractShareTable;
import javax.faces.validator.ValidatorException;
import javax.persistence.*;
import util.Messages;

@Entity
public class InstallmentShareTable extends AbstractShareTable {

	@OneToOne(mappedBy = "shareTable")
	private Installment installment;

	public InstallmentShareTable() {
		initFields();
	}

	public Installment getInstallment() {
		return installment;
	}

	public void setInstallment(Installment installment) {
		this.installment = installment;
	}

	// XXX: sto nome fa schifo
	// public List<Float> getMainValuesPercentageOfTotal() {
	// float agreementAmountPercentage = (installment.getWholeAmount() /
	// installment
	// .getAgreement().getWholeAmount()) * 100;
	// List<Float> mainValues = getMainValues();
	// for (int index = 0; index < mainValues.size(); index++) {
	// mainValues.set(index,
	// agreementAmountPercentage * mainValues.get(index) / 100);
	// }
	// return mainValues;
	// }

	@Override
	public void validate(float[] mainValues, float[] goodsAndShareValues,
			float[] personnelValues, float goodsAndServices, float personnel) {
		if (!areMainValuesConsistent(mainValues)) {
			throw new ValidatorException(
					Messages.getErrorMessage("err_shareTableValues"));
		}
		if (!areGoodsAndServicesValuesConsistent(goodsAndShareValues,
				goodsAndServices)) {
			throw new ValidatorException(
					Messages.getErrorMessage("err_shareTableGoods"));
		}
		if (!arePersonnelValuesConsistent(personnelValues, personnel)) {
			throw new ValidatorException(
					Messages.getErrorMessage("err_shareTablePersonnel"));
		}
	}
	
	// XXX: sto nome fa schifo

	// public boolean isValidWithOtherInstallments() throws
	// IllegalStateException {
	// // FIXME: per ora assumo che prima della validazione la seguente rata
	// // non sia ancora aggiunta alla convenzione
	// // (che mi sembra la cosa più logica e corretta)
	//
	// List<Installment> installments = installment.getAgreement()
	// .getInstallments();
	// List<Float> usedPercentages = new ArrayList<Float>();
	//
	// // XXX: non sono sicurissimo che debba inizializzare a mano, ma per ora
	// // la tengo
	// for (int index = 0; index < installment.getShareTable().getMainValues()
	// .size(); index++) {
	// usedPercentages.add(Float.valueOf(0));
	// }
	//
	// // TODO: getMainValues non va bene, bisogna fare un
	// // getMainValuesInRelationToTotal
	// for (Iterator<Installment> it = installments.iterator(); it.hasNext();) {
	// Installment i = it.next();
	// List<Float> installmentMainValues = i.getShareTable()
	// .getMainValuesPercentageOfTotal();
	// for (int index = 0; index < installmentMainValues.size(); index++) {
	// usedPercentages.set(index, usedPercentages.get(index)
	// + installmentMainValues.get(index));
	// }
	// }
	//
	// List<Float> myMainValues = getMainValuesPercentageOfTotal();
	//
	// for (int index = 0; index < myMainValues.size(); index++) {
	// usedPercentages.set(index, usedPercentages.get(index)
	// + myMainValues.get(index));
	// }
	//
	// for (int index = 0; index < usedPercentages.size(); index++) {
	// if (usedPercentages.get(index).compareTo(
	// installment.getAgreement().getShareTable().getMainValues()
	// .get(index)) == 1) {
	// throw new IllegalStateException("Le quote non sono corrette");
	// }
	// }
	//
	// return true;
	// }
}
