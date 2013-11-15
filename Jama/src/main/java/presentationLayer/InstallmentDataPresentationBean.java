package presentationLayer;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.enterprise.context.ConversationScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.inject.Inject;
import javax.inject.Named;

import org.joda.money.Money;

import util.Messages;
import annotations.TransferObj;
import businessLayer.Contract;
import businessLayer.Installment;

@Named("instDataPB")
@ConversationScoped
public class InstallmentDataPresentationBean implements Serializable {
	private static final long serialVersionUID = 1L;
	@Inject
	@TransferObj
	private Installment installment;


	public InstallmentDataPresentationBean() {}


	public void validateAmount(FacesContext context, UIComponent component, Object value) {
		Contract c = installment.getContract();
		List<Installment> installments = c.getInstallments();
		Money sum = installment.getWholeAmount();
		for (Installment i : installments) {
			sum = sum.plus(i.getWholeAmount());
		}
		if (sum.isGreaterThan(c.getWholeAmount())) {
			throw new ValidatorException(Messages.getErrorMessage("err_installmentAmount"));
		}
	}
	
	public void validateDeadlineDate(FacesContext context, UIComponent component, Object value) {

		try {
			Date deadline = (Date) value;
			Date begin = installment.getContract().getBeginDate();
			Date end = installment.getContract().getDeadlineDate();

			if (deadline.before(begin) || deadline.after(end)) {
				throw new ValidatorException(Messages.getErrorMessage("err_instInvalidDeadline"));
			}
		} catch (ClassCastException e) {
			String[] params = { (String) component.getAttributes().get("label")};
			throw new ValidatorException(Messages.getErrorMessage("err_invalidValue", params));
		}
	}
	
	
}
