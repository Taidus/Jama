package controllerLayer;

import java.io.Serializable;

import javax.enterprise.context.ConversationScoped;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;

import annotations.TransferObj;
import businessLayer.Agreement;
import businessLayer.Installment;

@Named("installmentManager")
@ConversationScoped
public class InstallmentManagerBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Inject
	@TransferObj
	private Agreement agreement;


	// TODO spostare return indirizzo pagina
	private Installment selectedInstallment;
	private Installment transferObjInstallment;
	private Installment installment;


	public Installment getSelectedInstallment() {
		return selectedInstallment;
	}

	public void setSelectedInstallment(Installment selectedInstallment) {
		this.selectedInstallment = selectedInstallment;
	}

	public String addInstallment() {

		installment = new Installment();
		transferObjInstallment = new Installment();
		transferObjInstallment.setAgreement(agreement);
		transferObjInstallment.initShareTableFromAgreement(agreement);
		return "/installmentWiz.xhtml";

	}

	public void cancel() {
		
		close();
	}

	public void save() {
		
		installment.copy(transferObjInstallment);


		if (selectedInstallment == null) {	
			agreement.getInstallments().add(installment);
		}
			
		
	close();

	}
	
	public void close(){
		
		selectedInstallment = null;
		
	}


	@Produces
	@TransferObj
	@RequestScoped
	public Installment getTransferObjAgreementInstallment() {
		
			return transferObjInstallment;

	}

	public Installment getInstallment() {
		return transferObjInstallment;
	}

		
	private void initInstallment(){
		
		transferObjInstallment = new Installment();
		transferObjInstallment.copy(selectedInstallment);
		
	}
	
	//TODO riunire?
	public void viewInstallment() {

		initInstallment();
		
	}

	public void editInstallment() {
		
		initInstallment();

	}

	public void deleteInstallment() {
		agreement.getInstallments().remove(selectedInstallment);
		close();
	}

}
