package controllerLayer;

import javax.enterprise.context.RequestScoped;

import util.Messages;
import businessLayer.AgreementInstallment;
import businessLayer.Installment;

@RequestScoped
public class AgreementHelper implements ContractHelper{

	@Override
	public Installment getNewInstallment() {

		return new AgreementInstallment();
		

	}

	@Override
	public boolean renderIvaComponents() {
		return true;
	}

	@Override
	public boolean renderType() {
		return true;
	}

	@Override
	public boolean renderPersonnelQuotes() {
		return true;
	}

	@Override
	public String getName() {
		return Messages.getString("agreement");
	}

	@Override
	public boolean renderShareTable() {
		return true;
	}

}
