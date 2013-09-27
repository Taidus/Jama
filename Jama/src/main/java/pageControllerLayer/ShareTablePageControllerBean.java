package pageControllerLayer;

import java.io.Serializable;

import javax.enterprise.context.ConversationScoped;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import annotations.Current;
import businessLayer.AbstractShareTable;

@Named("shareTablePCB")
@ConversationScoped
public class ShareTablePageControllerBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject @Current private AbstractShareTable shareTable;

	public ShareTablePageControllerBean() {
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
}
