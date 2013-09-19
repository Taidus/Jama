package daoLayer;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import businessLayer.ChiefScientist;

@Stateless
public class ChiefScientistDaoBean {

	@PersistenceContext(unitName = "primary")
	private EntityManager em;

	public ChiefScientistDaoBean() {
	}

	public ChiefScientist createChiefScientist(String name, String surname) {

		ChiefScientist chief = new ChiefScientist();
		chief.setName(name);
		chief.setSurname(surname);
		em.persist(chief);
		return chief;

	}

}
