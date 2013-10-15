package businessLayer;

import java.io.Serializable;

import javax.faces.validator.ValidatorException;
import javax.persistence.Entity;

import util.Messages;

@Entity
public class AgreementShareTable extends AbstractShareTable implements Serializable {

	private static final long serialVersionUID = 1L;
	
	public AgreementShareTable() {
		initFields();
	}

	

	@Override
	public void validate() {
		if (!areMainValuesConsistent()) {
			throw new ValidatorException(Messages.getErrorMessage("err_shareTableValues"));
		}
		if (!areGoodsAndServicesValuesConsistent()) {
			throw new ValidatorException(Messages.getErrorMessage("err_shareTableGoods"));
		}
		if (!arePersonnelValuesConsistent()) {
			throw new ValidatorException(Messages.getErrorMessage("err_shareTablePersonnel"));
		}
	}

}
