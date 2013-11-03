package controllerLayer;

import businessLayer.Installment;

public interface ContractHelper {
	public Installment getNewInstallment();
	public boolean renderIvaComponents();
	public boolean renderType();


}
