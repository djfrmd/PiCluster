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
		while(true){
			try{
				byte[] buf = new byte[256];
				DatagramPacket packet = new DatagramPacket(buf, buf.length);
				socket.receive(packet);
				
				// Create a MessageThread to handle messages
				new MessageThread(new String(packet.getData()), messages).start();	
				
			}catch(IOException e){
				e.printStackTrace();
				break;
			}
		}
		socket.close();
	}
	
	private DatagramSocket socket   = null;
	private MessageList    messages = null;
	
}
