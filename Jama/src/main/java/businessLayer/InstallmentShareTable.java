package businessLayer;

import businessLayer.AbstractShareTable;

import java.io.Serializable;

import javax.persistence.*;
import javax.resource.spi.IllegalStateException;

/**
 * Entity implementation class for Entity: InvoiceShareTable
 * 
 */
@Entity
public class InstallmentShareTable extends AbstractShareTable implements
		Serializable {

	private static final long serialVersionUID = 1L;
	
	@OneToOne(mappedBy = "shareTable")
	private Installment installment;

	public InstallmentShareTable() {
		super();
	}

	public Installment getInstallment() {
		return installment;
	}

	public void setInstallment(Installment installment) {
		this.installment = installment;
	}

	@Override
	public boolean validate() throws IllegalStateException {
		if (!areGoodsSharesConsistent()) {
			throw new IllegalStateException(
					"Le quote dei Beni e Servizi non sono corrette");
		}
		if (!arePersonnelSharesConsistent()) {
			throw new IllegalStateException(
					"Le quote del Personale non sono corrette");
		}
		if (areMainValuesConsistent()) {
			throw new IllegalStateException("Le quote non sono corrette");
		}
		return true;
	}

}
