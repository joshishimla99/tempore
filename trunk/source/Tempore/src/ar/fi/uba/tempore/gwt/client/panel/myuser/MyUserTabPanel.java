package ar.fi.uba.tempore.gwt.client.panel.myuser;

import ar.fi.uba.tempore.dto.UserDTO;
import ar.fi.uba.tempore.gwt.client.UserServicesClient;
import ar.fi.uba.tempore.gwt.client.login.SessionUser;
import ar.fi.uba.tempore.gwt.client.panel.TabsPanelContainer;
import ar.fi.uba.temporeutils.image.ImgClient;
import ar.fi.uba.temporeutils.image.UpdateImgHandler;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.HiddenItem;
import com.smartgwt.client.widgets.form.fields.PasswordItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

public class MyUserTabPanel extends TabsPanelContainer {

	public static final String ID = "idCol";
	public static final String NAME = "nameCol";
	public static final String LAST_NAME = "lastNameCol";
	public static final String USER_NAME = "userNameCol";
	public static final String PASSWORD = "passwordCol";
	public static final String EMAIL = "emailCol";
	public static final String ADDRESS = "addressCol";
	public static final String COMPANY = "companyCol";
	public static final String COUNTRY = "countryCol";
	public static final String PHONE = "phoneCol";
	public static final String ZIP_CODE = "zipCodeCol";
	public static final String IMAGE_NAME = "imageName";
	public static final String IS_ADMIN = "isAdmin";


	private static final String HELPTEXT = "";
	private DynamicForm form;
	private final ImgClient image;

	public MyUserTabPanel() {
		super();  
		final Label title = new Label("Mi Cuenta");
		title.setWidth(200);
		title.setHeight(15);
		title.setIcon("[SKIN]/actions/help.png");
		title.setStyleName("Informal");
		title.setIconOrientation("right");
		title.addIconClickHandler(new com.smartgwt.client.widgets.events.IconClickHandler() {
			@Override
			public void onIconClick(
					com.smartgwt.client.widgets.events.IconClickEvent event) {
				SC.say(HELPTEXT);
			}
		});

		final HiddenItem imageText = new HiddenItem(IMAGE_NAME);
		final TextItem name = new TextItem(NAME, "Nombre");
		name.setRequired(true);
		final TextItem lastname = new TextItem(LAST_NAME, "Apellido");
		lastname.setRequired(true);
		final TextItem userName = new TextItem(USER_NAME, "Usuario");
		userName.setDisabled(true);
		userName.setRequired(true);
		final PasswordItem password = new PasswordItem(PASSWORD, "Contrase&ntilde;a");
		password.setRequired(true);
		final TextItem email = new TextItem(EMAIL, "Email");
		email.setRequired(true);
		final TextItem address = new TextItem(ADDRESS, "Direcci&oacute;n");
		final TextItem country = new TextItem(COUNTRY, "Pa&iacute;s");
		final TextItem phone = new TextItem(PHONE, "Tel&eacute;fono");
		final TextItem zip = new TextItem(ZIP_CODE, "C&oacute;digo Postal");
		final HiddenItem admin = new HiddenItem(IS_ADMIN);

		form = new DynamicForm();
		form.setNumCols(4);
		form.setMargin(10);
		form.setFields(userName,name,lastname,password,email,address,country,phone,zip,imageText, admin);

		image = new ImgClient(imageText.getValueField());

		image.setWidth(150);
		image.setHeight(150);
		image.addUpdateImageHandler(new UpdateImgHandler() {
			@Override
			public void onUpdatedImg(String fileName) {
				//Actualizo el nombre del archivo
				imageText.setValue(fileName);
			}
		});				

		IButton saveButton = new IButton("Guardar");
		saveButton.setIcon("../images/ico/save.ico");
		saveButton.addClickHandler(saveData);  

		IButton cancelButton = new IButton("Cancelar");  
		cancelButton.setIcon("../images/ico/back.ico");
		cancelButton.addClickHandler(resetData);  

		//Botones
		final HLayout hLayoutBtn = new HLayout(10);  
		hLayoutBtn.setAlign(Alignment.CENTER);  
		hLayoutBtn.addMember(saveButton);  
		hLayoutBtn.addMember(cancelButton);  


		final VLayout vLayout = new VLayout(5);
		vLayout.setPadding(25);
		vLayout.addMember(form);  
		vLayout.addMember(hLayoutBtn);
		
		HLayout hLayoutRoot = new HLayout(30);
		hLayoutRoot.addMember(vLayout);
		hLayoutRoot.addMember(image);

		final VLayout mainLayout = new VLayout();
		mainLayout.addMember(title);
		mainLayout.addMember(hLayoutRoot);
		
		this.addChild(mainLayout);
	}

	private ClickHandler saveData = new ClickHandler() {
		@Override
		public void onClick(ClickEvent event) {
			if (form.validate()){
				UserDTO userDTO = new UserDTO();
				copy(form, userDTO);
				UserServicesClient.Util.getInstance().updateNotAdmin(userDTO, new AsyncCallback<UserDTO>() {
					@Override
					public void onSuccess(UserDTO userDTO) {
						//Si actualizacion correcta actualizar
						SessionUser.getInstance().setUser(userDTO);
						SC.say("Datos del usuario Actualizado!");
					}
					@Override
					public void onFailure(Throwable caught) {
						SC.warn("Error al intentar actualizar los datos del usuario");
					}
				});
			}
		}
	};

	private ClickHandler resetData = new ClickHandler() {
		@Override
		public void onClick(ClickEvent event) {
			form.reset();
		}
	};

	private void fetchData() {
		UserDTO userDTO = SessionUser.getInstance().getUser();
		copy(userDTO, form);
	}

	private void copy(UserDTO userDTO, DynamicForm form) {
		// TODO Auto-generated method stub
		form.setValue(ID, userDTO.getId());
		form.setValue(NAME, userDTO.getName());
		form.setValue(LAST_NAME, userDTO.getLastName());
		form.setValue(USER_NAME, userDTO.getUserName());
//		No se copia aproposito, para que se cargue nuevamente el pass al cambiar
//		form.setValue(PASSWORD, userDTO.getPassword());
		form.setValue(EMAIL, userDTO.getEmail());
		form.setValue(ADDRESS, userDTO.getAddress());
		form.setValue(COUNTRY, userDTO.getCountry());
		form.setValue(PHONE, userDTO.getPhone());
		form.setValue(ZIP_CODE, userDTO.getZipCode());
		form.setValue(IMAGE_NAME, userDTO.getImageName());
		form.setValue(IS_ADMIN, userDTO.getAdmin());

		image.updateImage(userDTO.getImageName());
		image.redraw();
	}

	private void copy(DynamicForm form, UserDTO u) {
		u.setId(new Integer(form.getValue(ID).toString()));
		u.setName(form.getValue(NAME).toString());
		u.setLastName(form.getValue(LAST_NAME).toString());
		u.setUserName(form.getValue(USER_NAME).toString());
		u.setPassword(form.getValue(PASSWORD).toString());
		u.setEmail(form.getValue(EMAIL).toString());
		u.setAddress((String)form.getValue(ADDRESS));
		u.setCountry((String)form.getValue(COUNTRY));
		u.setPhone((String)form.getValue(PHONE));
		u.setZipCode((String)form.getValue(ZIP_CODE));
		u.setImageName((String)form.getValue(IMAGE_NAME));
		u.setAdmin((String)form.getValue(IS_ADMIN));
	}


	@Override
	public void refreshPanel() {
		fetchData();
	}

	@Override
	public void freePanel() {
		//No hace falta realizar nada
	}
}
