package usersManagement;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;

import daoLayer.UserDaoBean;
import annotations.Logged;

@Named("userManager")
@SessionScoped
public class userManager implements Serializable {

	private static final long serialVersionUID = 1L;
	private User loggedUser;
	@Inject
	private UserDaoBean userDao;
	private User defaultUser;

	public userManager() {
	}
	
	@PostConstruct
	public void init(){
		defaultUser = new User();
		defaultUser.setRole(Role.GUEST);
		loggedUser = defaultUser;
	}

	@RequestScoped
	@Produces
	@Logged
	public User getLoggedUser() {
		return loggedUser;
	}

	public String login() {
		User u = userDao.getBySerialNumber(5101740);
		if (u == null) {
			u = new User();
			u.setRole(Role.ADMIN);
			u.setSerialNumber(5101740);
			userDao.create(u);
		}
		
		loggedUser = u;
		System.out.println("User Login: loggedUser= "+u);

		return "home";
	}

	public void logout() {
		loggedUser = defaultUser;
		// TODO return value;
	}

}
