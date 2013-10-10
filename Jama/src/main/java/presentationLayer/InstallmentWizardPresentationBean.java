package presentationLayer;

import java.io.Serializable;

import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;

import pageControllerLayer.InstallmentManagerBean;

@Named("installmentWizardPB")
@ConversationScoped
public class InstallmentWizardPresentationBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject InstallmentManagerBean manager;
	private String returnPage;

	public InstallmentWizardPresentationBean() {
	}
	
	
	public String cancel() {
		manager.cancel();
		return returnPage;

	}

	public String save() {
		manager.save();
		System.out.println("=========="+returnPage);
		return returnPage;

	}


	public String getReturnPage() {
		return returnPage;
	}


	public void setReturnPage(String returnPage) {
		this.returnPage = returnPage;
	}
	
	

}
