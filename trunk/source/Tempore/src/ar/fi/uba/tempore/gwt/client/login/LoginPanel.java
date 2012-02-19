package ar.fi.uba.tempore.gwt.client.login;

import ar.fi.uba.tempore.dto.UserDTO;
import ar.fi.uba.tempore.gwt.client.UserServicesClient;
import ar.fi.uba.tempore.gwt.client.panel.ConteinerMainPanel;
import ar.fi.uba.tempore.gwt.client.panel.HeaderPanel;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.RootPanel;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.Img;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.PasswordItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

public class LoginPanel extends Composite{

	private static final String USER = "User";
	private static final String PASSWORD = "Password";
	private final DynamicForm formLogin = new DynamicForm();
	final Label error = new Label("Usuario y/o Contrase&ntilde;a invalidas");

	public LoginPanel(){

		final Label headerTitle = new Label("Ingreso a Tempore");
		headerTitle.setWidth(250);
		headerTitle.setStyleName("logginHeader");
		headerTitle.setHeight(20);
		headerTitle.setAlign(Alignment.CENTER);

		final TextItem userText = new TextItem(USER);
		userText.setTitle("Usuario");
		userText.setSelectOnFocus(true);
		userText.setAlign(Alignment.LEFT);
		userText.setRequired(true);
		
		
		final PasswordItem passText = new PasswordItem(PASSWORD);
		passText.setTitle("Contrase&ntilde;a");
		passText.setRequired(true);
		passText.setAlign(Alignment.LEFT);
		
		error.setWidth(250);
		error.setHeight(20);
		error.setVisible(false);
		error.setStyleName("label-errorMessages");
		

		final IButton submit = new IButton();
		submit.setTitle("Acceder");
		submit.setShowRollOver(true);  
        submit.setShowDisabled(true);  
        submit.setShowDown(true);  
        submit.setTitleStyle("stretchTitle"); 
		
			
		formLogin.setAutoFocus(true);
		formLogin.setFields(userText, passText);
		
		final VLayout logginLayout = new VLayout();
		logginLayout.setShowEdges(true);
		logginLayout.setEdgeShowCenter(true);
		logginLayout.setEdgeImage("../images/login/glow_35.png");
		logginLayout.setEdgeSize(15);
		logginLayout.setLayoutMargin(20);
		logginLayout.setMembersMargin(15);
		logginLayout.setWidth(250);
		logginLayout.setBackgroundColor("rgb(216,214,248)");

		logginLayout.addMember(headerTitle);
		logginLayout.addMember(formLogin);
		logginLayout.addMember(error);
		logginLayout.addMember(submit);		

		//Textos introductorios
		final VLayout textVLayout = new VLayout();
		textVLayout.setWidth(750);
		addMember(textVLayout);
		
		final HLayout layout = new HLayout(15);
		layout.setHeight(200);
		layout.addMember(textVLayout);
		layout.addMember(logginLayout);
		layout.setLayoutLeftMargin(150);
		
		final VLayout main = new VLayout();
		main.setWidth100();
		main.setHeight100();
		main.setLayoutTopMargin(30);
		main.setMembersMargin(70);
		main.addMember(new HeaderPanel());
		main.addMember(layout);
		
		initWidget(main);
		
		
		
		final LoginPanel thisPanel = this;
		submit.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
				error.setVisible(false);
				logginLayout.redraw();
				if (formLogin.validate()){
					UserServicesClient.Util.getInstance().validateUser(formLogin.getValueAsString(USER),formLogin.getValueAsString(PASSWORD), new AsyncCallback<UserDTO>() {
						@Override
						public void onSuccess(UserDTO result) {
							if (result != null) {
								//LOGGIN SUCCESS
								SessionUser.getInstance().setUser(result);
								RootPanel.get("Content").add(new ConteinerMainPanel());
								
								//TODO ver de hacer el login correctamente
								thisPanel.setVisible(false);
							} else {
								error.setVisible(true);
							}
						}
						@Override
						public void onFailure(Throwable caught) {
							SC.warn("Error al intentar validar usuario");
						}
					});	
				}	
			}
		}); 
	}

	private void addMember(final VLayout textVLayout) {
		//Quick
		HLayout hLayoutQuick = new HLayout();
		Label space1 = new Label("");
		space1.setWidth(20);
		Label titleQuick = new Label("R&atilde;pida");
		titleQuick.setStyleName("loggin_title_text");
		LoginImg  pictureQuick = new LoginImg ("Clock.png", "quick");
		Label labelQuick = new Label("Tempore te permite cargar tus horas en forma rapida por medio de diversas formas de acceso al alcance de tu mano.");
		labelQuick.setWidth(300);
		hLayoutQuick.addMember(pictureQuick);
		hLayoutQuick.addMember(space1);
		hLayoutQuick.addMember(titleQuick);
		hLayoutQuick.addMember(labelQuick);
		//Simple
		HLayout hLayoutSimple = new HLayout();
		Label space2 = new Label("");
		space2.setWidth(20);
		Label titleSimple = new Label("Simple");
		titleSimple.setStyleName("loggin_title_text");
		LoginImg pictureSimple = new LoginImg("Template.png", "simple");
		Label labelSimple = new Label("Tempore es simple dado que brinda diferentes medios de acceso para que escogas el mas simple para ti.");
		labelSimple.setWidth(300);
		hLayoutSimple.addMember(pictureSimple);
		hLayoutSimple.addMember(space2);
		hLayoutSimple.addMember(titleSimple);
		hLayoutSimple.addMember(labelSimple);
		//Amigable
		HLayout hLayoutFriendly = new HLayout();
		Label space3 = new Label("");
		space3.setWidth(20);
		LoginImg pictureFriendly = new LoginImg("Chat.png", "friednly");
		Label titleFriendly = new Label("Amigable");
		titleFriendly.setStyleName("loggin_title_text");
		Label labelFriendly = new Label("Tempore es simple dado que brinda diferentes medios de acceso para que escogas el mas simple para ti.");
		labelFriendly.setWidth(300);
		hLayoutFriendly.addMember(pictureFriendly);
		hLayoutFriendly.addMember(space3);
		hLayoutFriendly.addMember(titleFriendly);
		hLayoutFriendly.addMember(labelFriendly);

		textVLayout.addMember(hLayoutQuick);
		textVLayout.addMember(hLayoutSimple);
		textVLayout.addMember(hLayoutFriendly);		
	}


	protected void login() {
			
	}


	/**
	 * 
	 * @author Nicolas
	 */
	public class LoginImg extends Img {
		public LoginImg(String src, String name) {
			this.setSrc(src);
			this.setWidth(48);
			this.setHeight(48);
			this.setID(name);
			this.setAppImgDir("../images/64x64/");
		}
	}
}
