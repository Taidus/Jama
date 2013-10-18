package presentationLayer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.enterprise.context.ConversationScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.inject.Inject;
import javax.inject.Named;

import util.MathUtil;
import util.Messages;
import annotations.TransferObj;
import businessLayer.Agreement;
import businessLayer.AgreementShareTable;
import businessLayer.ChiefScientist;

@Named("agrShareTablePB")
@ConversationScoped
public class AgreementShareTablePresentationBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@Inject @TransferObj private Agreement agreement;

	private PersonnelShare selectedShare;
	private PersonnelShare newShare;

	public AgreementShareTablePresentationBean() {
		newShare = new PersonnelShare();
		// FIXME questo darà problemi
	}

	public List<PersonnelShare> getShares() {
		List<PersonnelShare> result = new ArrayList<>();
		Map<ChiefScientist, Float> shares = agreement.getShareTable().getSharePerPersonnel();
		for (ChiefScientist chief : shares.keySet()) {
			result.add(new PersonnelShare(chief, shares.get(chief)));
		}
		return result;
	}

	public void addShare() {
		System.out.println("Adding share " + newShare);
		agreement.getShareTable().getSharePerPersonnel().put(newShare.chiefScientist, newShare.value);
		System.out.println("Share added: \n\t" + agreement.getShareTable().getSharePerPersonnel());
		newShare = new PersonnelShare();
	}

	public void removeShare(PersonnelShare share) {
		agreement.getShareTable().getSharePerPersonnel().remove(share.chiefScientist);
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
		return agreement.getShareTable().getSharePerPersonnel().keySet();
	}

	public AgreementShareTable getShareTable() {
		return agreement.getShareTable();
	}

	public void validateValues(FacesContext context, UIComponent component, Object value) {
		if (agreement.getShareTable().getGoodsAndServices() < 0.0) {
			throw new ValidatorException(Messages.getErrorMessage("err_shareTableValues"));
		}
	}

	public void validateGoodsAndServices(FacesContext context, UIComponent component, Object value) {
		if (agreement.getShareTable().getOtherCost() < 0.0) {
			throw new ValidatorException(Messages.getErrorMessage("err_shareTableGoods"));
		}
	}

	public void validatePersonnelShares(FacesContext context, UIComponent component, Object value) {
		Map<ChiefScientist, Float> shares = agreement.getShareTable().getSharePerPersonnel();
		float sum = 0F;
		for (float f : shares.values()) {
			sum += f;
		}
		if (!MathUtil.doubleEquals(agreement.getShareTable().getPersonnel() * sum / 100, agreement.getShareTable().getPersonnel())) {
			// NB: il controllo deve essere eseguito in questo modo. Controllare
			// che sum == 100 non funziona nel caso in cui personnel sia 0
			throw new ValidatorException(Messages.getErrorMessage("err_shareTablePersonnel"));
		}
	}

	public float getPercentOfMainField(float field) {
		return agreement.getWholeAmount() * field / 100;
	}

	public float getPercentOfGoodsField(float field) {
		return getPercentOfMainField(agreement.getShareTable().getGoodsAndServices()) * field / 100;
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
			return getPercentOfMainField(agreement.getShareTable().getPersonnel()) * value / 100;
		}

		@Override
		public String toString() {
			return "PersonnelShare [chiefScientist=" + chiefScientist + ", value=" + value + "]";
		}

	}

}
