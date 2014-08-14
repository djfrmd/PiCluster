package missouri.edu.pi_cluster;

import java.io.IOException;

import com.pi4j.system.NetworkInfo;
import com.pi4j.system.SystemInfo;

public final class Main{

    public static void main( String[] args ){
        
        try{
            System.out.println("Serial #: " + SystemInfo.getSerial());
            for( String address : NetworkInfo.getIPAddresses()){
                System.out.println("IP Address: " + address);
            }
            System.out.println("Temperature: " + SystemInfo.getCpuTemperature());
            
        }catch(IOException | InterruptedException e){
            
            System.out.println(e.getMessage());
        }        
    }

}
