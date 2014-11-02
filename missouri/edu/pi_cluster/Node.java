package missouri.edu.pi_cluster;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetAddress;
import com.pi4j.io.gpio.PinState;

public class Node{
    
    public static void main( String[] args ) {
    	
        // Set the static UNIT, ROW, & COL
        if(args.length != 1){
            System.out.println("Must provide only an ip address");
            System.exit(1);
        }
        String[] ip = args[0].split("\\.");
        if(ip.length != 4){
            System.out.println("Unknown ip address " + args[0]);
            System.out.println("Number of octets = " + ip.length);
            System.out.println("Number of args = " + args.length);
            System.exit(1);
        }
        try{
            UNIT = Integer.parseInt(ip[1]);
            ROW  = Integer.parseInt(ip[2]);
            COL  = Integer.parseInt(ip[3]);
        }catch(NumberFormatException e){
            System.out.println("Unknown ip address " + args[0]);
            System.exit(1);
        }

        // Setup GPIO
        GPIO.setPin(Node.FAN, PinState.LOW);

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
				System.out.println("Unit " + UNIT + ", Row " + ROW + ", Column " + COL);
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

    public static int getUnit(){ return UNIT; }
    public static int getRow(){ return ROW; }
    public static int getCol(){ return COL; }

    private static       int UNIT     = 0;
    private static       int ROW      = 0;
    private static       int COL      = 0;
	public  static final int FAN      = 0;
	public  static final int RST_ROW  = 16;
	public  static final int RST_COL  = 15;
	public  static final int RST_UNIT = 27;

}
