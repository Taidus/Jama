package util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import org.joda.money.CurrencyUnit;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.TemplateExceptionHandler;
import freemarker.template.Version;

public class Config {

	public static int defaultPageSize = 30;

	private static final String fileDir = "../standalone/deployments/Jama.war/WEB-INF/classes";
	public static final String depRatesPath = fileDir + "/" + "aliquoteDipartimenti/";
	private static final String mailTemplateDir = fileDir + "/" + "mailTemplates";
	
	private static final String logFile = fileDir + "/" + "log";
	
	public static final Configuration fmconf;
	public static final String instDeadlineTemplateFileName = "template_scadenzaRata.ftl";
	public static final String contractCreationTemplateFileName = "template_creazioneContratto.ftl";
	public static final String contractClosureTemplateFileName = "template_chiusuraContratto.ftl";
	
	public static final CurrencyUnit currency = CurrencyUnit.EUR;
	public static final Locale locale = Locale.ITALY;

	// TODO file di configurazione
	public static final int dailyScheduledTaskExecutionHour = 3;
	public static final int daysBeforeDeadlineExpriration = 15;

	static {
		
            try {
				System.setErr(new PrintStream(new File(logFile)));
				Calendar date = Calendar.getInstance();
				System.err.println(new Date(date.getTimeInMillis())+"\n=========\n"+"Inizio attività di logging\n"+"==========");
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			}
		
		fmconf = new Configuration();

		// Specify the data source where the template files come from. Here I
		// set a
		// plain directory for it, but non-file-system are possible too:
		try {
			fmconf.setDirectoryForTemplateLoading(new File(Config.mailTemplateDir));
		} catch (IOException e) {
			e.printStackTrace();
		}

		// Specify how templates will see the data-model. This is an advanced
		// topic...
		// for now just use this:
		fmconf.setObjectWrapper(new DefaultObjectWrapper());

		// Set your preferred charset template files are stored in. UTF-8 is
		// a good choice in most applications:
		fmconf.setDefaultEncoding("UTF-8");

		// Sets how errors will appear. Here we assume we are developing HTML
		// pages.
		// For production systems TemplateExceptionHandler.RETHROW_HANDLER is
		// better.
		fmconf.setTemplateExceptionHandler(TemplateExceptionHandler.HTML_DEBUG_HANDLER);

		// At least in new projects, specify that you want the fixes that aren't
		// 100% backward compatible too (these are very low-risk changes as far
		// as the
		// 1st and 2nd version number remains):
		fmconf.setIncompatibleImprovements(new Version(2, 3, 20)); // FreeMarker
																	// 2.3.20
	}
}
