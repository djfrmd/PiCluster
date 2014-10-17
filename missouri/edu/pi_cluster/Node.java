package missouri.edu.pi_cluster;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

//import com.pi4j.system.NetworkInfo;

public class Node{
    
    public static void main( String[] args ) {
    	
        int            MASTER_PORT = 2000;
        String         MASTER      = "10.100.100.2";
        Socket         socket      = null;
        PrintWriter    output      = null;
        BufferedReader input       = null;
        
    	// Temperature Thread Creation
    	TemperatureThread tThread = new TemperatureThread();
    	tThread.start();
    	
    	while(true){
    		
	    	// Create connection with master
	    	try {
				socket = new Socket(MASTER, MASTER_PORT);
				System.out.println("Connected on Port " + MASTER_PORT);
				
				output = new PrintWriter(socket.getOutputStream(), true); 
				input  = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			} catch (IOException e) {
				Log.write(e.getStackTrace());
				System.exit(1);
			}
	    	
	    	// Handle messages
	    	try {
				while((msg = input.readLine()) != null){
					new MessageThread(msg, output);
				}
			} catch (IOException e) {
				Log.write(e.getStackTrace());
			}
	    	
	    	// Close socket and try to reconnect
	    	try {
				socket.close();
			} catch (IOException e) {
				Log.write(e.getStackTrace());
			}
	    	
    	}
    	
    }
    
}
