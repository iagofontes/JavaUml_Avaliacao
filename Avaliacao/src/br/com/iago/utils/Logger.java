package br.com.iago.utils;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JOptionPane;

public class Logger {
	
	public static void saveLog( 
		Integer type, String message, Date date
	) {
		try {
			PrintWriter pw = new PrintWriter(new FileWriter("log.txt"));
			pw.write(createStringLog(type, message, date));
			pw.close();
		} catch (IOException ioEx) {
			ioEx.printStackTrace();
			JOptionPane.showMessageDialog(
					null, 
					"Problemas ao criar arquivo de log.", 
					"File Error", 
					JOptionPane.ERROR_MESSAGE
			);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static String createStringLog(
		Integer type, String message, Date date
	) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return "type:"+getType(type)+", date:"+sdf.format(date)+", message:"+message;
	}
	
	private static String getType(Integer type) {
		
		switch(type) {
			case 1:	return "error";
			case 2: return "warning";
			default: return "unrecognized";
		}
		
	}
	
}
