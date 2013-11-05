package presentationLayer;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Named;

import businessLayer.Department;
import daoLayer.DepartmentDaoBean;

@Named
@RequestScoped
public class DepartmentConverter implements Converter {

	@EJB
	private DepartmentDaoBean depthDaoBean;

	public DepartmentConverter() {}

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		int id = Integer.parseInt(value);
		return depthDaoBean.getById(id);
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (null == value) {
			return "";
		}
		return String.valueOf(((Department) value).getId());
	}
}
