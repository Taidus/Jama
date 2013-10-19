package businessLayer;

import java.util.Iterator;
import java.util.Map;

public class SimpleAgreementShareTableFiller implements AgreementShareTableFiller {

	private Map<Float, Float> atheneumCapitalBalanceRateTable;
	private float structuresRate;
	private float atheneumCommonBalanceRate;

	public SimpleAgreementShareTableFiller(Map<Float, Float> atheneumCapitalBalanceRateTable, float structuresRate, float atheneumCommonBalanceRate) {
		super();
		this.atheneumCapitalBalanceRateTable = atheneumCapitalBalanceRateTable;
		this.structuresRate = structuresRate;
		this.atheneumCommonBalanceRate = atheneumCommonBalanceRate;
	}

	@Override
	public void fill(AgreementShareTable table) {
		float personnel = table.getPersonnel();

		float athCommBal = atheneumCommonBalanceRate;
		float struct = structuresRate;

		boolean found = false;
		float athCapBal = 0.0F;
		Iterator<Float> it = atheneumCapitalBalanceRateTable.keySet().iterator();
		System.out.println("Quota personale nel build: " + personnel);
		while (false == found && it.hasNext()) {
			float threshold = it.next();
			System.out.println("Soglia: " + threshold);
			if (personnel <= threshold) {
				athCapBal = atheneumCapitalBalanceRateTable.get(threshold);
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

}
