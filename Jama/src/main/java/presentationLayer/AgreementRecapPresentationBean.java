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

import annotations.TransferObj;
import businessLayer.Agreement;
import businessLayer.Installment;

@Named("agreementRecapPB")
@RequestScoped
public class AgreementRecapPresentationBean {

	/**
	 * 
	 */
	@Inject
	@TransferObj
	private Agreement agr;

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
	
	

	public Agreement getAgr() {
		return agr;
	}

	@PostConstruct
	public void init() {
		annualRecap = new ArrayList<>();
		compute();
	}

	private void compute() {

		float totalAmount = 0;
		float totalPaid = 0;
		
		if(!agr.getInstallments().isEmpty()){
		
		Date d = agr.getInstallments().get(0).getDate();
		Calendar currentDate = new GregorianCalendar();
		currentDate.setTimeInMillis(d.getTime());

		float currentWholeAmount = 0;
		float currentpaid = 0;

		for (Installment i : agr.getInstallments()) {

			Calendar date = new GregorianCalendar();
			currentDate.setTimeInMillis(i.getDate().getTime());

			if (date.get(Calendar.YEAR) != currentDate.get(Calendar.YEAR)) {

				float remainder = currentWholeAmount - currentpaid;
				//cheack getTime
				annualRecap.add(new RecapItem(remainder, currentWholeAmount,
						currentpaid,currentDate.get(Calendar.YEAR)));
				currentDate = date;
				currentpaid = 0;
				currentWholeAmount = 0;

			}

			currentWholeAmount += i.getWholeAmount();
			totalAmount += i.getWholeAmount();

			if (i.isPaidInvoice()) {
				currentpaid += i.getWholeAmount();
				totalPaid += i.getWholeAmount();

			}

		}
		}

		float totalRemainder = totalAmount - totalPaid;
		totalRecap = new TotalRecap(totalAmount, totalPaid, totalRemainder);

	}

	public static class RecapItem {

		private float remainder;
		private float wholeAmount;
		private float paid;
		private int year;
		public RecapItem(float remainder, float wholeAmount, float paid,
				int year) {
			super();
			this.remainder = remainder;
			this.wholeAmount = wholeAmount;
			this.paid = paid;
			this.year = year;
		}
		public float getRemainder() {
			return remainder;
		}
		public float getWholeAmount() {
			return wholeAmount;
		}
		public float getPaid() {
			return paid;
		}
		public int getYear() {
			return year;
		}

		

	}

	public static class TotalRecap {

		private float agreementWholeAmount;
		private float totalTurnOver;
		// TODO scadenza / noin in scadenza
		private float totalRemainder;

		public TotalRecap(float agreementWholeAmount, float totalTurnOver,
				float totalRemainder) {
			super();
			this.agreementWholeAmount = agreementWholeAmount;
			this.totalTurnOver = totalTurnOver;
			this.totalRemainder = totalRemainder;
		}

		public float getAgreementWholeAmount() {
			return agreementWholeAmount;
		}

		public float getTotalTurnOver() {
			return totalTurnOver;
		}

		public float getTotalRemainder() {
			return totalRemainder;
		}

	}

}
