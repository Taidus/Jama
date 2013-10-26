package businessLayer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import util.MathUtil;

/**
 * Entity implementation class for Entity: Contract
 * 
 */

@MappedSuperclass
public abstract class Contract implements Serializable {

	public Contract() {
		// shareTable = new AgreementShareTable();
		installments = new ArrayList<>();
		attachments = new ArrayList<>();
	}

	protected static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	protected int id;

	@NotNull
	@Size(max = 1000)
	protected String title;
	protected String protocolNumber; // FIXME ma serve?

	@ManyToOne
	@NotNull
	protected ChiefScientist chief;
	@NotNull
	protected String contactPerson;

	@ManyToOne
	@NotNull
	protected Company company;

	@ManyToOne
	protected Department department;

	protected int CIA_projectNumber;
	protected int inventoryNumber;

	protected float spentAmount;
	protected float reservedAmount;

	@OneToMany(mappedBy = "agreement", cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE }, fetch = FetchType.EAGER)
	@OrderBy("date DESC")
	protected List<Installment> installments;

	@Temporal(TemporalType.DATE)
	protected Date approvalDate;

	@Temporal(TemporalType.DATE)
	protected Date beginDate;

	@Temporal(TemporalType.DATE)
	protected Date deadlineDate;

	protected String note;

	@OneToMany(cascade = { CascadeType.REMOVE, CascadeType.PERSIST })
	protected List<Attachment> attachments;

	public abstract void addInstallment(Installment i);

	public abstract float getWholeAmount();

	public boolean isClosed() {

		return MathUtil.doubleEquals(getWholeAmount(), spentAmount);

	}

	public void removeInstallment(Installment i) {
		installments.remove(i);
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

	public Date getApprovalDate() {
		return approvalDate;
	}

	public void setApprovalDate(Date approvalDate) {
		this.approvalDate = approvalDate;
	}

	public List<Installment> getInstallments() {

		// TODO check
		return new ArrayList<>(installments);
	}

	public void setInstallments(List<Installment> installments) {
		this.installments = installments;
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

	public List<Attachment> getAttachments() {
		return attachments;
	}

	public void setAttachments(List<Attachment> attachments) {
		this.attachments = attachments;
	}

	public float getSpentAmount() {
		return spentAmount;
	}

	public void setSpentAmount(float spentAmount) {
		this.spentAmount = spentAmount;
	}

	public float getReservedAmount() {
		return reservedAmount;
	}

	public void setReservedAmount(float reservedAmount) {
		this.reservedAmount = reservedAmount;
		System.out.println("set ReservedAmount");
	}

	// fatturato
	public float getTurnOver() {
		float sum = 0;
		for (Installment i : installments) {

			if (i.isPaidInvoice()) {
				sum += i.getWholeAmount();
			}

		}

		return sum;
	}

}
