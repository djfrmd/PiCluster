package missouri.edu.pi_cluster;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;


public class Log {

	private Log(){
		// Exists merely to stop default constructor
	}
	
	public static synchronized void write(StackTraceElement[] ste){
		int i = 0;
		StringBuilder msg = new StringBuilder(ste[0].toString() + System.lineSeparator());
		for( i = 1; i < ste.length - 1; i++){
			msg.append('\t' + ste[i].toString() + System.lineSeparator());
		}
		msg.append(ste[i].toString());
		write(msg.toString());
	}
	
	public static synchronized void write(String msg){
		
		try {
			logFile = new FileWriter("/var/log/picluster.log", true);
			logFile.append(getData() + msg + System.lineSeparator());
			logFile.close();
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}
	
	private static String getData(){
		StringBuilder formattedDate = new StringBuilder();
		formattedDate.append(month[Calendar.MONTH] + " ");
		formattedDate.append(Calendar.DAY_OF_MONTH + " ");
		formattedDate.append(Calendar.HOUR + ":" + Calendar.MINUTE);
		formattedDate.append(":" + Calendar.SECOND + " ");
		return formattedDate.toString();
	}
	
	private static       FileWriter logFile  = null;
	private final static String[]   month    = { "Jan", "Feb", "Mar", "Apr", "May", "Jun",
			                                     "Jul", "Aug", "Sep", "Oct", "Nov", "Dec" };
	
}
