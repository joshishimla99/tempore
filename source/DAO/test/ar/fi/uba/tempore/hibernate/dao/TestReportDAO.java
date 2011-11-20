
package ar.fi.uba.tempore.hibernate.dao;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.junit.Test;

import ar.fi.uba.tempore.dao.ReportDAO;
import ar.fi.uba.tempore.entity.reports.ProjectsTimes;
import ar.fi.uba.tempore.entity.reports.TasksTimes;
import ar.fi.uba.tempore.entity.reports.TasksUsersTimes;
import ar.fi.uba.tempore.entity.reports.UsersTimes;
import ar.fi.uba.tempore.hibernate.TestDAO;

public class TestReportDAO extends TestDAO{
	private ReportDAO rDAO = new ReportDAO();	
	
	
	@Test
	public void testProyectsTimes() throws ParseException {
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date ini = format.parse("2009-01-01");
		Date end = new Date();
		
		
		List<ProjectsTimes> pTime = rDAO.getProjectsTimes(ini, end);
		
		System.out.println("Cantidad de Registros: " + pTime.size());
		for (ProjectsTimes r : pTime) {
			System.out.println(r.getProjectName() + ", Horas Cargadas: " + r.getHourCounted());
		}
	}
	
	@Test
	public void testUserTimes() throws ParseException {
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date ini = format.parse("2009-01-01");
		Date end = new Date();
		
		
		List<UsersTimes> pTime = rDAO.getUsersTimes(ini, end);
		
		System.out.println("Cantidad de Registros: " + pTime.size());
		for (UsersTimes r : pTime) {
			System.out.println(r.getUserName() + " " + r.getUserLastName()+ ", Horas Cargadas: " + r.getHourCounted());
		}
	}
	
	@Test
	public void testPrimaryTaskTimes() throws ParseException {
		Integer projectId = 1;
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date ini = format.parse("2009-01-01");
		Date end = new Date();
		
		
		List<TasksTimes> pTime = rDAO.getPrimaryTaskTimes(projectId, ini, end);
		
		System.out.println("Cantidad de Registros: " + pTime.size());
		for (TasksTimes r : pTime) {
			System.out.println(r.getTaskName() + ", Horas Cargadas: " + r.getHourCounted());
		}
	}
	
	
	@Test
	public void testPrimaryTaskTimesXUsers() throws ParseException {
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date ini = format.parse("2009-01-01");
		Date end = new Date();
		
		
		List<TasksUsersTimes> pTime = rDAO.getPrimaryTaskTimesXUser(ini, end);
		
		System.out.println("Cantidad de Registros: " + pTime.size());
		for (TasksUsersTimes r : pTime) {
			System.out.println(r.getTaskName() + " - "+ r.getUserName() +" " + r.getUserLastName() + ", Horas Cargadas: " + r.getHourCounted());
		}
	}
	
	
	@Test
	public void testGetUserActivity() throws ParseException{
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date ini = format.parse("2009-01-01");
		Date end = new Date();
		Integer userId = 1;
		
		List<ProjectsTimes> userActivity = rDAO.getUserActivity(userId, ini, end);
		
		System.out.println("Cantidad de Registros: " + userActivity.size());
		for (ProjectsTimes r : userActivity) {
			System.out.println(r.getProjectName() + ", Horas Cargadas: " + r.getHourCounted());
		}
	}
}
