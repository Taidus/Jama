package daoLayer;

import java.util.List;

import javax.persistence.TypedQuery;

import util.Config;

public class ResultPager<T> extends Pager<T> {


	protected int currentPage;
	protected int pageSize = Config.defaultPageSize;
	protected TypedQuery<T> query;
	protected TypedQuery<Long> countQuery;

	

	public ResultPager(int currentPage, int pageSize, TypedQuery<T> query,
			TypedQuery<Long> countQuery) {
		super();
		this.currentPage = currentPage;
		this.pageSize = pageSize;
		this.query = query;
		this.countQuery = countQuery;
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


}
