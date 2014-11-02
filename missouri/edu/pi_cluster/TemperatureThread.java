package missouri.edu.pi_cluster;

import java.io.IOException;
import com.pi4j.system.SystemInfo;

public class TemperatureThread extends Thread {
	
	private MessageList messages   = null;
	private final float MAX_TEMP   = 60;
	private       float temp       = 0;
	private final int   timeout_ms = 30000;
	
	public TemperatureThread(MessageList msgList){
		super("TempThread");
		messages = msgList;
		setDaemon(true);
	}
	
	@Override
	public void run(){
		while(true){
			try {
				sleep(timeout_ms);
				temp   = SystemInfo.getCpuTemperature();
				messages.add("temp:" + temp);
				if(temp > MAX_TEMP){
					Log.write("Overtemp @ " + temp);
				}

			} catch (NumberFormatException | IOException | InterruptedException e) {
				Log.write(e.getStackTrace());
			}
		}
	}
}
