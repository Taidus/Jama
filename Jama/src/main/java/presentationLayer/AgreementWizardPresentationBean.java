package presentationLayer;

import java.io.Serializable;

import javax.enterprise.context.ConversationScoped;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.inject.Inject;
import javax.inject.Named;

import org.joda.money.Money;
import org.primefaces.event.FlowEvent;

import util.Messages;
import controllerLayer.AgreementManagerBean;

@Named("agreementWizardPB")
@ConversationScoped
public class AgreementWizardPresentationBean implements Serializable {
	//TODO eliminare stampe e commenti
	//TODO inserire controllo sulle date (scadenza > inizio etc)
	private static final long serialVersionUID = 1L;
	private static final String defaultTab = "tabDati";
	private String currentTabId;

	@Inject
	private AgreementManagerBean manager;

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

	// public void validateReserved(ValueChangeEvent event) {
	//
	//
	// float wholeTaxableAmount =
	// Float.parseFloat(event.getNewValue().toString());
	// float wholeAmount = wholeTaxableAmount * (100 +
	// agreement.getIVA_amount()) / 100;
	//
	// System.out.println("evento lanciato=================");
	// UIComponent errorComponent =
	// event.getComponent().findComponent("spentAmount");
	//
	//
	// if ((agreement.getReservedAmount()> agreement.tu)
	// || (agreement.getReservedAmount() > agreement.getTurnOver())) {
	//
	// System.out.println("dentro ifffff");
	//
	//
	// FacesMessage message = new FacesMessage("errore prova");
	// message.setSeverity(FacesMessage.SEVERITY_ERROR);
	// FacesContext context = FacesContext.getCurrentInstance();
	// context.addMessage(errorComponent.getClientId(), message);
	// //context.renderResponse();
	//
	//
	// }
	//
	// }

	public void validateReservedAmount(FacesContext context, UIComponent component, Object value) {
		Money reservedAmount = (Money) value;
		try {
			if (reservedAmount.isGreaterThan(manager.getAgreement().getTurnOver())) {
				throw new ValidatorException(Messages.getErrorMessage("err_agreementReserved"));
			}
			if (reservedAmount.isNegative()) {
				throw new ValidatorException(Messages.getErrorMessage("err_negativeAmount"));
			}
		} catch (ClassCastException e) {
			throw new ValidatorException(Messages.getErrorMessage("err_invalidAmount"));
		}
	}

	public void validateSpentAmount(FacesContext context, UIComponent component, Object value) {
		System.out.println("Validate spent amount");

		try {
			Money spentAmount = (Money) value;
			Money reservedAmount = (Money) ((UIInput) component.findComponent("reservedAmount")).getValue();
			System.out.println("spentAmount: " + spentAmount + ", reserved Amount: " + reservedAmount);

			if (spentAmount.isGreaterThan(reservedAmount)) {
				// System.out.println("dentro if");
				throw new ValidatorException(Messages.getErrorMessage("err_agreementSpent"));
			}
			if (spentAmount.isNegative()) {
				throw new ValidatorException(Messages.getErrorMessage("err_negativeAmount"));
			}
		} catch (ClassCastException e) {
			throw new ValidatorException(Messages.getErrorMessage("err_invalidAmount"));
		}

	}
	//
	// public void validateReservedAmount(FacesContext context, UIComponent
	// component, Object value){
	//
	//
	// float reservedAmount = Float.parseFloat(value.toString());
	// UIOutput output = (UIOutput) component.findComponent("amount");
	// System.out.println(output.getValue());
	// float wholeAmount = Float.parseFloat(output.getValue().toString());
	// System.out.println("reservedAmount: "+reservedAmount+", wholeAmount: "+wholeAmount
	// );
	//
	// if(reservedAmount > wholeAmount){
	// System.out.println("dentro if");
	// FacesMessage message = new FacesMessage("errore prova");
	// message.setSeverity(FacesMessage.SEVERITY_ERROR);
	// throw new ValidatorException(message);
	//
	// }
	//
	// }

}
