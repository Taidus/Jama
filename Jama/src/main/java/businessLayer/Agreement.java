package businessLayer;

import java.util.Calendar;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Agreement {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	private String title;
	private String protocolNumber;
	
	private AgreementType type;
	
	@ManyToOne
	private ChiefScientist chief;
	private String conctatPerson;
	
	@ManyToOne
	private Company company;
	
	@ManyToOne
	private UAR uar;
	
	private int CIA_projectNumber;
	
	@OneToOne
	private ConctractShareTable shareTable;
	
	private float wholeAmount;
	private float IVA_amount;
	private float wholeTaxableAmount;
	
	@Temporal(TemporalType.DATE)
	private Calendar approvalDate;
	
	@Temporal(TemporalType.DATE)
	private Calendar beginDate;
	
	@Temporal(TemporalType.DATE)
	private Calendar deadlineDate;
	
	private String note;
	
}
