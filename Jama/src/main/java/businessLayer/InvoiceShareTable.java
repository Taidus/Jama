package businessLayer;

import businessLayer.AbstractShareTable;

import java.io.Serializable;

import javax.persistence.*;

/**
 * Entity implementation class for Entity: InvoiceShareTable
 * 
 */
@Entity
public class InvoiceShareTable extends AbstractShareTable implements
		Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	@OneToOne(mappedBy="shareTable")
	private Installment installment;

	public InvoiceShareTable() {
		super();
	}

	public Installment getInstallment() {
		return installment;
	}

	public void setInstallment(Installment installment) {
		this.installment = installment;
	}

	@Override
	public boolean validate() {
		// TODO Auto-generated method stub
		return false;
	}

}
