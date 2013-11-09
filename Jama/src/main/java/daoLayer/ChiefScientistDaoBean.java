package daoLayer;

import java.util.List;

import javax.ejb.Stateful;
import javax.enterprise.context.ConversationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;

import businessLayer.ChiefScientist;

@Stateful
@ConversationScoped
public class ChiefScientistDaoBean {

	@PersistenceContext(unitName = "primary", type = PersistenceContextType.EXTENDED)
	private EntityManager em;

	public ChiefScientistDaoBean() {
	}

	public ChiefScientist createChiefScientist(ChiefScientist chief) {

		em.persist(chief);
		return chief;

	}

	public void delete(int id) {

		ChiefScientist chief = em.find(ChiefScientist.class, id);
		if (chief != null) {

			em.remove(chief);
		}

	}

	public ChiefScientist getById(int id) {

		return em.find(ChiefScientist.class, id);

	}

	public List<ChiefScientist> getAll() {

		return em.createNamedQuery("ChiefScientist.findAll",
				ChiefScientist.class).getResultList();

	}

	public ChiefScientist getBySerial(String serial) {
		List<ChiefScientist> list = em
				.createNamedQuery("ChiefScientist.getBySerial",
						ChiefScientist.class).setParameter("number", serial)
				.getResultList();
		
		if(list.isEmpty()){
			return null;
		}
		else{
			return list.get(0);
		}
	}

}
