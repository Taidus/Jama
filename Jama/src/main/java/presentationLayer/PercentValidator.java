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

	public PercentValidator() {}


	@Override
	public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
		boolean invalid = false;
		try {
			Percent p = (Percent) value;
			if (p.lessThan(Percent.ZERO) || p.greaterThan(Percent.ONE)) {
				invalid = true;
			}
		} catch (ClassCastException e) {
			invalid = true;
		}

		if (invalid) {
			String[] params = { (String) component.getAttributes().get("label") };
			throw new ValidatorException(Messages.getErrorMessage("err_invalidValue", params));
		}
	}

}
