package pageControllerLayer;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;

import businessLayer.Agreement;
import daoLayer.AgreementDaoBean;

@Named("agreementWizardPCB")
@ConversationScoped
public class agreementWizardPageControllerBean implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	@EJB private AgreementDaoBean agreementDao;
	@Inject private Conversation conversation;
	private Agreement agreement;

	public agreementWizardPageControllerBean() {
		this.agreement = new Agreement();
	}
	
	@PostConstruct
	public void init(){
		conversation.begin();		
	}
	
	public Agreement getAgreement() {
		return agreement;
	}
	
		
	public Conversation getConversation() {
		return conversation;
	}

	public void save(){
		agreementDao.create(agreement);
		conversation.end();
	}
	
	

}
