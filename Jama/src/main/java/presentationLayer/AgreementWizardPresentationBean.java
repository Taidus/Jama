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
	private static final String defaultTab = "tabDati";
	private String currentTabId;

	@Inject
	AgreementManagerBean manager;

	public AgreementWizardPresentationBean() {
		currentTabId = defaultTab;
	}

	public String getCurrentTabId() {
		return currentTabId;
	}

	public boolean isLastStep() {
		if (currentTabId.equals("tabEnd")) {
			return true;
		} else {
			return false;
		}
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
