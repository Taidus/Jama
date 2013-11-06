package presentationLayer;

import java.io.Serializable;

import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;

import controllerLayer.InstallmentManagerBean;

@Named("installmentWizardPB")
@ConversationScoped
public class InstallmentWizardPresentationBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Inject
	private InstallmentManagerBean manager;


	public InstallmentWizardPresentationBean() {}


//	public String cancel() {
//		manager.cancel();
//		return returnPage;
//
//	}
//
//
//	public String save() {
//		manager.save();
//		System.out.println("==========" + returnPage);
//		return returnPage;
//
//	}
//
//
//	public String getReturnPage() {
//		return returnPage;
//	}
//
//
//	public void setReturnPage(String returnPage) {
//		this.returnPage = returnPage;
//	}
//	
//	public String viewInstallment() {
//		manager.viewInstallment();
//		return "installmentView";
//	}
//
//
//	public String editInstallment() {
//		manager.editInstallment();
//		return "installmentEdit";
//	}
//
//
//	public void deleteInstallment() {
//		System.out.println("chiamatooo");
//		manager.deleteInstallment();
//		System.out.println("DELETEEEEEE");
//		System.out.println(returnPage);
//
//	}
	
	public void cancel() {
		manager.cancel();
	}


	public void save() {
		System.out.println("iluyfuolyf");
		manager.save();
	}
	
	public void addInstallment(){
		manager.addInstallment();
	}


	public void viewInstallment() {
		manager.viewInstallment();
	}


	public void editInstallment() {
		manager.editInstallment();
	}


	public void deleteInstallment() {
		manager.deleteInstallment();
	}

}
