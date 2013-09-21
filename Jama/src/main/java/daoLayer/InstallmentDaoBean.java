package daoLayer;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import businessLayer.Installment;

@Stateless
public class InstallmentDaoBean {

	@PersistenceContext(unitName = "primary")
	private EntityManager em;

	public Installment create(Installment i) {

		em.persist(i);
		return i;

	}

	public void delete(int id) {

		Installment i = em.find(Installment.class, id);
		if (i != null) {
			em.remove(i);
		}

	}

	public InstallmentDaoBean() {
	}

	public Installment getById(int id) {

		return em.find(Installment.class, id);

	}

}
