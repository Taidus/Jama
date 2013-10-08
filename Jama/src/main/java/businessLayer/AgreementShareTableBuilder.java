package businessLayer;

import java.util.Map;

public interface AgreementShareTableBuilder {
	public AgreementShareTable build(float personnel, Map<ChiefScientist, Float> sharePerPersonnel);
}
