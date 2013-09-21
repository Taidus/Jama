package presentationLayer;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Named;

@Named
public class ChiefConverter implements Converter {

	public ChiefConverter() {}

	@Override
	public Object getAsObject(FacesContext context, UIComponent component,
			String value) {
		
		return null;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component,
			Object value) {
		// TODO Auto-generated method stub
		return null;
	}

}
