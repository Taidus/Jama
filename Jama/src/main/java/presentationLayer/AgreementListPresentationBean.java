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

	@EJB private ChiefScientistDaoBean chiefDao;
	@Inject private LazyAgreementDataModel lazyModel;
	@Inject private AgreementManagerBean agrManager;
	
	@Inject private Conversation conversation;
	
	private Agreement selectedValue;

	public AgreementListPresentationBean() {}
	
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
	
	public void editAgreement(){
		System.out.println(selectedValue.getId() + ": " + selectedValue.getChief() + ", " + selectedValue.getCompany());
//		agrManager.setSelectedAgreementId(selectedValue.getId());
//		agrManager.editAgreement();
	}
	
	private void close(){
		conversation.end();
	}
	
	public String backToHome(){
		close();
		return "home";
	}

}
