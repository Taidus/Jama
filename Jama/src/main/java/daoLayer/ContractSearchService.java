package daoLayer;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.TemporalType;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.primefaces.model.SortOrder;

import security.Principal;
import security.annotations.AlterContractsAllowed;
import security.annotations.ViewOwnContractsAllowed;
import util.Config;
import annotations.Logged;
import businessLayer.Contract;
import businessLayer.Installment;

@Stateful
@ConversationScoped
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class ContractSearchService extends Pager<Contract> {
	
	@PersistenceContext(unitName = "primary",type=PersistenceContextType.EXTENDED)
	private EntityManager em;

	@Inject
	@Logged
	private Principal principal;
	
	private Pager<Contract> pager;


	@ViewOwnContractsAllowed
	public void initWithLoggedUserCode(Date lowerDeadLineDate,
			Date upperDeadLineDate, Integer companyId, SortOrder order,
			Class<? extends Contract> contractClass, Boolean closed,
			Date lowerInstDeadlineDate, Date upperInstDeadlineDate) {

		String code = principal.getSerialNumber();

		_init(lowerDeadLineDate, upperDeadLineDate, null, companyId, order,
				contractClass, code, closed, lowerInstDeadlineDate,
				upperInstDeadlineDate);

	}

	@AlterContractsAllowed
	public void init(Date lowerDeadLineDate, Date upperDeadLineDate,
			Integer chiefId, Integer companyId, SortOrder order,
			Class<? extends Contract> contractClass, Boolean closed) {

		_init(lowerDeadLineDate, upperDeadLineDate, chiefId, companyId, order,
				contractClass, null, closed, null, null);

	}

	private void _init(Date lowerDate, Date upperDate, Integer chiefId,
			Integer companyId, SortOrder order,
			Class<? extends Contract> contractClass,
			String principalSerialNumber, Boolean closed,
			Date lowerInstDeadlineDate, Date upperInstDeadlineDate) {

		if (contractClass == null) {
			contractClass = Contract.class;
		}
		
		TypedQuery<Contract> query;
		TypedQuery<Long> countQuery;


		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Contract> c = cb.createQuery(Contract.class);
		Root<? extends Contract> agr = c.from(contractClass);
		agr.alias("agr_alias"); // servono gli alias per nn dover riscrivere i
								// criteria per la countQuery

		c.select(agr).distinct(true);

		CriteriaQuery<Long> countC = cb.createQuery(Long.class);
		countC.select(cb.countDistinct(agr));
		Root<? extends Contract> agr2 = countC.from(contractClass);
		agr2.alias("agr_alias");

		Join<? extends Contract, Installment> join = null;

		Join<? extends Contract, Installment> join2;

		List<Predicate> criteria = new ArrayList<Predicate>();

		if (upperInstDeadlineDate != null || lowerInstDeadlineDate != null) {

			join = agr.join("installments", JoinType.INNER);
			join.alias("ContractJoinInstallment");
			join2 = agr2.join("installments", JoinType.INNER);
			join2.alias("ContractJoinInstallment");

			criteria.add(cb.equal(join.get("paidInvoice"), false));

		}

		if (lowerInstDeadlineDate != null) {

			ParameterExpression<Date> p = cb.parameter(Date.class,
					"lowerInstDate");
			criteria.add(cb.greaterThanOrEqualTo(join.<Date> get("date"), p));
		}

		if (upperInstDeadlineDate != null) {

			ParameterExpression<Date> p = cb.parameter(Date.class,
					"upperInstDate");
			criteria.add(cb.lessThanOrEqualTo(join.<Date> get("date"), p));
		}

		if (lowerDate != null) {

			ParameterExpression<Date> p = cb.parameter(Date.class, "lowerDate");
			criteria.add(cb.greaterThanOrEqualTo(
					agr.<Date> get("deadlineDate"), p));

		}

		if (upperDate != null) {

			ParameterExpression<Date> p = cb.parameter(Date.class, "upperDate");
			criteria.add(cb.lessThanOrEqualTo(agr.<Date> get("deadlineDate"), p));

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
		if (principalSerialNumber != null) {
			ParameterExpression<String> p = cb.parameter(String.class,
					"principalSerialNumber");
			criteria.add(cb.equal(agr.get("chief").get("serialNumber"), p));
		}
		if (closed != null) {
			ParameterExpression<Boolean> p = cb.parameter(Boolean.class,
					"closed");
			criteria.add(cb.equal(agr.get("closed"), p));
		}

		if (order == SortOrder.ASCENDING) {

			c.orderBy(cb.asc(agr.<Date> get("deadlineDate")));
		} else if (order == SortOrder.DESCENDING) {

			c.orderBy(cb.desc(agr.<Date> get("deadlineDate")));

		}

		List<String> codes = principal.getBelongingDepthsCodes();

		if (codes != null && (!codes.isEmpty())) {

			Expression<String> exp = agr.get("department").get("code");
			Predicate predicate = exp.in(codes);
			criteria.add(predicate);
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

			if (lowerInstDeadlineDate != null) {
				query.setParameter("lowerInstDate", lowerInstDeadlineDate,
						TemporalType.DATE);
				countQuery.setParameter("lowerInstDate", lowerInstDeadlineDate,
						TemporalType.DATE);

			}
			if (upperInstDeadlineDate != null) {
				query.setParameter("upperInstDate", upperInstDeadlineDate,
						TemporalType.DATE);
				countQuery.setParameter("upperInstDate", upperInstDeadlineDate,
						TemporalType.DATE);

			}

			if (lowerDate != null) {
				query.setParameter("lowerDate", lowerDate, TemporalType.DATE);
				countQuery.setParameter("lowerDate", lowerDate,
						TemporalType.DATE);

			}
			if (upperDate != null) {
				query.setParameter("upperDate", upperDate, TemporalType.DATE);
				countQuery.setParameter("upperDate", upperDate,
						TemporalType.DATE);

			}
			if (chiefId != null) {
				query.setParameter("chiefId", chiefId);
				countQuery.setParameter("chiefId", chiefId);

			}
			if (companyId != null) {
				query.setParameter("companyId", companyId);
				countQuery.setParameter("companyId", companyId);

			}
			if (principalSerialNumber != null) {
				query.setParameter("principalSerialNumber",
						principalSerialNumber);
				countQuery.setParameter("principalSerialNumber",
						principalSerialNumber);

			}
			if (closed != null) {
				query.setParameter("closed", closed);
				countQuery.setParameter("closed", closed);

			}

		} else {

			query = em.createQuery(c);
			countQuery = em.createQuery(countC);

		}
		
		pager= new ResultPager<>(0, Config.defaultPageSize, query, countQuery);

	}

	public void next() {
		pager.next();
	}

	public void previous() {
		pager.previous();
	}

	public int getCurrentPage() {
		return pager.getCurrentPage();
	}

	public void setCurrentPage(int currentPage) {
		pager.setCurrentPage(currentPage);
	}

	public List<Contract> getCurrentResults() {
		return pager.getCurrentResults();
	}

	public int getPageSize() {
		return pager.getPageSize();
	}

	public void setPageSize(int pageSize) {
		pager.setPageSize(pageSize);
	}

	public Long getResultNumber() {
		return pager.getResultNumber();
	}

	

}
