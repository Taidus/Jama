package businessLayer;

import java.util.List;
import businessLayer.AbstractShareTable;
import javax.faces.validator.ValidatorException;
import javax.persistence.*;
import util.Messages;

@Entity
public class InstallmentShareTable extends AbstractShareTable {

//	@OneToOne(mappedBy = "shareTable", cascade=CascadeType.PERSIST)
//	private Installment installment;

	public InstallmentShareTable() {
		initFields();
	}

	public InstallmentShareTable(Installment installment) {
		//this.installment = installment;
		initFields();
	}

//	public Installment getInstallment() {
//		return installment;
//	}
//
//	public void setInstallment(Installment installment) {
//		this.installment = installment;
//	}

	@Override
	public void validate() {
		if (!areMainValuesConsistent()) {
			throw new ValidatorException(
					Messages.getErrorMessage("err_shareTableValues"));
		}
		if (!areGoodsAndServicesValuesConsistent()) {
			throw new ValidatorException(
					Messages.getErrorMessage("err_shareTableGoods"));
		}
		if (!arePersonnelValuesConsistent()) {
			throw new ValidatorException(
					Messages.getErrorMessage("err_shareTablePersonnel"));
		}
		validateWithOtherInstallments();
	}

	private void validateWithOtherInstallments() {
//		List<Installment> installments = getInstallment().getAgreement()
//				.getInstallments();
//		float[] mainValuesAmounts = installment.getMainValuesAmounts();
//		for (Installment installment : installments) {
//			float[] installmentMainValuesAmount = installment
//					.getMainValuesAmounts();
//			for (int i = 0; i < mainValuesAmounts.length; i++) {
//				mainValuesAmounts[i] += installmentMainValuesAmount[i];
//			}
//		}
//		float[] agreementMainValuesAmounts = installment.getAgreement()
//				.getMainValuesAmounts();
//		for (int i = 0; i < mainValuesAmounts.length; i++) {
//			if (mainValuesAmounts[i] > agreementMainValuesAmounts[i]) {
//				throw new ValidatorException(
//						Messages.getErrorMessage("err_installmentShareTable"));
//			}
//		}
	}
}