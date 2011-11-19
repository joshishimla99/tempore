
package ar.fi.uba.tempore.hibernate.dao;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.junit.Test;

import ar.fi.uba.tempore.dao.ReportDAO;
import ar.fi.uba.tempore.entity.reports.ProjectsTimes;
import ar.fi.uba.tempore.hibernate.TestDAO;

public class TestReportDAO extends TestDAO{
	private ReportDAO rDAO = new ReportDAO();	
	
	
	@Test
	public void testFindById() throws ParseException {
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date ini = format.parse("2009-01-01");
		Date end = new Date();
		
		
		List<ProjectsTimes> pTime = rDAO.getProjectsTimes(ini, end);
		
		System.out.println("Cantidad de Registros: " + pTime.size());
		for (ProjectsTimes r : pTime) {
			System.out.println(r.getProjectName() + ", Horas Cargadas: " + r.getHourCounted());
		}
	}
	
}
