package businessLayer;

import java.util.Iterator;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Alternative;
import javax.inject.Named;

import util.Parameters;

@Alternative
@Named("agrShareTableBuilder")
@RequestScoped
public class SimpleAgreementShareTableBuilder implements AgreementShareTableBuilder {

	public SimpleAgreementShareTableBuilder() {
	}

	@Override
	public void build(AgreementShareTable table) {
		float personnel = table.getPersonnel();

		table.setAtheneumCommonBalance(personnel * Parameters.atheneumCommonBalanceRate);
		table.setStructures(personnel * Parameters.structuresRate);

		boolean found = false;
		float rate = 0.0F;
		Iterator<Float> it = Parameters.atheneumCapitalBalanceRateTable.keySet().iterator();
		while (false == found && it.hasNext()) {
			float threshold = it.next();
			if (personnel <= threshold) {
				rate = Parameters.atheneumCapitalBalanceRateTable.get(threshold);
				found = true;
			}
		}
		if (false == found) {
			throw new IllegalStateException("Atheneum rate table is not valid");
		}

		table.setAtheneumCapitalBalance(personnel * rate);
	}

}
