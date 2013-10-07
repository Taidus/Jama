package pageControllerLayer;

import java.io.Serializable;

import javax.enterprise.context.ConversationScoped;
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
	
	@Inject @TransferObj Agreement agreement;
	
	private int selectedInstalmentId = -1;
	private Installment transferObjInstallment;
	
	public int getSelectedInstalmentId() {
		return selectedInstalmentId;
	}

	public void setSelectedInstalmentId(int selectedInstalmentId) {
		this.selectedInstalmentId = selectedInstalmentId;
	}
	

	public String addInstallment() {
		
		
		//agreement.getInstallments().add(i);
		transferObjInstallment= new Installment();
		transferObjInstallment.setAgreement(agreement);
		return "/installmentWiz.xhtml";

	}
	
	public String cancel(){
		
		return "/agreementWiz.xhtml";
	}
	
	public String save(){
		
		agreement.getInstallments().add(transferObjInstallment);
		return "/agreementWiz.xhtml";
	}

	public String modifyInstallment() {
		//TODO
//		begin();
//		initEditing();
		return "/resources/sections/InstallmentWiz.xhtml";

	}
	
	@Produces
	@TransferObj
	@ConversationScoped
	public Installment getTransferObjAgreementInstallment() {

		return transferObjInstallment;

	}

	public Installment getInstallment() {
		return transferObjInstallment;
	}

	

}
