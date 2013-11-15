package controllerLayer;

import util.Messages;
import businessLayer.ContractHelper;
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

	@Override
	public boolean renderPersonnelQuotes() {
		return false;
	}

	@Override
	public String getName() {
		return Messages.getString("funding");
	}

	@Override
	public boolean renderShareTable() {
		return true;
	}
	
	

}
