package pageControllerLayer;

import java.io.Serializable;

import javax.enterprise.context.ConversationScoped;
import javax.inject.Named;

import org.primefaces.event.FlowEvent;

@Named("agreementWizardPBC")
@ConversationScoped
public class AgreementWizardPageBeanController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String currentTabId = "tabDati";

	public AgreementWizardPageBeanController() {
	}

	public String getCurrentTabId() {
		return currentTabId;
	}

	public String handleFlow(FlowEvent event) {

		currentTabId = event.getNewStep();
		return event.getNewStep();

	}

}
