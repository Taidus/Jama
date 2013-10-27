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
import org.joda.money.Money;

import security.AdminAccessDecisionVoter;
import security.Login;
import util.Config;
import annotations.Current;
import annotations.TransferObj;
import businessLayer.Agreement;
import businessLayer.Contract;
import businessLayer.ContractShareTable;
import businessLayer.Department;
import daoLayer.ContractDaoBean;
import daoLayer.DepartmentDaoBean;

@Named("contractManager")
@ConversationScoped
@Stateful
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class ContractManagerBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private Conversation conversation;
	@Inject
	private FillerFactoryBean fillerFactory;
	@EJB
	private ContractDaoBean ContractDao;

	@PersistenceContext(unitName = "primary", type = PersistenceContextType.EXTENDED)
	private EntityManager em;

	@EJB
	private DepartmentDaoBean depDao;

	private boolean conversationninherited;

	// TODO aggiungere un po' di eccezioni
	// TODO spostare return indirizzi pagine
	private int selectedContractId = -1;
	private Contract contract;

	public ContractManagerBean() {
		conversationninherited = false;
	}

	public int getSelectedContractId() {
		return selectedContractId;
	}

	public void setSelectedContractd(int selectedAgreementId) {
		this.selectedContractId = selectedAgreementId;
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

		ContractDao.create(contract);
		close();
	}

	public void cancel() {
		close();
	}

	public Conversation getConversation() {
		return conversation;
	}

	private void initAgreement() {
		contract = ContractDao.getById(selectedContractId);

	}

	public String editAgreement() {
		begin();
		initAgreement();

		return "/agreementEdit.xhtml?faces-redirect=true";
	}

	public String createAgreement() {

		contract = new Agreement();
		//insertRandomValues(contract); // TODO eliminare
		ContractShareTable shareTable = new ContractShareTable();
		shareTable
				.setFiller(fillerFactory.getFiller(contract.getDepartment()));
		contract.setShareTable(shareTable);
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
	public Contract getTransferObjContract() {
		return contract;
	}
	
	@Produces
	@RequestScoped
	@Current
	public InstallmentProducer getInstallmentManager(AgreementInstallmentProducer agrProd){
		
		if( contract instanceof Agreement){
			return agrProd;
		}
		
		else 
			return null;
	
	}
	

	public Contract getContract() {
		return contract;
	}

	@Secured(value = { AdminAccessDecisionVoter.class }, errorView = Login.class)
	public void deleteAgreement() {
		ContractDao.delete(selectedContractId);
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
		agr.setWholeTaxableAmount(Money.ofMajor(Config.currency, 10_000L));
		agr.setProtocolNumber("30000");
		agr.setApprovalDate(new Date());
		agr.setBeginDate(new Date());
		agr.setDeadlineDate(new Date());

	}

}
