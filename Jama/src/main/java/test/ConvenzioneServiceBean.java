package test;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class ConvenzioneServiceBean {
	@PersistenceContext(unitName="primary")
	private EntityManager em;

	public ConvenzioneServiceBean() {
	}
	
	public void createConvenzione(String title){
		Convenzione c = new Convenzione(title);
		em.persist(c);
	}

}
