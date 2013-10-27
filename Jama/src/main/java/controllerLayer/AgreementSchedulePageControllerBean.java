package controllerLayer;

import java.io.Serializable;

import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;

import presentationLayer.LazyAgreementScheduleDataModel;

@Named("agreementSchedulePCB")
@ConversationScoped
public class AgreementSchedulePageControllerBean extends AgreementTablePageController implements Serializable {
	private static final long serialVersionUID = 1L;

	@Inject
	private LazyAgreementScheduleDataModel lazyModel;

	@Override
	public LazyAgreementScheduleDataModel getLazyModel() {
		return lazyModel;
	}

	public String viewAgreement() {
		lazyModel.filterOnReload();
		agrManager.setSelectedContractd(lazyModel.getSelectedValue().getId());
		return agrManager.viewAgreement();
	}

}
