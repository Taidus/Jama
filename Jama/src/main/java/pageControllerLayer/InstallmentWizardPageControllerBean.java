package pageControllerLayer;

import java.io.Serializable;

import javax.ejb.EJB;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;

import daoLayer.InstallmentDaoBean;
import businessLayer.Installment;
import businessLayer.InstallmentShareTable;

@Named("installmentWizardPCB")
@ConversationScoped
public class InstallmentWizardPageControllerBean implements Serializable {

	private static final long serialVersionUID = 1L;
	@Inject
	private Conversation conversation;
	@EJB
	private InstallmentDaoBean installmentDao;
	private Installment installment;
	private InstallmentShareTable installmentShareTable;

	public InstallmentWizardPageControllerBean() {
		installment = new Installment();
		conversation.begin();
	}

	public Conversation getConversation() {
		return conversation;
	}

	public Installment getInstallment() {
		return installment;
	}

	public InstallmentShareTable getInstallmentShareTable() {
		return installmentShareTable;
	}

	public void setInstallmentShareTable(
			InstallmentShareTable installmentShareTable) {
		this.installmentShareTable = installmentShareTable;
	}

	public void save() {
		installmentDao.create(installment);
		conversation.end();

	}

}
