package businessLayer;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import controllerLayer.FillerFactoryBean;

@Entity
public class AgreementShareTable extends AbstractShareTable implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@ManyToOne
	private AgreementShareTableFiller filler;

	public AgreementShareTable() {
		initFields();
	}

	public AgreementShareTableFiller getFiller() {
		return filler;
	}

	public void setFiller(AgreementShareTableFiller filler) {
		this.filler = filler;
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
