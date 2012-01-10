package ar.fi.uba.tempore.gwt.client.panel.counter;

import java.util.Date;

import ar.fi.uba.tempore.dto.TempCounterDTO;
import ar.fi.uba.tempore.gwt.client.TempCounterServicesClient;
import ar.fi.uba.tempore.gwt.client.login.SessionUser;

import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.SelectionType;
import com.smartgwt.client.types.TimeDisplayFormat;
import com.smartgwt.client.util.BooleanCallback;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.ImgButton;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.IPickTreeItem;
import com.smartgwt.client.widgets.form.fields.TimeItem;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;


public class CounterTimePanel extends VLayout {

	public static final String COL_TASK_ID = "taskId";
	public static final String COL_NAME = "nameCol";
	public static final String COL_DESCRIPTION = "descriptionCol";
	public static final String COL_REPORT_TO = "ReportsTo";

	private static final int TIME_INTERVAL = 1000;
	private static final int GMT_3 = 10800000;

	private final TimeItem timerLabel = new TimeItem("Tiempo");
	private final Timer timer;
	private final IPickTreeItem selectTaskTree = new IPickTreeItem("tareas");
	private long contador = GMT_3;

	public CounterTimePanel(){
		super();
		this.setWidth100();
		this.setHeight100();



		Integer IMAGE_SIZE = 32;
		final ImgButton start = new ImgButton();
		start.setSize(IMAGE_SIZE);
		start.setSrc("../images/png/counter/play.png");
		start.setShowRollOver(false);
		start.setActionType(SelectionType.RADIO);
		start.setShowSelectedIcon(false);
		start.setShowDisabledIcon(false);
		start.setShowDown(false);
		start.setShowDownIcon(true);
		start.setRadioGroup("textTimer");	
		start.addClickHandler(startEvent );

		final ImgButton cancel = new ImgButton();
		cancel.setSize(IMAGE_SIZE);
		cancel.setSrc("../images/png/counter/cancel.png");
		cancel.setShowRollOver(false);
		cancel.setActionType(SelectionType.RADIO);  
		cancel.setRadioGroup("textTimer");
		cancel.addClickHandler(cancelEvent );

		final ImgButton pause = new ImgButton();
		pause.setSize(IMAGE_SIZE);
		pause.setSrc("../images/png/counter/pause.png");
		pause.setShowRollOver(false);
		pause.setActionType(SelectionType.RADIO);  
		pause.setRadioGroup("textTimer");
		pause.addClickHandler(pauseEvent);	

		final ImgButton save = new ImgButton();
		save.setSize(IMAGE_SIZE);
		save.setSrc("../images/png/counter/save.png");
		save.setShowRollOver(false);
		save.setActionType(SelectionType.RADIO);  
		save.setRadioGroup("textTimer");
		save.addClickHandler(saveEvent);		

		HLayout buttonsLayout = new HLayout();
		buttonsLayout.setWidth100();
		buttonsLayout.setMargin(10);
		buttonsLayout.setHeight(IMAGE_SIZE);
		buttonsLayout.setAlign(Alignment.CENTER);
		buttonsLayout.setMembers(start, pause, cancel, save);

		
		
		

		selectTaskTree.setTitle("Tareas");  
		//			selectTaskTree.setLoadDataOnDemand(true);
		selectTaskTree.setDataSource(TaskListDataSource.getInstance());  
		selectTaskTree.setEmptyMenuMessage("No Existe Sub-Tarea");
		selectTaskTree.setDefaultValue("Seleccione Tarea...");
		selectTaskTree.setCanSelectParentItems(true);
		selectTaskTree.setDisplayField(COL_NAME);
		selectTaskTree.addChangedHandler(new ChangedHandler() {
			@Override
			public void onChanged(ChangedEvent event) {
				Integer id =  (Integer) event.getValue();
				if (id < 0) {
					selectTaskTree.clearValue();
					selectTaskTree.redraw();
					SC.warn("La seleccion corresponde al nombre del Proyecto. Debe seleccionar una Tarea del Proyecto");
				} else {
					//TODO habilitar botonera para poner play
				}

			}
		});

		//			timerLabel.setValue(new Date(GMT_3));
		timerLabel.setTextAlign(Alignment.CENTER);
		timerLabel.setTimeFormatter(TimeDisplayFormat.TOPADDED24HOURTIME);
//		timerLabel.setEmptyDisplayValue("--:--");
		timerLabel.setUseMask(true);
		timerLabel.setHeight(50);
		timerLabel.setWidth(200);
		timerLabel.setHint("");
		timerLabel.setTextBoxStyle("counter");
		timerLabel.addChangedHandler(new ChangedHandler() {
			@Override
			public void onChanged(ChangedEvent event) {
				//Actualizo el contador
				contador = ((Date)timerLabel.getValue()).getTime();
			}
		});

		timer = new Timer(){
			public void run() {
				contador += TIME_INTERVAL;
				timerLabel.setValue(new Date(contador));
			}        
		};

		final DynamicForm form = new DynamicForm();
		form.setWidth100();
		form.setColWidths("20%", "80%");
		form.setAlign(Alignment.LEFT);
		form.setFields(selectTaskTree, timerLabel);
		form.draw();

		this.addMember(buttonsLayout);
		this.addMember(form);
		this.setHeight(150);
		initCounterPanel();			
	}

	
	
	ClickHandler startEvent = new ClickHandler() {	
		@Override
		public void onClick(ClickEvent event) {
			Integer userId = SessionUser.getInstance().getUser().getId();
			Integer taskId = (Integer) selectTaskTree.getValue();
			if (taskId != null && taskId > 0) {
				TempCounterServicesClient.Util.getInstance().start(userId, taskId, new AsyncCallback<TempCounterDTO>() {
					@Override
					public void onSuccess(TempCounterDTO result) {
						//Vista
						timer.scheduleRepeating(TIME_INTERVAL);
					}
					@Override
					public void onFailure(Throwable caught) {
						SC.warn("Error al iniciar el contador de la Tarea");
					}
				});
			} else {
				SC.say("Se requiere que se elija una tarea para comenzar con el contador");
			}
		}
	};

	ClickHandler cancelEvent = new ClickHandler() {
		@Override
		public void onClick(ClickEvent event) {
			Integer userId = SessionUser.getInstance().getUser().getId();
			TempCounterServicesClient.Util.getInstance().cancel(userId, new AsyncCallback<Long>() {
				
				@Override
				public void onSuccess(Long result) {
					timer.cancel();
					contador = GMT_3;
					timerLabel.setValue(new Date(contador));
				}
				@Override
				public void onFailure(Throwable caught) {
					SC.warn("Error al tratar de cancelar la Tarea que se estaba contabilizando");
				}
			});
			
			
		}
	};

	ClickHandler pauseEvent = new ClickHandler() {
		@Override
		public void onClick(ClickEvent event) {
			Integer userId = SessionUser.getInstance().getUser().getId();
			TempCounterServicesClient.Util.getInstance().pause(userId, new AsyncCallback<TempCounterDTO>() {
				@Override
				public void onSuccess(TempCounterDTO result) {
					//Vista
					timer.cancel();
				}
				@Override
				public void onFailure(Throwable caught) {
					SC.say("Error al pausar el contador de Horas");
				}
			});


		}
	};

	ClickHandler saveEvent = new ClickHandler() {
		@Override
		public void onClick(ClickEvent event) {
			SC.ask("Guardar", "¿Esta seguro que desea guardar la carga de horas?", new BooleanCallback() {
				@Override
				public void execute(Boolean yes) {
					if (yes) {

						Integer userId = SessionUser.getInstance().getUser().getId();
						TempCounterServicesClient.Util.getInstance().save(userId , new AsyncCallback<Long>() {
							@Override
							public void onSuccess(Long result) {
								timer.cancel();
								contador = GMT_3;
								timerLabel.setValue(new Date(contador));

								SC.say("Horas cargadas exitosamente! ("+result+" ms)");
							}

							@Override
							public void onFailure(Throwable caught) {
								SC.warn("Error al intentar guardar la informacion del contador de horas");
							}
						});
					}
				}
			});
		}
	};

	/**
	 * Inicializa el contador de horas segun el estado y tiempo acumulado correspondiente
	 */
	public void initCounterPanel(){
		Integer userId = SessionUser.getInstance().getUser().getId();
		TempCounterServicesClient.Util.getInstance().getActualState(userId, new AsyncCallback<TempCounterDTO>() {
			@Override
			public void onSuccess(TempCounterDTO result) {
				if (result != null) {
					selectTaskTree.setValue(result.getTask().getId());
					contador = result.getTimeAcumulated() + GMT_3;
					timerLabel.setValue(new Date(contador));
//					SC.say("Estado " + result.getControl());
					switch (result.getControl()){
					case 0:
						break;
					case 1:
						break;
					case 2:
						break;
					default:
						break;
					}
				}
			}

			@Override
			public void onFailure(Throwable caught) {
				SC.warn("Error al recuperar el estado actual del contador de Horas");
			}
		});
	}
}

