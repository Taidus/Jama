package daoLayer;

import javax.ejb.Remove;
import javax.ejb.Stateful;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;

import usersManagement.LdapManager;
import usersManagement.User;

@Stateful
@ConversationScoped
public class UserDaoBean {
	@PersistenceContext(unitName = "primary", type = PersistenceContextType.EXTENDED)
	private EntityManager em;

	@Inject
	private LdapManager ldapm;


	public UserDaoBean() {}


	public User create(User user) {

		em.persist(user);
		return user;
	}


	public void delete(int id) {

		User u = em.find(User.class, id);
		if (u != null) {

			em.remove(u);
		}
	}


	//TODO eliminare
//	public User getById(int id) {
//
//		return em.find(User.class, id);
//
//	}


	public User getBySerialNumber(String serialNumber) {
		User result = null;
		
		try {
			result = em.createNamedQuery("User.findBySerialNumber", User.class).setParameter("number", serialNumber).getSingleResult();
			
			if (null == result) {
				result = ldapm.getUserBySerial(serialNumber);
			}
			
		} catch (NoResultException e) {
			result = null;
		}

		return result;

	}


	@Remove
	public void close() {

	}

}
