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

	public int getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getProtocolNumber() {
		return protocolNumber;
	}

	public void setProtocolNumber(String protocolNumber) {
		this.protocolNumber = protocolNumber;
	}

	public AgreementType getType() {
		return type;
	}

	public void setType(AgreementType type) {
		this.type = type;
	}

	public ChiefScientist getChief() {
		return chief;
	}

	public void setChief(ChiefScientist chief) {
		this.chief = chief;
	}

	public String getConctatPerson() {
		return conctatPerson;
	}

	public void setConctatPerson(String conctatPerson) {
		this.conctatPerson = conctatPerson;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public UAR getUar() {
		return uar;
	}

	public void setUar(UAR uar) {
		this.uar = uar;
	}

	public int getCIA_projectNumber() {
		return CIA_projectNumber;
	}

	public void setCIA_projectNumber(int cIA_projectNumber) {
		CIA_projectNumber = cIA_projectNumber;
	}

	public ConctractShareTable getShareTable() {
		return shareTable;
	}

	public void setShareTable(ConctractShareTable shareTable) {
		this.shareTable = shareTable;
	}

	public float getWholeAmount() {
		return wholeAmount;
	}

	public void setWholeAmount(float wholeAmount) {
		this.wholeAmount = wholeAmount;
	}

	public float getIVA_amount() {
		return IVA_amount;
	}

	public void setIVA_amount(float iVA_amount) {
		IVA_amount = iVA_amount;
	}

	public float getWholeTaxableAmount() {
		return wholeTaxableAmount;
	}

	public void setWholeTaxableAmount(float wholeTaxableAmount) {
		this.wholeTaxableAmount = wholeTaxableAmount;
	}

	public Calendar getApprovalDate() {
		return approvalDate;
	}

	public void setApprovalDate(Calendar approvalDate) {
		this.approvalDate = approvalDate;
	}

	public Calendar getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(Calendar beginDate) {
		this.beginDate = beginDate;
	}

	public Calendar getDeadlineDate() {
		return deadlineDate;
	}

	public void setDeadlineDate(Calendar deadlineDate) {
		this.deadlineDate = deadlineDate;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}
	
	
	
	
	
}
