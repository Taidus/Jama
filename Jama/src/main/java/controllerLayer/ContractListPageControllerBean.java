package controllerLayer;

import java.io.Serializable;

import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;

import presentationLayer.OperatorContractListLDM;
import presentationLayer.ContractTableLazyDataModel;
import businessLayer.Contract;

@Named("contractListPCB")
@ConversationScoped
public class ContractListPageControllerBean extends ContractTablePageController implements Serializable {
	private static final long serialVersionUID = 1L;

	@Inject
	private OperatorContractListLDM lazyModel;

	@Override
	public ContractTableLazyDataModel getLazyModel() {
		return lazyModel;
	}

	@Override
	protected void closeModel() {
		lazyModel.closePager();
	}

	public String viewAgreement() {
		print("Viewing");
		contractManager.setSelectedContractd(lazyModel.getSelectedValue().getId());
		contractManager.setFiltersParamList((lazyModel.getFiltersAsParameterList()));
		System.out.println(lazyModel.getFiltersAsParameterList());
		close();
		return contractManager.viewContract();
	}

	public String editAgreement() {
		print("Editing");
		contractManager.setSelectedContractd(lazyModel.getSelectedValue().getId());
		contractManager.setFiltersParamList((lazyModel.getFiltersAsParameterList()));
		System.out.println(lazyModel.getFiltersAsParameterList());
		close();
		return contractManager.editContract();
	}

	public void deleteAgreement() {
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
