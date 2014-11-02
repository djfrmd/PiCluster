package missouri.edu.pi_cluster;

import java.io.IOException;


import com.pi4j.io.gpio.PinState;
//import com.pi4j.io.gpio.PinState;
import com.pi4j.system.SystemInfo;

public class TemperatureThread extends Thread {
	
	private MessageList messages   = null;
	private boolean     fan_on     = false;
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
					if(!fan_on){
						GPIO.setPin(Node.FAN, PinState.HIGH);
						fan_on = !fan_on;
					}
					Log.write("Overtemp @ " + temp);
				}else if(temp < MAX_TEMP && fan_on){
					GPIO.setPin(Node.FAN, PinState.LOW);
					fan_on = !fan_on;
				}

			} catch (NumberFormatException | IOException | InterruptedException e) {
				Log.write(e.getStackTrace());
			}
		}
	}
}
