package pageControllerLayer;

import java.io.Serializable;

import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.event.FlowEvent;

@Named("agreementWizardPCB")
@ConversationScoped
public class AgreementWizardPageBeanController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String currentTabId = defaultTab;
	private static final String defaultTab = "tabDati";
	
	@Inject AgreementManagerBean manager;

	public AgreementWizardPageBeanController() {
	}

	public String getCurrentTabId() {
		return currentTabId;
	}

	public String handleFlow(FlowEvent event) {

		currentTabId = event.getNewStep();
		return event.getNewStep();

	}
	
	public String cancel(){
		currentTabId=defaultTab;
		return manager.cancel();
	}
	
	public String save(){
		currentTabId=defaultTab;
		return manager.save();
	}

}
