package pageControllerLayer;

import javax.enterprise.context.ConversationScoped;
import javax.inject.Named;

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
	
	
	
	

}
