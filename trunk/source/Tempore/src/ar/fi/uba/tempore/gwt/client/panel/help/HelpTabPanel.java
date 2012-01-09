package ar.fi.uba.tempore.gwt.client.panel.help;


import java.util.Date;

import ar.fi.uba.tempore.dto.TempCounterDTO;
import ar.fi.uba.tempore.gwt.client.TempCounterServicesClient;
import ar.fi.uba.tempore.gwt.client.login.SessionUser;
import ar.fi.uba.tempore.gwt.client.panel.TabsPanelContainer;
import ar.fi.uba.temporeutils.observer.ProjectObserver;

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

public class HelpTabPanel extends TabsPanelContainer implements ProjectObserver {
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

	public HelpTabPanel(){
		super();
		this.setWidth100();
		this.setHeight100();

		final DynamicForm form = new DynamicForm();
		form.setWidth(300);    

		selectTaskTree.setTitle("Tareas");  
//		selectTaskTree.setLoadDataOnDemand(true);
		selectTaskTree.setDataSource(TaskListDS.getInstance());  
		selectTaskTree.setEmptyMenuMessage("No Existe Sub-Tarea");  
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

//		timerLabel.setValue(new Date(GMT_3));
		timerLabel.setTextAlign(Alignment.CENTER);
		timerLabel.setTimeFormatter(TimeDisplayFormat.TOPADDED24HOURTIME);
		timerLabel.setEmptyDisplayValue("--:--");
		timerLabel.setUseMask(true); 
		timerLabel.addChangedHandler(new ChangedHandler() {
			@Override
			public void onChanged(ChangedEvent event) {
				//Actualizo el contador
				contador = ((Date)timerLabel.getValue()).getTime();
			}
		});
		
		timer = new Timer(){
			public void run() {
				contador += 1000;
				timerLabel.setValue(new Date(contador));
			}        
		};

		form.setFields(selectTaskTree, timerLabel);
		form.draw();


		final ImgButton start = new ImgButton();
		start.setSize(32);

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
		cancel.setSize(32);
		cancel.setSrc("../images/png/counter/cancel.png");
		cancel.setShowRollOver(false);
		cancel.setActionType(SelectionType.RADIO);  
		cancel.setRadioGroup("textTimer");
		cancel.addClickHandler(cancelEvent );

		final ImgButton pause = new ImgButton();
		pause.setSize(32);
		pause.setSrc("../images/png/counter/pause.png");
		pause.setShowRollOver(false);
		pause.setActionType(SelectionType.RADIO);  
		pause.setRadioGroup("textTimer");
		pause.addClickHandler(pauseEvent);	

		final ImgButton save = new ImgButton();
		save.setSize(32);
		save.setSrc("../images/png/counter/save.png");
		save.setShowRollOver(false);
		save.setActionType(SelectionType.RADIO);  
		save.setRadioGroup("textTimer");
		save.addClickHandler(saveEvent);		
		
		HLayout buttonsLayout = new HLayout();
		buttonsLayout.setMembers(start, pause, cancel, save);

		VLayout mainLayout = new VLayout();
		mainLayout.addMember(form);
		mainLayout.addMember(buttonsLayout);

		initCounterPanel();
		
		this.addChild(mainLayout);
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
			timer.cancel();
			contador = GMT_3;
			timerLabel.setValue(new Date(contador));
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
	
	@Override
	public void refreshPanel() {
//		ProjectPanel.getInstance().addObserver(this);
//		updateProjectSelected();
	}

	@Override
	public void freePanel() {
//		ProjectPanel.getInstance().removeObserver(this);
	}

	@Override
	public void updateProjectSelected() {
	}
	
	public void initCounterPanel(){
		Integer userId = SessionUser.getInstance().getUser().getId();
		TempCounterServicesClient.Util.getInstance().getActualState(userId, new AsyncCallback<TempCounterDTO>() {
			@Override
			public void onSuccess(TempCounterDTO result) {
				if (result != null) {
					selectTaskTree.setValue(result.getTask().getId());
					contador = result.getTimeAcumulated() + GMT_3;
					timerLabel.setValue(new Date(contador));
					SC.say("Estado " + result.getControl());
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
