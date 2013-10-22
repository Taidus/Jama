package presentationLayer;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.ConversationScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.inject.Inject;
import javax.inject.Named;

import util.Messages;
import businessLayer.Agreement;
import businessLayer.Installment;
import annotations.TransferObj;

@Named("instDataPB")
@ConversationScoped
public class InstallmentDataPresentationBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@Inject @TransferObj private Installment installment;

	public InstallmentDataPresentationBean() {}
	
	public void validateAmount(FacesContext context, UIComponent component, Object value){
		Agreement agr = installment.getAgreement();
		List<Installment> installments = agr.getInstallments();
		
		float sum = installment.getWholeAmount();
		for(Installment i : installments){
			sum += i.getWholeAmount();
		}
		
		if(sum > agr.getWholeAmount()){
			throw new ValidatorException(Messages.getErrorMessage("err_installmentAmount"));
		}
		
	}

}
