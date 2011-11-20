package ar.fi.uba.tempore.gwt.client.panel.report;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

import ar.fi.uba.tempore.dto.reports.ProjectsTimesDTO;
import ar.fi.uba.tempore.gwt.client.ReportServicesClient;
import ar.fi.uba.tempore.gwt.client.panel.TabsPanelContainer;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.visualization.client.AbstractDataTable.ColumnType;
import com.google.gwt.visualization.client.DataTable;
import com.smartgwt.client.types.AnimationEffect;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.CloseClickHandler;
import com.smartgwt.client.widgets.events.CloseClientEvent;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.ButtonItem;
import com.smartgwt.client.widgets.form.fields.DateItem;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.form.fields.events.ClickEvent;
import com.smartgwt.client.widgets.form.fields.events.ClickHandler;
import com.smartgwt.client.widgets.layout.VLayout;

public class ReportTabPanel extends TabsPanelContainer{

	protected static final String DESDE_FIELD = "DesdeItem";
	protected static final String HASTA_FIELD = "HastaItem";
	private VLayout vLayout = new VLayout();
	private VLayout reportLayout = new VLayout();
	private GenericGrafic gg = null;
	private final DynamicForm form = new DynamicForm();
	
	public ReportTabPanel() {
		super();

		vLayout.setHeight100();
		vLayout.setWidth100();
		
		
		final SelectItem selState = new SelectItem("Estado", "Tipo de Grafico");
		LinkedHashMap<String, String> valueMap = new LinkedHashMap<String, String>(4);  
		valueMap.put("0", "Torta");
		valueMap.put("1", "Linea");
		valueMap.put("2", "Areas");
		valueMap.put("3", "Columnas");
//		valueMap.put("4", "Relojes");
		selState.setValueMap(valueMap);
		
		final DateItem ini = new DateItem(DESDE_FIELD,"Desde");
		ini.setValue(new Date(System.currentTimeMillis() + (3600000*24*30) ));
		final DateItem end = new DateItem(HASTA_FIELD, "Hasta");
		
		final ButtonItem btn = new ButtonItem("Reporte");
		btn.addClickHandler(onClickReport);
		
		form.setFields(ini,end,selState, btn);
		
		vLayout.addMember(form);
		vLayout.addMember(reportLayout);
		
		this.addChild(vLayout);
		
		selState.addChangedHandler(new ChangedHandler() {
			@Override
			public void onChanged(ChangedEvent event) {
				int typeGrafic = new Integer((String)event.getValue());
				gg.setGraficType(typeGrafic);
				gg.draw();
			}
		});
	}

	private ClickHandler onClickReport = new ClickHandler() {
		@Override
		public void onClick(ClickEvent event) {
			
			final Window winModal = new Window();  
            winModal.setWidth(700);  
            winModal.setHeight(400);  
            winModal.setTitle("Reportes");  
            winModal.setShowMinimizeButton(false);  
            winModal.setIsModal(true);  
            winModal.setShowModalMask(true);  
            winModal.centerInPage();
            winModal.animateShow(AnimationEffect.FLY);
            winModal.setAnimateTime(1000);
            winModal.addCloseClickHandler(new CloseClickHandler() {  
                public void onCloseClick(CloseClientEvent event) {
                    winModal.destroy();  
                }  
            });  
			
            final Date ini = (Date) form.getValue(DESDE_FIELD);
			final Date end = (Date) form.getValue(HASTA_FIELD);
			ReportServicesClient.Util.getInstance().getProjectsTimes(ini, end, new AsyncCallback<List<ProjectsTimesDTO>>() {

				@Override
				public void onSuccess(final List<ProjectsTimesDTO> result) {
					gg =  new GenericGrafic("Horas Cargadas a Proyectos desde " + ini + ", hasta " + end,GenericGrafic.AREA) {
						@Override
						public DataTable createTable() {
							final DataTable data = DataTable.create();
							data.addColumn(ColumnType.STRING, "Proyectos");
							data.addColumn(ColumnType.NUMBER, "Horas Cargadas");
					
							int i = 0;
							data.addRows(result.size());
							for (ProjectsTimesDTO reg : result) {
								data.setValue(i, 0, (String)reg.getProjectName());
								data.setValue(i, 1, reg.getHourCounted());
								i++;
							}
							return data;
						}
					};
					gg.setGraficType(GenericGrafic.COLUMNS);
					gg.draw();

					winModal.addMember(gg);
					winModal.show(); 
				}
				@Override
				public void onFailure(Throwable caught) {
					SC.warn("Error al buscar datos para reporte - Proyectos/HorasCargadas");
				}
				
			});	
		}
	};
	

	@Override
	public void refreshPanel() {
		//NADA
	}

	@Override
	public void freePanel() {
		//NADA
	}
}
