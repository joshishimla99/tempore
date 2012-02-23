package ar.fi.uba.tempore.dao.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.naming.ServiceUnavailableException;

import sun.misc.BASE64Encoder;

public class PasswordServiceUtil {

	private static PasswordServiceUtil instance;

	  private PasswordServiceUtil()
	  {
	  }

	  public synchronized String encrypt(String plaintext) throws ServiceUnavailableException
	  {
	    MessageDigest md = null;
	    try
	    {
	      md = MessageDigest.getInstance("SHA"); //step 2
	    }
	    catch(NoSuchAlgorithmException e)
	    {
	      throw new ServiceUnavailableException(e.getMessage());
	    }
	    try
	    {
	      md.update(plaintext.getBytes("UTF-8")); //step 3
	    }
	    catch(UnsupportedEncodingException e)
	    {
	      throw new ServiceUnavailableException(e.getMessage());
	    }

	    byte raw[] = md.digest(); //step 4
	    String hash = (new BASE64Encoder()).encode(raw); //step 5
	    return hash; //step 6
	  }
	  
	  public static synchronized PasswordServiceUtil getInstance() //step 1
	  {
	    if(instance == null)
	    {
	       instance = new PasswordServiceUtil(); 
	    } 
	    return instance;
	  }
}
