package controllerLayer;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;

import presentationLayer.LazyContractDataModel;

@ConversationScoped
public abstract class AgreementTablePageController implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Inject protected ContractManagerBean contractManager;
	@Inject protected Conversation conversation;

	@PostConstruct
	public void init() {
		conversation.begin();
	}

	public Conversation getConversation() {
		return conversation;
	}

	public abstract LazyContractDataModel getLazyModel();

	protected void close() {
		conversation.end();
		closeModel();
	}
	
	protected abstract void closeModel();

	public String backToHome() {
		close();
		return "/home?faces-redirect=true";
	}


}
