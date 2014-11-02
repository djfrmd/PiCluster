package missouri.edu.pi_cluster;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Node{
    
    public static void main( String[] args ) {
    	
        int            MASTER_PORT = 2000;
        String         host        = "10.100.100.2";
        DatagramSocket socket      = null;
        DatagramPacket packet      = null;
        InetAddress    address     = null;
        String         msg         = null;
        MessageList    msgList     = new MessageList();
        
    	while(true){
    		
	    	// Create socket connection with master
	    	try {
				socket  = new DatagramSocket(MASTER_PORT);
				address = InetAddress.getByName(host);
				System.out.println("Connected on Port " + MASTER_PORT);
			} catch (IOException e) {
				Log.write(e.getStackTrace());
				System.out.println(e.getMessage());
				System.exit(1);
			}
	    	
	    	// Create helper threads
			new TemperatureThread(msgList).start();
			new MessageThread(socket, msgList).start();
			
    		while(true){
    			
    			while(!msgList.isEmpty()){
    				
    				// Send all messages
    				msg    = msgList.remove();
    				if(msg.equals("exit")){
    					socket.close();
    					System.exit(1);
    				}
    				packet = new DatagramPacket(msg.getBytes(), msg.length(), address, MASTER_PORT);
    				try {
						socket.send(packet);
					} catch (IOException e) {
						Log.write(e.getStackTrace());
						socket.close();
						break;
					}
    			}
    			
    		}
	    	
    	}
    	
    }
    
}
