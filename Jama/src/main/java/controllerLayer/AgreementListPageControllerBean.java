package controllerLayer;

import java.io.Serializable;

import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;

import presentationLayer.LazyAgreementListDataModel;
import presentationLayer.LazyContractDataModel;
import businessLayer.Contract;

@Named("agreementListPCB")
@ConversationScoped
public class AgreementListPageControllerBean extends AgreementTablePageController implements Serializable {
	private static final long serialVersionUID = 1L;

	@Inject
	private LazyAgreementListDataModel lazyModel;

	@Override
	public LazyContractDataModel getLazyModel() {
		return lazyModel;
	}
	
	@Override
	protected void closeModel() {
		lazyModel.closeService();
	}
	
	public void printDel(){
		//TODO eliminare
		System.out.println("+++++++++++++++++++++++++++++++++++++");
	}

	public String viewAgreement() {
		close();
		print("Viewing");
		contractManager.setSelectedContractd(lazyModel.getSelectedValue().getId());
		contractManager.setFiltersParamList((lazyModel.getFiltersAsParameterList()));
		System.out.println(lazyModel.getFiltersAsParameterList());
//		lazyModel.setFilterMaxDate(new Date());
		return contractManager.viewContract();
	}

	public String editAgreement() {
		close();
		print("Editing");
		contractManager.setSelectedContractd(lazyModel.getSelectedValue().getId());
		contractManager.setFiltersParamList((lazyModel.getFiltersAsParameterList()));
		System.out.println(lazyModel.getFiltersAsParameterList());
		return contractManager.editContract();
	}

	public void deleteAgreement() {
		close();
		print("Deleting");
		contractManager.setSelectedContractd(lazyModel.getSelectedValue().getId());
		contractManager.deleteContract();
	}

	private void print(String action) {
		// TODO eliminare
		Contract selectedValue = lazyModel.getSelectedValue();
		System.out.println("***\n" + action + " contract with ID: " + selectedValue.getId() + ". Chief: " + selectedValue.getChief().getName()
				+ "; company: " + selectedValue.getCompany().getName() + "\n***");
	}
}
