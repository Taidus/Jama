package businessLayer;

import javax.persistence.Entity;

@Entity
public class AgreementShareTable extends AbstractShareTable {

	@Override
	public boolean validate() {
		return (areMainValuesConsistent() && arePersonnelSharesConsistent() && areGoodsSharesConsistent());
	}

}
