package test;

import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import businessLayer.ChiefScientist;
import businessLayer.Company;
import businessLayer.Department;
import daoLayer.AgreementDaoBean;
import daoLayer.ChiefScientistDaoBean;
import daoLayer.CompanyDaoBean;
import daoLayer.DepartmentDaoBean;

@Named
@RequestScoped
public class TestBean {

	@EJB
	private ChiefScientistDaoBean chiefSB;
	@EJB
	private DepartmentDaoBean UARSB;
	@EJB
	private AgreementDaoBean agrSB;
	@EJB
	private CompanyDaoBean compDB;

	public TestBean() {
	}

	private void testDepartmentGetAll() {

		Department d = new Department();
		d.setName("Disit");
		d.setCode("sdsdgh765");

		Department d1 = new Department();
		d1.setName("Energetica");

		Department d2 = new Department();
		d2.setName("Dsi");
		d2.setCode("dswdasd");

		UARSB.createDepartment(d);
		UARSB.createDepartment(d1);
		UARSB.createDepartment(d2);

		List<Department> result = UARSB.getAll();
		System.out.println(result);

	}

	private void testCompanyGetAll() {

		Company c = new Company();
		c.setName("Apple");

		Company c1 = new Company();
		c1.setName("Microsoft");

		Company c2 = new Company();
		c2.setName("Google");

		compDB.create(c);
		compDB.create(c1);
		compDB.create(c2);

		List<Company> result = compDB.getAll();
		System.out.println(result);

	}
	
	
	private void testChiefScientistGetAll(){
		
		
		ChiefScientist c = new ChiefScientist();
		c.setName("Enrico");
		c.setSurname("Vicario");
		
		ChiefScientist c1 = new ChiefScientist();
		c1.setName("Beppe");
		c1.setSurname("Modica");
		
		chiefSB.createChiefScientist(c);
		chiefSB.createChiefScientist(c1);

		
		List<ChiefScientist> result = chiefSB.getAll();
		System.out.println(result);
		
		
		
	}

	public void doJob() {
		
		testChiefScientistGetAll();

	}

}
