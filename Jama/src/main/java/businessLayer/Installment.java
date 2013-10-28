package businessLayer;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.joda.money.Money;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class Installment implements Serializable {

	/**
	 * 
	 */
	protected static final long serialVersionUID = 1L;

	public Installment() {
		this.shareTable = new InstallmentShareTable();
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	protected int id;

	@ManyToOne
	protected Contract contract;

	@Temporal(TemporalType.DATE)
	@NotNull
	protected Date date;

	@Min(0)
	protected int voucherNumber;

	@Temporal(TemporalType.DATE)
	protected Date voucherDate;

	@Min(0)
	protected int pendingNumber;
	@Min(0)
	protected int invoiceNumber;

	@Temporal(TemporalType.DATE)
	protected Date invoiceDate;

	protected boolean paidInvoice;
	protected boolean reportRequired;
	protected String note;

	@OneToOne(cascade = CascadeType.PERSIST)
	@JoinColumn
	protected InstallmentShareTable shareTable;

	public Contract getContract() {
		return contract;
	}

	public void setContract(Contract c) {
		this.contract = c;
	}

	public abstract Money getWholeAmount();

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public int getVoucherNumber() {
		return voucherNumber;
	}

	public void setVoucherNumber(int voucherNumber) {
		this.voucherNumber = voucherNumber;
	}

	public Date getVoucherDate() {
		return voucherDate;
	}

	public void setVoucherDate(Date voucherDate) {
		this.voucherDate = voucherDate;
	}

	public int getPendingNumber() {
		return pendingNumber;
	}

	public void setPendingNumber(int pendingNumber) {
		this.pendingNumber = pendingNumber;
	}

	public int getInvoiceNumber() {
		return invoiceNumber;
	}

	public void setInvoiceNumber(int invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}

	public Date getInvoiceDate() {
		return invoiceDate;
	}

	public void setInvoiceDate(Date invoiceDate) {
		this.invoiceDate = invoiceDate;
	}

	public boolean isPaidInvoice() {
		return paidInvoice;
	}

	public void setPaidInvoice(boolean paidInvoice) {
		this.paidInvoice = paidInvoice;
	}

	public boolean isReportRequired() {
		return reportRequired;
	}

	public void setReportRequired(boolean reportRequired) {
		this.reportRequired = reportRequired;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public int getId() {
		return id;
	}

	public InstallmentShareTable getShareTable() {
		return shareTable;
	}

	public void setShareTable(InstallmentShareTable shareTable) {
		this.shareTable = shareTable;
	}

	public void copy(Installment copy) {
		
		System.out.println("COPYYYYYYYY====");
		
		this.id = copy.id;
		this.date = new Date(copy.date.getTime());
		this.voucherNumber = copy.voucherNumber;
		this.voucherDate = new Date(copy.voucherDate.getTime());
		this.pendingNumber = copy.pendingNumber;
		this.invoiceNumber = copy.invoiceNumber;
		this.invoiceDate = new Date(copy.invoiceDate.getTime());
		this.paidInvoice = copy.paidInvoice;
		this.reportRequired = copy.reportRequired;
		this.note = copy.note;
		this.contract = copy.getContract();
		this.shareTable = new InstallmentShareTable();
		
		this.shareTable.copy(copy.getShareTable());


	}
	
	
		public void initShareTableFromContract(Contract c) {
			this.shareTable.copy(c.getShareTable());
		}

}
