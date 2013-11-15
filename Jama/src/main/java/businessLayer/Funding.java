package businessLayer;

import java.io.Serializable;

import javax.persistence.Entity;

import util.Messages;
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
	
	
	@Override
	public ContractHelper getHelper() {
		return new Helper();
	}
	
	public static class Helper implements ContractHelper{
		@Override
		public Installment getNewInstallment() {
			return new FundingInstallment();
		}

		@Override
		public boolean renderIvaComponents() {
			return false;
		}

		@Override
		public boolean renderType() {
			return false;
		}

		@Override
		public boolean renderPersonnelQuotes() {
			return false;
		}

		@Override
		public String getName() {
			return Messages.getString("funding");
		}

		@Override
		public boolean renderShareTable() {
			return false;
		}
	}
	
	

}
