package businessLayer;

import java.io.Serializable;

import javax.persistence.Entity;

@Entity
public class AgreementShareTable extends AbstractShareTable implements Serializable {

	private static final long serialVersionUID = 1L;
	
	public AgreementShareTable() {
		initFields();
	}


}
