package businessLayer;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderColumn;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Agreement {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	private String title;
	private String protocolNumber;

	private AgreementType type;

	@ManyToOne
	private ChiefScientist chief;
	private String contactPerson;

	@ManyToOne
	private Company company;

	@ManyToOne
	private Department department;

	private int CIA_projectNumber;
	private int inventoryNumber;

	@OneToOne(cascade = CascadeType.PERSIST)
	private AgreementShareTable shareTable;

	private float wholeAmount;
	private float IVA_amount;
	private float wholeTaxableAmount;
	
	@OneToMany(mappedBy="agreement")
	@OrderColumn
	private List<Installment> installments;

	public List<Installment> getInstallments() {
		return installments;
	}

	public void setInstallments(List<Installment> installments) {
		this.installments = installments;
	}

	@Temporal(TemporalType.DATE)
	private Date approvalDate;

	@Temporal(TemporalType.DATE)
	private Date beginDate;

	@Temporal(TemporalType.DATE)
	private Date deadlineDate;

	private String note;
	
	

	public Agreement() {
		shareTable = new AgreementShareTable();
	}

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

	public String getContactPerson() {
		return contactPerson;
	}

	public void setContactPerson(String contactPerson) {
		this.contactPerson = contactPerson;
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

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	public int getCIA_projectNumber() {
		return CIA_projectNumber;
	}

	public void setCIA_projectNumber(int cIA_projectNumber) {
		CIA_projectNumber = cIA_projectNumber;
	}

	public int getInventoryNumber() {
		return inventoryNumber;
	}

	public void setInventoryNumber(int inventoryNumber) {
		this.inventoryNumber = inventoryNumber;
	}

	public AgreementShareTable getShareTable() {
		return shareTable;
	}

	public void setShareTable(AgreementShareTable shareTable) {
		this.shareTable = shareTable;
	}

	public float getWholeAmount() {
		return wholeAmount;
	}

	private void setWholeAmount() {
		this.wholeAmount = this.wholeTaxableAmount*(100 + this.IVA_amount)/100;
	}

	public float getIVA_amount() {
		return IVA_amount;
	}

	public void setIVA_amount(float iVA_amount) {
		IVA_amount = iVA_amount;
		setWholeAmount();
	}

	public float getWholeTaxableAmount() {
		return wholeTaxableAmount;
	}

	public void setWholeTaxableAmount(float wholeTaxableAmount) {
		this.wholeTaxableAmount = wholeTaxableAmount;
		setWholeAmount();
	}

	public Date getApprovalDate() {
		return approvalDate;
	}

	public void setApprovalDate(Date approvalDate) {
		this.approvalDate = approvalDate;
	}

	public Date getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

	public Date getDeadlineDate() {
		return deadlineDate;
	}

	public void setDeadlineDate(Date deadlineDate) {
		this.deadlineDate = deadlineDate;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

}
