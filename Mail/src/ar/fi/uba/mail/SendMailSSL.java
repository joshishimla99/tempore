package ar.fi.uba.mail;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
 
public class SendMailSSL {
	
	private String passwordTempore="tempore1234";
	private String usernameTempore="temporeapplication";
	
	public SendMailSSL(){
		
	}
	
	public void sendMail(String username, String password, String destiny){
		Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.socketFactory.class",
				"javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", "465");
 
		Session session = Session.getDefaultInstance(props,
			new javax.mail.Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(usernameTempore, passwordTempore);
				}
			});
 
		try {
 
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("from@no-spam.com"));
			message.setRecipients(Message.RecipientType.TO,
					InternetAddress.parse(destiny));
			message.setSubject("Tempore - Recupero de Clave");
			message.setText("Estimado " + username +  
					", \n\n La nueva clave para ingresar a Tempore es: " + password + "\n\nSaludos cordiales, \nEl equipo de Tempore.");
 
			Transport.send(message);
 
		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}
}
