package ar.fi.uba.tempore.gwt.client.panel.counter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ar.fi.uba.tempore.dto.TempCounterDTO;
import ar.fi.uba.tempore.gwt.client.TempCounterServicesClient;
import ar.fi.uba.tempore.gwt.client.login.SessionUser;
import ar.fi.uba.temporeutils.observer.TimeCounterObserved;
import ar.fi.uba.temporeutils.observer.TimeCounterObserver;

import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.smartgwt.client.types.Alignment;
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


public class CounterTimePanel extends VLayout implements TimeCounterObserved{

	private static final String DEFAULT_TASK_VALUE = "Seleccionar Tarea...";
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

	private final ImgButton start = new ImgButton();
	private final ImgButton pause = new ImgButton();
	private final ImgButton cancel = new ImgButton();
	private final ImgButton save = new ImgButton();
	
	private static CounterTimePanel instance = null;
	private List<TimeCounterObserver> listObserver = new ArrayList<TimeCounterObserver>();
	
	public static CounterTimePanel getInstance(){
		if (instance == null){
			instance = new CounterTimePanel();
		}
		return instance;
	}
	
	private CounterTimePanel(){
		super();
		this.setWidth100();
		this.setHeight100();

		Integer IMAGE_SIZE = 32;
		
		start.setSize(IMAGE_SIZE);
		start.setSrc("../images/png/counter/ok.png");
		start.addClickHandler(startEvent);
		start.setDisabled(false);

		
		pause.setSize(IMAGE_SIZE);
		pause.setSrc("../images/png/counter/pause.png");
		pause.addClickHandler(pauseEvent);
		pause.setDisabled(true);

		
		cancel.setSize(IMAGE_SIZE);
		cancel.setSrc("../images/png/counter/trash.png");
		cancel.addClickHandler(cancelEvent );
		cancel.setDisabled(true);

		save.setSize(IMAGE_SIZE);
		save.setSrc("../images/png/counter/save.png");
		save.addClickHandler(saveEvent);
		save.setDisabled(true);

		HLayout buttonsLayout = new HLayout();
		buttonsLayout.setWidth100();
		buttonsLayout.setMargin(10);
		buttonsLayout.setHeight(IMAGE_SIZE);
		buttonsLayout.setAlign(Alignment.CENTER);
		buttonsLayout.setMembers(start, pause, cancel, save);

		selectTaskTree.setTitle("Tareas");  
		selectTaskTree.setEmptyMenuMessage("No Existe Sub-Tarea");
		selectTaskTree.setLoadDataOnDemand(false);
		selectTaskTree.setFetchMissingValues(false);
		selectTaskTree.setDefaultValue(DEFAULT_TASK_VALUE);
		selectTaskTree.setCanSelectParentItems(true);
		selectTaskTree.setDisplayField(COL_NAME);
		selectTaskTree.setDataSource(TaskListDataSource.getInstance());  
		selectTaskTree.addChangedHandler(new ChangedHandler() {
			@Override
			public void onChanged(ChangedEvent event) {
				Integer id =  (Integer) event.getValue();
				if (id < 0) {
					selectTaskTree.clearValue();
					SC.warn("La seleccion corresponde al nombre del Proyecto. Debe seleccionar una Tarea del Proyecto");
				} else {
					//VISTA
					start.setDisabled(false);
					pause.setDisabled(true);
					cancel.setDisabled(true);
					save.setDisabled(true);
				}
			}
		});

		timerLabel.setTextAlign(Alignment.CENTER);
		timerLabel.setTimeFormatter(TimeDisplayFormat.TOPADDED24HOURTIME);
//		timerLabel.setUseMask(true);
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
			//TODO no funciona la validacion
			if (taskId != null && taskId > 0) {
				TempCounterServicesClient.Util.getInstance().start(userId, taskId, new AsyncCallback<TempCounterDTO>() {
					@Override
					public void onSuccess(TempCounterDTO result) {
						timer.scheduleRepeating(TIME_INTERVAL);
						//Vista
						selectTaskTree.setDisabled(true);
						start.setDisabled(true);
						pause.setDisabled(false);
						cancel.setDisabled(false);
						save.setDisabled(false);						
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
					timerLabel.setValue(contador);
					selectTaskTree.setValue(DEFAULT_TASK_VALUE);
					
					//Vista
					selectTaskTree.setDisabled(false);
					start.setDisabled(false);
					pause.setDisabled(true);
					cancel.setDisabled(true);
					save.setDisabled(true);
				}
				@Override
				public void onFailure(Throwable caught) {
					SC.warn("Error al tratar de cancelar la Tarea que se estaba contabilizando");
				}
			});
			
			
		}
	};

	ClickHandler pauseEvent = new ClickHandler() {
		Integer userId = SessionUser.getInstance().getUser().getId();
		@Override
		public void onClick(ClickEvent event) {
			TempCounterServicesClient.Util.getInstance().pause(userId, new AsyncCallback<TempCounterDTO>() {
				@Override
				public void onSuccess(TempCounterDTO result) {
					timer.cancel();
					//Vista
					selectTaskTree.setDisabled(true);
					start.setDisabled(false);
					pause.setDisabled(true);
					cancel.setDisabled(false);
					save.setDisabled(false);
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
			SC.ask("Guardar", "Esta seguro que desea guardar la carga de horas?", new BooleanCallback() {
				@Override
				public void execute(Boolean yes) {
					if (yes) {
						Integer userId = SessionUser.getInstance().getUser().getId();
						TempCounterServicesClient.Util.getInstance().save(userId , new AsyncCallback<Long>() {
							@Override
							public void onSuccess(Long result) {
//								SC.say("Horas cargadas exitosamente! ("+timerLabel.getValueAsString()+")");
								notifyObservers();

								timer.cancel();
								contador = GMT_3;
								timerLabel.setValue(contador);
								selectTaskTree.setValue(DEFAULT_TASK_VALUE);
								
								//Vista
								selectTaskTree.setDisabled(false);
								start.setDisabled(false);
								pause.setDisabled(true);
								cancel.setDisabled(true);
								save.setDisabled(true);
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

					switch (result.getControl()){
					case 0:
						//None
						//Vista
						selectTaskTree.setDisabled(false);
						start.setDisabled(false);
						pause.setDisabled(true);
						cancel.setDisabled(true);
						save.setDisabled(true);
						break;
					case 1:
						//PLAY
						timer.scheduleRepeating(TIME_INTERVAL);
						//Vista
						selectTaskTree.setDisabled(true);
						start.setDisabled(true);
						pause.setDisabled(false);
						cancel.setDisabled(false);
						save.setDisabled(false);
						
						break;
					case 2:
						//PAUSE
						
						//Vista
						selectTaskTree.setDisabled(true);
						start.setDisabled(false);
						pause.setDisabled(true);
						cancel.setDisabled(false);
						save.setDisabled(false);
						break;
					default:
						//DESCONOCIDO
						SC.warn("Se recibe un estado del contador de horas no admitido");
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

	@Override
	public void addObserver(TimeCounterObserver observer) {
		listObserver.add(observer);
	}

	@Override
	public void removeObserver(TimeCounterObserver observer) {
		listObserver.remove(observer);
	}

	@Override
	public void notifyObservers() {
		for (TimeCounterObserver o : listObserver){
			o.updateTimesCounted();
		}
	}
}