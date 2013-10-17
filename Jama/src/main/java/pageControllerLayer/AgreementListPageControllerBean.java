package pageControllerLayer;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;

import presentationLayer.LazyAgreementDataModel;
import businessLayer.Agreement;
import daoLayer.ChiefScientistDaoBean;

@Named("agreementListPCB")
@ConversationScoped
public class AgreementListPageControllerBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@EJB
	private ChiefScientistDaoBean chiefDao;
	@Inject
	private LazyAgreementDataModel lazyModel;
	@Inject
	private AgreementManagerBean agrManager;

	@Inject
	private Conversation conversation;


	public AgreementListPageControllerBean() {
	}

	@PostConstruct
	public void init() {
		conversation.begin();
	}

	public Conversation getConversation() {
		return conversation;
	}

	public LazyAgreementDataModel getLazyModel() {
		return lazyModel;
	}

	public String editAgreement() {
		print("Editing");
		lazyModel.filterOnReload();
		agrManager.setSelectedAgreementId(lazyModel.getSelectedValue().getId());
		return agrManager.editAgreement();
	}

	public String viewAgreement() {
		print("Viewing");
		lazyModel.filterOnReload();
		agrManager.setSelectedAgreementId(lazyModel.getSelectedValue().getId());
		return agrManager.viewAgreement();
	}

	public void deleteAgreement() {
		print("Deleting");
		lazyModel.filterOnReload();
		agrManager.setSelectedAgreementId(lazyModel.getSelectedValue().getId());
		agrManager.deleteAgreement();
	}

	private void print(String action) {
		//TODO eliminare
		Agreement selectedValue = lazyModel.getSelectedValue();
		System.out.println("***\n" + action + " agreement with ID: " + selectedValue.getId() + ". Chief: "
				+ selectedValue.getChief().getName() + "; company: " + selectedValue.getCompany().getName() + "\n***");
	}

	private void close() {
		conversation.end();
	}

	public String backToHome() {
		close();
		return "/home?faces-redirect=true";
	}

}
