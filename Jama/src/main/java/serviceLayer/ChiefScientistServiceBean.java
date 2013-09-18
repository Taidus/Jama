package serviceLayer;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import businessLayer.ChiefScientist;

@Stateless
public class ChiefScientistServiceBean {

	@PersistenceContext(unitName = "primary")
	private EntityManager em;

	public ChiefScientistServiceBean() {
	}

	public ChiefScientist createChiefScientist(String name, String surname) {

		ChiefScientist chief = new ChiefScientist();
		chief.setName(name);
		chief.setSurname(surname);
		em.persist(chief);
		return chief;

	}

}
