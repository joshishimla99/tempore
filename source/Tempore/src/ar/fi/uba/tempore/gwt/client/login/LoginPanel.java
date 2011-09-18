package ar.fi.uba.tempore.gwt.client.login;

import ar.fi.uba.tempore.gwt.client.panel.ConteinerMainPanel;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.RootPanel;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.widgets.Img;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.ButtonItem;
import com.smartgwt.client.widgets.form.fields.HeaderItem;
import com.smartgwt.client.widgets.form.fields.PasswordItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.form.fields.events.ClickEvent;
import com.smartgwt.client.widgets.form.fields.events.ClickHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

public class LoginPanel  extends Composite{

	private HLayout layout = new HLayout(15);
	private DynamicForm formLogin = new DynamicForm();
	private TextItem login;
	private PasswordItem mdp ;
	
	public LoginPanel(){
		
		DataSource dataSource = LoginDS.getInstance();
		layout.setPageLeft(240);
		initWidget(layout);
		formLogin.setShowEdges(true);
		formLogin.setEdgeShowCenter(true);
		formLogin.setEdgeImage("../images/login/glow_35.png");
		formLogin.setEdgeSize(35);
		formLogin.setEdgeOffset(45);
		formLogin.setDataSource(dataSource);
		formLogin.setUseAllDataSourceFields(true);
				
		HeaderItem headerTitle = new HeaderItem();
		headerTitle.setDefaultValue("Ingreso a Tempore");
		headerTitle.setAlign(Alignment.CENTER);
		
		login = new TextItem();
		login.setName("login");
		login.setTitle("Usuario");
		
		mdp = new PasswordItem();
		mdp.setName("mdp");
		mdp.setTitle("Clave");
		mdp.setRequired(true);
		
		ButtonItem validateItem = new ButtonItem();   
        validateItem.setTitle("Ingresar");   
        validateItem.setWidth(75);
        validateItem.setAlign(Alignment.CENTER);
        validateItem.addClickHandler(new ClickHandler() {   
            public void onClick(ClickEvent event) {   
                if (formLogin.validate(false)){
                	// TODO: Crear la sesion
                	if (mdp.getValueAsString().equals("password") && login.getValueAsString().equals("user")){
                		layout.clear();
                		loadUser();
                		RootPanel.get("Content").add(new ConteinerMainPanel());
                	} else{
                    	Window.alert("Validacion incorrecta" + " " + mdp.getValueAsString() +" " + login.getValueAsString() );
                    }
                	
                }
            }

        });
        formLogin.focusInItem("login");
		formLogin.setFields(headerTitle, login,mdp,validateItem);
		
		/*
		 * Imagenes y texto de la izquierda de la pagina del login
		 * */
		
		Label space1 = new Label("");
		
		/*Quick
		 * */
		HLayout hLayoutQuick = new HLayout();
		Label titleQuick = new Label("Rapida");
		LoginImg  pictureQuick = new LoginImg ("Clock.png", "quick");
		Label labelQuick = new Label("Tempore te permite cargar tus horas en forma rapida por medio de diversas formas de acceso al alcance de tu mano.");
		labelQuick.setWidth(300);
		hLayoutQuick.addMember(pictureQuick);
		hLayoutQuick.addMember(space1);
		hLayoutQuick.addMember(titleQuick);
		hLayoutQuick.addMember(labelQuick);
		/* Simple
		 * */
		Label space2 = new Label("");
		HLayout hLayoutSimple = new HLayout();
		Label titleSimple = new Label("Simple");
		LoginImg pictureSimple = new LoginImg("Template.png", "simple");
		Label labelSimple = new Label("Tempore es simple dado que brinda diferentes medios de acceso para que escogas el mas simple para ti.");
		labelSimple.setWidth(300);
		hLayoutSimple.addMember(pictureSimple);
		hLayoutSimple.addMember(space2);
		hLayoutSimple.addMember(titleSimple);
		hLayoutSimple.addMember(labelSimple);
		/*
		 * */
		Label space3 = new Label("");
		HLayout hLayoutFriendly = new HLayout();
		Label titleFriendly = new Label("Amigable");
		LoginImg pictureFriendly = new LoginImg("Chat.png", "friednly");
		Label labelFriendly = new Label("Tempore es simple dado que brinda diferentes medios de acceso para que escogas el mas simple para ti.");
		labelFriendly.setWidth(300);
		hLayoutFriendly.addMember(pictureFriendly);
		hLayoutFriendly.addMember(space3);
		hLayoutFriendly.addMember(titleFriendly);
		hLayoutFriendly.addMember(labelFriendly);
		
		
		VLayout vLayout = new VLayout();
		vLayout.setWidth("600 px");
		vLayout.addMember(hLayoutQuick);
		vLayout.addMember(hLayoutSimple);
		vLayout.addMember(hLayoutFriendly);
		
		layout.addMember(vLayout);
		layout.addMember(formLogin);
		
		layout.redraw();
	}
	
	private void loadUser() {
		
	}
	
	public static class LoginImg extends Img {
		public LoginImg(String src, String name) {
			setSrc(src);
			setWidth(48);
			setHeight(48);
			setID(name);
			setAppImgDir("../images/64x64/");
		}

	}
}
