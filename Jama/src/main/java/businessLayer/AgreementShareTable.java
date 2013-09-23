package businessLayer;

import javax.persistence.Entity;
import javax.resource.spi.IllegalStateException;

@Entity
public class AgreementShareTable extends AbstractShareTable {

	public AgreementShareTable() {
		initFields();
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
