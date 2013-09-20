package pageControllerLayer;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.inject.Named;

import businessLayer.Agreement;
import businessLayer.AgreementShareTable;
import businessLayer.AgreementType;
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
	private AgreementShareTable agreementShareTable;

	public agreementWizardPageControllerBean() {
		this.agreement = new Agreement();
		this.agreementShareTable = new AgreementShareTable();
	}
	
	@PostConstruct
	public void init(){
		conversation.begin();		
	}
	
	public Agreement getAgreement() {
		return agreement;
	}
	
	public AgreementShareTable getAgreementShareTable() {
		return agreementShareTable;
	}
		
	public Conversation getConversation() {
		return conversation;
	}

	public void save(){
		agreementDao.create(agreement);
		conversation.end();
	}
	
	

}
