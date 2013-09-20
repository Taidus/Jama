package presentationLayer;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

@Named("agreementWizPB")
@SessionScoped
public class AgreementWizardPresentationBean implements Serializable{
	/**
	 */
	private static final long serialVersionUID = 1L;
	
	private String currentChief;
	
	
	public String getCurrentChief() {
		return currentChief;
	}

	public void setCurrentChief(String currentChief) {
		this.currentChief = currentChief;
	}
	
	public AgreementWizardPresentationBean() {}

}
