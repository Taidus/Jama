package presentationLayer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;

import org.joda.money.Money;

import util.Messages;
import util.Percent;
import businessLayer.AbstractShareTable;
import businessLayer.ChiefScientist;

public abstract class ShareTablePresentationObj {

	private PersonnelShare selectedShare;
	private PersonnelShare newShare;

	protected ShareTablePresentationObj() {
		newShare = new PersonnelShare();
		// FIXME questo darà "problemi" (inserisci, non salvi, esci e ritorni =
		// stessi valori di prima. Però non dà fastidio, anzi, può essere una
		// cosa positiva)
	}

	protected abstract AbstractShareTable getTransferObjShareTable();

	protected abstract Money getTransfetObjWholeAmount();

	public List<PersonnelShare> getShares() {
		List<PersonnelShare> result = new ArrayList<>();
		Map<ChiefScientist, Percent> shares = getTransferObjShareTable().getSharePerPersonnel();
		for (ChiefScientist chief : shares.keySet()) {
			result.add(new PersonnelShare(chief, shares.get(chief)));
		}
		return result;
	}

	public void addShare() {
		System.out.println("Adding share " + newShare);
		getTransferObjShareTable().getSharePerPersonnel().put(newShare.chiefScientist, newShare.value);
		System.out.println("Share added: \n\t" + getTransferObjShareTable().getSharePerPersonnel());
		newShare = new PersonnelShare();
	}

	public void removeShare(PersonnelShare share) {
		getTransferObjShareTable().getSharePerPersonnel().remove(share.chiefScientist);
	}

	public PersonnelShare getSelectedShare() {
		return selectedShare;
	}

	public void setSelectedShare(PersonnelShare selectedValue) {
		this.selectedShare = selectedValue;
	}

	public PersonnelShare getNewShare() {
		return newShare;
	}

	public Set<ChiefScientist> getAddedChiefs() {
		return getTransferObjShareTable().getSharePerPersonnel().keySet();
	}

	public AbstractShareTable getShareTable() {
		return getTransferObjShareTable();
	}

	public void validateMainValues(FacesContext context, UIComponent component, Object value) {
		if (getTransferObjShareTable().getGoodsAndServices().lessThan(Percent.ZERO)) {
			throw new ValidatorException(Messages.getErrorMessage("err_shareTableValues"));
		}
	}

	public void validateGoodsAndServices(FacesContext context, UIComponent component, Object value) {
		if (getTransferObjShareTable().getOtherCost().lessThan(Percent.ZERO)) {
			throw new ValidatorException(Messages.getErrorMessage("err_shareTableGoods"));
		}
	}

	public void validatePersonnelShares(FacesContext context, UIComponent component, Object value) {
		// Map<ChiefScientist, Float> shares =
		// getTransferObjShareTable().getSharePerPersonnel();
		// float sum = 0F;
		// for (float f : shares.values()) {
		// sum += f;
		// }
		// if (!MathUtil.doubleEquals(getTransferObjShareTable().getPersonnel()
		// * sum / 100, getTransferObjShareTable().getPersonnel())) {
		// // NB: il controllo deve essere eseguito in questo modo. Controllare
		// // che sum == 100 non funziona nel caso in cui personnel sia 0
		// throw new
		// ValidatorException(Messages.getErrorMessage("err_shareTablePersonnel"));
		// }

		Percent personnel = getTransferObjShareTable().getPersonnel();
		if (!personnel.equals(Percent.ZERO)) {
			Percent sum = Percent.ZERO.addAll(getTransferObjShareTable().getSharePerPersonnel().values());

			if (!sum.equals(Percent.ONE)) {
				throw new ValidatorException(Messages.getErrorMessage("err_shareTablePersonnel"));
			}
		}
	}

	public Money computePercentOnWholeAmount(Percent percent) {
		return percent.computeOn(getTransfetObjWholeAmount());
	}

	public Money computePercentOnGoodsAndServices(Percent percent) {
		return percent.computeOn(computePercentOnWholeAmount(getTransferObjShareTable().getGoodsAndServices()));
	}

	public class PersonnelShare {
		private ChiefScientist chiefScientist;
		private Percent value;

		public PersonnelShare(ChiefScientist chiefScientist, Percent value) {
			this.chiefScientist = chiefScientist;
			this.value = value;
		}

		public PersonnelShare() {
			chiefScientist = null;
		}

		public ChiefScientist getChiefScientist() {
			return chiefScientist;
		}

		public void setChiefScientist(ChiefScientist chiefScientist) {
			System.out.println("Setting: " + chiefScientist);
			this.chiefScientist = chiefScientist;
		}

		public Percent getValue() {
			return value;
		}

		public void setValue(Percent value) {
			System.out.println("Setting percent share: " + value);
			this.value = value;
		}

		public Money getFlatAmount() {
			System.out.println(">>> Computing flat amount");
			return value.computeOn(computePercentOnWholeAmount(getTransferObjShareTable().getPersonnel()));
		}

		@Override
		public String toString() {
			return "PersonnelShare [chiefScientist=" + chiefScientist + ", value=" + value + "]";
		}

	}

}
