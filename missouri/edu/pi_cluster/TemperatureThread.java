package missouri.edu.pi_cluster;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import com.pi4j.system.SystemInfo;

public class TemperatureThread extends Thread {
	
	private DatagramSocket socket     = null;
	private InetAddress    address    = null;
	private DatagramPacket packet     = null;
	private String         msg        = null;
	private       int      port       = 0;
	private final float    MAX_TEMP   = 60;
	private       float    temp       = 0;
	private final int      timeout_ms = 30000;
	
	public TemperatureThread(DatagramSocket socket, InetAddress address, int port){
		super("TempThread");
		this.socket  = socket;
		this.address = address;
		this.port    = port;
		setDaemon(true);
	}
	
	@Override
	public void run(){
		while(true){
			try {
				sleep(timeout_ms);
				temp   = SystemInfo.getCpuTemperature();
				msg    = new String("temp:" + temp);
				packet = new DatagramPacket(msg.getBytes(), msg.getBytes().length, address, port);
				socket.send(packet);
				if(temp > MAX_TEMP){
					Log.write("Overtemp @ " + temp);
				}

			} catch (NumberFormatException | IOException | InterruptedException e) {
				Log.write(e.getStackTrace());
			}
		}
	}
}
