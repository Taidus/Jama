package time;

import java.util.Calendar;
import java.util.Date;
import java.util.Timer;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;

import util.Config;

@ApplicationScoped
@Singleton
@Startup
@Named("scheduler")
public class Scheduler {

	@Inject
	private ContractDeadLineTimer contractDeadLineTimer;
	
	@Inject 
	private DepartmentImporter deptsImp;
	
	@Inject 
	private ChiefScientistImporter chiefImporter;
	
	private Timer timer;


	public Scheduler() {
		timer = new Timer();
	}


	@PostConstruct
	public void schedule() {

		scheduleNotifyTasks();
		scheduleImports();

	}
	
	private void scheduleNotifyTasks(){
		
		Calendar deadLineDate = Calendar.getInstance();
		deadLineDate.add(Calendar.DAY_OF_MONTH, Config.daysBeforeDeadlineExpriration);

		contractDeadLineTimer.setDate(deadLineDate);

		Calendar cal = Calendar.getInstance();
		if (cal.get(Calendar.HOUR_OF_DAY) >= Config.dailyScheduledTaskExecutionHour) {
			cal.add(Calendar.DAY_OF_MONTH, 1);
		}
		cal.set(Calendar.HOUR_OF_DAY, Config.dailyScheduledTaskExecutionHour);
		long period = 1000 * 60 * 60 * 24;
		timer.schedule(contractDeadLineTimer, new Date(cal.getTimeInMillis()), period);
		
	}
	
	private void scheduleImports(){
		//TODO mettere valori sensati
		
		Calendar date = Calendar.getInstance();
		date.add(Calendar.SECOND, 60*60);  //ogni ora
		timer.schedule(deptsImp, new Date(date.getTimeInMillis()));
		
	}

}
