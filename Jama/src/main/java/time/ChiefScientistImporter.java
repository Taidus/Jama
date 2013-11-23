package time;

import java.util.List;
import java.util.TimerTask;

import javax.ejb.Stateful;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;

import usersManagement.LdapManager;
import businessLayer.ChiefScientist;

@Stateful
@ApplicationScoped
public class ChiefScientistImporter extends TimerTask {

	@PersistenceContext(unitName = "primary", type = PersistenceContextType.EXTENDED)
	private EntityManager em;

	@Inject
	private LdapManager ldap;

	public ChiefScientistImporter() {
		super();
	}

	@Override
	public void run() {

		List<ChiefScientist> chiefs = ldap.getAllChiefScientists();
		for (ChiefScientist c : chiefs) {
			try {
				em.createNamedQuery("ChiefScientist.getBySerial",
						ChiefScientist.class)
						.setParameter("number", c.getSerialNumber())
						.getSingleResult();
			} catch (NoResultException e) {

				em.persist(c);
			}

		}

	}
}
