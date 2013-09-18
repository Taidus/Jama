package businessModel;

import java.util.Calendar;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Agreement {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	private String title;
	private String protocolNumber;
	
	private ChiefScientist chief;
	private String conctatPerson;
	private Company company;
	private UAR uar;
	
	private int CIA_projectNumber;
	private ConctractSpilittingTable splittingTable;
	
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
