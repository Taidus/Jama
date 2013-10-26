package businessLayer;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.ElementCollection;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.joda.money.Money;

import util.Config;
import util.Percent;

@MappedSuperclass
public abstract class AbstractShareTable {
	//TODO cancellare stampe varie
	
	

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	protected Percent atheneumCapitalBalance;
	protected Percent atheneumCommonBalance;
	protected Percent structures;
	protected Percent personnel;

	@ElementCollection
	protected Map<ChiefScientist, Percent> sharePerPersonnel;

	protected Percent goodsAndServices;
	protected Percent businessTrip;
	protected Percent consumerMaterials;
	protected Percent inventoryMaterials;
	protected Percent rentals;
	protected Percent personnelOnContract;
	protected Percent otherCost;

	protected AbstractShareTable() {
		super();
	}
	
	protected final void initFields() {
		sharePerPersonnel = new HashMap<>();
		otherCost = Percent.ONE;
		goodsAndServices = Percent.ONE;
	}
	
	protected void copy(AbstractShareTable copy){
		this.atheneumCapitalBalance = copy.atheneumCapitalBalance;
		this.atheneumCommonBalance = copy.atheneumCommonBalance;
		this.structures = copy.structures;
		this.personnel = copy.personnel;
		this.sharePerPersonnel = new HashMap<>(copy.sharePerPersonnel);
		this.goodsAndServices = copy.goodsAndServices;
		this.businessTrip = copy.businessTrip;
		this.consumerMaterials = copy.consumerMaterials;
		this.inventoryMaterials = copy.inventoryMaterials;
		this.rentals = copy.rentals;
		this.personnelOnContract = copy.personnelOnContract;
		this.otherCost = copy.otherCost;
	}

	public int getId() {
		return id;
	}

	public Percent getAtheneumCapitalBalance() {
		return atheneumCapitalBalance;
	}

	public void setAtheneumCapitalBalance(Percent atheneumCapitalBalance) {
		this.atheneumCapitalBalance = atheneumCapitalBalance;
		updateGoodsAndServices();
	}

	public Percent getAtheneumCommonBalance() {
		return atheneumCommonBalance;
	}

	public void setAtheneumCommonBalance(Percent atheneumCommonBalance) {
		this.atheneumCommonBalance = atheneumCommonBalance;
		updateGoodsAndServices();
	}

	public Percent getStructures() {
		return structures;
	}

	public void setStructures(Percent structures) {
		this.structures = structures;
		updateGoodsAndServices();
	}

	public Percent getPersonnel() {
		return personnel;
	}

	public void setPersonnel(Percent personnel) {
		this.personnel = personnel;
		updateGoodsAndServices();
	}

	public Percent getGoodsAndServices() {
		return goodsAndServices;
	}

	public Percent getBusinessTrip() {
		return businessTrip;
	}

	public void setBusinessTrip(Percent businessTrip) {
		this.businessTrip = businessTrip;
		updateOtherCosts();
	}

	public Percent getConsumerMaterials() {
		return consumerMaterials;
	}

	public void setConsumerMaterials(Percent consumerMaterials) {
		this.consumerMaterials = consumerMaterials;
		updateOtherCosts();
	}

	public Percent getInventoryMaterials() {
		return inventoryMaterials;
	}

	public void setInventoryMaterials(Percent inventoryMaterials) {
		this.inventoryMaterials = inventoryMaterials;
		updateOtherCosts();
	}

	public Percent getRentals() {
		return rentals;
	}

	public void setRentals(Percent rentals) {
		this.rentals = rentals;
		updateOtherCosts();
	}

	public Percent getPersonnelOnContract() {
		return personnelOnContract;
	}

	public void setPersonnelOnContract(Percent personnelOnContract) {
		this.personnelOnContract = personnelOnContract;
		updateOtherCosts();
	}

	public Percent getOtherCost() {
		return otherCost;
	}
	
	public Map<ChiefScientist, Percent> getSharePerPersonnel() {
		return sharePerPersonnel;
	}


	protected void updateGoodsAndServices() {
		Percent sum = atheneumCapitalBalance.addAll(atheneumCommonBalance, structures, personnel);
		this.goodsAndServices = Percent.ONE.subtract(sum);
		System.out.println("G&S aggiornato: " + goodsAndServices);
	}
	
	protected void updateOtherCosts() {
		Percent sum = rentals.addAll(inventoryMaterials, consumerMaterials, businessTrip, personnelOnContract);
		this.otherCost = Percent.ONE.subtract(sum);
		System.out.println("Other cost aggiornato: " + otherCost);
	}

}
