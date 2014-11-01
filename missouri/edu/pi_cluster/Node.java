package missouri.edu.pi_cluster;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Node{
    
    public static void main( String[] args ) {
    	
        int               MASTER_PORT = 2000;
        String            host        = "10.100.100.2";
        DatagramSocket    socket      = null;
        InetAddress       address     = null;
        String            msg         = null;
        
    	while(true){
    		
	    	// Create connection with master
	    	try {
				socket  = new DatagramSocket(MASTER_PORT);
				address = InetAddress.getByName(host);
				System.out.println("Connected on Port " + MASTER_PORT);
			} catch (IOException e) {
				Log.write(e.getStackTrace());
				e.printStackTrace();
				break;
			}
	    	
	    	// Temperature Thread Creation
			new TemperatureThread(socket, address, MASTER_PORT).start();
	    	
	    	// Handle messages
    		while(true){
    			DatagramPacket packet = new DatagramPacket(new byte[100],100);
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
