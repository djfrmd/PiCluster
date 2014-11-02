package missouri.edu.pi_cluster;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Node{
    
    public static void main( String[] args ) {
    	
        int            MASTER_PORT = 2000;
        String         host        = "10.100.100.2";
        DatagramSocket socket      = null;
        InetAddress    address     = null;
        MessageList    msgList     = new MessageList();
        final int      timeout_ms  = 10000;
        
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
			new RxThread(socket, msgList).start();
			new TxThread(socket, address, MASTER_PORT, msgList).start();
			
			// Do nothing while the socket is open
    		while(!socket.isClosed()){
    		    try {
                    Thread.sleep(timeout_ms);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
    		}
	    	
    	}
    	
    }
    
	public static final int FAN      = 0;
	public static final int RST_ROW  = 16;
	public static final int RST_COL  = 15;
	public static final int RST_UNIT = 27;
    
}
