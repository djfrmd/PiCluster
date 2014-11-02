package missouri.edu.pi_cluster;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class MessageThread extends Thread {

	public MessageThread(DatagramSocket socket, MessageList msgList){
		super("MsgThread");
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
				
				String msg = new String(packet.getData());
				msg += ":" + packet.getAddress().getHostAddress();
				System.out.println(msg);
				handle(msg);
			}catch(IOException e){
				e.printStackTrace();
				break;
			}
		}
		socket.close();
	}
	
	private void handle(String msg){
		
		String[] args = msg.split(":");
		if(args[0].trim().equals("exit")){
			reply("exit");
		}
		
	}
	
	private void reply(String msg){
		messages.add(msg);
	}
	
	private DatagramSocket socket   = null;
	private MessageList    messages = null;
	
}
