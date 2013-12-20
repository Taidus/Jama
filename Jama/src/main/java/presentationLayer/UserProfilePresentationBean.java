package presentationLayer;

import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

@Named("userProfilePB")
@ConversationScoped
public class UserProfilePresentationBean implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2294536483822527711L;

	@Inject
	private Conversation conversation;
	
	@Inject
	private UserEditPresentationBean usrEditPB;

	public UserProfilePresentationBean() {
	}
	
	public void close(){
		conversation.end();
	}
	
	public void begin(){
		conversation.begin();
	}
	
	
	public String save() {
		usrEditPB.save();
		close();
		return "home";
	}


	public String cancel() {
		usrEditPB.cancel();
		close();
		return "home";
	}


	public String editLoggedUser() {
		begin();
		usrEditPB.editLoggedUser();
		return "userProfile";
	}

	public Conversation getConversation() {
		return conversation;
	}
	
	

}
