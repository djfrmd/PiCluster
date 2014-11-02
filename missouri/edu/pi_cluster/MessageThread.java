package missouri.edu.pi_cluster;

import java.io.IOException;

public class MessageThread extends Thread {

    public MessageThread(String msg, MessageList msgList) {
        super("MsgThread");
        this.message = msg;
        this.messages = msgList;
    }

    @Override
    public void run() {

        String[] args = message.split(":");
        switch (args[0].trim()) {
            case "exit":
                exit();
                break;
            case "reset":
                system("shutdown -r now");
                break;
            case "shutdown":
                system("shutdown -h now");
                break;
            default:
                System.out.println(args[0]);
                Log.write("Unknown message: " + args[0]);
                break;
        }

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

    private MessageList messages   = null;
    private String      message    = null;
    private final int   timeout_ms = 10000;

}
