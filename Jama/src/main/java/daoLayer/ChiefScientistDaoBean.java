package daoLayer;

import java.util.List;

import javax.ejb.Stateful;
import javax.enterprise.context.ConversationScoped;
import javax.enterprise.event.Event;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;

import usersManagement.LdapManager;
import usersManagement.User;
import annotations.Created;
import businessLayer.ChiefScientist;

@Stateful
@ConversationScoped
public class ChiefScientistDaoBean {

	@PersistenceContext(unitName = "primary", type = PersistenceContextType.EXTENDED)
	private EntityManager em;

	@Inject
	@Created
	private Event<ChiefScientist> chiefCreationEvent;

	@Inject
	private LdapManager ldap;

	public ChiefScientistDaoBean() {
	}

	public ChiefScientist createChiefScientist(ChiefScientist chief) {

		if (getBySerial(chief.getSerialNumber()) == null) {
			em.persist(chief);
			chiefCreationEvent.fire(chief);
		}
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
						ChiefScientist.class)
				.setParameter("number", serial.toLowerCase()).getResultList();

		if (list.isEmpty()) {
			return null;
		} else {
			return list.get(0);
		}

	}

	public void onProfessorCreation(@Observes @Created User u) {
		if (getBySerial(u.getSerialNumber()) == null) {
			ChiefScientist c = ldap.getChiefScientistBySerial(u
					.getSerialNumber());
			createChiefScientist(c);
		}
	}

}
