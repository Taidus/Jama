package presentationLayer;

import java.util.Iterator;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Alternative;
import javax.inject.Inject;
import javax.inject.Named;

import util.InvalidValueException;
import util.Parameters;

@Alternative
@Named("agrShareTableBuilder")
@RequestScoped
public class SimpleAgreementShareTableBuilder implements AgreementShareTableBuilder {
	@Inject
	private AgreementShareTablePresentationBean table;

	public SimpleAgreementShareTableBuilder() {}

	@Override
	public void build() throws InvalidValueException {
		float personnel = table.getTempPersonnel();
		
		float athCommBal = Parameters.atheneumCommonBalanceRate;
		float struct = Parameters.structuresRate;

		boolean found = false;
		float athCapBal = 0.0F;
		Iterator<Float> it = Parameters.atheneumCapitalBalanceRateTable.keySet().iterator();
		System.out.println("Quota personale nel build: " + personnel);
		while (false == found && it.hasNext()) {
			float threshold = it.next();
			System.out.println("Soglia: " + threshold);
			if (personnel <= threshold) {
				athCapBal = Parameters.atheneumCapitalBalanceRateTable.get(threshold);
				found = true;
			}
		}
		if (false == found) {
			throw new IllegalStateException("Atheneum rate table is not valid");
		}

		if(athCommBal + athCapBal + struct + personnel > 100F){
			throw new InvalidValueException();
		}
		
		table.setAtheneumCommonBalance(athCommBal);
		table.setStructures(struct);
		table.setAtheneumCapitalBalance(athCapBal);
		table.setPersonnel(personnel);
	}

}