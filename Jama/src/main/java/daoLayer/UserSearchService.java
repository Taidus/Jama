package daoLayer;

import java.util.List;

import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.enterprise.context.ConversationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import businessLayer.Contract;
import usersManagement.User;
import util.Config;

@Stateful
@ConversationScoped
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class UserSearchService extends Pager<User> {
	
	@PersistenceContext(unitName = "primary",type=PersistenceContextType.EXTENDED)
	private EntityManager em;
	
	private Pager<User> pager;
	

	public void init(){
		
		TypedQuery<User> query;
		TypedQuery<Long> countQuery;

		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<User> c = cb.createQuery(User.class);
		Root<User> usr = c.from(User.class);
		c.select(usr);
		
		CriteriaQuery<Long> countC = cb.createQuery(Long.class);
		Root<User> usrC = countC.from(User.class);
		countC.select(cb.countDistinct(usrC));
		
		
		query=em.createQuery(c);
		countQuery=em.createQuery(countC);
	
	
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


	public List<User> getCurrentResults() {
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


	public void finished() {
		pager.finished();
	}
	
	

	
}
