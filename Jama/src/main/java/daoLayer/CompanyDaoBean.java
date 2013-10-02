package daoLayer;

import java.util.List;

import javax.ejb.Stateful;
import javax.ejb.Stateless;
import javax.enterprise.context.ConversationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import businessLayer.Company;

@Stateful
@ConversationScoped

public class CompanyDaoBean {
	@PersistenceContext(unitName = "primary")
	private EntityManager em;

	public CompanyDaoBean() {
	}

	public Company create(Company c) {

		em.persist(c);
		return c;
	}

	public void remove(int id) {

		Company c = em.find(Company.class, id);
		if (c != null) {
			em.remove(c);
		}

	}

	public Company getById(int id) {

		return em.find(Company.class, id);

	}
	
	public List<Company> getAll(){
		
		return em.createNamedQuery("Company.findAll",Company.class).getResultList();
		
	}

}
