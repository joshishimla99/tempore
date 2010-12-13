package com.gemalto.contactdirectory.web.pages.login;

import java.io.Serializable;

import org.apache.wicket.Component;
import org.apache.wicket.PageParameters;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.PageLink;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;

import com.gemalto.webcare.common.web.pages.AbstractLogin;

/**
 * Login page.
 * 
 * @author asalanova
 */
public class Login extends AbstractLogin {

	private FeedbackPanel feedbackPanel;

	/**
	 * Creates a new sign-in page with the given parameters (ignored).
	 * 
	 * @param parameters
	 *            page parameters (ignored)
	 */
	public Login(final PageParameters parameters) {
		super(parameters);
		add(getSignInForm(new CompoundPropertyModel(new SimpleUser())));
		add(new SignupForm());
	}

	protected Component getSignInForm(IModel model) {
		return new SignInForm(model);
	}

	/**
	 * The class <code>SignInForm</code> is a subclass of the Wicket {@link Form} class that attempts to authenticate
	 * the login request using Wicket auth (which again delegates to Phonebook Session).
	 */
	protected class SignInForm extends Form {
		/**
		 * @param model
		 *            user form model
		 */
		public SignInForm(IModel model) {
			super("loginform", model);
			feedbackPanel = new FeedbackPanel("feedback");
			add(feedbackPanel);
			add(new TextField("username").setRequired(true));
			add(new PasswordTextField("password").setResetPassword(true));
			add(new PageLink("passwordRecovery", getWebcareApplication().getLinkClass(Login.class, "passwordRecovery")));
		}

		protected String getUsername() {
			SimpleUser user = (SimpleUser) getModelObject();
			return user.getUsername();
		}

		/**
		 * Called upon form submit. Attempts to authenticate the user.
		 */
		protected void onSubmit() {
			SimpleUser user = (SimpleUser) getModelObject();
			authenticate(user.getUsername(), user.getPassword());
		}
	}

	/**
	 * Form for redirect to signup.
	 * 
	 * @author jarias
	 */
	private final class SignupForm extends Form {

		public SignupForm() {
			super("signupForm");
		}

		protected void onSubmit() {
			setResponsePage(getWebcareApplication().getLinkClass(this.getClass(), "signup"));
		}

	}

	/**
	 * User para el formulario de login. TODO (jarias) No esta de mas esta clase??.
	 * 
	 * @author jarias
	 */
	public static class SimpleUser implements Serializable {
		private static final long serialVersionUID = -5617176504597041829L;

		private String username;
		private String password;

		/**
		 * @return the username
		 */
		public String getUsername() {
			return username;
		}

		/**
		 * @param username
		 *            the username to set
		 */
		public void setUsername(String username) {
			this.username = username;
		}

		/**
		 * @return the password
		 */
		public String getPassword() {
			return password;
		}

		/**
		 * @param password
		 *            the password to set
		 */
		public void setPassword(String password) {
			this.password = password;
		}

	}

}
