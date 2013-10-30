package controllerLayer;

import java.io.Serializable;
import java.util.Date;

import javax.enterprise.context.ConversationScoped;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;

import annotations.Current;
import annotations.TransferObj;
import businessLayer.Agreement;
import businessLayer.AgreementInstallment;
import businessLayer.Contract;
import businessLayer.Installment;

@Named("installmentManager")
@ConversationScoped
public  class InstallmentManagerBean implements Serializable {

	protected static final long serialVersionUID = 1L;

	@Inject
	@TransferObj
	protected Contract contract;
	
	@Inject
	@Current
	private ContractHelper installmentProducer;

	// TODO spostare return indirizzo pagina
	protected Installment selectedInstallment;
	protected Installment transferObjInstallment;
	protected Installment installment;

	public Installment getSelectedInstallment() {
		return selectedInstallment;
	}

	public void setSelectedInstallment(Installment selectedInstallment) {
		this.selectedInstallment = selectedInstallment;
	}


	public void cancel() {

		close();
	}

	public void save() {
		

		// installment.copy(transferObjInstallment);

		if (selectedInstallment == null) {
			installment.copy(transferObjInstallment);
			//agreement.getInstallments().add(installment);
			contract.addInstallment(installment);
		} else {

			selectedInstallment.copy(transferObjInstallment);
		}

		close();
	}

	public void close() {

		selectedInstallment = null;
	}

	@Produces
	@TransferObj
	@RequestScoped
	public Installment getTransferObjInstallment() {

		return transferObjInstallment;
	}

	public Installment getInstallment() {
		return transferObjInstallment;
	}

	protected void initInstallment() {

		installment = new AgreementInstallment();
		transferObjInstallment = new AgreementInstallment();
		transferObjInstallment.copy(selectedInstallment);

	}

	// TODO riunire?
	public void viewInstallment() {

		initInstallment();
	}

	public void editInstallment() {

		initInstallment();
	}

	public void deleteInstallment() {
		
		//agreement.getInstallments().remove(selectedInstallment);
		contract.removeInstallment(selectedInstallment);
		selectedInstallment.setContract(null);
		close();
	}
	
	protected void insertRandomValues(AgreementInstallment inst){
		//TODO eliminare
		
		inst.setDate(new Date());
		inst.setInvoiceDate(new Date());
		inst.setVoucherDate(new Date());
		
	}
	
	public String addInstallment(){
		installment = installmentProducer.getNewInstallment();
	transferObjInstallment = new AgreementInstallment();
	transferObjInstallment.setContract(contract);
	transferObjInstallment.initShareTableFromContract(contract);;
	//insertRandomValues(transferObjInstallment); //TODO eliminare
	return "/installmentWiz.xhtml";
	}

}
