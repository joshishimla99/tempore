/**
 * 
 */
package com.gemalto.contactdirectory.web.pages;

import com.gemalto.contactdirectory.web.panels.AccessDeniedPanel;
import com.gemalto.webcare.common.web.pages.WebcarePage;

/**
 * Custom access denied page for Self Care.
 * 
 * @author jarias
 */
public class AccessDenied extends WebcarePage {

	/**
	 * 
	 */
	public AccessDenied() {
		super(new AccessDeniedPanel("content"));
	}

}
