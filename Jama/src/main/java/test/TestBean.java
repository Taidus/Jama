package test;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import daoLayer.AgreementDaoBean;
import daoLayer.ChiefScientistDaoBean;
import daoLayer.DepartmentDaoBean;
import businessLayer.Agreement;
import businessLayer.ChiefScientist;
import businessLayer.Department;

@Named
@RequestScoped
public class TestBean{
	
	@EJB
	private ChiefScientistDaoBean chiefSB;
	@EJB
	private DepartmentDaoBean UARSB;
	@EJB
	private AgreementDaoBean agrSB;
	

	public TestBean() {
	}

	public void doJob(){
		
		
		Department disit = UARSB.createDepartment("45/cvb55", "disit");
		ChiefScientist chief = chiefSB.createChiefScientist("paolo", "nesimerda");
		String title = "convenzione schifosa per eclap";
		Agreement agr = agrSB.createAgreement(title, disit, chief);
		
	}
	
	
}
