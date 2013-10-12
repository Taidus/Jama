package presentationLayer;

import java.util.Iterator;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Alternative;
import javax.inject.Inject;
import javax.inject.Named;

import util.Parameters;

@Alternative
@Named("agrShareTableBuilder")
@RequestScoped
public class SimpleAgreementShareTableBuilder implements AgreementShareTableBuilder {
	@Inject
	private AgreementShareTablePresentationBean table;

	public SimpleAgreementShareTableBuilder() {
	}

	@Override
	public void build() {
		float personnel = table.getPersonnel();

		table.setAtheneumCommonBalance(Parameters.atheneumCommonBalanceRate);
		table.setStructures(Parameters.structuresRate);

		boolean found = false;
		float rate = 0.0F;
		Iterator<Float> it = Parameters.atheneumCapitalBalanceRateTable.keySet().iterator();
		System.out.println("Quota personale nel build: " + personnel);
		while (false == found && it.hasNext()) {
			float threshold = it.next();
			System.out.println("Soglia: " + threshold);
			if (personnel <= threshold) {
				rate = Parameters.atheneumCapitalBalanceRateTable.get(threshold);
				found = true;
			}
		}
		if (false == found) {
			throw new IllegalStateException("Atheneum rate table is not valid");
		}

		table.setAtheneumCapitalBalance(rate);
	}

}
