package time;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.TimerTask;

import javax.ejb.Stateful;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.TypedQuery;

import util.MailSender;
import businessLayer.Installment;
import freemarker.template.TemplateException;

@Stateful
@ApplicationScoped

public class ContractDeadLineTimer extends TimerTask {

	@PersistenceContext(unitName = "primary", type = PersistenceContextType.EXTENDED)
	private EntityManager em;
	@Inject
	private MailSender mailSender;

	private Calendar date;

	public ContractDeadLineTimer() {
		super();
	}

	public Calendar getDate() {
		return date;
	}

	public void setDate(Calendar date) {
		this.date = date;
	}

	@Override
	public void run() {
		
		Calendar date = Calendar.getInstance();
		System.err.println(new Date(date.getTimeInMillis())+"\n=========\n"+"Avvio task periodico di notifica scadenze\n"+"==========");

		if (date != null) {

			TypedQuery<Installment> query = em.createNamedQuery(
					"Installment.findInstallmentsWithNearDeadLine",Installment.class).setParameter(
					"date", new Date(date.getTimeInMillis()));
			 
			
			for(Installment i : query.getResultList()){
				
				try {
					mailSender.notifyDeadline(i);
				} catch (IOException | TemplateException e) {
					e.printStackTrace();
				}
			}
			

			
		}
	}

}
