package presentationLayer;

import java.io.Serializable;

import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.event.FlowEvent;

import pageControllerLayer.AgreementManagerBean;

@Named("agreementWizardPB")
@ConversationScoped
public class AgreementWizardPresentationBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String currentTabId = defaultTab;
	private static final String defaultTab = "tabDati";

	@Inject
	AgreementManagerBean manager;

	public AgreementWizardPresentationBean() {
	}

	public String getCurrentTabId() {
		return currentTabId;
	}

	public String handleFlow(FlowEvent event) {

		currentTabId = event.getNewStep();
		return event.getNewStep();

	}

	public String cancel() {
		currentTabId = defaultTab;
		manager.cancel();
		return "home";

	}

	public String save() {
		currentTabId = defaultTab;
		manager.save();
		return "home";

	}

}
