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
public class ChiefConverter implements Converter {

	@EJB
	private ChiefScientistDaoBean chiefDaoBean;

	public ChiefConverter() {}

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		return chiefDaoBean.getBySerial(value);
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (null == value) {
			return "";
		}
		return ((ChiefScientist) value).getSerialNumber();
	}

}
