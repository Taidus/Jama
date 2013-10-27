package controllerLayer;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import annotations.TransferObj;
import businessLayer.Company;
import businessLayer.Contract;
import daoLayer.CompanyDaoBean;

@Named("companyDialogPCB")
@RequestScoped
public class CompanyDialogPageControllerBean {

	@Inject
	@TransferObj
	private Contract contract;
	@EJB
	private CompanyDaoBean companyDao;

	private Company company;

	public CompanyDialogPageControllerBean() {
		this.company = new Company();
	}

	public Company getCompany() {
		return company;
	}

	public void clear() {
		this.company = new Company();
	}

	public void save() {
		companyDao.create(company);
		contract.setCompany(company);
	}

}
