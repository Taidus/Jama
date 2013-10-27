package businessLayer;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import util.Percent;

@Entity
public class ContractShareTable extends AbstractShareTable implements Serializable {

	private static final long serialVersionUID = 1L;

	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.REMOVE }) 
	private AgreementShareTableFiller filler;

	public ContractShareTable() {
		initFields();
	}

	public AgreementShareTableFiller getFiller() {
		return filler;
	}

	public void setFiller(AgreementShareTableFiller filler) {
		this.filler = filler;
		setPersonnel(Percent.ZERO);
	}

	public void copy(ContractShareTable copy) {
		super.copy(copy);
		this.filler = copy.filler;
	}

	@Override
	public void setPersonnel(Percent personnel) {
		super.setPersonnel(personnel);
		if (filler != null) {
			filler.fill(this);
		} else {
			System.err.println("!!! Null filler");
			// TODO lanciare eccezione
		}
	}

}
