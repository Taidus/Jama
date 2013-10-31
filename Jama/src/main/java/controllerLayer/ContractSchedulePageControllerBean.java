package controllerLayer;

import java.io.Serializable;

import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;

import presentationLayer.LazyContractScheduleDataModel;

@Named("contractSchedulePCB")
@ConversationScoped
public class ContractSchedulePageControllerBean extends ContractTablePageController implements Serializable {
	private static final long serialVersionUID = 1L;

	@Inject
	private LazyContractScheduleDataModel lazyModel;

	@Override
	public LazyContractScheduleDataModel getLazyModel() {
		return lazyModel;
	}

	@Override
	protected void closeModel() {
		lazyModel.closeService();
	}

	public String viewAgreement() {
		contractManager.setSelectedContractd(lazyModel.getSelectedValue().getId());
		contractManager.setFiltersParamList(lazyModel.getFiltersAsParameterList());
		close();
		return contractManager.viewContract();
	}

}
