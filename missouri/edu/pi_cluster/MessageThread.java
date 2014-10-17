package missouri.edu.pi_cluster;

import java.io.PrintWriter;

public class MessageThread extends Thread {

	public MessageThread(String msg, PrintWriter out){
		super();
		message = msg;
		output  = out;
		start();
	}
	
	@Override
	public void run(){
		
		System.out.println(message);
		reply(message);
		
	}
	
//	private String[] parseMessage(){
//		String[] returnStr = message.split(".");
//		return returnStr;
//	}
	
	private void reply(String msg){
		output.println(msg);
	}
	
	private String      message;
	private PrintWriter output;
}