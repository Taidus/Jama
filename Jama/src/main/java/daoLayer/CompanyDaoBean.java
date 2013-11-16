package daoLayer;

import java.util.List;

import javax.ejb.Stateful;
import javax.enterprise.context.ConversationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;

import businessLayer.Company;

@Stateful
@ConversationScoped
public class CompanyDaoBean {
	@PersistenceContext(unitName = "primary", type = PersistenceContextType.EXTENDED)
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

	public List<Company> getAll() {

		return em.createNamedQuery("Company.findAll", Company.class)
				.getResultList();

	}

	public Company getBySocialNumber(String number) {
		List<Company> companies =  em.createNamedQuery("Company.findBySocialNumber", Company.class)
				.setParameter("number", number).getResultList();
		if(companies.size() !=0){
			return companies.get(0);
		}
		else{
			return null;
		}
	}

}
