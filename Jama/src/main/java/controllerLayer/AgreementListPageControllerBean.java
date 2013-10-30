package controllerLayer;

import java.io.Serializable;

import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;

import presentationLayer.LazyAgreementDataModel;
import presentationLayer.LazyAgreementListDataModel;
import businessLayer.Agreement;

@Named("agreementListPCB")
@ConversationScoped
public class AgreementListPageControllerBean extends AgreementTablePageController implements Serializable {
	private static final long serialVersionUID = 1L;

	@Inject
	private LazyAgreementListDataModel lazyModel;

	@Override
	public LazyAgreementDataModel getLazyModel() {
		return lazyModel;
	}
	
	public void printDel(){
		//TODO eliminare
		System.out.println("+++++++++++++++++++++++++++++++++++++");
	}

	public String viewAgreement() {
		close();
		print("Viewing");
		lazyModel.filterOnReload();
		contractManager.setSelectedContractd(lazyModel.getSelectedValue().getId());
		contractManager.setFiltersParamList((lazyModel.getFiltersAsParameterList()));
		System.out.println(lazyModel.getFiltersAsParameterList());
//		lazyModel.setFilterMaxDate(new Date());
		return contractManager.viewAgreement();
	}

	public String editAgreement() {
		print("Editing");
		lazyModel.filterOnReload();
		contractManager.setSelectedContractd(lazyModel.getSelectedValue().getId());
		return contractManager.editAgreement();
	}

	public void deleteAgreement() {
		print("Deleting");
		lazyModel.filterOnReload();
		contractManager.setSelectedContractd(lazyModel.getSelectedValue().getId());
		contractManager.deleteContract();
	}

	private void print(String action) {
		// TODO eliminare
		Agreement selectedValue = lazyModel.getSelectedValue();
		System.out.println("***\n" + action + " agreement with ID: " + selectedValue.getId() + ". Chief: " + selectedValue.getChief().getName()
				+ "; company: " + selectedValue.getCompany().getName() + "\n***");
	}
}
