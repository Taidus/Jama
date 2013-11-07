package daoLayer;

import javax.ejb.Stateful;
import javax.enterprise.context.ConversationScoped;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import usersManagement.User;

@Stateful
@ConversationScoped
public class UserSearchService extends ResultPagerBean<User> {

	public void init(){
		
		currentPage = 0;

		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<User> c = cb.createQuery(User.class);
		Root<User> usr = c.from(User.class);
		c.select(usr);
		
		CriteriaQuery<Long> countC = cb.createQuery(Long.class);
		Root<User> usrC = countC.from(User.class);
		countC.select(cb.countDistinct(usrC));
		
		
		query=em.createQuery(c);
		countQuery=em.createQuery(countC);
		
	}
	
}
