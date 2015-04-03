package controllerLayer;

import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;

import presentationLayer.AgreementEditPresentationBean;
import presentationLayer.lazyModel.ChiefContractListLDM;
import presentationLayer.lazyModel.SkeletalContractTableLDM;

@Named("chiefContractListPCB")
@ConversationScoped
public class ChiefContractListPCB extends ContractTablePageController {
	private static final long serialVersionUID = 3219550645747487234L;

	@Inject
	private ChiefContractListLDM lazyModel;
	
	@Inject private AgreementEditPresentationBean editPB;


	public ChiefContractListPCB() {}


	@Override
	public SkeletalContractTableLDM getLazyModel() {
		return lazyModel;
	}


	@Override
	protected String getProvenancePage() {
		return "chiefContractList.xhtml?faces-redirect=true";
	}


	@Override
	public String viewAgreement() {
		editPB.setProfessorModeOn(true);
		return super.viewAgreement();
	}

}
