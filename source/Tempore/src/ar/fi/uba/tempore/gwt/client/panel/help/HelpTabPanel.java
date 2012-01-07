package ar.fi.uba.tempore.gwt.client.panel.help;


import java.util.Date;

import ar.fi.uba.tempore.gwt.client.panel.TabsPanelContainer;
import ar.fi.uba.tempore.gwt.client.panel.project.ProjectPanel;
import ar.fi.uba.temporeutils.observer.ProjectObserver;

import com.google.gwt.user.client.Timer;
import com.smartgwt.client.types.DateDisplayFormat;
import com.smartgwt.client.types.SelectionType;
import com.smartgwt.client.types.TimeDisplayFormat;
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

	private final TimeItem timerLabel = new TimeItem ("Tiempo");
	private final Timer timer;
	private final IPickTreeItem selectTaskTree = new IPickTreeItem("tareas");
	private long contador = 0;

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
					SC.warn("Debe seleccionar una tarea del proyecto");
				}
			}
		});

		timerLabel.setValue(0);
		timerLabel.setDisabled(false);
		timerLabel.setDisplayFormat(TimeDisplayFormat.TOPADDEDTIME);
		timerLabel.setDateFormatter(DateDisplayFormat.TOSTRING);
		timerLabel.addChangedHandler(new ChangedHandler() {
			@Override
			public void onChanged(ChangedEvent event) {
				Date date = (Date) event.getValue();
				SC.say(date + " # " + date.getTime());
			}
		});
		
		timer = new Timer(){
			public void run() {
				Date instance = new Date();
				contador += 1000;
				instance.setTime(contador);
				timerLabel.setValue(instance);
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
		start.addClickHandler(new ClickHandler() {	
			@Override
			public void onClick(ClickEvent event) {
				timer.scheduleRepeating(TIME_INTERVAL);
			}
		});

		final ImgButton stop = new ImgButton();
		stop.setSize(32);
		stop.setSrc("../images/png/counter/cancel.png");
		stop.setShowRollOver(false);
		stop.setActionType(SelectionType.RADIO);  
		stop.setRadioGroup("textTimer");
		stop.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				timer.cancel();
				timerLabel.setValue(0);
			}
		});

		final ImgButton pause = new ImgButton();
		pause.setSize(32);
		pause.setSrc("../images/png/counter/add.png");
		pause.setShowRollOver(false);
		pause.setActionType(SelectionType.RADIO);  
		pause.setRadioGroup("textTimer");
		pause.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				timer.cancel();		
			}
		});		

		HLayout buttonsLayout = new HLayout();
		buttonsLayout.setMembers(start, pause, stop);

		VLayout mainLayout = new VLayout();
		mainLayout.addMember(form);
		mainLayout.addMember(buttonsLayout);

		this.addChild(mainLayout);
	}

	@Override
	public void refreshPanel() {
		ProjectPanel.getInstance().addObserver(this);
		updateProjectSelected();
	}

	@Override
	public void freePanel() {
		ProjectPanel.getInstance().removeObserver(this);
	}

	@Override
	public void updateProjectSelected() {
	}
}
