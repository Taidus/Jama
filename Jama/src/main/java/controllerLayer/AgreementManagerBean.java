package controllerLayer;

import java.io.Serializable;
import java.util.Date;

import javax.ejb.EJB;
import javax.ejb.Remove;
import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;

import org.apache.deltaspike.security.api.authorization.Secured;

import security.AdminAccessDecisionVoter;
import security.Login;
import annotations.TransferObj;
import businessLayer.Agreement;
import businessLayer.AgreementShareTable;
import businessLayer.Department;
import daoLayer.AgreementDaoBean;
import daoLayer.DepartmentDaoBean;

@Named("agreementManager")
@ConversationScoped
@Stateful
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class AgreementManagerBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private Conversation conversation;
	@Inject
	private FillerFactoryBean fillerFactory;
	@EJB
	private AgreementDaoBean agreementDao;

	@PersistenceContext(unitName = "primary", type = PersistenceContextType.EXTENDED)
	private EntityManager em;

	@EJB
	private DepartmentDaoBean depDao;

	private boolean conversationninherited;

	// TODO aggiungere un po' di eccezioni
	// TODO spostare return indirizzi pagine
	private int selectedAgreementId = -1;
	private Agreement agreement;

	public AgreementManagerBean() {
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

	@Remove
	private void close() {

		if (!conversationninherited) {
			conversation.end();
		}
		// agreementDao.close();
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void save() {

		agreementDao.create(agreement);
		close();
	}

	public void cancel() {
		close();
	}

	public Conversation getConversation() {
		return conversation;
	}

	private void initAgreement() {
		agreement = agreementDao.getById(selectedAgreementId);

	}

	public String editAgreement() {
		begin();
		initAgreement();

		return "/agreementEdit.xhtml?faces-redirect=true";
	}

	public String createAgreement() {

		agreement = new Agreement();
		insertRandomValues(agreement); // TODO eliminare
		AgreementShareTable shareTable = new AgreementShareTable();
		shareTable
				.setFiller(fillerFactory.getFiller(agreement.getDepartment()));
		agreement.setShareTable(shareTable);
		begin();

		return "/agreementWiz.xhtml";

	}

	public String viewAgreement() {
		begin();
		initAgreement();
		return "/agreementView.xhtml?faces-redirect=true";
	}

	@Produces
	@TransferObj
	@RequestScoped
	public Agreement getTransferObjAgreement() {
		return agreement;
	}

	public Agreement getAgreement() {
		return agreement;
	}

	@Secured(value = { AdminAccessDecisionVoter.class }, errorView = Login.class)
	public void deleteAgreement() {
		agreementDao.delete(selectedAgreementId);
	}

	private void insertRandomValues(Agreement agr) {
		// TODO eliminare
		agr.setTitle("Random title");
		agr.setCIA_projectNumber(10000);
		agr.setContactPerson("Random contact");
		agr.setInventoryNumber(20000);
		Department d = new Department();
		d.setCode("DSI/DINFO");
		d.setName("ex Dipartimento di Sistemi e Informatica");
		d.setRateDirectory("dsi");
		depDao.createDepartment(d);
		agr.setDepartment(d);
		agr.setWholeTaxableAmount(10000);
		agr.setProtocolNumber("30000");
		agr.setApprovalDate(new Date());
		agr.setBeginDate(new Date());
		agr.setDeadlineDate(new Date());

	}

}
