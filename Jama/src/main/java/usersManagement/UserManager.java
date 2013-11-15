package usersManagement;

import java.io.Serializable;

import security.Principal;
import util.Messages;

import javax.annotation.PostConstruct;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Produces;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.resource.spi.IllegalStateException;

import daoLayer.UserDaoBean;
import annotations.Logged;

@Named("userManager")
@SessionScoped
public class UserManager implements Serializable {

	private static final long serialVersionUID = 1L;
	private Principal loggedUser;
	@Inject
	private UserDaoBean userDao;
	private String insertedSerialNumber;
	
	@Inject
	private Conversation conversation;

	public UserManager() {
	}

	@PostConstruct
	public void init() {
		loggedUser = new Principal();
	}

	@RequestScoped
	@Produces
	@Logged
	public Principal getLoggedUser() {
		return loggedUser;
	}

	public String login(String password) {
		User u = userDao.getBySerialNumber(insertedSerialNumber);
		try {
			if (u != null && u.login(password)) {
				loggedUser = new Principal(u);
				System.out.println("User Login: loggedUser= " + u);
				return "home";
			} else {
				FacesMessage msg = Messages.getMessage("info_badLogin");
				msg.setSeverity(FacesMessage.SEVERITY_INFO);
				FacesContext.getCurrentInstance().addMessage(null, msg);
				
				return "login";
			}
		} catch (IllegalStateException e) {
			return "error";
		}
	}

	public String logout() {
		loggedUser = new Principal();
		if(!conversation.isTransient()){
			conversation.end();
		}
		return "login";
	}

	public String getInsertedSerialNumber() {
		return insertedSerialNumber;
	}

	public void setInsertedSerialNumber(String insertedSerialNumber) {
		this.insertedSerialNumber = insertedSerialNumber;
	}
}
