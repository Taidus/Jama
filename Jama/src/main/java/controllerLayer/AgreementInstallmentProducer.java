package controllerLayer;

import javax.enterprise.context.RequestScoped;

import businessLayer.AgreementInstallment;
import businessLayer.Installment;

@RequestScoped
public class AgreementInstallmentProducer implements InstallmentProducer{

	@Override
	public Installment getNewInstallment() {

		return new AgreementInstallment();
		

	}

}
