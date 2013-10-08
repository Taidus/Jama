package businessLayer;

import java.util.Map;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Alternative;
import javax.inject.Named;

@Alternative
@Named("agrShareTableBuilder")
@RequestScoped
public class SimpleAgreementShareTableBuilder implements AgreementShareTableBuilder {
	
	private AgreementShareTable table;

	public SimpleAgreementShareTableBuilder() {
		this.table = new AgreementShareTable();
	}

	@Override
	public AgreementShareTable build(float personnel, Map<ChiefScientist, Float> sharePerPersonnel) {
		//TODO scrivimi
		return null;
	}

}
