package pageControllerLayer;

import java.io.Serializable;
import java.util.Date;

import javax.ejb.EJB;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;

import annotations.TransferObj;
import businessLayer.Agreement;
import daoLayer.AgreementDaoBean;

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
	@PersistenceContext(unitName = "primary", type = PersistenceContextType.EXTENDED)
	private EntityManager em;

	private boolean conversationninherited;


	// TODO aggiungere un po' di eccezioni
	private int selectedAgreementId = -1;
	private Agreement agreement;
	private Agreement transferObjAgreement;

	public AgreementManagerBean() {
		//transferObjAgreement = new Agreement();
		conversationninherited = false;

	}

	public int getSelectedAgreementId() {
		return selectedAgreementId;
	}

	public void setSelectedAgreementId(int selectedAgreementId) {
		this.selectedAgreementId = selectedAgreementId;
	}

	

	private void begin() {
		if (conversation.isTransient()) {
			conversation.begin();

		} else {
			conversationninherited = true;
		}
	}

	public String save() {
		
		agreement.cloneFields(transferObjAgreement);
		agreementDao.create(agreement);


//		if (selectedAgreementId < 0) {
//			agreementDao.create(transferObjAgreement);
//		}
		
		return close();
	}

	private String close() {
		//TODO eliminare i system.out
		if (!conversationninherited) {
			System.out.println("Fail to load 1");
			conversation.end();
			System.out.println("Fail to load 2");
			agreementDao.close();

		}
		System.out.println("Fail to load 3");
		em.clear();
		System.out.println("Fail to load 4");
		
		if (selectedAgreementId < 0) {
			return "/home.xhtml";
		} else {
			return "/agreementList.xhtml";
		}
	}

	public String cancel() {
		return close() + "?faces-redirect=true";
		
	}

	public Conversation getConversation() {
		return conversation;
	}

	private void initAgreement() {
		transferObjAgreement = new Agreement();
		agreement = agreementDao.getById(selectedAgreementId);
		transferObjAgreement.cloneFields(agreement);

	}

	public String editAgreement() {
		begin();
		initAgreement();
		return "/agreementWiz.xhtml?faces-redirect=true";
	}

	public String createAgreement() {
		agreement= new Agreement();
		transferObjAgreement = new Agreement();
		insertRandomValues(transferObjAgreement); //TODO eliminare
		begin();
		//check
		//transferObjAgreement.cloneFields(agreement);
		return "/agreementWiz.xhtml";

	}
	
	public String viewAgreement(){
		
		begin();
		initAgreement();
		return "/agreementView.xhtml?faces-redirect=true";

	}


	@Produces
	@TransferObj
	@ConversationScoped
	public Agreement getTransferObjAgreement() {
		return transferObjAgreement;
	}

	public Agreement getAgreement() {
		return transferObjAgreement;
	}

	public void deleteAgreement() {
		agreementDao.delete(selectedAgreementId);
	}
	
	private void insertRandomValues(Agreement agr){
		//TODO eliminare
		agr.setTitle("Random title");
		agr.setCIA_projectNumber(10000);
		agr.setContactPerson("Random contact");
		agr.setInventoryNumber(20000);
		agr.setWholeTaxableAmount(99999);
		agr.setProtocolNumber("30000");
		agr.setApprovalDate(new Date());
		agr.setBeginDate(new Date());
		agr.setDeadlineDate(new Date());
		
	}

}
