package presentationLayer;

import javax.enterprise.context.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import javax.inject.Named;

import org.joda.money.Money;

import util.Messages;

@Named
@RequestScoped
public class MoneyValidator implements Validator {

	public MoneyValidator() {}

	@Override
	public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
		//TODO eliminare stampe
		System.out.println("Validator chiamato");
		try{
			if(((Money) value).isNegative()){
				throw new ValidatorException(Messages.getErrorMessage("err_negativeAmount"));
			}
		}catch(ClassCastException e){
			throw new ValidatorException(Messages.getErrorMessage("err_invalidAmount"));
		}
	}

}
