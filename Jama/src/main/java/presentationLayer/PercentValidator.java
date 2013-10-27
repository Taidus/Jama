package presentationLayer;

import javax.enterprise.context.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import javax.inject.Named;

import util.Messages;
import util.Percent;

@Named
@RequestScoped
public class PercentValidator implements Validator {

	public PercentValidator() {
	}

	@Override
	public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
		try{
			Percent p = (Percent) value;
			if(p.lessThan(Percent.ZERO) || p.greaterThan(Percent.ONE)){
				throw new ValidatorException(Messages.getErrorMessage("err_invalidValue"));
			}
		}catch(ClassCastException e){
			System.err.println(e);
			throw new ValidatorException(Messages.getErrorMessage("err_invalidValue"));
		}
	}

}
