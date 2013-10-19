package businessLayer;

import java.io.Serializable;

import javax.persistence.Entity;

import util.FillerFactory;

@Entity
public class AgreementShareTable extends AbstractShareTable implements Serializable {

	private static final long serialVersionUID = 1L;
	private AgreementShareTableFiller filler;

	public AgreementShareTable() {
		initFields();
	}

	public AgreementShareTable(Department dep) {
		initFields();
		filler = FillerFactory.getFiller(dep);
		setPersonnel(0F);
	}

	@Override
	public void setPersonnel(float personnel) {
		super.setPersonnel(personnel);
		if(filler != null){
			filler.fill(this);
		}
		else{
			System.err.println("!!! Null filler");
			//TODO lanciare eccezione
		}
	}

}
