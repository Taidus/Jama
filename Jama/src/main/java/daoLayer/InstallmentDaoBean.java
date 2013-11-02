package daoLayer;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateful;
import javax.enterprise.context.ConversationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.TypedQuery;

import businessLayer.Installment;

@Stateful
@ConversationScoped
public class InstallmentDaoBean {

	@PersistenceContext(unitName = "primary", type = PersistenceContextType.EXTENDED)
	private EntityManager em;

	public List<Installment> getInstallmenteWithDeadlineEqualsTo(Calendar date) {
		TypedQuery<Installment> query = em.createNamedQuery(
				"Installment.findInstallmentsWithNearDeadLine",Installment.class).setParameter(
				"date", new Date(date.getTimeInMillis()));
		return query.getResultList();

	}

}
