package controllerLayer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
import security.annotations.AlterContractsAllowed;
import security.annotations.ViewOwnContractsAllowed;
import usersManagement.RolePermission;
import util.Config;
import util.MailSender;
import annotations.Current;
import annotations.Logged;
import annotations.TransferObj;
import businessLayer.Agreement;
import businessLayer.Contract;
import businessLayer.ContractHelper;
import businessLayer.ContractShareTable;
import businessLayer.Department;
import businessLayer.Funding;
import daoLayer.ContractDaoBean;
import daoLayer.DepartmentDaoBean;

@Named("contractManager")
@ConversationScoped
@Stateful
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class ContractManagerBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8944924025517129577L;
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
	
	@EJB
	private DepartmentDaoBean deptDao;

	@Inject
	@Logged
	private Principal principal;

	private String filtersParamList;
	private String provenancePage;

	// TODO aggiungere un po' di eccezioni
	// TODO spostare return indirizzi pagine
	private int selectedContractId = -1;
	private Contract contract;

	private boolean editingClosedContract;
	private boolean creatingNewContract;


	public ContractManagerBean() {
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

		conversation.begin();
	}


	@Remove
	private void close() {
		conversation.end();
		reset();
	}


	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void save() {
		System.out.println("SAVE");

		ContractDao.create(contract);
		
		if (creatingNewContract) {
			mailSender.notifyCreation(contract);
		}

		if (!editingClosedContract && contract.isClosed()) {
			mailSender.notifyClosure(contract);
		}
		
		close();
	}


	public void cancel() {
		close();
	}


	public Conversation getConversation() {
		return conversation;
	}


	private void initContract() {
		begin();
		System.out.println("Contract manager -> init contract: tutto bene fino a qui ");
		contract = em.find(Contract.class, selectedContractId);

	}


	@AlterContractsAllowed
	public String editContract() {
		initContract();
		editingClosedContract = contract.isClosed();
		return "/agreementEdit.xhtml?faces-redirect=true";
	}


	private String createContract() {
		initDefaultValues();
//		insertRandomValues(contract); // TODO eliminare
		ContractShareTable shareTable = new ContractShareTable();
		shareTable.setFiller(fillerFactory.getFiller(contract.getDepartment()));
		contract.setShareTable(shareTable);
		begin();
		creatingNewContract = true;
		editingClosedContract = false;
		return "agreementCreate";

	}


	@AlterContractsAllowed
	public String createAgreement() {
		contract = new Agreement();
		return createContract();

	}


	@AlterContractsAllowed
	public String createFunding() {
		contract = new Funding();
		return createContract();

	}


	@ViewOwnContractsAllowed
	public String viewContract() {
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
	public ContractHelper getInstallmentManager() {
		
		return contract.getHelper();

	}


	public Contract getContract() {
		return contract;
	}


	@AlterContractsAllowed
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void deleteContract() {
		ContractDao.delete(selectedContractId);
	}
	
	private void initDefaultValues(){
		List<Department> depts = new ArrayList<>();

		for (String depCode : principal
				.getBelongingDepthsCodes(RolePermission.OPERATOR)) {
			depts.add(deptDao.getByCode(depCode));
		}

		if(depts.size() == 1){
			contract.setDepartment(depts.get(0));
		}
	}
	
	public void setContractDepartment(Department d){
		contract.setDepartment(d);
		contract.getShareTable().setFiller(fillerFactory.getFiller(d));		
	}
	
	public Department getContractDepartment(){
		return contract.getDepartment();
	}


	// TODO eliminare
	private void insertRandomValues(Contract c) {

		c.setTitle("Random title");
		c.setShortTitle("Random short title");
		c.setCIA_projectNumber("a10");
		c.setContactPerson("Random contact");
		c.setInventoryNumber("00/00z");
		c.setDepartment(depDao.getByCode(principal.getBelongingDepthsCodes().get(0)));
		c.setWholeTaxableAmount(Money.ofMajor(Config.currency, 10_000L));
		c.setApprovalDate(new Date());
		c.setBeginDate(new Date());
		c.setDeadlineDate(new Date());

	}

}
