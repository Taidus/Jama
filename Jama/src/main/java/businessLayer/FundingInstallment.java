package businessLayer;

import java.io.Serializable;

import util.Percent;

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
