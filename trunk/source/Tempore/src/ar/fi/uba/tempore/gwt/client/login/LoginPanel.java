package ar.fi.uba.tempore.gwt.client.login;

import ar.fi.uba.tempore.dto.UserDTO;
import ar.fi.uba.tempore.gwt.client.UserServicesClient;
import ar.fi.uba.tempore.gwt.client.panel.ConteinerMainPanel;
import ar.fi.uba.tempore.gwt.client.panel.HeaderPanel;


import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.RootPanel;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.VerticalAlignment;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.Img;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.events.CloseClickEvent;
import com.smartgwt.client.widgets.events.CloseClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.ButtonItem;
import com.smartgwt.client.widgets.form.fields.PasswordItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

public class LoginPanel extends Composite{

	private static final String USER = "User";
	private static final String PASSWORD = "Password";
	private static final String USERNAME = "Username";
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
		
		formLogin.setAutoFocus(true);
		formLogin.setFields(userText, passText);
		
		error.setWidth(250);
		error.setHeight(20);
		error.setVisible(false);
		error.setStyleName("label-errorMessages");
		

		final IButton submit = new IButton();
		submit.setHeight(35);
		submit.setTitle("Acceder");
		submit.setShowRollOver(true);  
        submit.setShowDisabled(true);  
        submit.setShowDown(true);  
        submit.setTitleStyle("stretchTitle"); 
		submit.setAlign(Alignment.CENTER);


		//Recuperar contrase�a
		final Label link = new Label("Recuperar Contrase&ntilde;a");
//		link.setHeight(30);
		link.setWidth(150);
		link.setAlign(Alignment.CENTER);
		link.setStyleName("clickable");
		link.addClickHandler(recoverPass);
		
		final HLayout hLayout = new HLayout();
//		hLayout.setMembersMargin(5);
		hLayout.setAlign(VerticalAlignment.CENTER);
		hLayout.addMember(submit);
		hLayout.addMember(link);
		

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
//		logginLayout.addMember(submit);
//		logginLayout.addMember(link);
		logginLayout.addMember(hLayout);
		

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


	private ClickHandler recoverPass = new ClickHandler() {  
		public void onClick(ClickEvent event) {  

			final Window winModal = new Window();  
			winModal.setWidth(360);  
			winModal.setHeight(225); 
			winModal.setTitle("Recuperar Contrase&ntilde;a");  
			winModal.setShowMinimizeButton(false);  
			
			winModal.setIsModal(true);  
			winModal.setShowModalMask(true);  
			winModal.centerInPage();  
			winModal.addCloseClickHandler(new CloseClickHandler() {  
				public void onCloseClick(CloseClickEvent event) {  
					winModal.destroy();  
				}  
			});  
			//Formulario para completar

			final Label errorRecoveryPassword = new Label("Usuario inv&aacute;lido");
			errorRecoveryPassword.setVisible(false);
			
			final DynamicForm formRecoveryPassword = new DynamicForm();  
			formRecoveryPassword.setHeight(120);  
			formRecoveryPassword.setWidth100();  
			formRecoveryPassword.setPadding(10);  
			formRecoveryPassword.setLayoutAlign(VerticalAlignment.BOTTOM);  

			final TextItem userItem = new TextItem(USERNAME, "Nombre de usuario");
			userItem.setRequired(true);
			//userItem.setLength(250);
			//userItem.setLength(20);
			userItem.setWidth(200);
			userItem.setValue("ngarcia");
			final Canvas indicationLabel = new Canvas();  
			indicationLabel.setContents("<b>&iquest; Ha olvidado su contrase&ntilde;a?</b><br><br>La aplicaci&oacute;n le enviar&aacute; la nueva contrase&ntilde;a al mail configurado del usuario.");
			indicationLabel.setPadding(5);
			indicationLabel.setHeight(50);
			final VLayout vLayoutRP = new VLayout();
			final ButtonItem acceptButton = new ButtonItem();
			acceptButton.setTitle("Enviar");
			acceptButton.setWidth(90);
			acceptButton.setIcon("../images/ico/mail.ico");
			acceptButton.setShowDisabled(true);  
			acceptButton.setTitleStyle("stretchTitle"); 
			acceptButton.setAlign(Alignment.CENTER);
			acceptButton.addClickHandler(new com.smartgwt.client.widgets.form.fields.events.ClickHandler() {
				@Override
				public void onClick(com.smartgwt.client.widgets.form.fields.events.ClickEvent event) {
					if (formRecoveryPassword.validate()){
						
						final String userName = formRecoveryPassword.getValueAsString(USERNAME);
						UserServicesClient.Util.getInstance().validateUserName(userName , new AsyncCallback<Boolean>() {
							@Override
							public void onSuccess(Boolean isUserValid) {
								if (isUserValid) {
									//Existe el usuario enviamos mail con la nueva contrase�a
									UserServicesClient.Util.getInstance().recoveryUserPassword(userName , new AsyncCallback<Void>(){
										@Override
										public void onSuccess(Void result) {
											winModal.destroy();
											SC.say("Se ha enviado el mail con su nuevo password");													
										}
										@Override
										public void onFailure(Throwable caught) {
											winModal.destroy();
											SC.say("Ha ocurrido un error al intentar enviar el mail");
										}
									});
								} else {
									//No existe el usuario 
									errorRecoveryPassword.setVisible(true);
									//errorRecoveryPassword.setContents("Usuario inv&aacute;lido");
								}
							}
							@Override
							public void onFailure(Throwable caught) {
								SC.warn("Error al intentar validar el usuario");
								vLayoutRP.redraw();
							}
						});	
					}

				}
			}); 


			formRecoveryPassword.setFields(userItem, acceptButton);

			winModal.addItem(indicationLabel);
			vLayoutRP.addChild(formRecoveryPassword);  
			vLayoutRP.addChild(errorRecoveryPassword);
			winModal.addItem(vLayoutRP);
			winModal.show();  
		}  
	};  



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
