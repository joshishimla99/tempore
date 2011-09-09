package ar.fi.uba.tempore.gwt.client.login;

import ar.fi.uba.tempore.gwt.client.panel.ConteinerMainPanel;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.ButtonItem;
import com.smartgwt.client.widgets.form.fields.HeaderItem;
import com.smartgwt.client.widgets.form.fields.PasswordItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.form.fields.events.ClickEvent;
import com.smartgwt.client.widgets.form.fields.events.ClickHandler;
import com.smartgwt.client.widgets.layout.VLayout;

public class LoginPanel  extends Composite{

	private VLayout layout = new VLayout(15);
	private DynamicForm formLogin = new DynamicForm();
	private TextItem login;
	private PasswordItem mdp ;
//	private final Logger log = Logger.getLogger(this.getClass());
	
	public LoginPanel(){
		
		DataSource dataSource = LoginDS.getInstance();
		layout.setPageLeft(800);
		initWidget(layout);
		formLogin.setShowEdges(true);
		formLogin.setEdgeShowCenter(true);
		formLogin.setEdgeImage("../images/login/glow_35.png");
		formLogin.setEdgeSize(35);
		formLogin.setEdgeOffset(25);
		formLogin.setDataSource(dataSource);
		formLogin.setUseAllDataSourceFields(true);
		
		HeaderItem header = new HeaderItem();
		header.setDefaultValue("Ingreso a Tempore");
		
		login = new TextItem();
		login.setName("login");
		login.setTitle("Usuario");
		
		mdp = new PasswordItem();
		mdp.setName("mdp");
		mdp.setTitle("Clave");
		mdp.setRequired(true);
		mdp.setLength(20);
		
		ButtonItem validateItem = new ButtonItem();   
        validateItem.setTitle("Ingresar");   
        validateItem.addClickHandler(new ClickHandler() {   
            public void onClick(ClickEvent event) {   
                if (formLogin.validate(false)){
                	// TODO: Crear la sesion
                	if (mdp.getValueAsString().equals("password") && login.getValueAsString().equals("user")){
                		Window.alert("Validacion correcta");
                		layout.clear();
                		
                		ConteinerMainPanel.getInstance().init();
                	} else{
                    	Window.alert("Validacion incorrecta" + " " + mdp.getValueAsString() +" " + login.getValueAsString() );
//                    	log.info("Login Error: " + login.getValueAsString() + " " + mdp.getValueAsString() );
                    }
                	
                }
            }   
        });
		
		formLogin.setFields(header,login,mdp,validateItem);
		
		layout.addMember(formLogin);
		
		layout.redraw();
	}
}
