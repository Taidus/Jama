package daoLayer;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import businessLayer.Agreement;
import businessLayer.Department;

@Stateless
public class AgreementDaoBean {
	@PersistenceContext(unitName = "primary")
	private EntityManager em;

	public AgreementDaoBean() {
	}

	public Agreement create(Agreement agreement) {
		em.persist(agreement);
		return agreement;
	}

	public void delete(int id) {

		Agreement agr = em.find(Agreement.class, id);
		if (agr != null) {

			em.remove(agr);
		}
	}

	public Agreement getById(int id) {

		return em.find(Agreement.class, id);

	}
	
	public List<Agreement> getAll(){
		
		return em.createNamedQuery("Agreement.findAll",Agreement.class).getResultList();
		
	}

}
