package businessLayer;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import util.Percent;

@Entity
public class ContractShareTable extends AbstractShareTable implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@ElementCollection
	private Map<ChiefScientist, String> personnelId;

	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.REMOVE }) 
	private ContractShareTableFiller filler;

	public ContractShareTable() {
		initFields();
		this.personnelId = new HashMap<>();
	}

	public ContractShareTableFiller getFiller() {
		return filler;
	}

	public void setFiller(ContractShareTableFiller filler) {
		this.filler = filler;
		setPersonnel(Percent.ZERO);
	}
	
	public Map<ChiefScientist, String> getPersonnelId() {
		return personnelId;
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
