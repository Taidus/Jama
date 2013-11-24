package controllerLayer;

import java.io.Serializable;
import java.security.GeneralSecurityException;

import javax.ejb.Remove;
import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;

import security.Principal;
import security.annotations.CreateUserAllowed;
import usersManagement.LdapManager;
import usersManagement.User;
import util.Messages;
import annotations.Logged;
import annotations.TransferObj;
import businessLayer.Department;
import daoLayer.UserDaoBean;

//TODO splittare in presentation e controller
@ConversationScoped
@Stateful
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class UserEditorBean implements Serializable {
	private static final long serialVersionUID = -4966124878956728047L;

	@Inject
	private UserDaoBean userDao;

	@PersistenceContext(unitName = "primary", type = PersistenceContextType.EXTENDED)
	private EntityManager em;

	@Inject
	@Logged
	private Principal loggedUser;

	private User currentUser;
	private User tempLdapUser;

	public UserEditorBean() {
		super();
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void save() throws GeneralSecurityException {
		userDao.create(currentUser);
	}

	public void cancel() {
	}

	@CreateUserAllowed
	public String createUser() {
		currentUser = new User();
		return "userWiz";
	}

	@CreateUserAllowed
	public void importUser() {
		// currentUser =
		// ldapManager.getUserBySerial(currentUser.getSerialNumber());
		currentUser = tempLdapUser;
	}

	public String editLoggedUser() {
		currentUser = userDao.getBySerialNumber(loggedUser.getSerialNumber());
		System.out.println("Current User: "+currentUser);
		return "userProfile";
	}

	@Produces
	@RequestScoped
	@TransferObj
	public User getUser() {
		return currentUser;
	}

	public Department getSelectedDept() {
		// if (!currentUser.getBelongingDepts().isEmpty()) {
		// return currentUser.getBelongingDepts().get(0);
		// }
		// else
		// return null;

		return currentUser.getDepartment();
	}

	public void setSelectedDept(Department selectedDept) {
		currentUser.addDepartment(selectedDept);
	}

	public User getCurrentUser() {
		return currentUser;
	}

	public void setCurrentUser(User currentUser) {
		this.currentUser = currentUser;
	}

	public User getTempLdapUser() {
		return tempLdapUser;
	}

	public void setTempLdapUser(User tempLdapUser) {
		this.tempLdapUser = tempLdapUser;
	}
	
	public User getBySerial(String serial){
		return userDao.getBySerialNumber(serial);
	}

}
