package businessLayer;

import java.io.Serializable;

import javax.persistence.Entity;

import util.Percent;

@Entity
public class Funding extends Contract implements Serializable {

	private static final long serialVersionUID = 1L;

	public Funding() {
		super();
		IVA_amount = Percent.ZERO;
	}

}
