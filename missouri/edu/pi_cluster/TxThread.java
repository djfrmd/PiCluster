package missouri.edu.pi_cluster;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class TxThread extends Thread {

    public TxThread(DatagramSocket socket,
                    InetAddress    address,
                    int            port,
                    MessageList    msgList
                   )
    {
        
        this.socket   = socket;
        this.address  = address;
        this.port     = port;
        this.messages = msgList;
        
    }
    
    @Override
    public void run(){
        
        String         msg    = null;
        DatagramPacket packet = null;
        while(!socket.isClosed()){
          
            if(!messages.isEmpty()){
                
                // Send a messages
                msg    = messages.remove();
                packet = new DatagramPacket(msg.getBytes(),
                                            msg.getBytes().length,
                                            address,
                                            port
                                           );
                
                try {
                    socket.send(packet);
                } catch (IOException e) {
                    Log.write(e.getStackTrace());
                    socket.close();
                    break;
                }
                
            }
            
        }
        
    }
    
    private DatagramSocket socket   = null;
    private InetAddress    address  = null;
    private MessageList    messages = null;
    private int            port     = 0;
}
