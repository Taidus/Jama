package controllerLayer;

import businessLayer.FundingInstallment;
import businessLayer.Installment;


public class FundingHelper implements ContractHelper {

	@Override
	public Installment getNewInstallment() {
		return new FundingInstallment();
	}

	@Override
	public boolean renderIvaComponents() {
		return false;
	}

	@Override
	public boolean renderType() {
		return false;
	}

}
