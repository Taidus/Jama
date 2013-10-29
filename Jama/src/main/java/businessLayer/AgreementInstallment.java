package businessLayer;

import java.io.Serializable;
import java.math.RoundingMode;
import java.util.Date;
import java.util.List;

import javax.faces.validator.ValidatorException;
import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.joda.money.Money;

import util.Config;
import util.Messages;
import util.Percent;

/**
 * Entity implementation class for Entity: Installment
 * 
 */

@Entity
public class AgreementInstallment extends Installment implements
		Serializable {

	private static final long serialVersionUID = 1L;

	

	@Min(0)
	private int ivaVoucherNumber;

	

	public AgreementInstallment() {
		this.IVA_amount = Percent.ZERO;
		this.wholeTaxableAmount = Money.zero(Config.currency);
	}

	

	public void setIVA_amount(Percent iVA_amount) {
		IVA_amount = iVA_amount;
	}

	public int getIvaVoucherNumber() {
		return ivaVoucherNumber;
	}

	public void setIvaVoucherNumber(int ivaVoucherNumber) {
		this.ivaVoucherNumber = ivaVoucherNumber;
	}


	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public void copy(Installment copy) {

		if (copy instanceof AgreementInstallment) {

			super.copy(copy);

			AgreementInstallment agrCopy = (AgreementInstallment) copy;

			this.ivaVoucherNumber = agrCopy.ivaVoucherNumber;
			this.wholeTaxableAmount = agrCopy.wholeTaxableAmount;
			this.IVA_amount = agrCopy.IVA_amount;


		} else
			throw new IllegalArgumentException("Installment type mismatch");

	}
	
	

	@Override
	public String toString() {
		return "Installment [id=" + id + ", date=" + date + ", IVA_amount="
				+ IVA_amount + ", wholeTaxableAmount=" + wholeTaxableAmount
				+ ", voucherNumber=" + voucherNumber + ", voucherDate="
				+ voucherDate + ", ivaVoucherNumber=" + ivaVoucherNumber
				+ ", pendingNumber=" + pendingNumber + ", invoiceNumber="
				+ invoiceNumber + ", invoiceDate=" + invoiceDate
				+ ", paidInvoice=" + paidInvoice + ", reportRequired="
				+ reportRequired + ", note=" + note + ", agreement id="
				+ contract.getId() + ", shareTable=" + shareTable + "]";
	}

}
