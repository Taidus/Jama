package pageControllerLayer;

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
	Agreement agreement;

	// TODO spostare return indirizzo pagina
	private int selectedInstalmentId = -1;
	private Installment transferObjInstallment;
	private Installment installment;

	public int getSelectedInstalmentId() {
		return selectedInstalmentId;
	}

	public void setSelectedInstalmentId(int selectedInstalmentId) {
		this.selectedInstalmentId = selectedInstalmentId;
		System.out.println("selected=====" + selectedInstalmentId);
	}

	public String addInstallment() {

		// agreement.getInstallments().add(i);
		installment = new Installment();
		transferObjInstallment = new Installment();
		transferObjInstallment.setAgreement(agreement);
		return "/installmentWiz.xhtml";

	}

	public void cancel() {
		
		close();
	}

	public void save() {
		
		installment.copy(transferObjInstallment);


		if (selectedInstalmentId < 0) {
			
			
			agreement.getInstallments().add(installment);

		}
		
		transferObjInstallment.setAgreement(null);
		
		
	close();

	}
	
	public void close(){
		
		selectedInstalmentId = -1;
		
	}
//
//	public String modifyInstallment() {
//		// TODO
//		// begin();
//		// initEditing();
//		return "/installmentWiz.xhtml";
//
//	}

	@Produces
	@TransferObj
	@RequestScoped
	public Installment getTransferObjAgreementInstallment() {
		
		//TODO eliminare if
		if(transferObjInstallment ==null){
			transferObjInstallment = new Installment();
			
		}

		return transferObjInstallment;

	}

	public Installment getInstallment() {
		return transferObjInstallment;
	}

		
	private void initInstallment(){
		
		installment = agreement.getInstallmentById(selectedInstalmentId);
		transferObjInstallment = new Installment();
		transferObjInstallment.copy(installment);
		
	}
	public void viewInstallment() {
		
		
		initInstallment();
		
		
	}

	public void editInstallment() {
		
		initInstallment();
		installment = agreement.getInstallmentById(selectedInstalmentId);
		transferObjInstallment = new Installment();
		transferObjInstallment.copy(installment);

	}

	public void deleteInstallment() {
		Installment i = agreement.getInstallmentById(selectedInstalmentId);
		agreement.getInstallments().remove(i);
		i.setAgreement(null);
		close();
	}

}
