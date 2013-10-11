package presentationLayer;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ConversationScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import annotations.TransferObj;
import businessLayer.Agreement;
import businessLayer.ChiefScientist;

@Named("agrShareTablePB")
@ConversationScoped
public class AgreementShareTablePresentationBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@Inject
	@TransferObj
	private Agreement agreement;
	@Inject
	private AgreementShareTableBuilder builder;

	private float atheneumCapitalBalance;
	private float atheneumCommonBalance;
	private float structures;
	private float personnel;

	private Map<ChiefScientist, Float> sharePerPersonnel;

	private float goodsAndServices;
	private float businessTrip;
	private float consumerMaterials;
	private float inventoryMaterials;
	private float rentals;
	private float personnelOnContract;
	private float otherCost;

	public AgreementShareTablePresentationBean() {
		this.sharePerPersonnel = new HashMap<>();
		this.otherCost = 100F;
		this.goodsAndServices = 100F;
	}
	
	@PostConstruct public void init(){
		update();
	}

	public float getAtheneumCapitalBalance() {
		return atheneumCapitalBalance;
	}

	public float getAtheneumCommonBalance() {
		return atheneumCommonBalance;
	}

	public float getStructures() {
		return structures;
	}

	public float getPersonnel() {
		return personnel;
	}

	public Map<ChiefScientist, Float> getSharePerPersonnel() {
		return sharePerPersonnel;
	}

	public float getGoodsAndServices() {
		return goodsAndServices;
	}

	public float getBusinessTrip() {
		return businessTrip;
	}

	public float getConsumerMaterials() {
		return consumerMaterials;
	}

	public float getInventoryMaterials() {
		return inventoryMaterials;
	}

	public float getRentals() {
		return rentals;
	}

	public float getPersonnelOnContract() {
		return personnelOnContract;
	}

	public float getOtherCost() {
		return otherCost;
	}

	public float getWholeAmount() {
		return agreement.getWholeAmount();
	}

	void setAtheneumCapitalBalance(float atheneumCapitalBalance) {
		this.atheneumCapitalBalance = atheneumCapitalBalance;
	}

	void setAtheneumCommonBalance(float atheneumCommonBalance) {
		this.atheneumCommonBalance = atheneumCommonBalance;
	}

	void setStructures(float structures) {
		this.structures = structures;
	}

	public void setPersonnel(float personnel) {
		this.personnel = personnel;
		System.out.println("Quota personale aggiornata: " + personnel);
		update();
	}

	public void update() {
		builder.build();
		System.out.println("Campi aggiornati: athCapBal = " + atheneumCapitalBalance + ", athCommonBal = " + atheneumCommonBalance
				+ ", structures = " + structures);
		float sum = atheneumCapitalBalance + atheneumCommonBalance + structures + personnel;
		if(sum > 100){
			throw new IllegalStateException("Incorrect agreement share table values");
		}

		this.goodsAndServices = 100F - sum;
		System.out.println("G&S aggiornato: " + goodsAndServices);

	}
	
	public void validatePersonnel(FacesContext context, UIComponent component,
			Object value) {
		
	}
}
