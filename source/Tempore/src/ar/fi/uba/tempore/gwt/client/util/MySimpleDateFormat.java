package ar.fi.uba.tempore.gwt.client.util;

import com.google.gwt.i18n.client.NumberFormat;

public class MySimpleDateFormat {
	
	private static final Long UNA_HORA = 60L*60L*1000L;
	private static final Long UN_MINUTO = 60L*1000L;
	private static final Long UN_SEGUNDO = 1000L;
	
	private static final NumberFormat formater = NumberFormat.getFormat("00");
	
	/**
	 * Formatea un long en tiempo con el formato HH:MM
	 * @param milliseconds que se desea formatear
	 * @return String con las horas, minutos
	 */
	public static String formatTime (Long milliseconds){
		Long resto;
		
		Long horas = new Long(milliseconds / UNA_HORA);
		resto = milliseconds - (UNA_HORA * horas);
		Long minutos = new Long(resto / UN_MINUTO);
		
		return formater.format(horas) + ":" + formater.format(minutos);
	}
	
	/**
	 * Formatea un long en tiempo con el formato HH:MM:SS
	 * @param milliseconds que se desea formatear
	 * @return String con las horas, minutos y segundos
	 */
	public static String formatTime_WithSeconds (Long milliseconds){
		Long resto;
		
		Long horas = new Long(milliseconds / UNA_HORA);
		resto = milliseconds - (UNA_HORA * horas);
		Long minutos = new Long(resto / UN_MINUTO);
		resto = resto - (UN_MINUTO * minutos);
		Long segundos = new Long(resto / UN_SEGUNDO);
		
		return formater.format(horas) + ":" + formater.format(minutos) + ":" + formater.format(segundos);
	}

}
