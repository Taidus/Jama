package presentationLayer;

import java.util.Date;

import javax.enterprise.context.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.inject.Named;

import util.Messages;

@Named("dateFromLong")
@RequestScoped
public class DateFromLongConverter implements Converter {

	public DateFromLongConverter() {}

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		try {
			return new Date(Long.parseLong(value));
		} catch (NumberFormatException e) {
			throw new ConverterException(Messages.getErrorMessage("Cannot convert " + value + "into a date"));
			//XXX tenere così o mettere l'errore nel file?
		}
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		try{
			if(null == value){
				return "0";
			}
			return String.valueOf(((Date) value).getTime());
		}
		catch(ClassCastException e){
			throw new ConverterException(Messages.getErrorMessage(value + " is not a date"));
			//XXX tenere così o mettere l'errore nel file?
		}
	}

}
