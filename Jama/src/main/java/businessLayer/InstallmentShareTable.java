package businessLayer;

import javax.persistence.Entity;

@Entity
public class InstallmentShareTable extends AbstractShareTable {

	public InstallmentShareTable() {
		initFields();
	}

	public void copy(InstallmentShareTable copy){
		super.copy(copy);
	}
}