package util;

import java.io.IOException;
import java.io.Serializable;
import java.io.StringWriter;
import java.util.Calendar;
import java.util.Date;

import javax.annotation.Resource;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;

import usersManagement.User;
import daoLayer.UserDaoBean;
import businessLayer.Agreement;
import businessLayer.AgreementInstallment;
import businessLayer.Contract;
import businessLayer.Installment;
import freemarker.template.Template;
import freemarker.template.TemplateException;

@Named
@SessionScoped
public class MailSender implements Serializable {
	private static final long serialVersionUID = 1L;

	@Resource(lookup = "java:jboss/mail/JaMail")
	private Session mailSession;

	// @Inject
	// @Logged
	// private User loggedUser;

	// TODO prevedere integrazione col LDAP!
	@Inject
	private UserDaoBean userDao;


	private void _send(String recipientEmail, String subject, String text) {
		// TODO addRecipient etc

		// MimeMessage message = new MimeMessage(mailSession);
		// try {
		// message.setRecipient(RecipientType.TO, new
		// InternetAddress(recipientEmail));
		// message.setSubject(subject);
		// message.setText(text);
		// message.saveChanges();
		//
		// Transport.send(message);
		//
		// } catch (MessagingException e) {// TODO errore a video
		// e.printStackTrace();
		// }

		if (recipientEmail != null) {

			String host = "smtp.gmail.com";
			String username = "jama.mail.services";
			String password = "pastrullo";

			MimeMessage message = new MimeMessage(mailSession);
			try {

				message.setRecipient(RecipientType.TO, new InternetAddress(recipientEmail));
				message.setSubject(subject);
				message.setText(text);
				message.saveChanges();

				Transport t = mailSession.getTransport("smtps");
				try {
					t.connect(host, username, password);
					t.sendMessage(message, message.getAllRecipients());
				} finally {
					t.close();
				}

			} catch (MessagingException e) {// TODO aggiungere growl opportuno
				FacesContext context = FacesContext.getCurrentInstance();
				context.addMessage(null, new FacesMessage(Messages.getString("err_sendingMail")));
			}
		} else {
			Date date = new Date(Calendar.getInstance().getTimeInMillis());
			System.err.println(date + ": Error sending email, null adress");
		}
	}


	private void spam() {
		// XXX inutile ai fini della business logic, ma chi non vorrebbe mandare
		// spam a Damaz?
		_send("damaz91@live.it", "Promozione", "Sei Stato promosso al grado di colonnello nella DeltaSpikeForce!");
	}


	public void test() {
		_send("tommaso.levato@stud.unifi.it", "Funziona!", "Yay!");
	}


	public void notifyCreation(Contract c) throws TemplateException, IOException {
		System.out.println("mail di notifica creazione contratto simulata");

		ContractTemplateFiller filler = new ContractTemplateFiller(c, "pippo@jama.jam");
		StringWriter out = new StringWriter();
		// variabile di tipo StringWriter perché un Writer qualunque non va
		// bene: serve che il metodo toString() restituisca esattamente la
		// stringa che rappresenta il contenuto della mail

		Template temp = Config.fmconf.getTemplate(Config.contractCreationTemplateFileName);
		temp.process(filler, out);
		String mailContent = out.toString();

		User u = userDao.getBySerialNumber(c.getChief().getSerialNumber());
		String email = u.getEmail();

		_send(email, "Jama: nuovo contratto", mailContent);

		System.out.println(" °°°°°°°°° Mail inviata! °°°°°°°°°°°°°");

		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Mail inviata", null));

	};


	public void notifyClosure(Contract c) throws IOException, TemplateException {
		System.out.println("mail di notifica chiusura contratto simulata");

		ContractTemplateFiller filler = new ContractTemplateFiller(c, "pippo@jama.jam");
		StringWriter out = new StringWriter();
		// variabile di tipo StringWriter perché un Writer qualunque non va
		// bene: serve che il metodo toString() restituisca esattamente la
		// stringa che rappresenta il contenuto della mail

		Template temp = Config.fmconf.getTemplate(Config.contractClosureTemplateFileName);
		temp.process(filler, out);
		String mailContent = out.toString();

		User u = userDao.getBySerialNumber(c.getChief().getSerialNumber());
		String email = u.getEmail();

		_send(email, "Jama: chiusura contratto", mailContent);

		System.out.println(" °°°°°°°°° Mail inviata! °°°°°°°°°°°°°");

		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Mail inviata", null));

	}


	public void notifyDeadline(Installment inst) throws IOException, TemplateException {
		// TODO il campo actuallySend serve per non intasare le mail in fase di
		// sviluppo e testing. Va eliminato

		// InstallmentTemplateFiller filler = new
		// InstallmentTemplateFiller(inst, "pluto@jama.jam",
		// "topolino@jama.jam");
		// StringWriter out = new StringWriter();
		// // variabile di tipo StringWriter perché un Writer qualunque non va
		// // bene: serve che il metodo toString() restituisca esattamente la
		// // stringa che rappresenta il contenuto della mail
		//
		// Template temp =
		// Config.fmconf.getTemplate(Config.instDeadlineTemplateFileName);
		// temp.process(filler, out);
		// String mailContent = out.toString();
		//
		// User u=
		// userDao.getBySerialNumber(inst.getContract().getChief().getSerialNumber());
		// String email = u.getEmail();
		//
		// _send(email, "Jama: la scadenza è vicina", mailContent);
		//
		//
		// FacesContext.getCurrentInstance().addMessage(null, new
		// FacesMessage(FacesMessage.SEVERITY_INFO, "Mail inviata", null));
	}



	public static class InstallmentTemplateFiller {
		private Contract contract;
		private Installment installment;
		private String mail1, mail2;
		private Integer installmentNumber;
		private String theContract;


		public InstallmentTemplateFiller(Installment installment, String mail1, String mail2) {
			super();
			if(installment instanceof AgreementInstallment){
				theContract = "alla convenzione";
			}
			else{
				theContract="al Contributo";
			}
			this.contract = installment.getContract();
			this.installment = installment;
			this.mail1 = mail1;
			this.mail2 = mail2;
			this.installmentNumber = 1 + contract.getInstallments().indexOf(installment);

			if (installmentNumber <= 0) {
				throw new IllegalStateException("Disallineamento tra la rata " + installment.getId() + " e la convenzione " + contract.getId()
						+ " (la convenzione non ha un riferimento alla rata)");
			}
		}


		public Contract getContract() {
			return contract;
		}


		public Installment getInstallment() {
			return installment;
		}


		public Integer getInstallmentNumber() {
			return installmentNumber;
		}


		public String getMail1() {
			return mail1;
		}


		public String getMail2() {
			return mail2;
		}


		public String getTheContract() {
			return theContract;
		}
		
		

	}



	public static class ContractTemplateFiller {
		private Contract contract;
		private String mail;
		private String theContract;



		public ContractTemplateFiller(Contract contract, String mail) {
			super();
			if(contract instanceof Agreement){
				theContract="la convenzione";
			}
			else{
				theContract="il contributo";
			}
			this.contract = contract;
			this.mail = mail;
		}


		public Contract getContract() {
			return contract;
		}


		public String getMail() {
			return mail;
		}
		
		public String getTheContract() {
			return theContract;
		}

	}

}
