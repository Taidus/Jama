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
public class UserManager implements Serializable {

	private static final long serialVersionUID = 1L;
	private User loggedUser;
	@Inject
	private UserDaoBean userDao;
	private User defaultUser;

	public UserManager() {
	}

	@PostConstruct
	public void init() {
		defaultUser = new User();
		// TODO: rimettere GUEST quando sarà tutto montato
		defaultUser.setRole(Role.ADMIN);
		loggedUser = defaultUser;
	}

	@RequestScoped
	@Produces
	@Logged
	public User getLoggedUser() {
		return loggedUser;
	}

	public String login(String serialNumber, String password) {
		// TODO: controllo password
		User u = userDao.getBySerialNumber(Integer.parseInt(serialNumber));
		if (u != null) {
			loggedUser = u;
			System.out.println("User Login: loggedUser= " + u);
			return "home";
		}
		return "login";
	}

	public void logout() {
		loggedUser = defaultUser;
		// TODO return value;
	}

}
