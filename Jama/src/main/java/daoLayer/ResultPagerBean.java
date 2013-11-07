package daoLayer;

import java.util.List;

import javax.ejb.Remove;
import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.TypedQuery;

import util.Config;

@Stateful
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)

public abstract class ResultPagerBean<T> {

	@PersistenceContext(unitName = "primary", type = PersistenceContextType.EXTENDED)
	protected EntityManager em;

	protected int currentPage;
	protected int pageSize = Config.defaultPageSize;
	protected TypedQuery<T> query;
	protected TypedQuery<Long> countQuery;

	public ResultPagerBean() {
	}

	public void next() {
		currentPage++;
	}

	public void previous() {
		currentPage--;
		if (currentPage < 0) {
			currentPage = 0;

		}
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public List<T> getCurrentResults() {
		em.clear();
		return query.setFirstResult(currentPage * pageSize)
				.setMaxResults(pageSize).getResultList();
	}
	
	public Long getResultNumber(){
		return countQuery.getSingleResult();
	}

	public int getPageSize() {
		
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	@Remove
	public void finished() {

	}

}
