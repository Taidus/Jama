package presentationLayer;

import java.io.Serializable;
import java.util.Date;

import javax.enterprise.context.ConversationScoped;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.inject.Inject;
import javax.inject.Named;

import org.joda.money.Money;

import controllerLayer.ContractManagerBean;
import util.Messages;

@Named("contractDataPB")
@ConversationScoped
public class ContractDataPresentationBean implements Serializable {
	private static final long serialVersionUID = -4436429123720879442L;

	@Inject
	private ContractManagerBean manager;


	public ContractDataPresentationBean() {}


	public void validateReservedAmount(FacesContext context, UIComponent component, Object value) {
		String[] params = { (String) component.getAttributes().get("label") };
		Money reservedAmount = (Money) value;
		try {
			if (reservedAmount.isGreaterThan(manager.getContract().getTurnOver())) {
				throw new ValidatorException(Messages.getErrorMessage("err_agreementReserved"));
			}
			if (reservedAmount.isNegative()) {
				throw new ValidatorException(Messages.getErrorMessage("err_negativeValue", params));
			}
		} catch (ClassCastException e) {
			throw new ValidatorException(Messages.getErrorMessage("err_invalidValue", params));
		}
	}


	public void validateSpentAmount(FacesContext context, UIComponent component, Object value) {
		String[] params = { (String) component.getAttributes().get("label") };

		try {
			Money spentAmount = (Money) value;
			Money reservedAmount = (Money) ((UIInput) component.findComponent("reservedAmount")).getValue();
			System.out.println("spentAmount: " + spentAmount + ", reserved Amount: " + reservedAmount);

			if (spentAmount.isGreaterThan(reservedAmount)) {
				throw new ValidatorException(Messages.getErrorMessage("err_agreementSpent"));
			}
			if (spentAmount.isNegative()) {
				throw new ValidatorException(Messages.getErrorMessage("err_negativeValue", params));
			}
		} catch (ClassCastException e) {
			throw new ValidatorException(Messages.getErrorMessage("err_invalidValue", params));
		}
	}


	public void validateDeadlineDate(FacesContext context, UIComponent component, Object value) {
		String[] params = { Messages.getString("deadlineDate")};

		try {
			Date deadline = (Date) value;
			Date begin = (Date) ((UIInput) component.findComponent("beginDate")).getValue();
			System.out.println("deadline: " + deadline + ", begin: " + begin);

			if (deadline.before(begin)) {
				throw new ValidatorException(Messages.getErrorMessage("err_invalidDeadline"));
			}
		} catch (ClassCastException e) {
			throw new ValidatorException(Messages.getErrorMessage("err_invalidValue", params));
		}
	}

}
