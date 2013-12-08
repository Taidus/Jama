package controllerLayer;

import java.io.Serializable;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ConversationScoped;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;

import security.annotations.AlterContractsAllowed;
import security.annotations.ViewContractsAllowed;
import annotations.Current;
import annotations.TransferObj;
import businessLayer.Agreement;
import businessLayer.AgreementInstallment;
import businessLayer.Contract;
import businessLayer.ContractHelper;
import businessLayer.ContractShareTable;
import businessLayer.Installment;
import businessLayer.InstallmentShareTable;

@Named("installmentManager")
@ConversationScoped
public class InstallmentManagerBean implements Serializable {

	protected static final long serialVersionUID = 1L;

	@Inject
	@TransferObj
	protected Contract contract;

	@Inject
	@Current
	private ContractHelper helper;

	// TODO spostare return indirizzo pagina
	protected Installment selectedInstallment;
	protected Installment transferObjInstallment;
	protected Installment installment;


	@PostConstruct
	public void init() {
		addInstallment();
	}


	public Installment getSelectedInstallment() {
		return selectedInstallment;
	}


	public void setSelectedInstallment(Installment selectedInstallment) {
		System.out.print("Installment manager: ");
		System.out.println("setting selected installment: " + selectedInstallment);
		this.selectedInstallment = selectedInstallment;
	}


	public void cancel() {
		System.out.println("Cancelling");
		close();
	}


	@AlterContractsAllowed
	public void save() {

		// installment.copy(transferObjInstallment);

		if (selectedInstallment == null) {
			System.out.println("Saving new installment");
			installment.copy(transferObjInstallment);
			// agreement.getInstallments().add(installment);
			contract.addInstallment(installment);
		}
		else {
			System.out.println("Saving installment with ID: " + selectedInstallment);
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


	@Produces
	@TransferObj
	@RequestScoped
	public InstallmentShareTable getTransferObjShareTable() {
		try {
			return ((AgreementInstallment) transferObjInstallment).getShareTable();
		} catch (ClassCastException e) {}

		return null;
	}


	public Installment getInstallment() {
		return transferObjInstallment;
	}


	protected void initInstallment() {

		installment = helper.getNewInstallment();
		transferObjInstallment = helper.getNewInstallment();
		transferObjInstallment.copy(selectedInstallment);

	}


	@ViewContractsAllowed
	public void viewInstallment() {
		initInstallment();
	}


	@AlterContractsAllowed
	public void editInstallment() {
		System.out.println("Edit inst " + selectedInstallment);
		initInstallment();
	}


	@AlterContractsAllowed
	public void deleteInstallment() {

		// agreement.getInstallments().remove(selectedInstallment);
		contract.removeInstallment(selectedInstallment);
		selectedInstallment.setContract(null);
		close();
	}


	protected void insertRandomValues(Installment inst) {
		// TODO eliminare

		inst.setDate(new Date());
		inst.setInvoiceDate(new Date());
		inst.setVoucherDate(new Date());

	}


	@AlterContractsAllowed
	public String addInstallment() {
		installment = helper.getNewInstallment();
		transferObjInstallment = helper.getNewInstallment();
		transferObjInstallment.setContract(contract);

		try {
			((AgreementInstallment) transferObjInstallment).initShareTableFromContract(contract);
		} catch (ClassCastException e) {}

		insertRandomValues(transferObjInstallment); //TODO eliminare
		return "/installmentWiz.xhtml";
	}

}
