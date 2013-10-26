package businessLayer;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.joda.money.Money;

import util.Config;
import util.MathUtil;
import util.Percent;

@Entity
@NamedQueries({ @NamedQuery(name = "Agreement.findAll", query = "SELECT a FROM Agreement a ORDER BY a.approvalDate") })
public class Agreement implements Serializable {
	private static final long serialVersionUID = 1L;

	// TODO rimettere i not null
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	@NotNull
	@Size(max = 1000)
	private String title;
	
	private String protocolNumber; // FIXME ma serve?

	@NotNull
	private AgreementType type;

	@ManyToOne
	@NotNull
	private ChiefScientist chief;
	
	@NotNull
	private String contactPerson;

	@ManyToOne
	@NotNull
	private Company company;

	@ManyToOne
	private Department department;

	private int CIA_projectNumber;
	private int inventoryNumber;

	@OneToOne(cascade = CascadeType.PERSIST)
	private AgreementShareTable shareTable;
	
	@Embedded
	private Percent IVA_amount;
	
	@Embedded
	@AttributeOverrides({
	@AttributeOverride(name="money", column=@Column(name="WHOLE_TAXABLE_AMOUNT")),
	@AttributeOverride(name="money.currency.code", column=@Column(name="WHOLE_TAXABLE_AMOUNT_CODE")),
	@AttributeOverride(name="money.currency.decimalPlaces", column=@Column(name="WHOLE_TAXABLE_AMOUNT_DECIMAL_PLACES")),
	@AttributeOverride(name="money.currency.numericCode", column=@Column(name="WHOLE_TAXABLE_AMOUNT_NUMERIC_CODE")),
	@AttributeOverride(name="money.amount", column=@Column(name="WHOLE_TAXABLE_AMOUNT_AMOUNT"))})
	private Money wholeTaxableAmount;
	
	@Embedded
	@AttributeOverrides({
	@AttributeOverride(name="money", column=@Column(name="SPENT_AMOUNT")),
	@AttributeOverride(name="money.currency.code", column=@Column(name="SPENT_AMOUNT_CODE")),
	@AttributeOverride(name="money.currency.decimalPlaces", column=@Column(name="SPENT_AMOUNT_DECIMAL_PLACES")),
	@AttributeOverride(name="money.currency.numericCode", column=@Column(name="SPENT_AMOUNT_NUMERIC_CODE")),
	@AttributeOverride(name="money.amount", column=@Column(name="SPENT_AMOUNT_AMOUNT"))})
	private Money spentAmount;
	
	@Embedded
	@AttributeOverrides({
	@AttributeOverride(name="money", column=@Column(name="RESERVED_AMOUNT")),
	@AttributeOverride(name="money.currency.code", column=@Column(name="RESERVED_AMOUNT_CODE")),
	@AttributeOverride(name="money.currency.decimalPlaces", column=@Column(name="RESERVED_AMOUNT_DECIMAL_PLACES")),
	@AttributeOverride(name="money.currency.numericCode", column=@Column(name="RESERVED_AMOUNT_NUMERIC_CODE")),
	@AttributeOverride(name="money.amount", column=@Column(name="RESERVED_AMOUNT_AMOUNT"))})
	private Money reservedAmount;

	@OneToMany(mappedBy = "agreement", cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE }, fetch = FetchType.EAGER)
	@OrderBy("date DESC")
	private List<Installment> installments;

	@Temporal(TemporalType.DATE)
	private Date approvalDate;

	@Temporal(TemporalType.DATE)
	private Date beginDate;

	@Temporal(TemporalType.DATE)
	private Date deadlineDate;

	private String note;

	@OneToMany(cascade = { CascadeType.REMOVE, CascadeType.PERSIST })
	private List<Attachment> attachments;

	public Agreement() {
		// shareTable = new AgreementShareTable();
		installments = new ArrayList<>();
		attachments = new ArrayList<>();
		IVA_amount = Percent.ZERO;
		wholeTaxableAmount = Money.zero(Config.currency);
		spentAmount = Money.zero(Config.currency);
		reservedAmount = Money.zero(Config.currency);
	}

	// public void cloneFields(Agreement copy) {
	//
	// // this.id = copy.getId();
	// this.title = copy.getTitle();
	// this.protocolNumber = copy.getProtocolNumber();
	// this.type = copy.getType();
	// this.chief = copy.getChief();
	// this.contactPerson = copy.getContactPerson();
	// this.company = copy.getCompany();
	// this.department = copy.getDepartment();
	// this.CIA_projectNumber = copy.getCIA_projectNumber();
	// this.inventoryNumber = copy.getInventoryNumber();
	//
	// this.shareTable = new AgreementShareTable();
	// this.shareTable.copy(copy.getShareTable());
	// this.installments = new ArrayList<>();
	//
	// this.IVA_amount = copy.getIVA_amount();
	// this.wholeTaxableAmount = copy.getWholeTaxableAmount();
	// this.approvalDate = new Date(copy.getApprovalDate().getTime());
	// this.beginDate = new Date(copy.getBeginDate().getTime());
	// this.deadlineDate = new Date(copy.getDeadlineDate().getTime());
	// this.note = copy.getNote();
	// this.attachments = copy.getAttachments();
	// this.spentAmount = copy.getSpentAmount();
	// this.reservedAmount = copy.getReservedAmount();
	//
	// for (Installment i : copy.getInstallments()) {
	// Installment j = new Installment();
	// j.copy(i);
	// j.setAgreement(this);
	// this.installments.add(j);
	// }
	//
	// }

	// public Installment getInstallmentById(int id) {
	//
	// boolean found = false;
	// Installment result = null;
	// Iterator<Installment> i = installments.iterator();
	// while (found == false && i.hasNext()) {
	// Installment current = i.next();
	//
	// if (current.getId() == id) {
	// result = current;
	// }
	// }
	//
	// return result;
	//
	// }

	public void addInstallment(Installment i) {
		i.setAgreement(this);
		installments.add(i);

	}

	public void removeInstallment(Installment i) {
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

	public Money getWholeAmount() {
		return wholeTaxableAmount.plus(IVA_amount.computeOn(wholeTaxableAmount));
	}

	public Percent getIVA_amount() {
		return IVA_amount;
	}

	public void setIVA_amount(Percent iVA_amount) {
		IVA_amount = iVA_amount;
	}

	public Money getWholeTaxableAmount() {
		return wholeTaxableAmount;
	}

	public void setWholeTaxableAmount(Money wholeTaxableAmount) {
		this.wholeTaxableAmount = wholeTaxableAmount;
	}

	public Money getSpentAmount() {
		return spentAmount;
	}

	public void setSpentAmount(Money spentAmount) {
		this.spentAmount = spentAmount;
	}

	public Money getReservedAmount() {
		return reservedAmount;
	}

	public void setReservedAmount(Money reservedAmount) {
		this.reservedAmount = reservedAmount;
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

	

	public boolean isClosed() {

		return getWholeAmount().equals(spentAmount);

	}

	// fatturato
	public Money getTurnOver() {
		Money sum = Money.zero(Config.currency);
		for (Installment i : installments) {

			if (i.isPaidInvoice()) {
				sum.plus(i.getWholeAmount());
			}

		}

		return sum;
	}

}
