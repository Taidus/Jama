package presentationLayer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.ConversationScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.inject.Inject;
import javax.inject.Named;

import org.joda.money.Money;

import util.Config;
import util.Messages;
import annotations.TransferObj;
import businessLayer.AbstractShareTable;
import businessLayer.Contract;
import businessLayer.Installment;

@Named("instShareTablePB")
@ConversationScoped
public class InstallmentShareTablePresentationBean extends ShareTablePresentationObj implements Serializable {
	private static final long serialVersionUID = 1L;

	@Inject
	@TransferObj
	private Installment installment;

	public InstallmentShareTablePresentationBean() {
		super();
	}

	@Override
	protected AbstractShareTable getTransferObjShareTable() {
		return installment.getShareTable();
	}

	@Override
	protected Money getTransfetObjWholeAmount() {
		return installment.getWholeAmount();
	}

	public void validateWithOtherInstallments(FacesContext context, UIComponent component, Object value) {
		// TODO eliminare try-catch
		try {
			Contract c = installment.getContract();
			List<Installment> installments = c.getInstallments();
			installments.add(installment);
			List<List<Money>> instShareTablesMainAttributes = new ArrayList<>();
			List<List<Money>> instShareTablesSubAttributes = new ArrayList<>();

			System.out.println(1);

			for (Installment i : installments) {
				System.out.print("Rata" + i + " \n\t ");
				instShareTablesMainAttributes.add(getMainAttributeList(i.getShareTable(), i.getWholeAmount()));
				System.out.print("\t");
				instShareTablesSubAttributes.add(getSubAttributeList(i.getShareTable(),
						i.getShareTable().getGoodsAndServices().computeOn(i.getWholeAmount())));
			}

			System.out.println(2);

			System.out.print("Convenzione: ");
			List<Money> l = getMainAttributeList(c.getShareTable(), c.getWholeAmount());
			System.out.println(3);
			System.out.print("\t");
			List<Money> p = getSubAttributeList(c.getShareTable(), c.getShareTable().getGoodsAndServices().computeOn(c.getWholeAmount()));
			System.out.println(4);

			// devono essere nello stesso ordine in cui si aggiungono sotto!
			// L'oscenità fatta codice
			String[] mainAttr = { Messages.getString("shareTableCapitalBalance"), Messages.getString("shareTableCommonBalance"),
					Messages.getString("shareTableStructures"), Messages.getString("shareTablePersonnel"), Messages.getString("shareTableGoods") };
			String[] subAttr = { Messages.getString("shareTableTrip"), Messages.getString("shareTableInvMaterials"),
					Messages.getString("shareTableConsMaterials"), Messages.getString("shareTableRentals"),
					Messages.getString("shareTableContrPersonnel"), Messages.getString("shareTableOtherCosts") };

			validateFields(l, instShareTablesMainAttributes, mainAttr);
			System.out.println(5);
			validateFields(p, instShareTablesSubAttributes, subAttr);
			System.out.println(6);
		} catch (Exception | Error e) {
			System.out.println("Blbl " + e);
			throw e;
		}
	}

	private void validateFields(List<Money> agrAttributes, List<List<Money>> instAttrs, String[] attrNames) {
		// XXX questo metodo e tutti quelli ad esso correlati sono osceni. Se si
		// trova un meccanismo per non scrivere simili blasfemie è meglio

		for (int i = 0; i < agrAttributes.size(); i++) {
			Money sum = Money.zero(Config.currency);
			for (List<Money> l : instAttrs) {
				sum.plus(l.get(i));
			}
			if (sum.isGreaterThan((agrAttributes.get(i)))) {
				System.err.println("Errore sull'attributo '" + attrNames[i] + "': convenzione=" + agrAttributes.get(i) + ", rate=" + sum);
				Object[] params = { attrNames[i] };
				throw new ValidatorException(Messages.getErrorMessage("err_installmentShareTable", params));
			}
		}

	}

	private List<Money> getMainAttributeList(AbstractShareTable t, Money wholeAmount) {
		List<Money> attributes = new ArrayList<>();

		attributes.add(t.getAtheneumCapitalBalance().computeOn(wholeAmount));
		attributes.add(t.getAtheneumCommonBalance().computeOn(wholeAmount));
		attributes.add(t.getStructures().computeOn(wholeAmount));
		attributes.add(t.getPersonnel().computeOn(wholeAmount));
		attributes.add(t.getGoodsAndServices().computeOn(wholeAmount));

		System.out.println(attributes);

		return attributes;
	}

	private List<Money> getSubAttributeList(AbstractShareTable t, Money wholeAmount) {
		List<Money> attributes = new ArrayList<>();

		attributes.add(t.getBusinessTrip().computeOn(wholeAmount));
		attributes.add(t.getInventoryMaterials().computeOn(wholeAmount));
		attributes.add(t.getConsumerMaterials().computeOn(wholeAmount));
		attributes.add(t.getRentals().computeOn(wholeAmount));
		attributes.add(t.getPersonnelOnContract().computeOn(wholeAmount));
		attributes.add(t.getOtherCost().computeOn(wholeAmount));

		System.out.println(attributes);

		return attributes;
	}
}
