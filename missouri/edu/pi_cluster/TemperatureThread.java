package missouri.edu.pi_cluster;

import java.io.IOException;
import com.pi4j.system.SystemInfo;;

public class TemperatureThread extends Thread {
	
	private final float MAX_TEMP    = 60;
	private       float temperature = 0;
	
	public TemperatureThread(){
		super();
		setDaemon(true);
	}
	
	@Override
	public void run(){
		while(true){
			try {
				temperature = SystemInfo.getCpuTemperature();
				if(temperature > MAX_TEMP){
					Log.write("Overtemp @ " + temperature);
				}
				sleep(5000);

			} catch (NumberFormatException e) {
				Log.write(e.getStackTrace());
			} catch (IOException e) {
				Log.write(e.getStackTrace());
			} catch (InterruptedException e) {
				Log.write(e.getStackTrace());
			}
		}
	}
}
