package ar.fi.uba.tempore.gwt.client.panel.report;

import java.util.Date;
import java.util.Map;
import java.util.Set;

import ar.fi.uba.tempore.dto.reports.TaskTypesTimesDTO;
import ar.fi.uba.tempore.gwt.client.ReportServicesClient;
import ar.fi.uba.tempore.gwt.client.login.SessionUser;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.visualization.client.AbstractDataTable.ColumnType;
import com.google.gwt.visualization.client.DataTable;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.layout.VLayout;

public class ResumeReportHoursWeek extends VLayout {

//	private VLayout parent;

	public ResumeReportHoursWeek(){
	}
		
	public void drawReport(Date dayWeek){
		Canvas[] oldGrafics = this.getChildren();
		for (Canvas old : oldGrafics) {
			this.removeChild(old);
		}
	
		Integer userId = SessionUser.getInstance().getUser().getId();
		draw(userId, dayWeek);
	};

	private void draw(Integer userId, Date date) {
		final VLayout thisPanel = this; 
		ReportServicesClient.Util.getInstance().getUserTimesByWeek(userId, date, new AsyncCallback<Map<Integer,TaskTypesTimesDTO>>() {
			@Override
			public void onSuccess(final Map<Integer,TaskTypesTimesDTO> result) {
				final DateTimeFormat fmt = DateTimeFormat.getFormat("EEE");

				final ResumeGrafic gg =  new ResumeGrafic() {
					@Override
					public DataTable createTable() {
						final DataTable data = DataTable.create();
						data.addColumn(ColumnType.STRING, "Dia de la Semana");
						data.addColumn(ColumnType.NUMBER, "Horas Cargadas");

						int i = 0;
						data.addRows(result.size());
						Set<Integer> keySet = result.keySet();
						for (Integer key : keySet) {
							data.setValue(i, 0, fmt.format(result.get(key).getDate()));
//							GWT.log(fmt.format(result.get(key).getDate())+"-"+new Float(result.get(key).getHourCounted())/GenericGrafic.HORA);
							data.setValue(i, 1, new Float(result.get(key).getHourCounted())/GenericGrafic.HORA);
							i++;
						}

						return data;
					}
				};
				thisPanel.addChild(gg);
				gg.draw();
				show(); 
			}

			@Override
			public void onFailure(Throwable caught) {
				SC.warn("Error al buscar datos para reporte - Horas de Usuario en la semanao");
			}
		});		
	}
}
