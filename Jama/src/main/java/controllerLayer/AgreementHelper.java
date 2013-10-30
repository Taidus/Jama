package controllerLayer;

import javax.enterprise.context.RequestScoped;

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

}
