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
import usersManagement.LdapManager;
import usersManagement.User;
import util.Messages;
import businessLayer.Department;
import controllerLayer.UserEditorBean;

@Named("userEditor")
@ConversationScoped
public class UserPresentationBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7518079536711094530L;

	@EJB
	private UserEditorBean userEditor;

	@Inject
	private Conversation conversation;

	@Inject
	private LdapManager ldapManager;

	private User tempLdapUser;


	public UserPresentationBean() {}


	@PostConstruct
	public void init() {
		conversation.begin();
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
		userEditor.setCurrentUser(tempLdapUser);
	}


	public String editLoggedUser() {
		return userEditor.editLoggedUser();
	}


	public User getUser() {
		return userEditor.getCurrentUser();
	}


	public void validateSerialNumber(FacesContext context, UIComponent component, Object value) {
		if (value != null) {
			String serial = value.toString();

			if (null != userEditor.getBySerial(serial)) {
				System.out.println("User editor: matricola duplicata");
				throw new ValidatorException(Messages.getErrorMessage("err_duplicateSerial"));
			}

			tempLdapUser = ldapManager.getUserBySerial(serial);
			if (null == tempLdapUser) {
				System.out.println("User editor: matricola non trovata in ldap");
				throw new ValidatorException(Messages.getErrorMessage("err_badImport"));
			}
		}
	}
}
