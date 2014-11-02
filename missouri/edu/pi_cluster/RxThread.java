package missouri.edu.pi_cluster;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class RxThread extends Thread {

	public RxThread(DatagramSocket socket, MessageList msgList){
		super("RxThread");
		this.socket   = socket;
		this.messages = msgList;
	}
	
	@Override
	public void run(){
	    
		while(!socket.isClosed()){
		    
		    String msg;
		    
		    // Receive messages
			try{
				byte[] buf = new byte[100];
				DatagramPacket packet = new DatagramPacket(buf, buf.length);
				socket.receive(packet);
				
				// Exit on "exit"
				msg = new String(packet.getData());
				if(msg.trim().equals("exit")){
				    socket.close();
				    System.exit(1);;
				}
				
				// Create a MessageThread to handle messages
				new MessageThread(msg, messages).start();	
				
			}catch(IOException e){
				e.printStackTrace();
			}
		}

	}
	
	private DatagramSocket socket   = null;
	private MessageList    messages = null;
	
}
