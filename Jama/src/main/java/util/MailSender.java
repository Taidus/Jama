package util;

import java.io.IOException;
import java.io.Serializable;
import java.io.StringWriter;

import javax.annotation.Resource;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;

import usersManagement.User;
import annotations.Logged;
import businessLayer.Agreement;
import businessLayer.Installment;
import freemarker.template.Template;
import freemarker.template.TemplateException;

@Named
@SessionScoped
public class MailSender implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Resource(lookup = "java:jboss/mail/JaMail")
	private Session mailSession;

	@Inject
	@Logged
	private User loggedUser;

	private void sendToLoggedUser(String text, String subject) {
		// XXX eliminare?

		String recipientEmail = loggedUser.getEmail();
		_send(recipientEmail, subject, text);

	}

	private void _send(String recipientEmail, String subject, String text) {

		MimeMessage message = new MimeMessage(mailSession);
		try {
			message.setRecipient(RecipientType.TO, new InternetAddress(recipientEmail));
			message.setSubject(subject);
			message.setText(text);
			message.saveChanges();

			Transport.send(message);

		} catch (MessagingException e) {// TODO errore a video
			e.printStackTrace();
		}

	}

	private void spam() {
		// XXX inutile ai fini della business logic, ma chi non vorrebbe mandare
		// spam a Damaz?
		_send("damaz91@live.it", "Promozione", "Sei Stato promosso al grado di sergente nella DeltaSpikeForce!");
	}

	public void send(Installment inst) throws IOException, TemplateException {
		
		TemplateFiller filler = new TemplateFiller(inst, "pluto@jama.jam", "topolino@jama.jam");
		StringWriter out = new StringWriter();
		// variabile di tipo StringWriter perché un Writer qualunque non va
		// bene: serve che il metodo toString() restituisca esattamente la
		// stringa che rappresenta il contenuto della mail
		
		Template temp = Config.fmconf.getTemplate(Config.mailTemplateFileName);
		temp.process(filler, out);
		String mailContent = out.toString();

		//System.out.println("\n********************\n" + mailContent + "\n*********************");
		_send("giulio.galvan@stud.unifi.it", "Comunicazione da Jama", mailContent);
		_send("tommaso.levato@stud.unifi.it", "Comunicazione da Jama", mailContent);
		_send("alessio.sarullo@stud.unifi.it", "Comunicazione da Jama", mailContent);
		spam();
	}

	public static class TemplateFiller {
		private Agreement agreement;
		private Installment installment;
		private String mail1, mail2;
		private Integer installmentNumber;

		public TemplateFiller(Installment installment, String mail1, String mail2) {
			super();
			this.agreement = installment.getAgreement();
			this.installment = installment;
			this.mail1 = mail1;
			this.mail2 = mail2;
			this.installmentNumber = 1+ agreement.getInstallments().indexOf(installment);

			if (installmentNumber <= 0) {
				throw new IllegalStateException("Disallineamento tra la rata " + installment.getId() + " e la convenzione " + agreement.getId()
						+ " (la convenzione non ha un riferimento alla rata)");
			}
		}

		public Agreement getAgreement() {
			return agreement;
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

}
