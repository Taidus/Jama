package controllerLayer;

import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;

import presentationLayer.lazyModel.ContractTableLazyDataModel;
import presentationLayer.lazyModel.OperatorContractScheduleLDM;

@Named("opContractSchedulePCB")
@ConversationScoped
public class OperatorContractSchedulePCB extends ContractTablePageController {
	private static final long serialVersionUID = 7438878243463382590L;

	@Inject
	private OperatorContractScheduleLDM lazyModel;


	public OperatorContractSchedulePCB() {}


	@Override
	public ContractTableLazyDataModel getLazyModel() {
		return lazyModel;
	}


	@Override
	public String viewAgreement() {
		return super.viewAgreement();
	}

}
