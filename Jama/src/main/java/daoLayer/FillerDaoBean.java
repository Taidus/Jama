package daoLayer;

import java.util.List;

import javax.ejb.Remove;
import javax.ejb.Stateful;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;

import businessLayer.AgreementShareTableFiller;

@Named
@Stateful
@ConversationScoped
public class FillerDaoBean {
	@PersistenceContext(unitName = "primary",type=PersistenceContextType.EXTENDED)
	private EntityManager em;

	public FillerDaoBean() {
	}
	
	public List<AgreementShareTableFiller> getAll(){
		return em.createNamedQuery("AgreementShareTableFiler.findAll",AgreementShareTableFiller.class).getResultList();
	}
	
	
public AgreementShareTableFiller create(AgreementShareTableFiller filler) {
		
		em.persist(filler);
		return filler;
	}

	public void delete(int id) {

		AgreementShareTableFiller filler = em.find(AgreementShareTableFiller.class, id);
		if (filler != null) {

			em.remove(filler);
		}
	}

	public AgreementShareTableFiller getById(int id) {

		return em.find(AgreementShareTableFiller.class, id);

	}
	

	
	@Remove
	public void close(){
		
	}
	
	

}
