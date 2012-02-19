package ar.fi.uba.mail.test;

import ar.fi.uba.mail.SendMailSSL;

public class TestSendMail {

	public static void main(String[] args) {
		SendMailSSL mail= new SendMailSSL();
		
		try {
			mail.sendMail("ngarcia", "khkhkjh", "garcian@gmail.com");
		} catch (RuntimeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
