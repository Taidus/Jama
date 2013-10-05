package presentationLayer;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;

import pageControllerLayer.AgreementManagerBean;
import businessLayer.Agreement;
import daoLayer.ChiefScientistDaoBean;

@Named("agreementListPB")
@ConversationScoped
public class AgreementListPresentationBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@EJB
	private ChiefScientistDaoBean chiefDao;
	@Inject
	private LazyAgreementDataModel lazyModel;
	@Inject
	private AgreementManagerBean agrManager;

	@Inject
	private Conversation conversation;

	private Agreement selectedValue;

	public AgreementListPresentationBean() {
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

	public Agreement getSelectedValue() {
		return selectedValue;
	}

	public void setSelectedValue(Agreement selectedValue) {
		this.selectedValue = selectedValue;
	}

	public String editAgreement() {
		System.out.println("Editing agreement with ID: " + selectedValue.getId() + ". Chief: " + selectedValue.getChief().getCompleteName() + "; company: "
				+ selectedValue.getCompany().getName() + "\n***");
		lazyModel.filterOnReload();
		agrManager.setSelectedAgreementId(selectedValue.getId());
		return agrManager.editAgreement();
	}
	
	public String viewAgreement() {
		System.out.println("Viewing agreement with ID: " + selectedValue.getId() + ". Chief: " + selectedValue.getChief().getCompleteName() + "; company: "
				+ selectedValue.getCompany().getName() + "\n***");
		lazyModel.filterOnReload();
		agrManager.setSelectedAgreementId(selectedValue.getId());
		return agrManager.viewAgreement();
	}

	private void close() {
		conversation.end();
	}

	public String backToHome() {
		close();
		return "home";
	}

}
