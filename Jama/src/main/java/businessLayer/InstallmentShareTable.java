package businessLayer;

import javax.persistence.Entity;

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