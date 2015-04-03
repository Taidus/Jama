package controllerLayer;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;

import presentationLayer.lazyModel.CompanyTableLazyDataModel;

@Named("companyTablePCB")
@ConversationScoped
public class CompanyTablePageControllerBean implements Serializable {
	private static final long serialVersionUID = -4414242077228626672L;
	
	@Inject
	private CompanyManagerBean manager;

	@Inject
	private CompanyTableLazyDataModel lazyModel;
	
	@Inject
	private Conversation conversation;


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
	
	public CompanyTableLazyDataModel getLazyModel() {
		return lazyModel;
	}

	public String backToHome() {
		close();
		return "home";
	}
	
	public void viewCompany(){
		editCompany();
	}
	
	public void editCompany(){
		System.out.println("Edit company");
		manager.editCompany(lazyModel.getSelectedValue().getId());
	}
	
	public void addCompany(){
		manager.addCompany();
	}
	
	


}
