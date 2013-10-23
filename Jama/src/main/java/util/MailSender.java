package util;

import java.io.Serializable;

import javax.annotation.Resource;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;

import annotations.Logged;
import usersManagement.User;

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

	public void sendToLoggedUser(String text,String subject){
		
		String recipientEmail = loggedUser.getEmail();
		send(recipientEmail, subject, text);
		
	}

	private void send(String recipientEmail, String subject, String text) {

		MimeMessage message = new MimeMessage(mailSession);
		try {
			message.setRecipient(RecipientType.TO, new InternetAddress(
					recipientEmail));
			message.setSubject(subject);
			message.setText(text);
			message.saveChanges();

			Transport.send(message);

		} catch (MessagingException e) {// TODO errore a video
			e.printStackTrace();
		}

	}

	public void spam() {
		MimeMessage message = new MimeMessage(mailSession);
		try {
			message.setRecipient(RecipientType.TO, new InternetAddress(
					"tommaso.levato@gmail.com"));
			message.setSubject("Promozione");
			message.setText("Sei Stato promosso al grado di sergente nella DeltaSpikeForce");
			message.saveChanges();

			Transport.send(message);
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
