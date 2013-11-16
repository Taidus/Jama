package presentationLayer;

import java.math.BigDecimal;
import java.math.RoundingMode;

import javax.enterprise.context.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;
import javax.inject.Named;

import util.Messages;
import util.Percent;

@Named
@RequestScoped
@FacesConverter(forClass = Percent.class)
public class PercentConverter implements Converter {
	// oltre a svolgere le normali operazioni di conversione,
	// normalizza/"denormalizza" i dati: a video compariranno valori tra 0 e
	// 100,
	// nel modello tra 0 e 1

	public PercentConverter() {}


	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		BigDecimal d = null;
		boolean invalid = false;
		try {
			d = new BigDecimal(value).divide(BigDecimal.valueOf(100));
		} catch (NumberFormatException e) {
			invalid = true;
		}

		if (invalid || d.compareTo(BigDecimal.ONE) > 0) {
			String[] params = { (String) component.getAttributes().get("label") };
			throw new ConverterException(Messages.getErrorMessage("err_invalidValue", params));
		}
		else{
			return new Percent(d);
		}
	}


	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (null == value) {
			return "";
		}
		return ((Percent) value).getValue().multiply(BigDecimal.valueOf(100)).setScale(2, Percent.DEFAULT_ROUNDING).toPlainString();
	}

}
