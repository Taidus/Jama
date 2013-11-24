package presentationLayer;

import java.io.Serializable;
import java.security.GeneralSecurityException;

import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.inject.Inject;
import javax.inject.Named;

import usersManagement.LdapManager;
import usersManagement.User;
import util.Messages;
import businessLayer.Department;
import controllerLayer.UserEditorBean;
import daoLayer.UserDaoBean;

@Named("userEditor")
@ConversationScoped
public class UserPresentationBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7518079536711094530L;

	@Inject
	private UserEditorBean userEditor;

	@Inject
	private Conversation conversation;

	@Inject
	private LdapManager ldapManager;

	public UserPresentationBean() {
	}

	public Conversation getConversation() {
		return conversation;
	}

	private void begin() {

		conversation.begin();
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
		begin();
		return userEditor.createUser();
	}

	public void importUser() {
//		begin();
		userEditor.importUser();
	}

	public String editLoggedUser() {

		return userEditor.editLoggedUser();
	}

	public User getUser() {
		return userEditor.getCurrentUser();
	}

	public Department getSelectedDept() {
		// if (!currentUser.getBelongingDepts().isEmpty()) {
		// return currentUser.getBelongingDepts().get(0);
		// }
		// else
		// return null;

		return userEditor.getSelectedDept();
	}

	public void setSelectedDept(Department selectedDept) {
		userEditor.setSelectedDept(selectedDept);
	}

	public void validateSerialNumber(FacesContext context,
			UIComponent component, Object value) {
		if (value != null) {
			String serial = value.toString();

			if (null != userEditor.getBySerial(serial)) {
				System.out.println("User editor: matricola duplicata");
				throw new ValidatorException(
						Messages.getErrorMessage("err_duplicateSerial"));
			}

			userEditor.setTempLdapUser(ldapManager.getUserBySerial(serial));
			if (null == userEditor.getTempLdapUser()) {
				System.out
						.println("User editor: matricola non trovata in ldap");
				throw new ValidatorException(
						Messages.getErrorMessage("err_badImport"));
			}
		}
	}

}
