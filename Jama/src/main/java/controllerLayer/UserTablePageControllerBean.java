package controllerLayer;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;

import presentationLayer.UserEditPresentationBean;
import presentationLayer.lazyModel.UserTableLazyDataModel;

@Named("userTablePCB")
@ConversationScoped
public class UserTablePageControllerBean implements Serializable {
	private static final long serialVersionUID = -3375427782132586156L;

	@Inject
	private UserTableLazyDataModel lazyModel;

	@Inject
	private Conversation conversation;

	@Inject
	private UserEditPresentationBean editor;


	@PostConstruct
	public void init() {
		conversation.begin();
	}


	public Conversation getConversation() {
		return conversation;
	}


	private void close() {
		conversation.end();
		lazyModel.closePager();
	}


	public UserTableLazyDataModel getLazyModel() {
		return lazyModel;
	}
	
	public void viewUser(){
		editUser();
	}
	
	public void editUser(){
		System.out.println("User table PCB: editing/viewing user");
		editor.editUser(lazyModel.getSelectedUser());
	}


	public String backToHome() {
		close();
		return "/home?faces-redirect=true";
	}
}
