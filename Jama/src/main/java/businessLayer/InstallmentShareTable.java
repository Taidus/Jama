package businessLayer;

import javax.persistence.Entity;

@Entity
public class InstallmentShareTable extends AbstractShareTable {

	public InstallmentShareTable() {
		initFields();
	}
}