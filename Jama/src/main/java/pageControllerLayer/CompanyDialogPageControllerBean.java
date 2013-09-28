package pageControllerLayer;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import businessLayer.Company;
import daoLayer.CompanyDaoBean;


@Named("companyDialogPCB")
@RequestScoped
public class CompanyDialogPageControllerBean{
	//FIXME (in realtà non bisogna aggiustare questa classe ma l'interfaccia, creando un template per i dialoghi)
	@EJB private CompanyDaoBean companyDao;
	
	private Company company;

	public CompanyDialogPageControllerBean() {
		this.company = new Company();
	}
	
	public Company getCompany() {
		return company;
	}
	
	public void clear(){
		this.company = new Company();
	}

	public void save(){
		companyDao.create(company);
	}
	

}
