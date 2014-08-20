package missouri.edu.pi_cluster;

import java.util.ArrayList;

public final class Master extends Node{

    public ArrayList<RemoteNodeInfo> nodes = new ArrayList<RemoteNodeInfo>(24);
    public Fan                       fan   = new Fan();
       
    public void resetNode(/*Node*/){
        // TODO: Implement 1A3UxSDD1 Reset Driver
    }
    
    public void fanControl(){
        // TODO: Implement 1A3UxSDD3 Temperature/Fan Control Driver
    }
    
    public void getSystemStatus(){
        // TODO: Implement 1A3UxSDD2 System Status Driver 
    }
    
}
