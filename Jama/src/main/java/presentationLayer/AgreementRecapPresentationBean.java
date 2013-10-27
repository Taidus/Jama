package presentationLayer;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.joda.money.Money;
import org.joda.money.MoneyUtils;

import util.Config;
import annotations.TransferObj;
import businessLayer.Agreement;
import businessLayer.AgreementInstallment;
import businessLayer.Contract;
import businessLayer.Installment;

@Named("agreementRecapPB")
@RequestScoped
public class AgreementRecapPresentationBean {
	@Inject
	@TransferObj
	private Contract c;

	private TotalRecap totalRecap;
	private List<RecapItem> annualRecap;

	public AgreementRecapPresentationBean() {
	}

	public TotalRecap getTotalRecap() {
		return totalRecap;
	}

	public List<RecapItem> getAnnualRecap() {
		return annualRecap;
	}

	public Contract getContract() {
		return c;
	}

	@PostConstruct
	public void init() {
		System.out.println("building Recap");
		annualRecap = new ArrayList<>();
		compute();
	}

	private void compute() {

		Money totalAmount = Money.zero(Config.currency);
		Money totalPaid = Money.zero(Config.currency);

		if (!c.getInstallments().isEmpty()) {

			Date d = c.getInstallments().get(0).getDate();
			Calendar currentDate = new GregorianCalendar();
			currentDate.setTimeInMillis(d.getTime());

			Money currentWholeAmount = Money.zero(Config.currency);
			Money currentpaid = Money.zero(Config.currency);

			for (Installment i : c.getInstallments()) {

				Calendar date = new GregorianCalendar();
				date.setTimeInMillis(i.getDate().getTime());

				if (date.get(Calendar.YEAR) != currentDate.get(Calendar.YEAR)) {

					Money remainder = currentWholeAmount.minus(currentpaid);
					annualRecap.add(new RecapItem(remainder, currentWholeAmount, currentpaid, currentDate.get(Calendar.YEAR)));
					currentDate = date;
					currentpaid = Money.zero(Config.currency);
					currentWholeAmount = Money.zero(Config.currency);

				}

				currentWholeAmount.plus(i.getWholeAmount());
				totalAmount.plus(i.getWholeAmount());

				if (i.isPaidInvoice()) {
					currentpaid.plus(i.getWholeAmount());
					totalPaid.plus(i.getWholeAmount());

				}

			}

			if (!MoneyUtils.isZero(currentWholeAmount)) {

				Money remainder = currentWholeAmount.minus(currentpaid);
				annualRecap.add(new RecapItem(remainder, currentWholeAmount, currentpaid, currentDate.get(Calendar.YEAR)));
			}

		}

		// check 2 remainder
		Money totalRemainder = totalAmount.minus(totalPaid);
		// float totalRemainderRespectToAgremeement = agr.getWholeAmount() -
		// totalAmount;
		// totalRecap = new TotalRecap(totalAmount, totalPaid,
		// totalRemainder,totalRemainderRespectToAgremeement);

	}

	public static class RecapItem {

		private Money remainder;
		private Money wholeAmount;
		private Money paid;
		private int year;

		public RecapItem(Money remainder, Money wholeAmount, Money paid, int year) {
			super();
			this.remainder = remainder;
			this.wholeAmount = wholeAmount;
			this.paid = paid;
			this.year = year;
		}

		public Money getRemainder() {
			return remainder;
		}

		public Money getWholeAmount() {
			return wholeAmount;
		}

		public Money getPaid() {
			return paid;
		}

		public int getYear() {
			return year;
		}

	}

	public static class TotalRecap {

		private Money agreementWholeAmount;
		private Money totalTurnOver;
		// TODO scadenza / noin in scadenza
		private Money totalRemainder;
		private Money totalRemainderRespectToAgreement;

		public TotalRecap(Money agreementWholeAmount, Money totalTurnOver, Money totalRemainder, Money totalRemainderRespectToAgreement) {
			super();
			this.agreementWholeAmount = agreementWholeAmount;
			this.totalTurnOver = totalTurnOver;
			this.totalRemainder = totalRemainder;
			this.totalRemainderRespectToAgreement = totalRemainderRespectToAgreement;
		}

		public Money getAgreementWholeAmount() {
			return agreementWholeAmount;
		}

		public Money getTotalTurnOver() {
			return totalTurnOver;
		}

		public Money getTotalRemainder() {
			return totalRemainder;
		}

		public Money getTotalRemainderRespectToAgreement() {
			return totalRemainderRespectToAgreement;
		}

	}

}
