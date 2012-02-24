package ar.fi.uba.mail;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
 
public class SendMailSSL{
	
	private String passwordTempore="tempore1234";
	private String usernameTempore="temporeapplication";
	private String userName;
	private String mail;
	private String password;
	
	public SendMailSSL(String userName, String email, String password){
		this.userName = userName;
		this.mail = email;
		this.password = password;
	}
	
	public void sendMail() throws AddressException, MessagingException {
		Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.socketFactory.class",
				"javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", "465");
 
		Session session = Session.getInstance(props,
			new javax.mail.Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(usernameTempore, passwordTempore);
				}
			});
 
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("from@no-spam.com"));
			message.setRecipients(Message.RecipientType.TO,
					InternetAddress.parse(this.mail));
			message.setSubject("Tempore - Recupero de Clave");
			message.setText("Estimado " + this.userName +  
					", \n\n La nueva clave para ingresar a Tempore es: " + this.password + "\n\nSaludos cordiales, \nEl equipo de Tempore.");
 
			Transport.send(message);
	}
}
