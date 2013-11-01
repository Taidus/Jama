package controllerLayer;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;

import businessLayer.Contract;
import presentationLayer.lazyModel.ContractTableLazyDataModel;

@ConversationScoped
public abstract class ContractTablePageController implements Serializable {
	private static final long serialVersionUID = -121442773357311865L;
	
	@Inject
	protected ContractManagerBean contractManager;
	@Inject
	protected Conversation conversation;


	@PostConstruct
	public void init() {
		conversation.begin();
	}


	public Conversation getConversation() {
		return conversation;
	}


	public abstract ContractTableLazyDataModel getLazyModel();


	protected String viewAgreement() {
		print("Viewing");
		contractManager.setSelectedContractd(getLazyModel().getSelectedValue().getId());
		contractManager.setFiltersParamList((getLazyModel().getFiltersAsParameterList()));
		System.out.println(getLazyModel().getFiltersAsParameterList());
		close();
		return contractManager.viewContract();
	}


	protected String editAgreement() {
		print("Editing");
		contractManager.setSelectedContractd(getLazyModel().getSelectedValue().getId());
		contractManager.setFiltersParamList((getLazyModel().getFiltersAsParameterList()));
		System.out.println(getLazyModel().getFiltersAsParameterList());
		close();
		return contractManager.editContract();
	}


	protected void deleteAgreement() {
		print("Deleting");
		contractManager.setSelectedContractd(getLazyModel().getSelectedValue().getId());
		contractManager.deleteContract();
	}


	private void print(String action) {
		// TODO eliminare
		Contract selectedValue = getLazyModel().getSelectedValue();
		System.out.println("***\n" + action + " contract with ID: " + selectedValue.getId() + ". Chief: " + selectedValue.getChief().getName()
				+ "; company: " + selectedValue.getCompany().getName() + "\n***");
	}


	private void close() {
		conversation.end();
		getLazyModel().closePager();
	}


	public String backToHome() {
		close();
		return "/home?faces-redirect=true";
	}

}
