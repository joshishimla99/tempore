package com.gemalto.contactdirectory.web.logout;

import com.gemalto.webcare.common.web.pages.AbstractLogout;



/**
 * Logout page.
 *
 * @author asalanova, jarias
 */
public class Logout extends AbstractLogout {

	/**
	 * Defauilt no args constructor.
	 */
	public Logout() {
		super();
		setResponsePage(getWebcareApplication().getSignInPageClass());
		setRedirect(true);
	}
	
}
