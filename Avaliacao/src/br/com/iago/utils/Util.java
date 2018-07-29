package br.com.iago.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Util {

	public static String formatDateToBr(Date date) {

		SimpleDateFormat fd = new SimpleDateFormat("dd/MM/yyyy");
		return fd.format(date);

	}
	
	public static String formatDateToString(Date date) {
		
		SimpleDateFormat fd = new SimpleDateFormat("yyyy-MM-dd");
		return fd.format(date);
		
	}

}
