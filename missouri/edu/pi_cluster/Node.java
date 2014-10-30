package missouri.edu.pi_cluster;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.MulticastSocket;

public class Node{
    
    public static void main( String[] args ) {
    	
        int               MASTER_PORT = 2000;
        MulticastSocket   socket      = null;
        String            msg         = null;
        
    	// Temperature Thread Creation
    	TemperatureThread tThread  = new TemperatureThread();
    	tThread.start();
    	
    	while(true){
    		
	    	// Create connection with master
	    	try {
				socket = new MulticastSocket(MASTER_PORT);
				System.out.println("Connected on Port " + MASTER_PORT);
			} catch (IOException e) {
				Log.write(e.getStackTrace());
				break;
			}
	    	
	    	// Handle messages
    		while(true){
    			DatagramPacket packet = new DatagramPacket(new byte[256],256);
    			try {
					socket.receive(packet);
				} catch (IOException e) {
					e.printStackTrace();
					System.out.println("Socket being closed");
					socket.close();
					break;
				}
    			msg = new String(packet.getData());
    			if(msg.isEmpty()){
    				socket.close();
    				System.exit(4);
    			}
    			new MessageThread(new String(packet.getData()), socket);
    		}
	    	
    	}
    	
    }
    
}
