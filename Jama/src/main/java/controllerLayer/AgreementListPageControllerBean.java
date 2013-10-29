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

	public String viewAgreement() {
		print("Viewing");
		lazyModel.filterOnReload();
		agrManager.setSelectedContractd(lazyModel.getSelectedValue().getId());
		return agrManager.viewAgreement();
	}

	public String editAgreement() {
		print("Editing");
		lazyModel.filterOnReload();
		agrManager.setSelectedContractd(lazyModel.getSelectedValue().getId());
		return agrManager.editAgreement();
	}

	public void deleteAgreement() {
		print("Deleting");
		lazyModel.filterOnReload();
		agrManager.setSelectedContractd(lazyModel.getSelectedValue().getId());
		agrManager.deleteContract();
	}

	private void print(String action) {
		// TODO eliminare
		Agreement selectedValue = lazyModel.getSelectedValue();
		System.out.println("***\n" + action + " agreement with ID: " + selectedValue.getId() + ". Chief: " + selectedValue.getChief().getName()
				+ "; company: " + selectedValue.getCompany().getName() + "\n***");
	}
}
