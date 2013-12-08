package presentationLayer;

import javax.enterprise.context.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Named;

import usersManagement.RolePermission;

@Named
@RequestScoped
public class RoleMenuConverter implements Converter {

	public RoleMenuConverter() {}


	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		try {
			return RolePermission.valueOf(value);
		} catch (IllegalArgumentException e) {
			for (RolePermission r : RolePermission.values()) {
				if (r.getDisplayString().equalsIgnoreCase(value)) {
					return r;
				}
			}
		} catch (NullPointerException e) {}
		return null;
	}


	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (null == value) {
			return "";
		}
		return ((RolePermission) value).getDisplayString();
	}

}