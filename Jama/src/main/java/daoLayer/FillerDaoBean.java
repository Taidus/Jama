package daoLayer;

import java.util.List;

import javax.ejb.Remove;
import javax.ejb.Stateful;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;

import businessLayer.ContractShareTableFiller;

@Named
@Stateful
@ConversationScoped
public class FillerDaoBean {
	@PersistenceContext(unitName = "primary",type=PersistenceContextType.EXTENDED)
	private EntityManager em;

	public FillerDaoBean() {
	}
	
	public List<ContractShareTableFiller> getAll(){
		return em.createNamedQuery("AgreementShareTableFiller.findAll",ContractShareTableFiller.class).getResultList();
	}
	
	
public ContractShareTableFiller create(ContractShareTableFiller filler) {
		
		em.persist(filler);
		return filler;
	}

	public void delete(int id) {

		ContractShareTableFiller filler = em.find(ContractShareTableFiller.class, id);
		if (filler != null) {

			em.remove(filler);
		}
	}

	public ContractShareTableFiller getById(int id) {

		return em.find(ContractShareTableFiller.class, id);

	}
	

	
	@Remove
	public void close(){
		
	}
	
	

}
