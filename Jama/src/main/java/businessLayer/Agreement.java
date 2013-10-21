package businessLayer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import util.MathUtil;

@Entity
@NamedQueries({ @NamedQuery(name = "Agreement.findAll", query = "SELECT a FROM Agreement a ORDER BY a.approvalDate") })
public class Agreement implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// TODO rimmettere i not null
	@Id @GeneratedValue(strategy = GenerationType.AUTO) private int id;

	@NotNull @Size(max = 1000) private String title;
	private String protocolNumber; // FIXME ma serve?

	@NotNull private AgreementType type;

	@ManyToOne @NotNull private ChiefScientist chief;
	@NotNull private String contactPerson;

	@ManyToOne @NotNull private Company company;

	@ManyToOne private Department department;

	private int CIA_projectNumber;
	private int inventoryNumber;

	@OneToOne(cascade = CascadeType.PERSIST) private AgreementShareTable shareTable;

	private float IVA_amount;
	private float wholeTaxableAmount;
	private float spentAmount;
	private float reservedAmount;

	@OneToMany(mappedBy = "agreement", cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE }) 
	@OrderBy("date DESC") 
	private List<Installment> installments;

	@Temporal(TemporalType.DATE) private Date approvalDate;

	@Temporal(TemporalType.DATE) private Date beginDate;

	@Temporal(TemporalType.DATE) private Date deadlineDate;

	private String note;

	@OneToMany(cascade = { CascadeType.REMOVE, CascadeType.PERSIST }) private List<Attachment> attachments;

	public Agreement() {
		// shareTable = new AgreementShareTable();
		installments = new ArrayList<>();
		attachments = new ArrayList<>();
	}

//	public void cloneFields(Agreement copy) {
//
//		// this.id = copy.getId();
//		this.title = copy.getTitle();
//		this.protocolNumber = copy.getProtocolNumber();
//		this.type = copy.getType();
//		this.chief = copy.getChief();
//		this.contactPerson = copy.getContactPerson();
//		this.company = copy.getCompany();
//		this.department = copy.getDepartment();
//		this.CIA_projectNumber = copy.getCIA_projectNumber();
//		this.inventoryNumber = copy.getInventoryNumber();
//
//		this.shareTable = new AgreementShareTable();
//		this.shareTable.copy(copy.getShareTable());
//		this.installments = new ArrayList<>();
//
//		this.IVA_amount = copy.getIVA_amount();
//		this.wholeTaxableAmount = copy.getWholeTaxableAmount();
//		this.approvalDate = new Date(copy.getApprovalDate().getTime());
//		this.beginDate = new Date(copy.getBeginDate().getTime());
//		this.deadlineDate = new Date(copy.getDeadlineDate().getTime());
//		this.note = copy.getNote();
//		this.attachments = copy.getAttachments();
//		this.spentAmount = copy.getSpentAmount();
//		this.reservedAmount = copy.getReservedAmount();
//
//		for (Installment i : copy.getInstallments()) {
//			Installment j = new Installment();
//			j.copy(i);
//			j.setAgreement(this);
//			this.installments.add(j);
//		}
//
//	}

//	public Installment getInstallmentById(int id) {
//
//		boolean found = false;
//		Installment result = null;
//		Iterator<Installment> i = installments.iterator();
//		while (found == false && i.hasNext()) {
//			Installment current = i.next();
//
//			if (current.getId() == id) {
//				result = current;
//			}
//		}
//
//		return result;
//
//	}
	
	public void addInstallment(Installment i){
		i.setAgreement(this);
		installments.add(i);
		
	}
	
	public void removeInstallment(Installment i){
		installments.remove(i);
	}

	@Override
	public String toString() {
		return "Agreement [id=" + id + ", title=" + title + ", protocolNumber=" + protocolNumber + ", type=" + type + ", chief=" + chief
				+ ", contactPerson=" + contactPerson + ", company=" + company + ", department=" + department + ", CIA_projectNumber="
				+ CIA_projectNumber + ", inventoryNumber=" + inventoryNumber + ", shareTable=" + shareTable + ", IVA_amount=" + IVA_amount
				+ ", wholeTaxableAmount=" + wholeTaxableAmount + ", installments=" + installments + ", approvalDate=" + approvalDate + ", beginDate="
				+ beginDate + ", deadlineDate=" + deadlineDate + ", note=" + note + "]";
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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
		return this.wholeTaxableAmount * (100 + this.IVA_amount) / 100;
	}

	public float getIVA_amount() {
		return IVA_amount;
	}

	public void setIVA_amount(float iVA_amount) {
		IVA_amount = iVA_amount;
		System.out.println("setIVA");
	}

	public float getWholeTaxableAmount() {
		return wholeTaxableAmount;
	}

	public void setWholeTaxableAmount(float wholeTaxableAmount) {
		this.wholeTaxableAmount = wholeTaxableAmount;
		System.out.println("setAmount");
	}

	public Date getApprovalDate() {
		return approvalDate;
	}

	public void setApprovalDate(Date approvalDate) {
		this.approvalDate = approvalDate;
	}

	public List<Installment> getInstallments() {
		
		//TODO check
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

	public boolean isClosed() {

		return MathUtil.doubleEquals(getWholeAmount(), spentAmount);

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
