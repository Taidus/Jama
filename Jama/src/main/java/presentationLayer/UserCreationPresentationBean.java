package presentationLayer;

import java.io.Serializable;
import java.security.GeneralSecurityException;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.inject.Inject;
import javax.inject.Named;

import security.annotations.CreateUserAllowed;
import usersManagement.BusinessRole;
import usersManagement.Role;
import usersManagement.RolePermission;
import usersManagement.SystemRole;
import usersManagement.User;
import util.Messages;
import businessLayer.Department;
import controllerLayer.UserControllerBean;

@Named("userCreator")
@ConversationScoped
public class UserCreationPresentationBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7518079536711094530L;

	@EJB
	private UserControllerBean userController;

	@Inject
	private Conversation conversation;

	private RolePermission newRolePermission;
	private Department newDepartment;


	public UserCreationPresentationBean() {
		resetNewRoleFields();
	}


	@PostConstruct
	public void init() {
		conversation.begin();
	}


	private void resetNewRoleFields() {
		newRolePermission = RolePermission.ADMIN;
		newDepartment = null;
	}


	public String getSerialNumberToImport() {
		return getUser().getSerialNumber();
	}


	public void setSerialNumberToImport(String serialNumberToImport) {
		// XXX serve un setter per l'input text, ma in realtà il valore inserito
		// serve solo per importare l'utente da LDAP
	}


	public Conversation getConversation() {
		return conversation;
	}


	private void close() {
		conversation.end();
	}


	public String save() {
		try {
			userController.save();
		} catch (GeneralSecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		close();
		return "home";
	}


	public String cancel() {
		userController.cancel();
		close();
		return "home";
	}


	public String createUser() {
		return userController.createUser();
	}


	@CreateUserAllowed
	public void importUser() {
		userController.importUser();
	}


	public User getUser() {
		return userController.getCurrentUser();
	}


	public Department getDepartmentFromRole(Role role) {
		if (role instanceof BusinessRole) {
			return ((BusinessRole) role).getDepartment();
		}
		return null;
	}


	public RolePermission getNewRolePermission() {
		return newRolePermission;
	}


	public void setNewRolePermission(RolePermission newRolePermission) {
		this.newRolePermission = newRolePermission;
	}


	public Department getNewDepartment() {
		return newDepartment;
	}


	public void setNewDepartment(Department newDepartment) {
		this.newDepartment = newDepartment;
	}


	public boolean newDepartmentRequired() {
		return !newRolePermission.equals(RolePermission.ADMIN) && !newRolePermission.equals(RolePermission.PROFESSOR);
	}


	public void addRole() {
		Role newRole;

		if (newRolePermission.equals(RolePermission.ADMIN)) {
			newRole = new SystemRole(newRolePermission);
		}
		else {
			if (newRolePermission.equals(RolePermission.PROFESSOR)) {
				newDepartment = getUser().getDepartment();
			}
			newRole = new BusinessRole(newRolePermission, newDepartment);
		}

		getUser().addRole(newRole);
		resetNewRoleFields();
	}


	public void removeRole(Role role) {
		userController.getCurrentUser().removeRole(role);
	}


	public void validateSerialNumber(FacesContext context, UIComponent component, Object value) {
		if (value != null) {
			String serialNumberToImport = value.toString();

			System.out.print("User creator: validazione matricola '" + serialNumberToImport + "'. ");

			if (null != userController.getFromDbBySerial(serialNumberToImport)) {
				System.out.println("Matricola duplicata");
				throw new ValidatorException(Messages.getErrorMessage("err_duplicateSerial"));
			}

			User user = userController.getFromLdapBySerial(serialNumberToImport);
			if (null == user) {
				System.out.println("Matricola non trovata in ldap");
				throw new ValidatorException(Messages.getErrorMessage("err_badImport"));
			}
			System.out.println("Validazione completata: " + user);
		}
	}
}
