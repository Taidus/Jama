package pageControllerLayer;

import java.io.Serializable;

import javax.ejb.EJB;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;

import daoLayer.AgreementDaoBean;
import businessLayer.Agreement;
import businessLayer.Installment;

@Named("agreementWizardPCB")
@ConversationScoped
public class AgreementWizardPageControllerBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ShareTableController shareTableController;

	private int agreementId = -1;
	private Agreement agreement;
	@Inject
	private Conversation conversation;
	@EJB
	private AgreementDaoBean agreementDao;
	private int selectedAgreementId;

	public AgreementWizardPageControllerBean() {

	}

	public int getSelectedAgreementId() {
		return selectedAgreementId;
	}

	public void setSelectedAgreementId(int selectedAgreementId) {
		this.selectedAgreementId = selectedAgreementId;
	}

	public void begin() {

		conversation.begin();

	}

	public String save() {

		agreementDao.create(agreement);
		close();
		return "/home.xhtml";
	}

	public void close() {

		conversation.end();
		agreementDao.close();
	}

	public Agreement getAgreement() {
		return agreement;
	}

	public ShareTableController getShareTableController() {
		return shareTableController;
	}

	public void setShareTableController(
			ShareTableController shareTableController) {
		this.shareTableController = shareTableController;
	}

	public Conversation getConversation() {
		return conversation;
	}
	
	private void initEditing(){
		begin();
		if (agreementId > 0) {

			agreement = agreementDao.getById(selectedAgreementId);
		} else {
			agreement = new Agreement();
		}
		
	}

	public String editAgreement() {

		initEditing();

		shareTableController = new ShareTableController(
				agreement.getShareTable());
		return "/resources/sections/agreementWiz.xhtml";
	}

	public String addInstallment() {

		initEditing();
		Installment i = new Installment();
		i.setAgreement(agreement);
		agreement.getInstallments().add(i);
		shareTableController = new ShareTableController(i.getShareTable());
		return "/resources/sections/InstallmentWiz.xhtml";

	}

	public int getId() {
		return agreementId;
	}

	public void setId(int id) {
		this.agreementId = id;
	}

}
