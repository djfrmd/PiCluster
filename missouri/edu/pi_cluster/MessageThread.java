package missouri.edu.pi_cluster;

import java.io.PrintWriter;

public class MessageThread extends Thread {

	public MessageThread(String msg, PrintWriter out){
		super();
		System.out.println("Created MessageThread");
		message = msg;
		output  = out;
		start();
	}
	
	@Override
	public void run(){
		
		System.out.println("The MessageThread is running");
		System.out.println(message);
		reply(message);
		
	}
	
//	private String[] parseMessage(){
//		String[] returnStr = message.split(".");
//		return returnStr;
//	}
	
	private void reply(String msg){
		output.write(msg);
	}
	
	private String      message;
	private PrintWriter output;
}
