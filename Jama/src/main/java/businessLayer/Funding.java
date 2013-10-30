package businessLayer;

import java.io.Serializable;

import javax.persistence.Entity;

import util.Percent;

@Entity
public class Funding extends Contract implements Serializable {

	private static final long serialVersionUID = 1L;

	public Funding() {
		super();
		IVA_amount = Percent.ZERO;
	}

	@Override
	public String toString() {
		return "Funding [id=" + id + ", title=" + title + ", protocolNumber="
				+ protocolNumber + ", chief=" + chief + ", contactPerson="
				+ contactPerson + ", company=" + company + ", department="
				+ department + ", CIA_projectNumber=" + CIA_projectNumber
				+ ", inventoryNumber=" + inventoryNumber + ", spentAmount="
				+ spentAmount + ", reservedAmount=" + reservedAmount
				+ ", IVA_amount=" + IVA_amount + ", wholeTaxableAmount="
				+ wholeTaxableAmount + ", approvalDate=" + approvalDate
				+ ", beginDate=" + beginDate + ", deadlineDate=" + deadlineDate
				+ ", note=" + note + ", installments=" + installments
				+ ", attachments=" + attachments + ", shareTable=" + shareTable
				+ "]";
	}
	
	

}
