package controllerLayer;

import javax.ejb.EJB;
import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.enterprise.context.ConversationScoped;
import javax.enterprise.context.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;

import util.Messages;
import annotations.TransferObj;
import businessLayer.Company;
import businessLayer.Contract;
import daoLayer.CompanyDaoBean;

@Named("companyDialogPCB")
@ConversationScoped
@Stateful
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class CompanyDialogPageControllerBean {

//	@Inject
//	@TransferObj
//	private Contract contract;
	@EJB
	private CompanyDaoBean companyDao;
	@PersistenceContext(unitName = "primary", type = PersistenceContextType.EXTENDED)
	private EntityManager em;

	private Company company;

	public CompanyDialogPageControllerBean() {
		this.company = new Company();
	}

	public Company getCompany() {
		return company;
	}

	public void cancel() {
	}
	
	public void addCompany(){
		this.company = new Company();

	}
	
	
	public void editCompany(int id){
		company = companyDao.getById(id);
	}
	
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void deleteCompany(int id){
		companyDao.remove(id);
	}
	

	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void save() {
		companyDao.create(company);
		//contract.setCompany(company);
	}
	
	//TODO spostare???
	public void validateSocialNumber(FacesContext context, UIComponent component, Object value) {
		String socialNumber = (String) value;
		
		if(companyDao.getBySocialNumber(socialNumber)!= null){

			throw new ValidatorException(Messages.getErrorMessage("err_duplicateSocialNumber"));

		}
		
		
	}

}
