package ar.fi.uba.tempore.gwt.client.panel.report;

import java.util.Date;
import java.util.List;

import ar.fi.uba.tempore.dto.reports.ProjectsTimesDTO;
import ar.fi.uba.tempore.gwt.client.ReportServicesClient;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.visualization.client.AbstractDataTable.ColumnType;
import com.google.gwt.visualization.client.DataTable;
import com.smartgwt.client.types.AnimationEffect;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.CloseClickHandler;
import com.smartgwt.client.widgets.events.CloseClientEvent;

public class Report1 extends Window {

	public Report1 (){
        this.setWidth(700);  
        this.setHeight(400);  
        this.setTitle("Reporte - Totales de Horas por Proyecto");  
        this.setShowMinimizeButton(false);  
        this.setIsModal(true);  
        this.setShowModalMask(true);  
        this.centerInPage();
        this.animateShow(AnimationEffect.FLY);
        this.setAnimateTime(3000);
        this.addCloseClickHandler(new CloseClickHandler() {  
            public void onCloseClick(CloseClientEvent event) {
                destroy();  
            }  
        });  
	}

	public void draw(final Date ini, final Date end) {
		ReportServicesClient.Util.getInstance().getProjectsTimes(ini, end, new AsyncCallback<List<ProjectsTimesDTO>>() {
			@Override
			public void onSuccess(final List<ProjectsTimesDTO> result) {
				 DateTimeFormat fmt = DateTimeFormat.getFormat("dd-MMMM-yyyy");
				
				final GenericGrafic gg =  new GenericGrafic("Horas Cargadas a Proyectos desde " + fmt.format(ini) + ", hasta " + fmt.format(end),GenericGrafic.AREA) {
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

				addMember(gg);
				show(); 
			}
			@Override
			public void onFailure(Throwable caught) {
				SC.warn("Error al buscar datos para reporte - Proyectos/HorasCargadas");
			}
		});	
	}
}
