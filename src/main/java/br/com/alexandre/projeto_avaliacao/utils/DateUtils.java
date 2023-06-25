package br.com.alexandre.projeto_avaliacao.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateUtils {

private static DateTimeFormatter dtfBR = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	
	
	public static LocalDate strToLocalDate(String dateStr) {
		return LocalDate.parse(dateStr, dtfBR);
	}
	
	public static String LocalDateToStr(LocalDate date) {
		return dtfBR.format(date);
	}
}
