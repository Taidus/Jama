package daoLayer;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.ejb.Stateful;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;

import usersManagement.LdapManager;
import businessLayer.ChiefScientist;

@Stateful
@ConversationScoped
public class ChiefScientistDaoBean {
	
	@Inject
	private LdapManager ldap;

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

		List<ChiefScientist> databaseList = em.createNamedQuery("ChiefScientist.findAll",
				ChiefScientist.class).getResultList();
		List<ChiefScientist> ldapList = ldap.getAllChiefScientists();
		
		Set<ChiefScientist> chiefSet = new HashSet<>(databaseList);
		chiefSet.addAll(ldapList);
		
		return new ArrayList<>(chiefSet);
				

	}

	public ChiefScientist getBySerial(String serial) {
		List<ChiefScientist> list = em
				.createNamedQuery("ChiefScientist.getBySerial",
						ChiefScientist.class).setParameter("number", serial)
				.getResultList();
		
		if(list.isEmpty()){
			return ldap.getChiefScientistBySerial(serial);
		}
		else{
			return list.get(0);
		}
		
		
	}

}
