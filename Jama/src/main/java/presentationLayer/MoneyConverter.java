package presentationLayer;

import java.math.BigDecimal;

import javax.enterprise.context.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Named;

import org.joda.money.Money;

import util.Config;

@Named
@RequestScoped
public class MoneyConverter implements Converter {
	// il valore viene convertito in e da numeri decimali (niente valuta o
	// altro)

	public MoneyConverter() {}

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		return Money.of(Config.currency, new BigDecimal(value).setScale(2));
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (null == value) {
			return "";
		}
		
		boolean plain = (boolean) component.getAttributes().get("plain");
		if (plain)
			return ((Money) value).getAmount().toPlainString();
		else {
			return ((Money) value).toString();
		}
	}

}
