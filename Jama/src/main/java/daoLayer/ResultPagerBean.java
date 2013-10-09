package daoLayer;

import java.util.List;

import javax.ejb.Remove;
import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.TypedQuery;

import util.Config;

@Stateful
public abstract class ResultPagerBean<T> {

	@PersistenceContext(unitName = "primary", type = PersistenceContextType.EXTENDED)
	protected EntityManager em;

	protected int currentPage;
	protected int pageSize = Config.defaultPageSize;
	protected TypedQuery<T> query;

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
		//fixup indagare
		em.clear();
		return query.setFirstResult(currentPage * pageSize)
				.setMaxResults(pageSize).getResultList();
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
