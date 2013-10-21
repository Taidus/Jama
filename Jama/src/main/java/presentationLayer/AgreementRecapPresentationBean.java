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
		System.out.println("building Recap");
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
			date.setTimeInMillis(i.getDate().getTime());
			
			

			if (date.get(Calendar.YEAR) != currentDate.get(Calendar.YEAR)) {
					
				
				float remainder = currentWholeAmount - currentpaid;
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
		
		if(currentWholeAmount!=0){
		
		float remainder = currentWholeAmount - currentpaid;
		annualRecap.add(new RecapItem(remainder, currentWholeAmount,
				currentpaid,currentDate.get(Calendar.YEAR)));}
		
		
		}
		
		
		
		//check 2 remainder
		float totalRemainder = totalAmount - totalPaid;
		float totalRemainderRespectToAgremeement = agr.getWholeAmount() - totalAmount;
		totalRecap = new TotalRecap(totalAmount, totalPaid, totalRemainder,totalRemainderRespectToAgremeement);

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
		private float totalRemainderRespectToAgreement;
		public TotalRecap(float agreementWholeAmount, float totalTurnOver,
				float totalRemainder, float totalRemainderRespectToAgreement) {
			super();
			this.agreementWholeAmount = agreementWholeAmount;
			this.totalTurnOver = totalTurnOver;
			this.totalRemainder = totalRemainder;
			this.totalRemainderRespectToAgreement = totalRemainderRespectToAgreement;
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
		public float getTotalRemainderRespectToAgreement() {
			return totalRemainderRespectToAgreement;
		}

		

	}

}
