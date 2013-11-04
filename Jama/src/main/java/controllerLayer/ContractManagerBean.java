package controllerLayer;

import java.io.IOException;
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

import org.joda.money.Money;

import security.Principal;
import util.Config;
import util.MailSender;
import annotations.Current;
import annotations.Logged;
import annotations.TransferObj;
import businessLayer.Agreement;
import businessLayer.Contract;
import businessLayer.ContractShareTable;
import businessLayer.Department;
import businessLayer.Funding;
import daoLayer.ContractDaoBean;
import daoLayer.DepartmentDaoBean;
import freemarker.template.TemplateException;

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

	@Inject
	private MailSender mailSender;
	
	@Inject
	@Logged
	private Principal principal;

//	private boolean conversationninherited;
	private String filtersParamList;
	private String provenancePage;

	// TODO aggiungere un po' di eccezioni
	// TODO spostare return indirizzi pagine
	private int selectedContractId = -1;
	private Contract contract;

	private boolean editingClosedContract;
	private boolean creatingNewContract;


	public ContractManagerBean() {
		// conversationninherited = false;
		editingClosedContract = false;
		creatingNewContract = false;
	}


	public String getProvenancePage() {
		return provenancePage;
	}


	public void setProvenancePage(String provenancePage) {
		this.provenancePage = provenancePage;
	}


	public String getFiltersParamList() {
		return filtersParamList;
	}


	public void setFiltersParamList(String filtersParamList) {
		this.filtersParamList = filtersParamList;
	}


	public int getSelectedContractId() {
		return selectedContractId;
	}


	public void setSelectedContractd(int selectedAgreementId) {
		this.selectedContractId = selectedAgreementId;
	}


	private void reset() {
		creatingNewContract = false;
		editingClosedContract = false;
		selectedContractId = -1;
	}


	private void begin() {
		// System.out.println("Conversation Inherited=" +
		// conversationninherited);

		// if (conversation.isTransient()) {
		// conversation.begin();
		//
		// } else {
		// conversationninherited = true;
		// }

		conversation.begin();
	}


	@Remove
	private void close() {

		// if (!conversationninherited) {
		conversation.end();
		// }
		// agreementDao.close();

		reset();
	}


	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void save() {
		System.out.println("SAVE");

		try {
			if (creatingNewContract) {
				mailSender.notifyCreation(contract);
			}

			if (!editingClosedContract && contract.isClosed()) {
				mailSender.notifyClosure(contract);
			}
		} catch (IOException | TemplateException e) {
			e.printStackTrace();
			//TODO gestire l'eccezione, se necessario
		}

		ContractDao.create(contract);
		close();
	}


	public void cancel() {
		System.out.println("CANCEL");
		close();
	}


	public Conversation getConversation() {
		return conversation;
	}


	private void initContract() {
		begin();
		// contract = ContractDao.getById(selectedContractId);
		contract = em.find(Contract.class, selectedContractId);

	}


	public String editContract() {
		// begin();
		initContract();
		editingClosedContract = contract.isClosed();
		return "/agreementEdit.xhtml?faces-redirect=true";
	}


	private String createContract() {
		insertRandomValues(contract); // TODO eliminare
		ContractShareTable shareTable = new ContractShareTable();
		shareTable.setFiller(fillerFactory.getFiller(contract.getDepartment()));
		contract.setShareTable(shareTable);
		begin();
		creatingNewContract = true;
		editingClosedContract = false;
		return "agreementCreate";

	}


	public String createAgreement() {
		System.out.println("createAgreement() !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
		contract = new Agreement();
		return createContract();

	}


	public String createFunding() {
		contract = new Funding();
		return createContract();

	}


	public String viewContract() {
		// begin();
		initContract();
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
	public ContractHelper getInstallmentManager(AgreementHelper agrHelper, FundingHelper funHelper) {

		if (contract instanceof Agreement) {
			return agrHelper;
		}

		else if (contract instanceof Funding) {
			return funHelper;
		} else {
			return null;
		}

	}


	public Contract getContract() {
		return contract;
	}


	public void deleteContract() {
		ContractDao.delete(selectedContractId);
	}


	// TODO eliminare
	private void insertRandomValues(Contract c) {

		c.setTitle("Random title");
		c.setCIA_projectNumber(10000);
		c.setContactPerson("Random contact");
		c.setInventoryNumber(20000);
//		Department d = new Department();
//		d.setCode("DSI/DINFO");
//		d.setName("ex Dipartimento di Sistemi e Informatica");
//		d.setRateDirectory("dsi");
//		depDao.createDepartment(d);
		c.setDepartment(depDao.getByCode(principal.getBelongingDepthsCodes().get(0)));
		c.setWholeTaxableAmount(Money.ofMajor(Config.currency, 10_000L));
		c.setProtocolNumber("30000");
		c.setApprovalDate(new Date());
		c.setBeginDate(new Date());
		c.setDeadlineDate(new Date());

	}

}
