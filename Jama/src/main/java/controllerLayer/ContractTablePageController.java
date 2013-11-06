package controllerLayer;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;

import businessLayer.Contract;
import presentationLayer.lazyModel.SkeletalContractTableLDM;

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


	public abstract SkeletalContractTableLDM getLazyModel();


	protected abstract String getProvenancePage();


	protected String viewAgreement() {
		print("Viewing");
		setupPageChange();
		return contractManager.viewContract();
	}


	protected String editAgreement() {
		print("Editing");
		setupPageChange();
		return contractManager.editContract();
	}


	protected void deleteAgreement() {
		print("Deleting");
		contractManager.setSelectedContractd(getLazyModel().getSelectedValue().getId());
		contractManager.deleteContract();
	}


	private void setupPageChange() {
		contractManager.setSelectedContractd(getLazyModel().getSelectedValue().getId());
		contractManager.setFiltersParamList((getLazyModel().getFiltersAsParameterList()));
		contractManager.setProvenancePage(getProvenancePage());
		System.out.println(getLazyModel().getFiltersAsParameterList());
		close();
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
