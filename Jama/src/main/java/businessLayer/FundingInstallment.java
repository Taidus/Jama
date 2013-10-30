package businessLayer;

import java.io.Serializable;

import javax.persistence.Entity;

import util.Percent;

@Entity
public class FundingInstallment extends Installment implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public FundingInstallment() {
		super();
		IVA_amount =Percent.ZERO;
	}

	@Override
	public void copy(Installment copy) {
		super._copy(copy);
	}
	
	

}
