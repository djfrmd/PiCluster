package missouri.edu.pi_cluster;

import java.io.IOException;

import com.pi4j.io.gpio.PinState;

public class MessageThread extends Thread {

    public MessageThread(String msg, MessageList msgList) {
        super("MsgThread");
        this.message = msg.trim();
        this.messages = msgList;
    }

    @Override
    public void run() {

        String[] args = message.split(":");
        String cmd = args[0].trim();
        switch (cmd) {
            case "reset":
                reset(args);
                break;
            case "shutdown":
                system("shutdown -h now");
                break;
            default:
                System.out.println(cmd);
                Log.write("Unknown message: " + cmd);
                break;
        }

    }

    private void reset(String[] cmd){
        
        // Verify message format
        if(cmd.length < 2){
            system("shutdown -r now");
            System.exit(1);
        }
        String[] ip = cmd[1].split("\\.");
        if(ip.length != 4){
            Log.write("Unknown ip: " + cmd[1]);
            System.out.println("Unknown ip: " + cmd[1]);
            return;
        }

        // Get the corresponding states of the reset pins
        int[]      vals   = new int[4];
        PinState[] states = new PinState[4];
        try{
            vals[UNIT] = Integer.parseInt(ip[UNIT]);
            vals[ROW]  = Integer.parseInt(ip[ROW]);
            vals[COL]  = Integer.parseInt(ip[COL]);
            
        }catch(NumberFormatException e){
            Log.write("Unknown ip: " + cmd[1]);
            return;
        }

        // Set corresponding Pin high if matches unit, row, or col
        states[UNIT] = Node.getUnit() == vals[UNIT] ? HIGH : LOW;
        states[ROW]  = Node.getRow()  == vals[ROW]  ? HIGH : LOW;
        states[COL]  = Node.getCol()  == vals[COL]  ? HIGH : LOW;

        // Check for entire unit, row, or col reset
        states[UNIT] = vals[ROW] == -1 || vals[COL]  == -1 ? HIGH : states[UNIT];
        states[ROW]  = vals[COL] == -1 || vals[UNIT] == -1 ? HIGH : states[ROW];
        states[COL]  = vals[ROW] == -1 || vals[UNIT] == -1 ? HIGH : states[COL];

        // If all pins are high, reset this node
        if( states[UNIT] == HIGH && states[ROW] == HIGH && states[COL] == HIGH){
            system("shutdown -r now");
            System.exit(1);
        }

        // Set pins high for 5 sec
        GPIO.setPin(Node.RST_UNIT, states[UNIT]);
        GPIO.setPin(Node.RST_ROW, states[ROW]);
        GPIO.setPin(Node.RST_COL, states[COL]);

        try {
            sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Set pins low
        GPIO.setPin(Node.RST_UNIT, LOW);
        GPIO.setPin(Node.RST_ROW, LOW);
        GPIO.setPin(Node.RST_COL, LOW);

    }

    private void exit() {
        reply("exit");
        try {
            sleep(timeout_ms);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void system(String msg) {
        try {
            Runtime.getRuntime().exec(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
        exit();
    }

    private void reply(String msg) {
        messages.add(msg);
    }

    private MessageList    messages   = null;
    private String         message    = null;
    private final int      timeout_ms = 10000;
    private final PinState HIGH       = PinState.HIGH;
    private final PinState LOW        = PinState.LOW;
    private final int      UNIT       = 1;
    private final int      ROW        = 2;
    private final int      COL        = 3;

}
