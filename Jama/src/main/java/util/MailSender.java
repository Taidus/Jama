package util;

import java.io.IOException;
import java.io.Serializable;
import java.io.StringWriter;

import javax.annotation.Resource;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;

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

	private void _send(String recipientEmail, String subject, String text) {
		//TODO addRecipient etc

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

		} catch (MessagingException e) {// TODO errore a video
			e.printStackTrace();
		}
	}


	private void spam() {
		// XXX inutile ai fini della business logic, ma chi non vorrebbe mandare
		// spam a Damaz?
//		_send("damaz91@live.it", "Promozione", "Sei Stato promosso al grado di maggiore nella DeltaSpikeForce!");
	}
	
	public void test(){
		_send("tommaso.levato@stud.unifi.it", "Funziona!", "Yay!");
	}


	public void notifyCreation(Contract c) throws TemplateException, IOException {
		System.out.println("mail di notifica creazione contratto simulata");
		//
		// ContractTemplateFiller filler = new ContractTemplateFiller(c,
		// "pippo@jama.jam");
		// StringWriter out = new StringWriter();
		// // variabile di tipo StringWriter perché un Writer qualunque non va
		// // bene: serve che il metodo toString() restituisca esattamente la
		// // stringa che rappresenta il contenuto della mail
		//
		// Template temp =
		// Config.fmconf.getTemplate(Config.contractCreationTemplateFileName);
		// temp.process(filler, out);
		// String mailContent = out.toString();
		//
		// spam();
		//
		// _send("giulio.galvan@stud.unifi.it", "Jama: nuovo contratto",
		// mailContent);
		// _send("tommaso.levato@stud.unifi.it", "Jama: nuovo contratto",
		// mailContent);
		// _send("alessio.sarullo@stud.unifi.it", "Jama: nuovo contratto",
		// mailContent);
		//
		// System.out.println(" °°°°°°°°° Mail inviata! °°°°°°°°°°°°°");
		//
		// FacesContext.getCurrentInstance().addMessage(null, new
		// FacesMessage(FacesMessage.SEVERITY_INFO, "Mail inviata", null));

	};


	// TODO implementare
	public void notifyClosure(Contract c) throws IOException, TemplateException {
		System.out.println("mail di notifica chiusura contratto simulata");
		//
		// ContractTemplateFiller filler = new ContractTemplateFiller(c,
		// "pippo@jama.jam");
		// StringWriter out = new StringWriter();
		// // variabile di tipo StringWriter perché un Writer qualunque non va
		// // bene: serve che il metodo toString() restituisca esattamente la
		// // stringa che rappresenta il contenuto della mail
		//
		// Template temp =
		// Config.fmconf.getTemplate(Config.contractClosureTemplateFileName);
		// temp.process(filler, out);
		// String mailContent = out.toString();
		//
		// spam();
		//
		// _send("giulio.galvan@stud.unifi.it", "Jama: chiusura contratto",
		// mailContent);
		// _send("tommaso.levato@stud.unifi.it", "Jama: chiusura contratto",
		// mailContent);
		// _send("alessio.sarullo@stud.unifi.it", "Jama: chiusura contratto",
		// mailContent);
		//
		// System.out.println(" °°°°°°°°° Mail inviata! °°°°°°°°°°°°°");
		//
		// FacesContext.getCurrentInstance().addMessage(null, new
		// FacesMessage(FacesMessage.SEVERITY_INFO, "Mail inviata", null));

	}


	public void notifyDeadline(Installment inst) throws IOException, TemplateException {
		// alla fine rimarrà solo questo metodo, l'overload verrà cancellato
		notifyDeadline(inst, true);
	}


	public void notifyDeadline(Installment inst, boolean actuallySend) throws IOException, TemplateException {
		// TODO il campo actuallySend serve per non intasare le mail in fase di
		// sviluppo e testing. Va eliminato
		actuallySend = false;

		InstallmentTemplateFiller filler = new InstallmentTemplateFiller(inst, "pluto@jama.jam", "topolino@jama.jam");
		StringWriter out = new StringWriter();
		// variabile di tipo StringWriter perché un Writer qualunque non va
		// bene: serve che il metodo toString() restituisca esattamente la
		// stringa che rappresenta il contenuto della mail

		Template temp = Config.fmconf.getTemplate(Config.instDeadlineTemplateFileName);
		temp.process(filler, out);
		String mailContent = out.toString();

		// System.out.println("\n********************\n" + mailContent +
		// "\n*********************");
		spam();

		if (actuallySend) {
			_send("giulio.galvan@stud.unifi.it", "Jama: la scadenza è vicina", mailContent);
			_send("tommaso.levato@stud.unifi.it", "Jama: la scadenza è vicina", mailContent);
			_send("alessio.sarullo@stud.unifi.it", "Jama: la scadenza è vicina", mailContent);

			System.out.println(" °°°°°°°°° Mail inviata! °°°°°°°°°°°°°");
		} else {
			System.out.println(" °°°°°°°°° Mail di spam inviata! °°°°°°°°°°°°°");
		}

		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Mail inviata", null));
	}



	public static class InstallmentTemplateFiller {
		private Contract contract;
		private Installment installment;
		private String mail1, mail2;
		private Integer installmentNumber;


		public InstallmentTemplateFiller(Installment installment, String mail1, String mail2) {
			super();
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

	}



	public static class ContractTemplateFiller {
		private Contract contract;
		private String mail;


		public ContractTemplateFiller(Contract contract, String mail) {
			super();
			this.contract = contract;
			this.mail = mail;
		}


		public Contract getContract() {
			return contract;
		}


		public String getMail() {
			return mail;
		}

	}

}
