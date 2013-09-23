package businessLayer;

import businessLayer.AbstractShareTable;

import javax.persistence.*;
import javax.resource.spi.IllegalStateException;

@Entity
public class InstallmentShareTable extends AbstractShareTable{

	
	@OneToOne(mappedBy = "shareTable")
	private Installment installment;

	public InstallmentShareTable() {
		initFields();
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
		if (!areMainValuesConsistent()) {
			throw new IllegalStateException("Le quote non sono corrette");
		}
		return true;
	}

}
