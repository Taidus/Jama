package daoLayer;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateful;
import javax.enterprise.context.ConversationScoped;
import javax.persistence.TemporalType;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.primefaces.model.SortOrder;

import businessLayer.Agreement;
import businessLayer.Installment;

@Stateful
@ConversationScoped
//TODO ALL
public class DeadLineSearchService extends ResultPagerBean<Agreement>{

	public DeadLineSearchService() {
	}
	
	public void init(Date lowerDate, Date upperDate, Integer chiefId,
			Integer companyId, SortOrder order) {
		currentPage = 0;

		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Agreement> c = cb.createQuery(Agreement.class);
		Root<Agreement> agr = c.from(Agreement.class);
		//Root<Installment> inst = c.from(Installment.class);
		Join<Agreement,Installment> agrInst = agr.join("installments");
		c.multiselect(agrInst).distinct(true);

		List<Predicate> criteria = new ArrayList<Predicate>();
		if (lowerDate != null) {

			ParameterExpression<Date> p = cb.parameter(Date.class, "lowerDate");
			criteria.add(cb.greaterThanOrEqualTo(
					agr.<Date> get("approvalDate"), p));

		}

		if (upperDate != null) {

			ParameterExpression<Date> p = cb.parameter(Date.class, "upperDate");
			criteria.add(cb.lessThanOrEqualTo(agr.<Date> get("approvalDate"), p));

		}

		if (chiefId != null) {

			ParameterExpression<Integer> p = cb.parameter(Integer.class,
					"chiefId");
			criteria.add(cb.equal(agr.get("chief").get("id"), p));

		}
		if (companyId != null) {

			ParameterExpression<Integer> p = cb.parameter(Integer.class,
					"companyId");
			criteria.add(cb.equal(agr.get("company").get("id"), p));

		}
		
		if(order == SortOrder.ASCENDING){
			
			c.orderBy(cb.asc(agr.<Date> get("approvalDate")));
		}
		else if(order == SortOrder.DESCENDING){
			
			c.orderBy(cb.desc(agr.<Date> get("approvalDate")));

		}

		if (criteria.size() != 0) {

			if (criteria.size() == 1) {
				c.where(criteria.get(0));
			}

			else {
				c.where(cb.and(criteria.toArray(new Predicate[0])));
			}

			query = em.createQuery(c);
			if (lowerDate != null) {
				query.setParameter("lowerDate", lowerDate, TemporalType.DATE);
			}
			if (upperDate != null) {
				query.setParameter("upperDate", upperDate, TemporalType.DATE);
			}
			if (chiefId != null) {
				query.setParameter("chiefId", chiefId);
			}
			if (companyId != null) {
				query.setParameter("companyId", companyId);
			}

		} else {

			query = em.createQuery(c);
		}

	}

}
