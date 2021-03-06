package controllerLayer;

import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;

import presentationLayer.lazyModel.SkeletalContractTableLDM;
import presentationLayer.lazyModel.OperatorContractListLDM;

@Named("opContractListPCB")
@ConversationScoped
public class OperatorContractListPCB extends ContractTablePageController {
	private static final long serialVersionUID = 8448524183572350875L;

	@Inject
	private OperatorContractListLDM lazyModel;


	public OperatorContractListPCB() {}


	@Override
	public SkeletalContractTableLDM getLazyModel() {
		return lazyModel;
	}


	@Override
	protected String getProvenancePage() {
		return "agreementList.xhtml?faces-redirect=true";
	}


	@Override
	public String viewAgreement() {
		return super.viewAgreement();
	}


	@Override
	public String editAgreement() {
		return super.editAgreement();
	}


	@Override
	public void deleteAgreement() {
		super.deleteAgreement();
	}

}
