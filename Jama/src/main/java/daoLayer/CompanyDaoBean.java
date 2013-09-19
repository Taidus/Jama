package daoLayer;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import businessLayer.Company;

@Stateless
public class CompanyDaoBean {
	@PersistenceContext(unitName="primary")
	private EntityManager em;

	public CompanyDaoBean() {
	}
	
	public Company createCompany(String name) {

		Company c = new Company();
		c.setName(name);
		em.persist(c);
		return c;
	}
	
	public void removeCompany(int id){
		
		Company c = em.find(Company.class,id);
		em.remove(c);
		
	};
		
		
	

}
