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
import usersManagement.LdapManager;
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
	private UserControllerBean userEditor;

	@Inject
	private Conversation conversation;

	@Inject
	private LdapManager ldapManager;

	private User tempLdapUser;
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
			userEditor.save();
		} catch (GeneralSecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		close();
		return "home";
	}


	public String cancel() {
		userEditor.cancel();
		close();
		return "home";
	}


	public String createUser() {
		return userEditor.createUser();
	}


	@CreateUserAllowed
	public void importUser() {
		System.out.println("User editor: importing user " + tempLdapUser);
		userEditor.setCurrentUser(tempLdapUser);
	}


	public User getUser() {
		return userEditor.getCurrentUser();
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
		System.out.println("User editor: newDepReq = " + !newRolePermission.equals(RolePermission.ADMIN));
		return !newRolePermission.equals(RolePermission.ADMIN);
	}


	public void addRole() {
		Role newRole;
		if (newDepartmentRequired()) {
			newRole = new BusinessRole(newRolePermission, newDepartment);
		}
		else {
			newRole = new SystemRole(newRolePermission);
		}
		userEditor.getCurrentUser().addRole(newRole);
		resetNewRoleFields();
	}


	public void removeRole(Role role) {
		userEditor.getCurrentUser().removeRole(role);
	}


	public void validateSerialNumber(FacesContext context, UIComponent component, Object value) {
		if (value != null) {
			String serialNumberToImport = value.toString();
			System.out.print("User editor: validazione matricola " + serialNumberToImport + ". ");

			if (null != userEditor.getBySerial(serialNumberToImport)) {
				System.out.println("Matricola duplicata");
				throw new ValidatorException(Messages.getErrorMessage("err_duplicateSerial"));
			}

			tempLdapUser = ldapManager.getUserBySerial(serialNumberToImport);
			if (null == tempLdapUser) {
				System.out.println("Matricola non trovata in ldap");
				throw new ValidatorException(Messages.getErrorMessage("err_badImport"));
			}
			System.out.println("Validazione completata: " + tempLdapUser);
		}
	}
}