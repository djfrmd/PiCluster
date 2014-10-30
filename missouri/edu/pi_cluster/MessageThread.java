package missouri.edu.pi_cluster;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.UnknownHostException;

public class MessageThread extends Thread {

	public MessageThread(String message, DatagramSocket socket){
		super();
		this.message = message;
		start();
	}
	
	@Override
	public void run(){
		
		System.out.println(message);
		
		try {
			reply(message);
		} catch (UnknownHostException e) {
			Log.write(e.getStackTrace());
		} catch (IOException e) {
			Log.write(e.getStackTrace());
		}
		
	}
	
	private void reply(String msg) throws IOException{}
	
	@SuppressWarnings("unused")
	private void boardReset(){
		 /* Brandon, this is a stub for the board reset functionality
		  * You can change the input and output parameters as you see
		  * fit.  I will remove the unused warning when I create the 
		  * parser.
		  */
	}
	
	private String         message;
}
