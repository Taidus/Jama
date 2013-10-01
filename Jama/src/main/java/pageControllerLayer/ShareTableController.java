package pageControllerLayer;

import java.util.ArrayList;
import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;

import businessLayer.AbstractShareTable;
import businessLayer.ChiefScientist;

public class ShareTableController {

	private AbstractShareTable shareTable;
	private List<PersonnelShare> shares;
	private PersonnelShare selectedShare;

	public ShareTableController(AbstractShareTable shareTable) {
		shares = new ArrayList<PersonnelShare>();
		shares.add(new PersonnelShare());
		this.shareTable = shareTable;
	}

	public PersonnelShare getSelectedShare() {
		return selectedShare;
	}

	public void setSelectedShare(PersonnelShare selectedShare) {
		this.selectedShare = selectedShare;
	}

	public List<PersonnelShare> getShares() {
		return shares;
	}

	public AbstractShareTable getShareTable() {
		return shareTable;
	}

	public void validate(FacesContext context, UIComponent component,
			Object value) {

		float atheneumCapitalBalance = (Float) ((UIInput) component
				.findComponent("atheneumCapitalBalance")).getValue();
		float atheneumCommonBalance = (Float) ((UIInput) component
				.findComponent("atheneumCommonBalance")).getValue();
		float structures = (Float) ((UIInput) component
				.findComponent("structures")).getValue();
		float personnel = (Float) ((UIInput) component
				.findComponent("personnel")).getValue();

		float goodsAndServices = (Float) ((UIInput) component
				.findComponent("goodsAndServices")).getValue();
		float businessTrip = (Float) ((UIInput) component
				.findComponent("businessTrip")).getValue();
		float consumerMaterials = (Float) ((UIInput) component
				.findComponent("consumerMaterials")).getValue();
		float inventoryMaterials = (Float) ((UIInput) component
				.findComponent("inventoryMaterials")).getValue();
		float rentals = (Float) ((UIInput) component.findComponent("rentals"))
				.getValue();
		float personnelOnContract = (Float) ((UIInput) component
				.findComponent("personnelOnContract")).getValue();
		float otherCost = (Float) ((UIInput) component
				.findComponent("otherCost")).getValue();

		float[] mainValues = { atheneumCapitalBalance, atheneumCommonBalance,
				structures, personnel, goodsAndServices };
		float[] goodsAndServicesValues = { businessTrip, consumerMaterials,
				inventoryMaterials, rentals, personnelOnContract, otherCost };
		float[] personnelValues = createPersonnelValues(shares);
		
		shareTable.validate(mainValues, goodsAndServicesValues,
				personnelValues, goodsAndServices, personnel);
	}

	private float[] createPersonnelValues(List<PersonnelShare> shares) {
		float[] f = new float[shares.size()];
		int i = 0;
		for (PersonnelShare p : shares) {
			if(p.getChiefScientist() != null) {
				f[i] = p.getShare();
				System.out.println(f[i]);
				i++;
			}
		}
		return f;
	}

	public static class PersonnelShare {
		private ChiefScientist chiefScientist;
		private float share;

		public PersonnelShare() {
			chiefScientist = null;
			share = 0;
		}

		public ChiefScientist getChiefScientist() {
			return chiefScientist;
		}

		public void setChiefScientist(ChiefScientist chiefScientist) {
			this.chiefScientist = chiefScientist;
		}

		public float getShare() {
			return share;
		}

		public void setShare(float share) {
			this.share = share;
		}
	}

	public void addRow() {
		shares.add(new PersonnelShare());
	}

	public void removeRow() {
		shares.remove(selectedShare);
	}

}
