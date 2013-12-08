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
import businessLayer.ContractShareTable;

public abstract class ShareTablePresentationObj {

	// private PersonnelShare selectedShare;
	private PersonnelShare newShare;


	protected ShareTablePresentationObj() {
		newShare = new PersonnelShare();
		// FIXME questo darà "problemi" (inserisci, non salvi, esci e ritorni =
		// stessi valori di prima. Però non dà fastidio, anzi, può essere una
		// cosa positiva)
	}


	protected abstract AbstractShareTable getTransferObjShareTable();


	protected abstract Money getTransfetObjWholeAmount();


	protected abstract ContractShareTable getContractShareTable();


	public List<PersonnelShare> getShares() {
		System.out.println("Building");
		List<PersonnelShare> result = new ArrayList<>();
		Map<ChiefScientist, Percent> shares = getTransferObjShareTable().getSharePerPersonnel();
		Map<ChiefScientist, String> ids = getContractShareTable().getPersonnelId();
		for (ChiefScientist chief : shares.keySet()) {
			String id = ids.get(chief);
			if (null == id) {
				id = "";
			}
			result.add(new PersonnelShare(chief, shares.get(chief), id));
		}
		return result;
	}


	public void addShare() {
		System.out.println("ShareTablePO: adding share");
		getTransferObjShareTable().getSharePerPersonnel().put(newShare.chiefScientist, newShare.value);
		getContractShareTable().getPersonnelId().put(newShare.chiefScientist, newShare.id);
		newShare = new PersonnelShare();

	}


	public void removeShare(PersonnelShare share) {
		getTransferObjShareTable().getSharePerPersonnel().remove(share.chiefScientist);
	}


	// public void editValue(PersonnelShare badshare){
	// PersonnelShare share = selectedShare;
	// System.out.println("Edit value sees: " + share.getValue());
	// getTransferObjShareTable().getSharePerPersonnel().put(share.chiefScientist,
	// share.getValue());
	// }

	// public PersonnelShare getSelectedShare() {
	// return selectedShare;
	// }
	//
	// public void setSelectedShare(PersonnelShare selectedValue) {
	// this.selectedShare = selectedValue;
	// }

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

		Percent personnel = getTransferObjShareTable().getPersonnel();
		System.out.println(personnel + " scale: " + personnel.getValue().scale() + "; zero scale: " + Percent.ZERO.getValue().scale());
		if (!personnel.equals(Percent.ZERO)) {
			System.out.println("Entered");
			Percent sum = Percent.sum(getTransferObjShareTable().getSharePerPersonnel().values());
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
		private String id;


		public PersonnelShare(ChiefScientist chiefScientist, Percent value, String id) {
			this.chiefScientist = chiefScientist;
			this.value = value;
			this.id = id;
		}


		public PersonnelShare() {
			this.chiefScientist = null;
			this.value = Percent.ZERO;
			this.id = "";
		}


		public ChiefScientist getChiefScientist() {
			return chiefScientist;
		}


		public void setChiefScientist(ChiefScientist chiefScientist) {
			this.chiefScientist = chiefScientist;
		}


		public Percent getValue() {
			System.out.println("Personnel share getter called: " + value);
			return value;
		}


		public void setValue(Percent value) {

			System.out.println("Personnel share setter called: " + value);
			this.value = value;
			Map<ChiefScientist, Percent> map = getTransferObjShareTable().getSharePerPersonnel();
			if (map.containsKey(this.chiefScientist)) {
				map.put(this.chiefScientist, this.value);
			}

		}


		public String getId() {
			System.out.println("Personnel id getter called: " + id);
			if (null == this.id) {
				return "";
			}
			return id;
		}


		public void setId(String id) {

			if (null == id) {
				id = "";
			}
			System.out.println("Personnel id setter called: " + id);
			this.id = id;
			Map<ChiefScientist, String> map = getContractShareTable().getPersonnelId();
			if (map.containsKey(this.chiefScientist)) {
				map.put(this.chiefScientist, this.id);
			}

		}


		public Money getFlatAmount() {
			return value.computeOn(computePercentOnWholeAmount(getTransferObjShareTable().getPersonnel()));
		}


		@Override
		public String toString() {
			return "PersonnelShare [chiefScientist=" + chiefScientist + ", value=" + value + "]";
		}

	}

}
