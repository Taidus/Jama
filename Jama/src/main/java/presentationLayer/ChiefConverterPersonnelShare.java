package presentationLayer;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Named;

import businessLayer.ChiefScientist;
import daoLayer.ChiefScientistDaoBean;

@Named
@RequestScoped
public class ChiefConverterPersonnelShare implements Converter {

	@EJB
	private ChiefScientistDaoBean chiefDaoBean;

	public ChiefConverterPersonnelShare() {
	}

	@Override
	public Object getAsObject(FacesContext context, UIComponent component,
			String value) {
		int id = Integer.parseInt(value);
		return chiefDaoBean.getById(id);
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component,
			Object value) {
		if (value == null) {
			return "";
		}
		ChiefScientist c = (ChiefScientist) value;
		return String.valueOf(c.getSurname() + " " + c.getName());
	}

}