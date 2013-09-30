package pageControllerLayer;

import java.util.ArrayList;
import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;

import businessLayer.AbstractShareTable;
import businessLayer.ChiefScientist;


public class ShareTableController  {


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
		float[] personnelValues = {};

		shareTable.validate(mainValues, goodsAndServicesValues,
				personnelValues, goodsAndServices, personnel);
	}

	public static class PersonnelShare {
		private ChiefScientist chiefScientist;
		private float share;

		public PersonnelShare() {
			chiefScientist = new ChiefScientist();
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
		PersonnelShare p = new PersonnelShare();
		p.setChiefScientist(new ChiefScientist());
		p.setShare(0);
		shares.add(p);
	}

	public void removeRow() {
		System.out.println(selectedShare);
		shares.remove(selectedShare);
		System.out.println(shares);
	}
}
