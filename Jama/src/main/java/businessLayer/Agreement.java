package businessLayer;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

import util.Messages;
import util.Percent;

@Entity
@NamedQueries({ @NamedQuery(name = "Agreement.findAll", query = "SELECT a FROM Agreement a ORDER BY a.approvalDate") })
public class Agreement extends Contract implements Serializable {
	private static final long serialVersionUID = 1L;

	@Enumerated(EnumType.STRING)
	private AgreementType type;

	public Agreement() {
		super();

	}

	@Override
	public String toString() {
		return "Agreement [id=" + id + ", title=" + title + ", type=" + type + ", chief=" + chief
				+ ", contactPerson=" + contactPerson + ", company=" + company
				+ ", department=" + department + ", CIA_projectNumber="
				+ CIA_projectNumber + ", inventoryNumber=" + inventoryNumber
				+ ", shareTable=" + shareTable + ", IVA_amount=" + IVA_amount
				+ ", wholeTaxableAmount=" + wholeTaxableAmount
				+ ", installments=" + installments + ", approvalDate="
				+ approvalDate + ", beginDate=" + beginDate + ", deadlineDate="
				+ deadlineDate + ", note=" + note + "]";
	}

	public AgreementType getType() {
		return type;
	}

	public void setType(AgreementType type) {
		this.type = type;
	}

	public void setIVA_amount(Percent iVA_amount) {
		IVA_amount = iVA_amount;
	}
	
	
	
	private static class Helper implements ContractHelper{
		
		@Override
		public Installment getNewInstallment() {
			return new AgreementInstallment();
		}

		@Override
		public boolean renderIvaComponents() {
			return true;
		}

		@Override
		public boolean renderType() {
			return true;
		}

		@Override
		public boolean renderPersonnelQuotes() {
			return true;
		}

		@Override
		public String getName() {
			return Messages.getString("agreement");
		}

		@Override
		public boolean renderShareTable() {
			return true;
		}
	}



	@Override
	public ContractHelper getHelper() {
		return new Helper();
	}

	

}
