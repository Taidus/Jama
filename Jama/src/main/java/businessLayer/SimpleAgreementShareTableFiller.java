package businessLayer;

import java.util.Iterator;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;

import util.Percent;

@Entity
public class SimpleAgreementShareTableFiller extends AgreementShareTableFiller {

	@ElementCollection
	@AttributeOverrides({
	@AttributeOverride(name="key.value",column=@Column(name="ATHENEUM_CAPITAL_BALANCE_RATE_TABLE_KEY")),
	@AttributeOverride(name="value.value",column=@Column(name="ATHENEUM_CAPITAL_BALANCE_RATE_TABLE_VALUE"))})
	private Map<Percent, Percent> atheneumCapitalBalanceRateTable;
	
	@Embedded
	@AttributeOverride(name="value",column=@Column(name="STRUCTURES_RATES"))
	private Percent structuresRate;
	@Embedded
	@AttributeOverride(name="value",column=@Column(name="ATHENEUM_COMMON_BALANCE"))
	private Percent atheneumCommonBalanceRate;

	public SimpleAgreementShareTableFiller() {
		super();
	}

	public SimpleAgreementShareTableFiller(Map<Percent, Percent> atheneumCapitalBalanceRateTable, Percent structuresRate,
			Percent atheneumCommonBalanceRate) {
		super();
		this.atheneumCapitalBalanceRateTable = atheneumCapitalBalanceRateTable;
		this.structuresRate = structuresRate;
		this.atheneumCommonBalanceRate = atheneumCommonBalanceRate;
	}

	@Override
	public void fill(AgreementShareTable table) {
		System.out.println("Filler map: " + atheneumCapitalBalanceRateTable);
		SortedMap<Percent, Percent> atheneumCapitalBalanceRateTable_sorted = new TreeMap<>(atheneumCapitalBalanceRateTable);;
		// XXX soluzione di ripego perché le annotazoni di Hibernate sono
		// stupide

		Percent personnel = table.getPersonnel();

		Percent athCommBal = atheneumCommonBalanceRate;
		Percent struct = structuresRate;

		boolean found = false;
		Percent athCapBal = Percent.ZERO;
		Iterator<Percent> it = atheneumCapitalBalanceRateTable_sorted.keySet().iterator();
		System.out.println("Quota personale nel build: " + personnel);
		while (false == found && it.hasNext()) {
			Percent threshold = it.next();
			System.out.println("Soglia: " + threshold);
			if (!personnel.greaterThan(threshold)) {
				athCapBal = atheneumCapitalBalanceRateTable_sorted.get(threshold);
				found = true;
			}
		}
		if (false == found) {
			throw new IllegalStateException("Atheneum rate table is not valid");
		}

		table.setAtheneumCommonBalance(athCommBal);
		table.setStructures(struct);
		table.setAtheneumCapitalBalance(athCapBal);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((atheneumCapitalBalanceRateTable == null) ? 0 : atheneumCapitalBalanceRateTable.hashCode());
		result = prime * result + ((atheneumCommonBalanceRate == null) ? 0 : atheneumCommonBalanceRate.hashCode());
		result = prime * result + ((structuresRate == null) ? 0 : structuresRate.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SimpleAgreementShareTableFiller other = (SimpleAgreementShareTableFiller) obj;
		if (atheneumCapitalBalanceRateTable == null) {
			if (other.atheneumCapitalBalanceRateTable != null)
				return false;
		} else if (!atheneumCapitalBalanceRateTable.equals(other.atheneumCapitalBalanceRateTable))
			return false;
		if (atheneumCommonBalanceRate == null) {
			if (other.atheneumCommonBalanceRate != null)
				return false;
		} else if (!atheneumCommonBalanceRate.equals(other.atheneumCommonBalanceRate))
			return false;
		if (structuresRate == null) {
			if (other.structuresRate != null)
				return false;
		} else if (!structuresRate.equals(other.structuresRate))
			return false;
		return true;
	}

}
