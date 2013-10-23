package presentationLayer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;

import util.MathUtil;
import util.Messages;
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

	protected abstract float getTransfetObjWholeAmount();

	public List<PersonnelShare> getShares() {
		List<PersonnelShare> result = new ArrayList<>();
		Map<ChiefScientist, Float> shares = getTransferObjShareTable().getSharePerPersonnel();
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
		if (getTransferObjShareTable().getGoodsAndServices() < 0.0) {
			throw new ValidatorException(Messages.getErrorMessage("err_shareTableValues"));
		}
	}

	public void validateGoodsAndServices(FacesContext context, UIComponent component, Object value) {
		if (getTransferObjShareTable().getOtherCost() < 0.0) {
			throw new ValidatorException(Messages.getErrorMessage("err_shareTableGoods"));
		}
	}

	public void validatePersonnelShares(FacesContext context, UIComponent component, Object value) {
		Map<ChiefScientist, Float> shares = getTransferObjShareTable().getSharePerPersonnel();
		float sum = 0F;
		for (float f : shares.values()) {
			sum += f;
		}
		if (!MathUtil.doubleEquals(getTransferObjShareTable().getPersonnel() * sum / 100, getTransferObjShareTable().getPersonnel())) {
			// NB: il controllo deve essere eseguito in questo modo. Controllare
			// che sum == 100 non funziona nel caso in cui personnel sia 0
			throw new ValidatorException(Messages.getErrorMessage("err_shareTablePersonnel"));
		}
	}

	public float getPercentOfMainField(float field) {
		return getTransfetObjWholeAmount() * field / 100;
	}

	public float getPercentOfGoodsField(float field) {
		return getPercentOfMainField(getTransferObjShareTable().getGoodsAndServices()) * field / 100;
	}

	protected float getPercentOf(float percent, float amount) {
		return percent * amount / 100;
	}

	public class PersonnelShare {
		private ChiefScientist chiefScientist;
		private float value;

		public PersonnelShare(ChiefScientist chiefScientist, float value) {
			this.chiefScientist = chiefScientist;
			this.value = value;
		}

		public PersonnelShare() {
			chiefScientist = null;
			value = 0;
		}

		public ChiefScientist getChiefScientist() {
			return chiefScientist;
		}

		public void setChiefScientist(ChiefScientist chiefScientist) {
			System.out.println("Setting: " + chiefScientist);
			this.chiefScientist = chiefScientist;
		}

		public float getValue() {
			return value;
		}

		public void setValue(float value) {
			System.out.println("Setting: " + value);
			this.value = value;
		}

		public float getPercentValue() {
			System.out.println(">>> Percent value called");
			return getPercentOfMainField(getTransferObjShareTable().getPersonnel()) * value / 100;
		}

		@Override
		public String toString() {
			return "PersonnelShare [chiefScientist=" + chiefScientist + ", value=" + value + "]";
		}

	}

}
