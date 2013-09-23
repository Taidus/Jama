package businessLayer;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

/**
 * Entity implementation class for Entity: Installment
 * 
 */
@Entity
public class Installment implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	@Temporal(TemporalType.DATE)
	private Date date;
	private float amount;
	private float iva;
	private int voucherNumber;
	@Temporal(TemporalType.DATE)
	private Date voucherDate;
	private int ivaVoucherNumber;
	private int pendingNumber;
	private int invoiceNumber;
	@Temporal(TemporalType.DATE)
	private Date invoiceDate;
	private boolean paidInvoice;
	private boolean reportRequired;
	private String note;

	@OneToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "pippo")
	private InstallmentShareTable shareTable;

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public float getAmount() {
		return amount;
	}

	public void setAmount(float amount) {
		this.amount = amount;
	}

	public float getIva() {
		return iva;
	}

	public void setIva(float iva) {
		this.iva = iva;
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

	public int getIvaVoucherNumber() {
		return ivaVoucherNumber;
	}

	public void setIvaVoucherNumber(int ivaVoucherNumber) {
		this.ivaVoucherNumber = ivaVoucherNumber;
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

	public InstallmentShareTable getShareTable() {
		return shareTable;
	}

	public void setShareTable(InstallmentShareTable shareTable) {
		this.shareTable = shareTable;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public int getId() {
		return id;
	}

	public Installment() {
	}

}
