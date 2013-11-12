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
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.resource.spi.IllegalStateException;

import businessLayer.Department;
import annotations.Logged;
import annotations.TransferObj;
import daoLayer.UserDaoBean;
import security.Principal;
import security.annotations.CreateUserAllowed;
import usersManagement.User;
import util.Encryptor;
import util.Messages;

//TODO splittare in presentation e controller
@Named("userEditor")
@ConversationScoped
@Stateful
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class UserEditorBean implements Serializable {

	private static final long serialVersionUID = -4966124878956728047L;
	@Inject
	private Conversation conversation;

	private User currentUser;
	@Inject
	private UserDaoBean userDao;

	@PersistenceContext(unitName = "primary", type = PersistenceContextType.EXTENDED)
	private EntityManager em;

	private String password;

	@Inject
	@Logged
	private Principal loggedUser;


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
		if (password != null) {
			currentUser.setPassword(Encryptor.JAMA_DEFAULT.encrypt(password));
		}
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
		if (!currentUser.getBelongingDepts().isEmpty()) {
			return currentUser.getBelongingDepts().get(0);
		}
		else
			return null;
	}


	public void setSelectedDept(Department selectedDept) {
		currentUser.addDepartment(selectedDept);
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public void validateOldPassword(FacesContext context, UIComponent component, Object value) {

		String password = (String) value;
		try {
			if (!currentUser.login(password)) {
				throw new ValidatorException(Messages.getErrorMessage("err_invalidPassword"));
			}
		} catch (IllegalStateException e) {
			throw new ValidatorException(Messages.getErrorMessage("err_invalidPassword"));
		}

	}


	public void validatePassword(FacesContext context, UIComponent component, Object value) {

		try {
			String password1 = (String) value;
			String password2 = (String) ((UIInput) component.findComponent("password")).getValue();

			if (!password1.equals(password2)) {
				throw new ValidatorException(Messages.getErrorMessage("err_passwordMismatch"));
			}
		} catch (ClassCastException e) {
			throw new ValidatorException(Messages.getErrorMessage("err_invalidPassword"));
		}
	}

}
