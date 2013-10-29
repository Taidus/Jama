package controllerLayer;

import businessLayer.FundingInstallment;
import businessLayer.Installment;


public class FundingInstallmentProducer implements InstallmentProducer {

	@Override
	public Installment getNewInstallment() {
		return new FundingInstallment();
	}

}
