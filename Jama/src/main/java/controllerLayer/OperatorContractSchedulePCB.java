package controllerLayer;

import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;

import presentationLayer.lazyModel.SkeletalContractTableLDM;
import presentationLayer.lazyModel.OperatorContractScheduleLDM;

@Named("opContractSchedulePCB")
@ConversationScoped
public class OperatorContractSchedulePCB extends ContractTablePageController {
	private static final long serialVersionUID = 7438878243463382590L;

	@Inject
	private OperatorContractScheduleLDM lazyModel;


	public OperatorContractSchedulePCB() {}


	@Override
	public SkeletalContractTableLDM getLazyModel() {
		return lazyModel;
	}


	@Override
	protected String getProvenancePage() {
		return "agreementSchedule.xhtml?faces-redirect=true";
	}


	@Override
	public String viewAgreement() {
		return super.viewAgreement();
	}

}
