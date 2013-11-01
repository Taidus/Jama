package time;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimerTask;

import javax.ejb.DependsOn;
import javax.ejb.Singleton;
import javax.ejb.Stateful;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.TypedQuery;

import businessLayer.Installment;
import util.MailSender;
import daoLayer.InstallmentDaoBean;
import freemarker.template.TemplateException;

@Stateful
@ApplicationScoped
// @Singleton
// @DependsOn("scheduler")
public class ContractDeadLineTimer extends TimerTask {

	@PersistenceContext(unitName = "primary", type = PersistenceContextType.EXTENDED)
	private EntityManager em;
	@Inject
	private InstallmentDaoBean instDao;
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

		if (date != null) {
//			List<Installment> list = instDao
//					.getInstallmenteWithDeadlineEqualsTo(date);
			TypedQuery<Installment> query = em.createNamedQuery(
					"Installment.findInstallmentsWithNearDeadLine",Installment.class).setParameter(
					"date", new Date(date.getTimeInMillis()));
			 
			
			System.out.println("Sto eseguendo ContractDeadLineTimer");
			for(Installment i : query.getResultList()){
				
				try {
					mailSender.notifyDeadLine(i, true);
				} catch (IOException | TemplateException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			

			
		}
	}

}
