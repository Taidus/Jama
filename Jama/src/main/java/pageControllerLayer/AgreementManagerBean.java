package pageControllerLayer;

import java.io.Serializable;

import javax.ejb.EJB;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;

import daoLayer.AgreementDaoBean;
import annotations.TransferObj;
import businessLayer.Agreement;
import businessLayer.Installment;

@Named("agreementManager")
@ConversationScoped
public class AgreementManagerBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Inject
	private Conversation conversation;
	@EJB
	private AgreementDaoBean agreementDao;

	// TODO aggiungere un po di eccezzioni
	private int selectedAgreementId = -1;
	private int selectedInstalmentId = -1;
	private Agreement agreement;
	private Agreement transferObjAgreement;

	public AgreementManagerBean() {

	}

	public int getSelectedAgreementId() {
		return selectedAgreementId;
	}

	public void setSelectedAgreementId(int selectedAgreementId) {
		this.selectedAgreementId = selectedAgreementId;
	}

	public int getSelectedInstalmentId() {
		return selectedInstalmentId;
	}

	public void setSelectedInstalmentId(int selectedInstalmentId) {
		this.selectedInstalmentId = selectedInstalmentId;
	}

	public void begin() {

		conversation.begin();

	}

	public String save() {

		if (selectedAgreementId < 0) {
			agreementDao.create(transferObjAgreement);
		} else {
			agreement.cloneFields(transferObjAgreement);

		}
		close();
		return "/home.xhtml";
	}

	public void close() {

		conversation.end();
		agreementDao.close();
	}

	public Conversation getConversation() {
		return conversation;
	}

	private void initEditing() {

		agreement = agreementDao.getById(selectedAgreementId);
		transferObjAgreement.cloneFields(agreement);

	}

	public String editAgreement() {
		begin();
		initEditing();
		return "/resources/sections/agreementWiz.xhtml";
	}

	public String createAgreement() {
		begin();
		transferObjAgreement = new Agreement();
		return "/resources/sections/agreementWiz.xhtml";

	}

	public String addInstallment() {
		begin();
		initEditing();
		Installment i = new Installment();
		transferObjAgreement.getInstallments().add(i);
		return "/resources/sections/InstallmentWiz.xhtml";

	}

	public String modifyInstallment() {
		begin();
		initEditing();
		return "/resources/sections/InstallmentWiz.xhtml";

	}

	@Produces
	@TransferObj
	@ConversationScoped
	public Agreement getTransferObjAgreement() {
		return transferObjAgreement;
	}

	@Produces
	@TransferObj
	@ConversationScoped
	public Installment getTransferObjAgreementInstallment() {

		return transferObjAgreement.getInstallmentById(selectedInstalmentId);

	}

}
