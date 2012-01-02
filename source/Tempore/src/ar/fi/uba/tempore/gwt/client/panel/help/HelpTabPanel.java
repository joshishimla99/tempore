package ar.fi.uba.tempore.gwt.client.panel.help;

import ar.fi.uba.tempore.gwt.client.panel.TabsPanelContainer;
import ar.fi.uba.tempore.gwt.client.panel.project.ProjectPanel;
import ar.fi.uba.tempore.gwt.client.panel.time.TaskTimeDataSource;
import ar.fi.uba.temporeutils.observer.ProjectObserver;

import com.google.gwt.user.client.Timer;
import com.smartgwt.client.types.SelectionType;
import com.smartgwt.client.types.TimeDisplayFormat;
import com.smartgwt.client.types.TreeModelType;
import com.smartgwt.client.widgets.ImgButton;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.TimeItem;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.tree.Tree;
import com.smartgwt.client.widgets.tree.TreeGrid;
import com.smartgwt.client.widgets.tree.TreeGridField;
import com.smartgwt.client.widgets.tree.TreeNode;

public class HelpTabPanel extends TabsPanelContainer implements ProjectObserver {
	public static final String COL_TASK_ID = "taskId";
	public static final String COL_NAME = "nameCol";
	public static final String COL_DESCRIPTION = "descriptionCol";
	public static final String COL_REPORT_TO = "ReportsTo";

	private final TimeItem timerLabel = new TimeItem ("Tiempo");
	private final Timer timer;
	private TreeGrid tasksTree;

	public HelpTabPanel(){
		super();
		this.setWidth100();
		this.setHeight100();

		final DynamicForm df = new DynamicForm();

		timerLabel.setValue(0);
		timerLabel.setDisabled(false);
		timerLabel.setHint("segundos");
		timerLabel.setDisplayFormat(TimeDisplayFormat.TOPADDED24HOURTIME);


		final ImgButton start = new ImgButton();
		start.setSize(64);

		start.setSrc("../images/64x64/player_play.png");
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
				timer.scheduleRepeating(1000);
			}
		});

		final ImgButton stop = new ImgButton();
		stop.setSize(64);
		stop.setSrc("../images/64x64/player_stop.png");
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
		pause.setSize(64);
		pause.setSrc("../images/64x64/player_pause.png");
		pause.setShowRollOver(false);
		pause.setActionType(SelectionType.RADIO);  
		pause.setRadioGroup("textTimer");
		pause.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				timer.cancel();		
			}
		});		

		df.setFields(timerLabel);

		timer = new Timer(){
			public void run() {
				timerLabel.setValue((Integer)timerLabel.getValue() + 1);
			}        
		};

		HLayout playLayout = new HLayout();
		playLayout.setMembers(start, pause, stop);


		tasksTree = createTreeGrid();

		VLayout vLayoutTimer = new VLayout();
		vLayoutTimer.addMember(df);
		vLayoutTimer.addMember(playLayout);


		HLayout hLayout = new HLayout();
		hLayout.setHeight100();
		hLayout.setWidth100();
		hLayout.addMember(tasksTree);
		hLayout.addMember(vLayoutTimer);

		this.addChild(hLayout);
	}

	public TreeGrid createTreeGrid (){
		final Tree tree = new Tree();  
        tree.setModelType(TreeModelType.PARENT);  
        tree.setRootValue(1);  
        tree.setNameProperty(COL_NAME);  
        tree.setIdField(COL_TASK_ID);  
        tree.setParentIdField("ReportsTo");  
        tree.setOpenProperty("isOpen");  
        tree.setData(employeeData);
                
 		final TreeGrid tasksTree = new TreeGrid();
		tasksTree.setShowDropIcons(true);
		tasksTree.setHeight100();
		tasksTree.setWidth("50%");
		tasksTree.setHeight("100%");		
		tasksTree.setAutoFetchData(true);
		tasksTree.setFolderIcon("../images/folder.png"); 
		tasksTree.setNodeIcon("../images/tasks.png");

		tasksTree.setShowOpenIcons(true); 
		tasksTree.setShowDropIcons(false);
		tasksTree.setAnimateFolders(true);  
		tasksTree.setAnimateFolderSpeed(100);
		
		tasksTree.setEmptyMessage("Seleccion&aacute un proyecto...");  

		//Campos para visualizar
		TreeGridField tfId = new TreeGridField(COL_TASK_ID);
		tfId.setHidden(true);
		TreeGridField tfName = new TreeGridField(COL_NAME);
		TreeGridField tfDescription = new TreeGridField(COL_DESCRIPTION);
		tasksTree.setFields(tfId, tfName, tfDescription);  

		tasksTree.setDataSource(TaskListDS.getInstance());
//		tasksTree.setDataSource(TaskTimeDataSource.getInstance());
//		tasksTree.setData(tree);

		return tasksTree;
	}

	public final TreeNode[] employeeData = new TreeNode[] {  
		new EmployeeTreeNode("5", "1", "Padre", "Test", true),
		new EmployeeTreeNode("51", "5", "Hijo", "Test", true),
		new EmployeeTreeNode("4", "1", "Charles Madigen", "Chief Operating Officer", true),  
		new EmployeeTreeNode("189", "4", "Gene Porter", "Mgr Tech Plng IntIS T", false),  
		new EmployeeTreeNode("265", "189", "Olivier Doucet", "Asset Spec Lines Stns", false),  
		new EmployeeTreeNode("264", "189", "Cheryl Pearson", "Dsl Sys Rep", false),  
		new EmployeeTreeNode("263", "189", "Priya Sambhus", "Line Wrker A", false),  
		new EmployeeTreeNode("188", "4", "Rogine Leger", "Mgr Syst P P", true),  
		new EmployeeTreeNode("262", "188", "Jacques Desautels", "Line Wrker A", false),  
		new EmployeeTreeNode("261", "188", "Kay Monroe", "Stn Opr", false),  
		new EmployeeTreeNode("260", "188", "Francine Dugas", "Fire Sec Off", false),  
		new EmployeeTreeNode("259", "188", "Jacques Leblanc", "Purch Clk", false),  
		new EmployeeTreeNode("258", "188", "Ren Xian", "Mobile Eq Opr", false),  
		new EmployeeTreeNode("257", "188", "Olivier Hebert", "Met Read/Coll", false),  
		new EmployeeTreeNode("182", "4", "Tamara Kane", "Mgr Site Services", false),  
		new EmployeeTreeNode("195", "182", "Kai Kong", "Stores Worker", false),  
		new EmployeeTreeNode("194", "182", "Felicia Piper", "Dsl Sys Rep", false),  
		new EmployeeTreeNode("193", "182", "Darcy Feeney", "Inventory Ck", false)  
	};  
	public class EmployeeTreeNode extends TreeNode {  
		public EmployeeTreeNode(String employeeId, String reportsTo, String name, String job, boolean isOpen) {  
			setAttribute(COL_TASK_ID, employeeId);  
			setAttribute("ReportsTo", reportsTo);  
			setAttribute(COL_NAME, name);  
			setAttribute(COL_DESCRIPTION, job);  
			setAttribute("isOpen", isOpen);  
		}  
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
		if (ProjectPanel.getInstance().getSelected() != null){
			TaskTimeDataSource.getInstance().setId(ProjectPanel.getInstance().getSelected().getId());
			tasksTree.fetchData();
		}
	}
}
