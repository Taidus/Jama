package businessLayer;

import javax.faces.validator.ValidatorException;
import javax.persistence.Entity;

import util.Messages;

@Entity
public class AgreementShareTable extends AbstractShareTable {

	public AgreementShareTable() {
		initFields();
	}

	@Override
	public void validate(float[] mainValues, float[] goodsAndShareValues,
			float[] personnelValues, float goodsAndServices, float personnel) {
		if (!areMainValuesConsistent(mainValues)) {
			throw new ValidatorException(
					Messages.getErrorMessage("err_shareTableValues"));
		}
		if (!areGoodsAndServicesValuesConsistent(goodsAndShareValues,
				goodsAndServices)) {
			throw new ValidatorException(
					Messages.getErrorMessage("err_shareTableGoods"));
		}
		if (!arePersonnelValuesConsistent(personnelValues, personnel)) {
			throw new ValidatorException(
					Messages.getErrorMessage("err_shareTablePersonnel"));
		}
	}

}
