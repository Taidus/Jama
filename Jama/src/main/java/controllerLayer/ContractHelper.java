package controllerLayer;

import businessLayer.Installment;

public interface ContractHelper {
	public Installment getNewInstallment();
	public boolean renderIvaComponents();
	public boolean renderType();
	public boolean renderPersonnelQuotes();
	public String getName();
	public boolean renderShareTable();


}
