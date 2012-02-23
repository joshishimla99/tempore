package ar.fi.uba.tempore.test;

import ar.fi.uba.mail.RandomString;
import ar.fi.uba.mail.SendMailSSL;

public class TestSendMail {
	public static void main(String[] args) {
		RandomString random = new RandomString(5);
		String password = random.nextString();
		
		try{
			SendMailSSL mail= new SendMailSSL("lrinaudo", "rinaudo.ludmila@gmail.com", password);
			mail.sendMail();
		} catch (Exception e) {
			// TODO: handle exception
		}
			

	}
}
