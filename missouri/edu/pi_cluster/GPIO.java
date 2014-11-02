package missouri.edu.pi_cluster;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.RaspiPin;

public class GPIO {

    private GPIO() {
        gpio = GpioFactory.getInstance();

        // Setup output pins
        fan     = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_00, PinState.LOW);
        rstRow  = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_16, PinState.LOW);
        rstCol  = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_15, PinState.LOW);
        rstUnit = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_27, PinState.LOW);

        // Set default shutdown option
        fan.setShutdownOptions(true, PinState.LOW);
        rstRow.setShutdownOptions(true, PinState.LOW);
        rstCol.setShutdownOptions(true, PinState.LOW);
        rstUnit.setShutdownOptions(true, PinState.LOW);

    }

    public static synchronized void setPin(int pin, PinState state) {
        if (instance == null) {
            instance = new GPIO();
        }
        switch (pin) {
            case Node.FAN:
                fan.setState(state);
                break;
            case Node.RST_ROW:
                rstRow.setState(state);
                break;
            case Node.RST_COL:
                rstCol.setState(state);
                break;
            case Node.RST_UNIT:
                rstUnit.setState(state);
                break;
            default:
                Log.write("Unspecified pin");
                break;
        }
    }

    private static GPIO instance                = null;
    private static GpioController gpio          = null;
    private static GpioPinDigitalOutput fan     = null;
    private static GpioPinDigitalOutput rstRow  = null;
    private static GpioPinDigitalOutput rstCol  = null;
    private static GpioPinDigitalOutput rstUnit = null;

}
