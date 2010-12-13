package com.gemalto.contactdirectory.web;

import com.gemalto.contactdirectory.web.logout.Logout;
import com.gemalto.contactdirectory.web.pages.AccessDenied;
import com.gemalto.contactdirectory.web.pages.BigIcons;
import com.gemalto.contactdirectory.web.pages.login.LoginSelfCare;
import com.gemalto.gus.web.pages.ChangePasswordSuccess;
import com.gemalto.gus.web.pages.PasswordRecovery;
import com.gemalto.gus.web.pages.PasswordRecoverySuccess;
import com.gemalto.gus.web.pages.Signup;
import com.gemalto.gus.web.pages.SignupSuccess;
import com.gemalto.gus.web.panels.ChangePasswordPanel;
import com.gemalto.gus.web.panels.SignupPanel;
import com.gemalto.webcare.common.web.LinksRolesManager;
import com.gemalto.webcare.common.web.NavigationResolver;

/**
 * 
 */
public class NavigationResolverConfiguration extends LinksRolesManager {

	/**
	 * {@inheritDoc}
	 */
	public void configureNavigationResolver(NavigationResolver resolver) {
		// SignupPanel
		resolver.setLinkClass("success", SignupPanel.class, SignupSuccess.class);
		resolver.setLinkClass("cancel", SignupPanel.class, LoginSelfCare.class);

		// Change Password
		resolver.setLinkClass("success", ChangePasswordPanel.class, ChangePasswordSuccess.class);
		resolver.setLinkClass("cancel", ChangePasswordPanel.class, BigIcons.class);

		// Global keys
		resolver.setGlobalLinkClass("passwordRecovery", PasswordRecovery.class);
		resolver.setGlobalLinkClass("passwordRecoverySuccess", PasswordRecoverySuccess.class);
		resolver.setGlobalLinkClass("signup", Signup.class);

		// Welcome
		resolver.setGlobalLinkClass("welcome", BigIcons.class);
		resolver.setGlobalLinkClass("welcome", resolver.getSignInClass());

		resolver.setGlobalLinkClass("logout", Logout.class);

		resolver.setGlobalLinkClass("home", BigIcons.class);

		// access denied
		resolver.setGlobalLinkClass("denied", AccessDenied.class);
	}

}
