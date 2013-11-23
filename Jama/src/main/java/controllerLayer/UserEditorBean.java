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
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;

import security.Principal;
import security.annotations.CreateUserAllowed;
import usersManagement.LdapManager;
import usersManagement.User;
import annotations.Logged;
import annotations.TransferObj;
import businessLayer.Department;
import daoLayer.UserDaoBean;

//TODO splittare in presentation e controller
@Named("userEditor")
@ConversationScoped
@Stateful
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class UserEditorBean implements Serializable {
	private static final long serialVersionUID = -4966124878956728047L;

	@Inject
	private Conversation conversation;

	@Inject
	private UserDaoBean userDao;

	@PersistenceContext(unitName = "primary", type = PersistenceContextType.EXTENDED)
	private EntityManager em;

	@Inject
	@Logged
	private Principal loggedUser;

	@Inject
	private LdapManager ldapManager;

	private User currentUser;

	public UserEditorBean() {
		super();
	}


	private void begin() {

		conversation.begin();
	}


	@Remove
	private void close() {

		conversation.end();

	}


	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public String save() throws GeneralSecurityException {
		userDao.create(currentUser);

		close();

		return "home";
	}


	public String cancel() {
		close();
		return "home";
	}


	@CreateUserAllowed
	public String createUser() {
		begin();
		currentUser = new User();
		return "userWiz";
	}


	@CreateUserAllowed
	public void importUser() {
		currentUser = ldapManager.getUserBySerial(currentUser.getSerialNumber());
	}


	public String editLoggedUser() {

		begin();

		currentUser = userDao.getBySerialNumber(loggedUser.getSerialNumber());
		return "userProfile";
	}


	@Produces
	@RequestScoped
	@TransferObj
	public User getUser() {
		return currentUser;
	}


	public Conversation getConversation() {
		return conversation;
	}


	public Department getSelectedDept() {
//		if (!currentUser.getBelongingDepts().isEmpty()) {
//			return currentUser.getBelongingDepts().get(0);
//		}
//		else
//			return null;
		
		return currentUser.getDepartment();
	}


	public void setSelectedDept(Department selectedDept) {
		currentUser.addDepartment(selectedDept);
	}

}
