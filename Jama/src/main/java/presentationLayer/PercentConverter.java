package presentationLayer;

import java.math.BigDecimal;
import java.math.RoundingMode;

import javax.enterprise.context.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.inject.Named;

import util.Messages;
import util.Percent;

@Named
@RequestScoped
public class PercentConverter implements Converter {
	// oltre a svolgere le normali operazioni di conversione,
	// normalizza/denormalizza i dati: a video compariranno valori tra 0 e 100,
	// nel modello tra 0 e 1

	public PercentConverter() {}

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		try{
			return new Percent(new BigDecimal(value).divide(BigDecimal.valueOf(100)).setScale(2, RoundingMode.HALF_EVEN));
		}catch(IllegalArgumentException e){
			throw new ConverterException(Messages.getErrorMessage("err_invalidValue"));
		}
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if(null == value){
			return "";
		}
		return ((Percent) value).getValue().multiply(BigDecimal.valueOf(100)).setScale(2, RoundingMode.HALF_EVEN).toPlainString();
	}

}
