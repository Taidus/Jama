package daoLayer;

import java.util.List;

import javax.ejb.Remove;
import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.TypedQuery;

import businessLayer.Agreement;

@Stateful
public class AgreementDaoBean {
	@PersistenceContext(unitName = "primary",type=PersistenceContextType.EXTENDED)
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
	
	public TypedQuery<Agreement> getFindAllQuery(){
		return em.createNamedQuery("Agreement.findAll",Agreement.class);
	}
	
	@Remove
	public void close(){
		
	}

}
