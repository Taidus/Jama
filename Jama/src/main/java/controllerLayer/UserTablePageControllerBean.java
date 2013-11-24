package controllerLayer;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.event.CellEditEvent;

import daoLayer.UserDaoBean;
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
	private UserDaoBean userDao;


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


	public void changeRole(CellEditEvent event) {
		int rowIndex = event.getRowIndex();
		System.out.println("Row index: " + rowIndex);
		if (rowIndex >= 0) {
			userDao.create(lazyModel.getDisplayedUsers().get(rowIndex));
		}
	}


	public String backToHome() {
		close();
		return "/home?faces-redirect=true";
	}
}
