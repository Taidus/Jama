package controllerLayer;

import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;

import presentationLayer.lazyModel.ChiefContractListLDM;
import presentationLayer.lazyModel.ContractTableLazyDataModel;

@Named("chiefContractListPCB")
@ConversationScoped
public class ChiefContractListPCB extends ContractTablePageController {
	private static final long serialVersionUID = 3219550645747487234L;

	@Inject
	private ChiefContractListLDM lazyModel;


	public ChiefContractListPCB() {}


	@Override
	public ContractTableLazyDataModel getLazyModel() {
		return lazyModel;
	}


	@Override
	protected String getProvenancePage() {
		return "chiefContractList";
	}


	@Override
	public String viewAgreement() {
		return super.viewAgreement();
	}

}
