package presentationLayer;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.ConversationScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.inject.Inject;
import javax.inject.Named;

import org.joda.money.Money;

import util.Messages;
import businessLayer.Agreement;
import businessLayer.AgreementInstallment;
import businessLayer.Contract;
import businessLayer.Installment;
import annotations.TransferObj;

@Named("instDataPB")
@ConversationScoped
public class InstallmentDataPresentationBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@Inject
	@TransferObj
	private Installment installment;

	public InstallmentDataPresentationBean() {
	}

	public void validateAmount(FacesContext context, UIComponent component, Object value) {
		Contract c = installment.getContract();
		List<Installment> installments = c.getInstallments();

		Money sum = installment.getWholeAmount();
		for (Installment i : installments) {
			sum.plus(i.getWholeAmount());
		}

		if (sum.isGreaterThan(c.getWholeAmount())) {
			throw new ValidatorException(Messages.getErrorMessage("err_installmentAmount"));
		}

	}

}
