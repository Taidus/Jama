package businessLayer;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.joda.money.Money;

import util.Config;
import util.Percent;

@MappedSuperclass
public abstract class AbstractShareTable {
	// TODO cancellare stampe varie

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	@Embedded
	@AttributeOverride(name = "value", column = @Column(name = "ATHENEUM_CAPITAL_BALANCE"))
	protected Percent atheneumCapitalBalance;

	@Embedded
	@AttributeOverride(name = "value", column = @Column(name = "ATHENEUM_COMMON_BALANCE"))
	protected Percent atheneumCommonBalance;

	@Embedded
	@AttributeOverride(name = "value", column = @Column(name = "STRUCTURES"))
	protected Percent structures;

	@Embedded
	@AttributeOverride(name = "value", column = @Column(name = "PERSONNEL"))
	protected Percent personnel;

	@ElementCollection
	protected Map<ChiefScientist, Percent> sharePerPersonnel;

	@Embedded
	@AttributeOverride(name = "value", column = @Column(name = "GOODS_AND_SERVICES"))
	protected Percent goodsAndServices;

	@Embedded
	@AttributeOverride(name = "value", column = @Column(name = "BUSINESS_TRIP"))
	protected Percent businessTrip;

	@Embedded
	@AttributeOverride(name = "value", column = @Column(name = "CONSUMER_MATERIALS"))
	protected Percent consumerMaterials;

	@Embedded
	@AttributeOverride(name = "value", column = @Column(name = "INVENTORY_MATERIALS"))
	protected Percent inventoryMaterials;

	@Embedded
	@AttributeOverride(name = "value", column = @Column(name = "RENTALS"))
	protected Percent rentals;

	@Embedded
	@AttributeOverride(name = "value", column = @Column(name = "PERSONNEL_ON_CONTRACT"))
	protected Percent personnelOnContract;

	@Embedded
	@AttributeOverride(name = "value", column = @Column(name = "OTHER_COST"))
	protected Percent otherCost;

	protected AbstractShareTable() {
		super();
	}

	protected final void initFields() {
		this.atheneumCapitalBalance = new Percent();
		this.atheneumCommonBalance = new Percent();
		this.structures = new Percent();
		this.personnel = new Percent();
		this.businessTrip = new Percent();
		this.consumerMaterials = new Percent();
		this.inventoryMaterials = new Percent();
		this.rentals = new Percent();
		this.personnelOnContract = new Percent();

		this.sharePerPersonnel = new HashMap<>();
		this.otherCost = Percent.ONE;
		this.goodsAndServices = Percent.ONE;
	}

	protected void copy(AbstractShareTable copy) {
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
		Percent sum = Percent.sum(atheneumCapitalBalance, atheneumCommonBalance, structures, personnel);
		this.goodsAndServices = Percent.subtract(Percent.ONE, sum);
	}

	protected void updateOtherCosts() {
		Percent sum = Percent.sum(rentals, inventoryMaterials, consumerMaterials, businessTrip, personnelOnContract);
		this.otherCost = Percent.subtract(Percent.ONE, sum);
	}

}
