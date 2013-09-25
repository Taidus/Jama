package pageControllerLayer;

import javax.enterprise.context.ConversationScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.inject.Named;
import javax.resource.spi.IllegalStateException;
import businessLayer.AbstractShareTable;

import java.io.Serializable;

@Named("shareTablePCB")
@ConversationScoped
public class ShareTablePageControllerBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private AbstractShareTable shareTable;

	public ShareTablePageControllerBean() {
	}

	public AbstractShareTable getShareTable() {
		return shareTable;
	}

	public void setShareTable(AbstractShareTable shareTable) {
		this.shareTable = shareTable;
	}

	public void validate(FacesContext context, UIComponent component,
			Object value) {

		try {
			shareTable.isValid();
		} catch (IllegalStateException e) {

			FacesMessage message = new FacesMessage(e.getMessage());
			message.setSeverity(FacesMessage.SEVERITY_ERROR);
			throw new ValidatorException(message);

		}

	}

}
