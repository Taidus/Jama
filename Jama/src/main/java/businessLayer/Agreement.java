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
public class Agreement extends Contract implements Serializable {
	private static final long serialVersionUID = 1L;

	private AgreementType type;

	public Agreement() {
		super();

	}

	@Override
	public String toString() {
		return "Agreement [id=" + id + ", title=" + title + ", protocolNumber="
				+ protocolNumber + ", type=" + type + ", chief=" + chief
				+ ", contactPerson=" + contactPerson + ", company=" + company
				+ ", department=" + department + ", CIA_projectNumber="
				+ CIA_projectNumber + ", inventoryNumber=" + inventoryNumber
				+ ", shareTable=" + shareTable + ", IVA_amount=" + IVA_amount
				+ ", wholeTaxableAmount=" + wholeTaxableAmount
				+ ", installments=" + installments + ", approvalDate="
				+ approvalDate + ", beginDate=" + beginDate + ", deadlineDate="
				+ deadlineDate + ", note=" + note + "]";
	}

	public AgreementType getType() {
		return type;
	}

	public void setType(AgreementType type) {
		this.type = type;
	}

	public void setIVA_amount(Percent iVA_amount) {
		IVA_amount = iVA_amount;
	}

	

}
