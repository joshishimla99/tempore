package ar.fi.uba.tempore.gwt.server;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.dozer.DozerBeanMapper;

import ar.fi.uba.tempore.dao.ReportDAO;
import ar.fi.uba.tempore.dto.reports.ProjectsTimesDTO;
import ar.fi.uba.tempore.dto.reports.TasksTimesDTO;
import ar.fi.uba.tempore.dto.reports.UsersTimesDTO;
import ar.fi.uba.tempore.entity.reports.ProjectsTimes;
import ar.fi.uba.tempore.entity.reports.TaskTypesTimes;
import ar.fi.uba.tempore.entity.reports.TasksTimes;
import ar.fi.uba.tempore.entity.reports.UsersTimes;
import ar.fi.uba.tempore.gwt.client.ReportServicesClient;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class ReportServicesImpl extends RemoteServiceServlet implements ReportServicesClient {

	private static final long serialVersionUID = -632059708650593031L;
	private final Logger log = Logger.getLogger(this.getClass());
	private final DozerBeanMapper mapper = new DozerBeanMapper();
	
	@Override
	public List<ProjectsTimesDTO> getProjectsTimes(Date dateIni, Date dateEnd) {
		ReportDAO report = new ReportDAO();
		log.info("REPORTE - GetProjectsTimes ["+dateIni+"],["+dateEnd+"]");
		
		List<ProjectsTimes> projectsTimesList = report.getProjectsTimes(dateIni, dateEnd);
		
		List<ProjectsTimesDTO> dtoList = new ArrayList<ProjectsTimesDTO>(projectsTimesList.size());
		for (ProjectsTimes e : projectsTimesList){
			dtoList.add(mapper.map(e, ProjectsTimesDTO.class));
		}
		
		return dtoList; 
	}

	@Override
	public List<UsersTimesDTO> getUsersTimes(Integer projectId, Date dateIni, Date dateEnd) {
		ReportDAO report = new ReportDAO();
		log.info("REPORTE - getUsersTimes ["+dateIni+"],["+dateEnd+"]");

		List<UsersTimes> usersTimes = report.getUsersTimes(projectId, dateIni, dateEnd);
		List<UsersTimesDTO> result = new ArrayList<UsersTimesDTO>(usersTimes.size());
		for (UsersTimes ut : usersTimes) {
			result.add(mapper.map(ut, UsersTimesDTO.class));
		}
		
		return result;
	}

	@Override
	public List<TasksTimesDTO> getPrimaryTaskTimes(Integer projectId, Date from, Date to) {
		ReportDAO report = new ReportDAO();
		log.info("REPORTE - getPrimaryTaskTimes "+projectId+" ["+from+" ,"+to+"]");

		List<TasksTimes> primaryTaskTimes = report.getPrimaryTaskTimes(projectId, from, to);
		List<TasksTimesDTO> result = new ArrayList<TasksTimesDTO>(primaryTaskTimes.size());
		for (TasksTimes tasksTimes : primaryTaskTimes) {
			result.add(mapper.map(tasksTimes, TasksTimesDTO.class));
		}
		
		return result;
	}

	@Override
	public List<ProjectsTimesDTO> getUserActivity(Integer userId, Date ini, Date end) {
		ReportDAO report = new ReportDAO();
		log.info("REPORTE - getUserActivity "+ userId +" ["+ini+" ,"+end+"]");

		List<ProjectsTimes> userActivity = report.getUserActivity(userId, ini, end);
		List<ProjectsTimesDTO> result = new ArrayList<ProjectsTimesDTO>(userActivity.size());
		for (ProjectsTimes projectsTimes : userActivity) {
			result.add(mapper.map(projectsTimes, ProjectsTimesDTO.class));
		}

		return result;
	}

	@Override
	public Map<String, Map<Integer, Long>> getProjectTaskTypeByTime(Integer projectId, Date ini, Date end) {
		ReportDAO report = new ReportDAO();
		log.info("REPORTE - getProjectTaskTypeByTime "+ projectId +" ["+ini+" ,"+end+"]");
		
		List<TaskTypesTimes> projectTaskTypeByTime = report.getProjectTaskTypeByTime(projectId);
		
		Map<String, Map<Integer, Long>> mainMap = new HashMap<String, Map<Integer,Long>>();

		if (!projectTaskTypeByTime.isEmpty()){
			Date iniDate = projectTaskTypeByTime.get(0).getDate();
			
			//Creo HashMap de Tipos de Tarea
			Map<Integer, Long> internalMap = new HashMap<Integer, Long>();
			for (TaskTypesTimes item : projectTaskTypeByTime) {			
				if (!mainMap.containsKey(item.getTaskTypeName())){
					mainMap.put(item.getTaskTypeName(), null);
				}
				internalMap.put(difBetweenDates(iniDate, item.getDate()), 0L);
			}
			
			//Inicializo los map de horas con ceros en todos los dias cargados
			Set<String> keySet = mainMap.keySet();
			for (String key : keySet) {
				Map<Integer, Long> iniMap = new HashMap<Integer, Long>(internalMap);
				mainMap.put(key, iniMap);
			}
			
			//Coloco los Datos reales a cada fecha de proyecto
			for (TaskTypesTimes item : projectTaskTypeByTime) {
				Map<Integer, Long> map = mainMap.get(item.getTaskTypeName());
				map.put(difBetweenDates(iniDate, item.getDate()), item.getHourCounted());
			}
		}
		return mainMap;
	}
	
	private Integer difBetweenDates(Date dateIni, Date dateEnd){
		return (int)((dateEnd.getTime()-dateIni.getTime())/(1000*60*60*24));
	}
}
