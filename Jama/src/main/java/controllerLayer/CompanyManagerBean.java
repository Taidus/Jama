package controllerLayer;

import javax.ejb.EJB;
import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;

import businessLayer.Company;
import daoLayer.CompanyDaoBean;

@Named("companyManager")
@ConversationScoped
@Stateful
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class CompanyManagerBean {

	// @Inject
	// @TransferObj
	// private Contract contract;
	@EJB
	private CompanyDaoBean companyDao;

	@PersistenceContext(unitName = "primary", type = PersistenceContextType.EXTENDED)
	private EntityManager em;

	private Company company;


	public CompanyManagerBean() {
		this.company = new Company();
	}


	public Company getCompany() {
		return company;
	}


	public void cancel() {}


	public void addCompany() {
		this.company = new Company();

	}


	public void editCompany(int id) {
		company = companyDao.getById(id);
	}


	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void deleteCompany(int id) {
		companyDao.remove(id);
	}


	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void save() {
		System.out.println("......... Save called");

		if (company.getSocialNumber().isEmpty()) {
			company.setSocialNumber(null);
		}
		/*
		 * XXX La riga qui sopra è un workaround. Il problema è che in JSF
		 * quando non si riempie un campo stringa viene passato come stringa
		 * vuota e non come NULL. La soluzione sarebbe inserire nel web.xml: 
		 * 
		 * <context-param>
		 * <param-name>javax.faces.INTERPRET_EMPTY_STRING_SUBMITTED_VALUES_AS_NULL
		 * </param-name> <param-value>true</param-value> </context-param>
		 * 
		 * 
		 * e, a causa di un bug di JBoss 7, passare come argomento alla VM:
		 * 
		 * -Dorg.apache.el.parser.COERCE_TO_ZERO=false
		 * 
		 */

		companyDao.create(company);
		// contract.setCompany(company);
	}

}
