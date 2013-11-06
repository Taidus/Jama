package daoLayer;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateful;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.persistence.TemporalType;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.primefaces.model.SortOrder;

import security.AlterContractsAllowed;
import security.Principal;
import annotations.Logged;
import businessLayer.Contract;
import businessLayer.Installment;

@Stateful
@ConversationScoped
public class DeadlineSearchService extends ResultPagerBean<Contract> {
	
	@Inject
	@Logged
	private Principal principal;

	public DeadlineSearchService() {
		
	
	}

	@AlterContractsAllowed
	public void init(Date lowerDate, Date upperDate, Integer chiefId,
			Integer companyId, SortOrder order,
			Class<? extends Contract> contractClass, Boolean closed ) {
		currentPage = 0;

		if (contractClass == null) {
			contractClass = Contract.class;
		}

		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Contract> c = cb.createQuery(Contract.class);
		Root<? extends Contract> agr = c.from(contractClass);
		agr.alias("agr_alias");
		// Root<AgreementInstallment> inst = c.from(AgreementInstallment.class);
		
		//countQuery
		CriteriaQuery<Long> countC = cb.createQuery(Long.class);
		Root<? extends Contract> agr2 = countC.from(c.getResultType());
		agr2.alias("agr_alias");
		countC.select(cb.countDistinct(agr2));

		c.select(agr)
				.distinct(true);
	
		// c.select(agr)
		// .distinct(true)
		// .where(cb.equal(agr,
		// inst.get("contract")));

		List<Predicate> criteria = new ArrayList<Predicate>();
	
		
		
		Join<? extends Contract,Installment> join = agr.join("installments",JoinType.INNER);
		join.alias("contractJoinInstallment");
		
		Join<? extends Contract,Installment> join2 = agr2.join("installments",JoinType.INNER);
		join2.alias("contractJoinInstallment");
		
		criteria.add((cb.equal(join.get("paidInvoice"),
				false)));
		

		if (lowerDate != null) {

			ParameterExpression<Date> p = cb.parameter(Date.class, "lowerDate");
			criteria.add(cb.greaterThanOrEqualTo(join.<Date> get("date"), p));

		}

		if (upperDate != null) {

			ParameterExpression<Date> p = cb.parameter(Date.class, "upperDate");
			criteria.add(cb.lessThanOrEqualTo(join.<Date> get("date"), p));

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

		if (order == SortOrder.ASCENDING) {

			c.orderBy(cb.asc(join.<Date> get("date")));
		} else if (order == SortOrder.DESCENDING) {

			c.orderBy(cb.desc(join.<Date> get("date")));

		}

		List<String> codes = principal.getBelongingDepthsCodes();

		if (codes != null && (!codes.isEmpty())) {

			Expression<String> exp = agr.get("department").get("code");
			Predicate predicate = exp.in(codes);
			criteria.add(predicate);
		}
		
		if (closed != null) {
			ParameterExpression<Boolean> p = cb.parameter(Boolean.class, "closed");
			criteria.add(cb.equal(agr.get("closed"), p));
		}

		if (criteria.size() != 0) {

			if (criteria.size() == 1) {
				c.where(criteria.get(0));
				countC.where(criteria.get(0));
			}

			else {
				c.where(cb.and(criteria.toArray(new Predicate[0])));
				countC.where(cb.and(criteria.toArray(new Predicate[0])));

				
			}

			query = em.createQuery(c);
			countQuery = em.createQuery(countC);
			
			if (lowerDate != null) {
				query.setParameter("lowerDate", lowerDate, TemporalType.DATE);
				countQuery.setParameter("lowerDate", lowerDate, TemporalType.DATE);

			}
			if (upperDate != null) {
				query.setParameter("upperDate", upperDate, TemporalType.DATE);
				countQuery.setParameter("upperDate", upperDate, TemporalType.DATE);

			}
			if (chiefId != null) {
				query.setParameter("chiefId", chiefId);
				countQuery.setParameter("chiefId", chiefId);

			}
			if (companyId != null) {
				query.setParameter("companyId", companyId);
				countQuery.setParameter("companyId", companyId);
			}
			if (closed != null) {
				query.setParameter("closed", closed);
				countQuery.setParameter("closed", closed);

			}

		} else {

			query = em.createQuery(c);
			countQuery = em.createQuery(countC);

		}

	}

}
